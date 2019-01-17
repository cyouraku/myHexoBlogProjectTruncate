package com.lzj.springbatch.tasklet;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzj.mybatis.dao.MenuChannelLinkMapper;

public class RemoteSqlReader implements ItemReader<Integer> {

	private Logger logger = Logger.getLogger(RemoteSqlReader.class.getName());

	@Autowired
	private MenuChannelLinkMapper mapper;
	/** 記事リストカウンタ */
	private int cnt = 0;
	/** 処理開始時間 */
	private long start = System.currentTimeMillis();

	@Override
	public Integer read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		List<Integer> articleList = getArticleList();
		int length = articleList.size();
		if(length==0){
			logger.info(String.format("Round %d, total article id count = %d,process complete. \n", cnt, length));
		}else{
			while(this.cnt < length){
				this.cnt++;
				logger.info(String.format("Round %d, total article id count = %d, First Article ID = %d, \n", cnt, length, articleList.get(0) ));
				logger.info(String.format("Round %d, Time spent = %d seconds \n", cnt, (System.currentTimeMillis() - this.start)/1000));
				return articleList.get(this.cnt-1);
			}
			// Total time spent
			logger.info(String.format("Total Time spent = %d seconds \n", (System.currentTimeMillis() - this.start)/1000));
		}
		// 全件処理終了で終了
		logger.info(String.format("Round %d, 全件処理終了で終了", cnt));
		return null;
	}

	/**
	 * 既存記事IDリスト取得する
	 *
	 * @return
	 */
	private List<Integer> getArticleList() {
		return mapper.queryForExistingArticleIdList();
	}
}
