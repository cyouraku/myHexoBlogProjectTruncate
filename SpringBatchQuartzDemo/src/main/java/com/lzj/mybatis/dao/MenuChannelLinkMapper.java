package com.lzj.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lzj.springbatch.model.MenuChannelLinkTbl;


/**
 * Created by Administrator on 2016/7/25.
 */
public interface MenuChannelLinkMapper {

	@Insert("insert into menu_channel_link(menu_id, channel_id, delete_flag, update_date, update_user, regist_date, regist_user) VALUES (#{menuId}, #{channelId}, #{deleteFlag},#{updateDate},#{updateUser},#{registDate}, #{registUser})")
	int insertMenuChannelLinkEntity(MenuChannelLinkTbl record);

	/**
	 * 「channel_element」テーブルにて
	 * 記事IDにてチャンネルIDを取得する
	 * @param articleId
	 * @return
	 */
	@Select("select ce.channel_id from channel_element ce inner join (select ai.site_id from article_info ai where ai.article_id=${articleId} and ai.delete_flag='0') search_result on ce.element_value=search_result.site_id where ce.element_type='2'")
	@Results({
        @Result(property = "channelId",  column = "channel_id"),
    })
	List<Integer> selectChannelIdByArticleId(@Param("articleId") long articleId);


	/**
	 * 「menu_channel_link」テーブルにて、
	 * 記事IDにてメニューIDを取得する
	 * @param articleId
	 * @return
	 */
	@Select("select mcl.menu_id from menu_channel_link mcl"
			+ " inner join system_channel_article sca"
			+ " on mcl.channel_id = sca.channel_id where sca.delete_flag='0' and sca.article_id=${articleId};")
	@Results({
        @Result(property = "menuId",  column = "menu_id"),
    })
	List<Integer> queryMenuIdByArticleId(@Param("articleId") long articleId);

	/**
	 * 既存記事IDリスト取得する
	 * 検証環境：220672
	 * ローカル環境：54457
	 * @return
	 */
	@Select("select ai.article_id from article_info ai inner join ( select av.article_id, max(av.article_version) as latest_version,"
			+ " av.article_title from article_version av group by av.article_id, av.article_title ) as maxver on ai.article_id = maxver.article_id where ai.delete_flag='0'")
	@Results({
        @Result(property = "articleId",  column = "article_id"),
    })
	List<Integer> queryForExistingArticleIdList();

    /**
     * チャンネルIDにて「menu_channel_link」エンティティを取得する
     * @param channelId
     * @return
     */
    @Select("SELECT menu_id,channel_id,delete_flag,update_date,update_user,regist_date,regist_user from menu_channel_link where channel_id=${channelId}")
    @Results({
        @Result(property = "menuId",  column = "menu_id"),
        @Result(property = "channelId", column = "channel_id"),
        @Result(property = "deleteFlag", column = "delete_flag"),
        @Result(property = "updateDate", column = "update_date"),
        @Result(property = "updatetUser",  column = "update_user"),
        @Result(property = "registDate",  column = "regist_date"),
        @Result(property = "registUser",  column = "regist_user"),
    })
    List<MenuChannelLinkTbl> queryByIdWithChannelId(@Param("channelId") long channelId);

    /**
     * メニューIDにて「menu_channel_link」エンティティを取得する
     * @param channelId
     * @return
     */
    @Select("SELECT menu_id,channel_id,delete_flag,update_date,update_user,regist_date,regist_user from menu_channel_link where menu_id=${menuId}")
    @Results({
        @Result(property = "menuId",  column = "menu_id"),
        @Result(property = "channelId", column = "channel_id"),
        @Result(property = "deleteFlag", column = "delete_flag"),
        @Result(property = "updateDate", column = "update_date"),
        @Result(property = "updatetUser",  column = "update_user"),
        @Result(property = "registDate",  column = "regist_date"),
        @Result(property = "registUser",  column = "regist_user"),
    })
    List<MenuChannelLinkTbl> queryByIdWithMenuId(@Param("menuId") long menuId);
}
