package com.lzj.springbatch.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzj.mybatis.dao.MenuChannelLinkMapper;
import com.lzj.springbatch.model.MenuChannelLinkTbl;

public class RemoteSqlWriter implements ItemWriter<List<Integer>> {

	private Logger logger = Logger.getLogger(RemoteSqlWriter.class.getName());

	@Autowired
	private MenuChannelLinkMapper mapper;
	private int cnt = 0;
	private int cntUsr = 0;
	private int cntSys = 0;

	@Override
	public void write(List<? extends List<Integer>> menuIdList) throws Exception {
		cnt++;
		List<Integer> args = menuIdList.get(0);
		for(int i=0;i<args.size();i++){
			Integer menuId = args.get(i);
			if(menuId == 201){
				cntUsr++;
			}else{
				List<MenuChannelLinkTbl>  entity = getMenuChannelLinkTblByMenuId(menuId);
				if(entity.size()>0){
					cntSys++;
				}
			}
		}
		logger.info(String.format("Round %d, Menu ID List Size = %d, System Menu ID count = %d; User Menu ID count = %d; \n", cnt, args.size(), cntSys, cntUsr));
	}

	/**
	 * select record by channel_id from menu_channel_link table
	 */
	private List<MenuChannelLinkTbl> getMenuChannelLinkTblByMenuId(long menuId){
		List<MenuChannelLinkTbl> tbl = new ArrayList<MenuChannelLinkTbl>();
		tbl = mapper.queryByIdWithMenuId(menuId);
		return tbl;
	}

}
