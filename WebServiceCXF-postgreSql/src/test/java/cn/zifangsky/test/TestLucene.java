package cn.zifangsky.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import cn.zifangsky.common.CmsArticleConstants;
import cn.zifangsky.common.FileUtil;
import cn.zifangsky.common.LuceneUtil;
import cn.zifangsky.manager.LucenceApiManager;
import cn.zifangsky.manager.impl.LucenceApiManagerImpl;
import cn.zifangsky.model.CmsArticleInfo;

public class TestLucene {

	private static final String COMMA = ",";
	private static final String INDEX_CSV_FILE_PATH3 = "C:\\temp\\CSV\\OUTPUT3\\";
	private static final String CSV_FILE_PATH = "C:\\temp\\CSV\\CmsArticleInfoList.csv";
	private static final String CSV_FILE_PATH2 = "C:\\temp\\CSV\\KeywordList.csv";

	private static final String FILE_PATH = "C:\\data\\log\\tomcat\\d2puser\\applog\\";//TEST_LUCENE
	private static final String INDEX_FILE_PATH = "C:\\data\\log\\tomcat\\d2puser\\applog\\TEST_LUCENE\\";
	private static final String FOLDER = "TEST_LUCENE";
	private static final String IPUT_TEXT = "北海道で感染性胃腸炎患者発生、富山県で食中毒発生【感染症・食中毒情報 No.3806（2016/3/22）】";
	private static final String FIELD_NAME = "title";
	private static final String KEYWORD = "北海道";
	private LuceneUtil lu = new  LuceneUtil();
	private LucenceApiManager api = new LucenceApiManagerImpl();
	private Logger logger = Logger.getLogger("cn.zifangsky.test.TestLucene");

	@Test
	public void testCreateIndexToRam() throws IOException, ParseException {
		assertEquals(1, lu.createIndexToRam(IPUT_TEXT, FIELD_NAME, KEYWORD ));
	}

	@Test
	public void testCreateIndexToFile() throws IOException, ParseException {
		assertEquals(1, lu.createIndexToFile(FILE_PATH ,FOLDER ,IPUT_TEXT, FIELD_NAME, KEYWORD ));
	}

	@Test
	public void testReadIndexFromFile() throws IOException, ParseException {
//		assertEquals(0, lu.readIndexFromFile(INDEX_FILE_PATH, FIELD_NAME, KEYWORD ));
		assertEquals(0, lu.readIndexFromFile(INDEX_CSV_FILE_PATH3, CmsArticleConstants.ARTICLE_BODY_PLAIN, "医学"));
	}

	@Test
	public void testReadIndexFromFile2() throws IOException, ParseException {
//		assertEquals(0, lu.readIndexFromFile2(INDEX_CSV_FILE_PATH3, "article_title", "医学"));
		assertEquals(0, lu.readIndexFromFile2(INDEX_CSV_FILE_PATH3, CmsArticleConstants.ARTICLE_BODY_PLAIN, "医学"));
	}

	//article_id, site_id, cms_content_id, max_version, article_title, article_body_plain
	@Test
	public void testReadCSVFileAndCreateIndexFile() throws IOException, ParseException {
		assertEquals(0,api.readCSVFileAndCreateIndexFile());
	}

	@Test
	public void testCreateIndexToFileForCmsArticleInfo() throws IOException, ParseException {
		List<String> dataList = FileUtil.readFile(CSV_FILE_PATH);
		List<String> keywordDataList = FileUtil.readFile(CSV_FILE_PATH2);
		List<CmsArticleInfo> articleList = new ArrayList<CmsArticleInfo>();
		List<String> keywordList = new ArrayList<String>();
		// read CmsArticleInfo data list and save into articleList
		for (String str : dataList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 6) {
				logger.info("not enough data!");
			}
			CmsArticleInfo articleInfo = new CmsArticleInfo();
			articleInfo.setArticle_id(line[0]);
			articleInfo.setSite_id(line[1]);
			articleInfo.setCms_content_id(line[2]);
			articleInfo.setMax_version(line[3]);
			articleInfo.setArticle_title(line[4]);
			articleInfo.setArticle_body_plain(line[5]);
			articleList.add(articleInfo);
		}
		// read keyword data list and save into keywordList
		for (String str : keywordDataList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 2) {
				logger.info("not enough data!");
			}
			keywordList.add(line[1]);
		}
		assertEquals(0,lu.createIndexToFileForCmsArticleInfo(INDEX_CSV_FILE_PATH3, articleList, keywordList));
	}

	//createIndexToRamForCmsArticleInfo
	@Test
	public void testCreateIndexToRamForCmsArticleInfo() throws IOException, ParseException {
		List<String> dataList = FileUtil.readFile(CSV_FILE_PATH);
		List<String> keywordDataList = FileUtil.readFile(CSV_FILE_PATH2);
		List<CmsArticleInfo> articleList = new ArrayList<CmsArticleInfo>();
		List<String> keywordList = new ArrayList<String>();
		// read CmsArticleInfo data list and save into articleList
		for (String str : dataList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 6) {
				logger.info("not enough data!");
			}
			CmsArticleInfo articleInfo = new CmsArticleInfo();
			articleInfo.setArticle_id(line[0]);
			articleInfo.setSite_id(line[1]);
			articleInfo.setCms_content_id(line[2]);
			articleInfo.setMax_version(line[3]);
			articleInfo.setArticle_title(line[4]);
			articleInfo.setArticle_body_plain(line[5]);
			articleList.add(articleInfo);
		}
		// read keyword data list and save into keywordList
		for (String str : keywordDataList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 2) {
				logger.info("not enough data!");
			}
			keywordList.add(line[1]);
		}
		assertEquals(0,lu.createIndexToRamForCmsArticleInfo(articleList, keywordList,CmsArticleConstants.ARTICLE_BODY_PLAIN));
	}

	//article_id, site_id, cms_content_id, max_version, article_title, article_body_plain
	@Test
	public void testHelloLucene() throws IOException, ParseException {
		lu.helloLucene("Lucene");
	}

}
