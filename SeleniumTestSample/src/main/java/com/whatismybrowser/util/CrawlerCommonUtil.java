package com.whatismybrowser.util;

import java.util.ArrayList;
import java.util.List;

import seleniumTest.Util.SeleniumServerUtil;

import com.whatismybrowser.entity.UserAgentDto;

public class CrawlerCommonUtil {

	/**
	 * create test url
	 * @param url
	 * @param page
	 * @return
	 */
	public static String createTestUrl(String url, String page){
		return String.format("%s%s",url,page);
	}


	/**
	 * List<UserAgentDto>対象をTSVファイルに格納する
	 * @param entityList
	 * @param filePath
	 */
	public static void saveToTsvFile(List<UserAgentDto> entityList, String filePath){
		List<String> strList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(UserAgentDto dto : entityList){
			if(checkEmpty(dto.getUserAgent())){
				sb.append(dto.getUserAgent());
				sb.append("\t");
			}
			if(checkEmpty(dto.getVersion())){
				sb.append(dto.getVersion());
				sb.append("\t");
			}
			if(checkEmpty(dto.getOs())){
				sb.append(dto.getOs());
				sb.append("\t");
			}
			if(checkEmpty(dto.getHardWareType())){
				sb.append(dto.getHardWareType());
				sb.append("\t");
			}
			if(checkEmpty(dto.getPopularity())){
				sb.append(dto.getPopularity());
			}
			sb.toString();
			strList.add(sb.toString());
		}
		SeleniumServerUtil.writerFile(strList, filePath);
	}

	/**
	 * 空文字検索
	 * @param input
	 * @return
	 */
	public static boolean checkEmpty(String input){
		if(!"".equals(input)){
			return true;
		}
		return false;
	}

}