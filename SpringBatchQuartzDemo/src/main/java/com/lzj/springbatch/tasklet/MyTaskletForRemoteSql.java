package com.lzj.springbatch.tasklet;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzj.mybatis.dao.MenuChannelLinkMapper;
import com.lzj.springbatch.model.MenuChannelLinkTbl;
import com.lzj.util.ConstantsUtil;
import com.lzj.util.DateTimeUtil;

public class MyTaskletForRemoteSql implements Tasklet {

	private Logger logger = Logger.getLogger(MyTaskletForRemoteSql.class.getName());

	@Autowired
	private  MenuChannelLinkMapper mapper;

	@Autowired
	private DataSource dataSource;

	public MenuChannelLinkMapper getMenuChannelLinkMapper() {
		return mapper;
	}
	public void setMenuChannelLinkMapper(MenuChannelLinkMapper mapper) {
		this.mapper = mapper;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		long start = System.currentTimeMillis();
//		testInsertMenuChannelLinkTbl(201);
//		testGetMenuIdByArticleIdViaMenuChannelLinkTbl();
		testGetMenuIdByArticleIdModified();
		logger.info(String.format("Time spent = %d seconds \n", (System.currentTimeMillis() - start)/1000));
		return RepeatStatus.FINISHED;
	}


	/**
	 * 「menu_channel_link」テーブルにて、
	 * 記事IDにてメニューIDを取得する
	 */
	@SuppressWarnings("unused")
	private void testGetMenuIdByArticleIdViaMenuChannelLinkTbl(){
		int cntUsr = 0;
		int cntSys = 0;
		List<Integer> articleList = getArticleList();
		int length = articleList.size();
		logger.info(String.format("Total article id count = %d; \n", length ));
		for(int i=0;i<length;i++){
			//get menu id from menu_channel_link table by certain article id
			List<Integer> itr = getMenuIdViaMenuChannelLinkTbl(articleList.get(i));
			if(itr.size()>0){
				cntSys++;
				logger.info(String.format("System Menu ID count = %d; \n", cntSys ));
			}else{
				cntUsr++;
//				logger.info(String.format("User Menu ID count = %d; \n", cntUsr ));
			}
		}
		logger.info(String.format("Total System Menu ID count = %d; Total User Menu ID count = %d; \n", cntSys, cntUsr));
	}

	/**
	 * 「channel_element」テーブルにて、
	 * 記事IDにてチャンネルIDを取得してからメニューIDを特定する
	 */
//	@SuppressWarnings("unused")
	private void testGetMenuIdByArticleIdModified(){
		int cnt = 0;
		int cntUsr = 0;
		int cntSys = 0;
		List<Integer> articleList = getArticleList();
		int length = articleList.size();
		logger.info(String.format("Total article id count = %d; \n", length ));
		for(int i=0;i<length;i++){
			//get channel id from channel_element table by certain article id
			int articleId = articleList.get(i);
			List<Integer> itr = getChannelId(articleId);
			if (itr.size() > 0) {
				cnt += itr.size();
				List<MenuChannelLinkTbl> entity = getMenuChannelLinkTbl(itr.get(0));
				if (entity.size() > 0) {
					logger.info(String.format("System Menu ID count = %d; Article ID = %d; \n", cntSys, articleId));
					cntSys++;
				} else if (entity.size() == 0){
					cntUsr++;
//					logger.info(String.format("User Menu ID count = %d; \n", cntUsr ));
				}
			}
		}
		logger.info(String.format("Total channel id count = %d; \n", cnt));
		logger.info(String.format("System Menu ID count = %d; User Menu ID count = %d; \n", cntSys, cntUsr));
	}

	/**
	 * Test get menu id by article id
	 */
	@SuppressWarnings("unused")
	private void testGetMenuIdByArticleId(){
		int cnt = 0;
		for(int i=0;i<100;i++){
			List<Integer> itr = getChannelId(i);
			cnt = itr.size();
			if(cnt>0){
				for(Integer it : itr){
					logger.info(String.format("Channel ID = %d; \n", it));
				}
			}
		}
		logger.info(String.format("Total channel id count = %d; \n", cnt));
//		logger.info(String.format("Menu ID = %d; Article ID = %d; \n", getMenuId(i),i));
	}

	/**
	 * Test insert record with available channel id list into menu_channel_link table
	 */
	@SuppressWarnings("unused")
	private void testInsertMenuChannelLinkTbl(int menuId){
		for(int str : ConstantsUtil.SYS_CHANNEL_ID){
			int result = insertMenuChannelLinkTbl(menuId, str);
			System.out.printf("insert operation result = %d \n", result);
			if(result<=0){
				break;
			}else{
				System.out.printf("insert operation channel id = %s \n", str);
			}
		}
	}

	/**
	 * Test get record by menu_id from menu_channel_link table
	 */
	@SuppressWarnings("unused")
	private void testGetMenuChannelLinkTbl(){
		long menuId = 101L;
		 getMenuChannelLinkTbl(menuId);
	}

	/**
	 * insert record with available channel id into menu_channel_link table
	 */
	private int insertMenuChannelLinkTbl(int menuId, int channelId){
		MenuChannelLinkTbl record = new MenuChannelLinkTbl();
		record.setMenuId(menuId);
		record.setChannelId(channelId);
		record.setDeleteFlag("0");
		record.setUpdateDate(DateTimeUtil.getNow());
		record.setUpdateUser("Tim");
		record.setRegistDate(DateTimeUtil.getNow());
		record.setRegistUser("Tim");
		return mapper.insertMenuChannelLinkEntity(record);
	}

	/**
	 * select record by channel_id from menu_channel_link table
	 */
	private List<MenuChannelLinkTbl> getMenuChannelLinkTbl(long channelId){
		List<MenuChannelLinkTbl> tbl = new ArrayList<MenuChannelLinkTbl>();
		tbl = mapper.queryByIdWithChannelId(channelId);
//		System.out.println(tbl);
		return tbl;
	}

	/**
	 * select record by channel_id from menu_channel_link table
	 */
	private List<MenuChannelLinkTbl> getMenuChannelLinkTblByMenuId(long menuId){
		List<MenuChannelLinkTbl> tbl = new ArrayList<MenuChannelLinkTbl>();
		tbl = mapper.queryByIdWithMenuId(menuId);
//		System.out.println(tbl);
		return tbl;
	}

	/**
	 * get channel id from channel_element table by certain article id
	 */
	private List<Integer> getChannelId(int articleId){
		List<Integer> result = new ArrayList<Integer>();
		result = mapper.selectChannelIdByArticleId(articleId);
		return result;
	}

	/**
	 * get menu id from menu_channel_link table by certain article id
	 */
//	@SuppressWarnings("unused")
	private List<Integer> getMenuIdViaMenuChannelLinkTbl(int articleId){
		List<Integer> result = new ArrayList<Integer>();
		result = mapper.queryMenuIdByArticleId(articleId);
		return result;
	}

	/**
	 * 既存記事IDリスト取得する
	 * @return
	 */
//	@SuppressWarnings("unused")
	private List<Integer> getArticleList(){
		return mapper.queryForExistingArticleIdList();
	}

}
