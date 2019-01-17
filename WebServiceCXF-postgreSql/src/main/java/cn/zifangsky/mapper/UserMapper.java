package cn.zifangsky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.zifangsky.model.User;


public interface UserMapper {


    User selectUserByUserId(@Param("userId") Integer userId);

    User selectUserByName(@Param("userName") String userName);

    List<User> selectAllUser();

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);



}