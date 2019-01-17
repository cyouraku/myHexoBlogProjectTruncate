package cn.zifangsky.webservice.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.zifangsky.manager.UserManager;
import cn.zifangsky.model.User;
import cn.zifangsky.webservice.UserService;

public class UserServiceImpl implements UserService {

	@Resource(name="userManager")
	private UserManager userManager;

	@Override
	public User getUserById(String userId) {
		return userManager.findUser(Integer.parseInt(userId));
	}

	@Override
	public User getUserByUserName(String userName) {
		return userManager.getUserByUserName(userName);
	}

	@Override
	public List<User> getAllUser() {
		return userManager.getAllUser();
	}

	@Override
	public Boolean addUser(User user) {
		return userManager.addUser(user);
	}

	@Override
	public Boolean deleteUser(String id) {
		return userManager.deleteUser(Integer.parseInt(id));
	}

	@Override
	public Boolean updateUser(User user) {
		return userManager.updateUser(user);
	}

}
