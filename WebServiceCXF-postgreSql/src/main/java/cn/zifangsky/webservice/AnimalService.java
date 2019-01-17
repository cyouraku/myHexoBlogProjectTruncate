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

import cn.zifangsky.model.Animal;

@Scope("prototype")
@WebService
public interface AnimalService {

    /**
     * inquire animal by id
     * @param id
     * @return
     */
	@GET
	@Path("/getAnimalById")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Animal getAnimalById(@QueryParam("id") String id);

    /**
     * inquire animal by name
     * @param name
     * @return
     */
	@GET
	@Path("/getAnimalByName")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Animal getAnimalByAnimalName(@QueryParam("name") String name);

    /**
     * inquire animal all
     * @return
     */
	@GET
    @CrossOriginResourceSharing(
            allowAllOrigins = true,
            allowOrigins = { "*" },
            allowCredentials = true
    )
	@Path("/getAllAnimals")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	List<Animal> getAllAnimals();

    /**
     * add animal
     * @param animal
     * @return
     */
	@POST
	@Path("/addAnimal")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean addAnimal(@Param("") Animal animal);

    /**
     * delete animal by id
     * @param id
     * @return
     */
	@DELETE
	@Path("/deleteAnimal")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean deleteAnimal(@QueryParam("id") String id);

    /**
     * update animal
     * @param animal
     * @return
     */
	@POST
	@Path("/updateAnimal")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
	Boolean updateAnimal(@Param("") Animal animal);
}
