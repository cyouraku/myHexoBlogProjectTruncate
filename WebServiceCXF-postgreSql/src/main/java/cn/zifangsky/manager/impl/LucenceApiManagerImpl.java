package cn.zifangsky.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.lucene.queryparser.classic.ParseException;

import cn.zifangsky.common.FileUtil;
import cn.zifangsky.common.LuceneUtil;
import cn.zifangsky.manager.LucenceApiManager;
import cn.zifangsky.model.CmsArticleInfo;

public class LucenceApiManagerImpl implements LucenceApiManager {

	private static final String COMMA = ",";
	private static final String INDEX_CSV_FILE_PATH = "C:\\temp\\CSV\\OUTPUT\\";
	private static final String INDEX_CSV_FILE_PATH2 = "C:\\temp\\CSV\\OUTPUT2\\";
	private static final String CSV_FILE_PATH = "C:\\temp\\CSV\\CmsArticleInfoList.csv";
	private static final String CSV_FILE_PATH2 = "C:\\temp\\CSV\\KeywordList.csv";
	private static final String CSV_FILE_PATH3 = "C:\\temp\\CSV\\SiteList.csv";
	private static final String OUTPUT_FILE_PATH = "C:\\temp\\CSV\\KeywordSearchResult.txt";
	private static final String OUTPUT_FILE_PATH2 = "C:\\temp\\CSV\\KeywordSearchResultNull.txt";
	private LuceneUtil lu = new LuceneUtil();
	private Logger logger = Logger.getLogger("cn.zifangsky.test.TestLucene");

	@Override
	public int readCSVFileAndCreateIndexFile() throws IOException,
			ParseException {
		List<String> dataList = FileUtil.readFile(CSV_FILE_PATH);
		List<String> keywordList = FileUtil.readFile(CSV_FILE_PATH2);
		List<String> siteList = FileUtil.readFile(CSV_FILE_PATH3);
		Map<String, String> keyMap = new HashMap<String, String>();
		Map<String, String> siteMap = new HashMap<String, String>();
		List<CmsArticleInfo> articleList = new ArrayList<CmsArticleInfo>();
		// read data list and save into dto
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
		// read keyword list and save to map
		for (String str : keywordList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 2) {
				logger.info("not enough data!");
			}
			// キー：チャンネルID、値：キーワード
			keyMap.putIfAbsent(line[0], line[1]);
		}
		// read site list and save to map
		for (String str : siteList) {
			String[] line = str.split(COMMA);
			if (line.length < 0) {
				logger.info("empty data!");
			} else if (line.length < 2) {
				logger.info("not enough data!");
			}
			// キー：サイト情報、値：チャンネルID
			siteMap.putIfAbsent(line[1], line[0]);
		}
		// Loop: search keyword in article title and create index file from dto
		// list
		int cnt = 0;
		int cnt2 = 0;
		for (CmsArticleInfo info : articleList) {
			// チャンネルIDを取得する
			String channel = siteMap.get(info.getSite_id());
			if (channel != null) {
				cnt += 1;
				logger.info("cnt = " + cnt);
				// キーワードを取得する
				String keyWord = keyMap.get(channel);
				if (keyWord != null) {
					cnt2 += 1;
					logger.info("cnt2 = " + cnt2);
					// create index and save to file
					int result = lu.createIndexToFile(INDEX_CSV_FILE_PATH, info
							.getArticle_id().replace("\"", ""), info
							.getArticle_title().replace("\"", ""), "title",
							keyWord.replace("\"", "").replace("+", ""));
					int result2 = lu.createIndexToFile(INDEX_CSV_FILE_PATH2,
							info.getArticle_id().replace("\"", ""), info
									.getArticle_body_plain().replace("\"", ""),
							"body", keyWord.replace("\"", "").replace("+", ""));
					String logInfo = "";
					boolean isFlag = false;
					if (result > 0) {
						logInfo = cnt2 + ". チャンネルID： " + channel + " 記事ID： "
								+ info.getArticle_id().replace("\"", "")
								+ "; キーワード ＝ "
								+ keyWord.replace("\"", "").replace("+", "")
								+ "; 記事タイトル検索 HIT回数 ＝ " + result + "\n";
						logger.info(logInfo);
						FileUtil.saveAsFile(OUTPUT_FILE_PATH, logInfo);
						isFlag = false;
					} else {
						isFlag = true;
					}
					if (result2 > 0) {
						logInfo = cnt2 + ". チャンネルID： " + channel + " 記事ID： "
								+ info.getArticle_id().replace("\"", "")
								+ "; キーワード ＝ "
								+ keyWord.replace("\"", "").replace("+", "")
								+ "; 記事本文検索 HIT回数 ＝ " + result2 + "\n";
						logger.info(logInfo);
						FileUtil.saveAsFile(OUTPUT_FILE_PATH, logInfo);
						isFlag = false;
					} else {
						isFlag = true;
					}
					if (isFlag) {
						logInfo = cnt2 + ". チャンネルID： " + channel + " 記事ID： "
								+ info.getArticle_id().replace("\"", "")
								+ "; キーワード ＝ "
								+ keyWord.replace("\"", "").replace("+", "")
								+ "; 記事タイトル検索 HIT回数 ＝ " + result
								+ "; 記事本文検索 HIT回数 ＝ " + result2 + "\n";
						logger.info(logInfo);
						FileUtil.saveAsFile(OUTPUT_FILE_PATH2, logInfo);
					}
				}
			}
		}
		return 0;
	}

}
