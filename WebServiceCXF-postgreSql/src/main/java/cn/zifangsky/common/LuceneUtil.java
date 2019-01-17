package cn.zifangsky.common;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import cn.zifangsky.model.CmsArticleInfo;

public class LuceneUtil {

	private Logger logger = Logger.getLogger("cn.zifangsky.common.LuceneUtil");

	/**
	 * Create index of input text to ram and search keyword in specialized field.
	 * @param inputText
	 * @param fieldNm
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int createIndexToRam(String inputText,String fieldNm,String keyword) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
	    // Store the index in memory:
	    Directory directory = new RAMDirectory();
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    Document doc = new Document();
	    doc.add(new Field(fieldNm, inputText, TextField.TYPE_STORED));
	    iwriter.addDocument(doc);
	    iwriter.close();
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    Query query = parser.parse(keyword);
	    ScoreDoc[] hits = isearcher.search(query, 1000, new Sort()).scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
			Document d = isearcher.doc(hits[i].doc);
			logger.info(String.format("%d : keyword = %s ; text = %s ; \n",i, keyword, d.get(fieldNm)));
		}
		ireader.close();
	    directory.close();
	    return hits.length;
	}

	/**
	 * Create index of CMS article info list to ram and search keyword list in specialized field.
	 * @param cmsArticleInfoList
	 * @param keywordList
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int createIndexToRamForCmsArticleInfo(List<CmsArticleInfo> cmsArticleInfoList,List<String> keywordList, String fieldNm) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
	    // Store the index in memory:
	    Directory directory = new RAMDirectory();
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    for(CmsArticleInfo article : cmsArticleInfoList){
	    	Document doc = new Document();
	    	doc.add(new StringField(CmsArticleConstants.ARTICLE_ID, article.getArticle_id(), Field.Store.YES));
	    	doc.add(new TextField(CmsArticleConstants.ARTICLE_TITLE, article.getArticle_title(),  Field.Store.YES));
	    	doc.add(new TextField(CmsArticleConstants.ARTICLE_BODY_PLAIN, article.getArticle_body_plain(),  Field.Store.YES));
	    	iwriter.addDocument(doc);
	    }
	    iwriter.close();
	    // Result output process
	    StringBuilder sb = new StringBuilder();
	    List<String> resultList = new ArrayList<String>();
	    int cnt = 0;
	    // Now search the index:
		IndexReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    for(String keyword : keywordList){
	    	cnt++;
	    	sb.append(String.format("%d %s : ", cnt, keyword.replace("\"","")));
		    Query query = parser.parse(keyword);
	    	ScoreDoc[] hits = isearcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;
	    	if(hits.length <=0){
	    		sb.append("対象記事なし");
	    	}
		    // Iterate through the results:
		    for (int i = 0; i < hits.length; i++) {
				Document d = isearcher.doc(hits[i].doc);
//				logger.info(String.format("%d : keyword = %s ; article id = %s ;  text = %s ; \n",i, keyword, d.get(CmsArticleConstants.ARTICLE_ID),d.get(fieldNm)));
				String articleId = d.get(CmsArticleConstants.ARTICLE_ID).replace("\"","");
				sb.append(String.format("%s ,",articleId));
			}
		    sb.append("\n");
		    resultList.add(sb.toString());
	    }
	    System.out.println("内存中索引文件：" + Arrays.asList(directory.listAll()));
	    //Print keyword : article_id
	    for(String str : resultList){
	    	 System.out.println(str);
	    }
	    directory.close();
	    return 0;
	}


	/**
	 * Create index of input text to file and search keyword
	 * @param filePath
	 * @param folderNm
	 * @param inputText
	 * @param fieldNm
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int createIndexToFile(String filePath,String folderNm,String inputText,String fieldNm,String keyword) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
	    // To store an index on disk, use this instead:
		Path path = FileSystems.getDefault().getPath(filePath,folderNm);
		if(FileUtil.checkFile(path)){
			logger.info(String.format("file path already exists! %s \n", path).toString());
		}
	    Directory directory = FSDirectory.open(path);

	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    Document doc = new Document();
	    doc.add(new Field(fieldNm, inputText, TextField.TYPE_STORED));
	    iwriter.addDocument(doc);
	    iwriter.close();
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    Query query = parser.parse(keyword);
	    ScoreDoc[] hits = isearcher.search(query, 1000, new Sort()).scoreDocs;
	    ireader.close();
	    directory.close();
	    return hits.length;
	}


	/**
	 * Create index of input text list to file under specialized folder and search keyword in specialized field.
	 * @param filePath
	 * @param folderNm
	 * @param inputText
	 * @param fieldNm
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int createIndexToFileForAll(String filePath,String folderNm,List<String> inputText,String fieldNm,String keyword) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
	    // To store an index on disk, use this instead:
		Path path = FileSystems.getDefault().getPath(filePath,folderNm);
		if(FileUtil.checkFile(path)){
			logger.info(String.format("file path already exists! %s \n", path).toString());
		}
	    Directory directory = FSDirectory.open(path);

	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    for(String str : inputText){
	    	Document doc = new Document();
	    	doc.add(new Field(fieldNm, str, TextField.TYPE_STORED));
	    	iwriter.addDocument(doc);
	    }
	    iwriter.close();
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    Query query = parser.parse(keyword);
	    ScoreDoc[] hits = isearcher.search(query, 1000, new Sort()).scoreDocs;
	    ireader.close();
	    directory.close();
	    return hits.length;
	}

	/**
	 * Create index of CMS article info list to file and search keyword list in article_title and article_body_plain fields.
	 * Search cms article info list by keyword and store index to file
	 * @param filePath
	 * @param folderNm
	 * @param cmsArticleInfoList
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int createIndexToFileForCmsArticleInfo(String filePath,List<CmsArticleInfo> cmsArticleInfoList,List<String> keywordList) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
	    // To store an index on disk, use this instead:
		Path path = FileSystems.getDefault().getPath(filePath);
		if(FileUtil.checkFile(path)){
			logger.info(String.format("file path already exists! %s \n", path).toString());
		}
	    Directory directory = FSDirectory.open(path);
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    for(CmsArticleInfo article : cmsArticleInfoList){
	    	Document doc = new Document();
//	    	doc.add(new Field(CmsArticleConstants.ARTICLE_ID, article.getArticle_id(), TextField.TYPE_STORED));
//	    	doc.add(new Field(CmsArticleConstants.ARTICLE_TITLE, article.getArticle_title(), TextField.TYPE_STORED));
//	    	doc.add(new Field(CmsArticleConstants.ARTICLE_BODY_PLAIN, article.getArticle_body_plain(), TextField.TYPE_STORED));
	    	doc.add(new StringField(CmsArticleConstants.ARTICLE_ID, article.getArticle_id(), Field.Store.YES));
	    	doc.add(new TextField(CmsArticleConstants.ARTICLE_TITLE, article.getArticle_title(),  Field.Store.YES));
	    	doc.add(new TextField(CmsArticleConstants.ARTICLE_BODY_PLAIN, article.getArticle_body_plain(),  Field.Store.YES));
	    	iwriter.addDocument(doc);
	    }
	    iwriter.close();

	    // Now search the index:
	    for(String str: keywordList){
	    	this.readIndexFromFile2(filePath, CmsArticleConstants.ARTICLE_TITLE, str);
	    }
	    for(String str: keywordList){
	    	this.readIndexFromFile2(filePath, CmsArticleConstants.ARTICLE_BODY_PLAIN, str);
	    }
	    directory.close();
	    return 0;
	}

	/**
	 * Read index from file and search by keyword in specialized field with page size of 10.
	 * @param filePath
	 * @param inputText
	 * @param fieldNm
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int readIndexFromFile(String filePath,String fieldNm,String keyword) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
		IndexReader ireader = DirectoryReader.open(FSDirectory.open(Paths.get(filePath)));
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    Query query = parser.parse(keyword);
	    int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
    	isearcher.search(query,  collector);
    	ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = isearcher.doc(docId);
			logger.info(String.format("%d : keyword = %s ; article id = %s ;  text = %s ; \n",i, keyword, d.get(CmsArticleConstants.ARTICLE_ID),d.get(fieldNm)));
		}
	    ireader.close();
	    return 0;
	}


	/**
	 * Read index from file and search by keyword in specialized field without pageing.
	 * @param filePath
	 * @param fieldNm
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public int readIndexFromFile2(String filePath,String fieldNm,String keyword) throws IOException, ParseException{
		Analyzer analyzer = new StandardAnalyzer();
		IndexReader ireader = DirectoryReader.open(FSDirectory.open(Paths.get(filePath)));
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser(fieldNm, analyzer);
	    Query query = parser.parse(keyword);
    	ScoreDoc[] hits = isearcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
			Document d = isearcher.doc(hits[i].doc);
			logger.info(String.format("%d : keyword = %s ; article id = %s ;  text = %s ; \n",i, keyword, d.get(CmsArticleConstants.ARTICLE_ID),d.get(fieldNm)));
		}
	    ireader.close();
	    return 0;
	}

	/**
	 * Sample of lucene usage.
	 * Create index to ram and search input query string in title field.
	 * @param querystr
	 * @throws IOException
	 * @throws ParseException
	 */
	public void helloLucene(String querystr) throws IOException, ParseException {
		// 0. Specify the analyzer for tokenizing text.
		//    The same analyzer should be used for indexing and searching
		StandardAnalyzer analyzer = new StandardAnalyzer();

		// 1. create the index
		Directory index = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig();

		IndexWriter w = new IndexWriter(index, config);
		addDoc(w, "Lucene in Action", "193398817");
		addDoc(w, "Lucene for Dummies", "55320055Z");
		addDoc(w, "Managing Gigabytes", "55063554A");
		addDoc(w, "The Art of Computer Science", "9900333X");
		w.close();

		// 2. query
		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		Query q = null;
		try {
			q = new QueryParser("title", analyzer).parse(querystr);
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}

		// 3. search
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
		}

		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close();
	}

	/**
	 * Method to add fields into document.
	 * @param w
	 * @param title
	 * @param isbn
	 * @throws IOException
	 */
	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));

		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}

}
