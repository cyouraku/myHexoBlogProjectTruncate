package cn.zifangsky.webservice;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;

import cn.zifangsky.model.User;

@Scope("prototype")
@WebService
public interface UserService {

    /**
     * 查询某个用户 by id
     * @param userId
     * @return
     */
	@GET
	@Path("/getUserById")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	User getUserById(@QueryParam("userId") String userId);

    /**
     * 查询某个用户 by name
     * @param userName
     * @return
     */
	@GET
	@Path("/getUserByUserName")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	User getUserByUserName(@QueryParam("userName") String userName);

    /**
     * 查询所有用户
     * @return
     */
	@GET
	@Path("/getAllUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	List<User> getAllUser();

    /**
     * 增加某个用户
     * @param user
     * @return
     */
	@POST
	@Path("/addUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean addUser(@Param("") User user);

    /**
     * 删除某个用户 by id
     * @param id
     * @return
     */
	@DELETE
	@Path("/deleteUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean deleteUser(@QueryParam("userId") String id);

    /**
     * 更新某个用户
     * @param user
     * @return
     */
	@POST
	@Path("/updateUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean updateUser(@Param("") User user);
}
