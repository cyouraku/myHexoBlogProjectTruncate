package cn.zifangsky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.zifangsky.model.Video;


public interface VideoMapper {


    Video selectVideoById(@Param("id") Integer id);

    Video selectVideoByName(@Param("name") String name);

    List<Video> selectAllVideos();

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(Video record);

    int updateByPrimaryKeySelective(Video record);



}