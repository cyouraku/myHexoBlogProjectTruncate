package cn.zifangsky.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.SnapshotDeletionPolicy;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;

/**
 * 索引的同时需要搜索，前提是内存总量大于索引文件总量，如果要求新加入的索引对搜索可见，即实时搜索，要怎么做呢？
 * 显然实时搜索需要writer和searcher共用同一份索引
 * ，同时要定时的将内存中索引备份到文件系统，否则机器一旦宕机，内存中所有的索引文件都将丢失。代码实现也很简单
 * ，在备份的时候，可以使用调度线程去进行备份操作，同时还不影响主线程继续接受索引请求
 * ；备份策略有两种：全量和增量，增量直接比较文件名，将新增文件拷贝到文件系统，同时删除已过时的索引文件即可。
 *
 * @author HD800241
 *
 */
public class RamFSDirectoryDemo2 {
    private static IndexWriter ramWriter;
    private static RAMDirectory ramDirectory;
    private static final Backup backup = new Backup();
    public static void main(String[] args) throws IOException {
        //将文件系统中索引文件加载到内存中
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("F:/index_RAM_FS"));
        ramDirectory = new RAMDirectory(fsDirectory, IOContext.READONCE);
        fsDirectory.close();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new WhitespaceAnalyzer());//import lucene-analyzers-common-7.3.0.jar
        indexWriterConfig.setIndexDeletionPolicy(new SnapshotDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy()));
        ramWriter = new IndexWriter(ramDirectory, indexWriterConfig);
        //先添加一条记录
        Document document = new Document();
        document.add(new StringField("first", "first", Field.Store.YES));
        ramWriter.addDocument(document);
        ramWriter.commit();
        //定时备份线程
        ScheduledExecutorService backupThread = Executors.newSingleThreadScheduledExecutor();
        backupThread.scheduleAtFixedRate(backup, 0, 2, TimeUnit.SECONDS);
        //可以继续接受索引请求
        document = new Document();
        document.add(new StringField("key", "key", Field.Store.YES));
        ramWriter.addDocument(document);
        ramWriter.commit();
        //等待索引拷贝完成
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //接受搜索请求，可实现实时搜索
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(ramWriter.getDirectory()));
        int count = indexSearcher.count(new MatchAllDocsQuery());
        System.out.println("total hits: " + count);
        System.out.println("内存中索引文件：" + Arrays.asList(ramDirectory.listAll()));
        //查看备份索引是否完整，此处有一个注意点，如果需要在备份索引上打开searcher，那么在备份索引文件的时候需要先备份其它文件，最后再备份segments_N文件
        //因为开searcher的时候，会先加载segments_N文件，这种方式可以保证加载完segments_N文件之后，再加载其它文件一定成功
        fsDirectory = FSDirectory.open(Paths.get("F:/index_RAM_FS"));
        System.out.println("备份中索引文件：" + Arrays.asList(fsDirectory.listAll()));
        backupThread.shutdown();
    }

    static class Backup implements Runnable {
        public void run() {
            IndexWriterConfig config;
            SnapshotDeletionPolicy indexDeletionPolicy = null;
            IndexCommit snapshot = null;
            try {
                ramWriter.commit();
                config = (IndexWriterConfig) ramWriter.getConfig();
                indexDeletionPolicy = (SnapshotDeletionPolicy) config.getIndexDeletionPolicy();
                snapshot = indexDeletionPolicy.snapshot();
                config.setIndexCommit(snapshot);
                Collection<String> fileNames = snapshot.getFileNames();
                Path path = Paths.get("F:/index_RAM_FS");
                //全量增量任选一种即可
                boolean b = incrementalBackup(fileNames, path);
                //boolean b = fullBackup(fileNames, path);
                if (!b) {
                    System.err.println("Backup occurs error!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            } finally {
                try {
                    indexDeletionPolicy.release(snapshot);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }
        }
        private boolean fullBackup(Collection<String> fileNames, Path path) {
            Objects.requireNonNull(path);
            Directory to;
            try {
                to = FSDirectory.open(path);
                // 全量备份，直接清空拷贝
                for (File file : path.toFile().listFiles()) {
                    file.delete();
                }
                for (String fileName : fileNames) {
                    to.copyFrom(ramDirectory, fileName, fileName, IOContext.DEFAULT);
                }
                to.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        private boolean incrementalBackup(Collection<String> fileNames, Path path) {
            // 增量备份，稍微复杂一些，比较文件名，将ramDirectory中新增索引拷贝过去，同时将ramDirectory中不存在的索引但是在path中存在的旧索引删除
            Objects.requireNonNull(path);
            //fileNames被IndexCommit引用，需要重新构造set集合，进行移除操作
            Set<String> files = new HashSet<>(fileNames);
            for (File file : path.toFile().listFiles()) {
                if (files.contains(file.getName())) {
                    //该索引已存在，则不拷贝
                    files.remove(file.getName());
                } else {
                    //删除已经过时的索引
                    file.delete();
                }
            }
            //拷贝全部新增索引
            try {
                Directory to = FSDirectory.open(path);
                for (String file : files) {
                    to.copyFrom(ramDirectory, file, file, IOContext.DEFAULT);
                }
                to.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
