package cn.zifangsky.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.UserManager;
import cn.zifangsky.mapper.UserMapper;
import cn.zifangsky.model.User;

@Service("userManager")
public class UserManagerImpl implements UserManager {

	@Autowired
    private UserMapper userMapper;

    public User findUser(int userId) {
        return this.userMapper.selectUserByUserId(userId);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public Boolean addUser(User user) {
    	//Debug:
    	System.out.println("add user = " + user.getUserName() + "; " + user.getPassword());
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Boolean deleteUser(int id) {
        return userMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Boolean updateUser(User user) {
    	//Debug:
    	System.out.println("update user = " + user.getUserName() + "; " + user.getPassword());
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

}
