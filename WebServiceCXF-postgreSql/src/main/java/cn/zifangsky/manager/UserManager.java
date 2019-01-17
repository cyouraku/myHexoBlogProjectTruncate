package cn.zifangsky.manager;

import java.util.List;

import cn.zifangsky.model.User;

public interface UserManager {

    /**
     * 查询某个用户 by id
     * @return
     */
	public User findUser(int id);

    /**
     * 查询某个用户 by name
     * @return
     */
	public User getUserByUserName(String name);

    /**
     * 查询全体用户
     * @return
     */
	public List<User>  getAllUser();

    /**
     * 增加用户
     * @return
     */
	public Boolean addUser(User user);

    /**
     * 删除用户
     * @return
     */
	public Boolean deleteUser(int id);

    /**
     * 更新用户信息
     * @return
     */
	public Boolean updateUser(User user);
}
