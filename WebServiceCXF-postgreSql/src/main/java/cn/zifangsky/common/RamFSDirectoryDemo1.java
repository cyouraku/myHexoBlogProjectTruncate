package cn.zifangsky.common;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * 批量索引，而无需检索的情况下，先把document存到RAMDirectory中，当达到一定数量之后，再把这些索引一次性加入FSDirectory里
 * @author HD800241
 *
 */
public class RamFSDirectoryDemo1 {
    public static void main(String[] args) throws IOException {
        RAMDirectory ramDirectory = new RAMDirectory();
        IndexWriter ramWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("F:/index_RAM_FS"));
        IndexWriter fsWriter = new IndexWriter(fsDirectory, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode
                .CREATE_OR_APPEND));
        //简单起见，这里的数量都比较少，例如索引10000个document，每100个document作为一批
        int tempCount = 0;
        for (int i = 0; i < 10000; i++) {
            Document document = new Document();
            document.add(new StringField("key", String.valueOf(i), Field.Store.YES));
            ramWriter.addDocument(document);
            tempCount++;
            if (tempCount % 100 == 0) {
                ramWriter.commit();
                ramWriter.close();
                fsWriter.addIndexes(ramDirectory);
                ramDirectory.close();
                fsWriter.commit();
                ramDirectory = new RAMDirectory();
                ramWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));
            }
        }
        //退出时确保RAMDirectory中内容都被加入本地
        if (ramWriter != null) {
            ramWriter.commit();
            ramWriter.close();
            fsWriter.addIndexes(ramDirectory);
            ramDirectory.close();
        }
        IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(fsWriter.getDirectory()));
        fsWriter.close();
        int count = indexSearcher.count(new MatchAllDocsQuery());
        System.out.println(count);
    }
}