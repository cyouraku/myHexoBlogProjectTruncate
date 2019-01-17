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

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;

import cn.zifangsky.model.Video;

@Scope("prototype")
@WebService
public interface VideoService {

    /**
     * 查询某个video by id
     * @param id
     * @return
     */
	@GET
	@Path("/getVideoById")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Video getVideoById(@QueryParam("id") String id);

    /**
     * 查询某个video by name
     * @param name
     * @return
     */
	@GET
	@Path("/getVideoByName")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Video getVideoByVideoName(@QueryParam("name") String name);

    /**
     * 查询所有video
     * @return
     */
	@GET
    @CrossOriginResourceSharing(
            allowAllOrigins = true,
            allowOrigins = { "*" },
            allowCredentials = true
    )
	@Path("/getAllVideos")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	List<Video> getAllVideos();

    /**
     * 增加某个video
     * @param video
     * @return
     */
	@POST
	@Path("/addVideo")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean addVideo(@Param("") Video video);

    /**
     * 删除某个video by id
     * @param id
     * @return
     */
	@DELETE
	@Path("/deleteUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean deleteVideo(@QueryParam("id") String id);

    /**
     * 更新某个video
     * @param video
     * @return
     */
	@POST
	@Path("/updateUser")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean updateVideo(@Param("") Video video);
}
