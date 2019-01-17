package com.lzj.springbatch.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzj.mybatis.dao.MenuChannelLinkMapper;

public class RemoteSqlProcessor implements ItemProcessor<Integer, List<Integer>>{

	private Logger logger = Logger.getLogger(RemoteSqlProcessor.class.getName());

	@Autowired
	private MenuChannelLinkMapper mapper;
	private int cnt = 0;

	@Override
	public List<Integer> process(Integer articleId) throws Exception {
		cnt++;
		//get menu id from menu_channel_link table by certain article id
		List<Integer> itr = getMenuIdViaMenuChannelLinkTbl(articleId);
		logger.info(String.format("Round %d, Article ID = %d, Menu List Size = %d \n", cnt, articleId, itr.size()));
		if(itr.size()>0){
			// Writerコール
			return itr;
		}else{
			List<Integer> userMenuList = new ArrayList<Integer>();
			userMenuList.add(201);
			// Writerコール
			return userMenuList;
		}
	}

	/**
	 * get menu id from menu_channel_link table by certain article id
	 */
	private List<Integer> getMenuIdViaMenuChannelLinkTbl(int articleId){
		List<Integer> result = new ArrayList<Integer>();
		result = mapper.queryMenuIdByArticleId(articleId);
		return result;
	}

}
