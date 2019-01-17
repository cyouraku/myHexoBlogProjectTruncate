package cn.zifangsky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.zifangsky.model.Animal;

public interface AnimalMapper {

    @Select("select id,name,effect,speak,jpg,description from animals where id=${id}")
    @Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "effect", column = "effect"),
        @Result(property = "speak", column = "speak"),
        @Result(property = "jpg", column = "jpg"),
        @Result(property = "description", column = "description")
    })
	Animal selectAnimalById(@Param("id") Integer id);

    @Select("select id,name,effect,speak,jpg,description from animals where name='${name}'")
    @Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "effect", column = "effect"),
        @Result(property = "speak", column = "speak"),
        @Result(property = "jpg", column = "jpg"),
        @Result(property = "description", column = "description")
    })
	Animal selectAnimalByName(@Param("name") String name);

    @Select("select id,name,effect,speak,jpg,description from animals")
    @Results({
        @Result(property = "id",  column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "effect", column = "effect"),
        @Result(property = "speak", column = "speak"),
        @Result(property = "jpg", column = "jpg"),
        @Result(property = "description", column = "description")
    })
    List<Animal> selectAllAnimals();

    @Delete("delete from animals where id=${id}")
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Insert("insert into animals (id,name,effect,speak,jpg,description) values (#{id},#{name},#{effect},#{speak},#{jpg},#{description})")
    int insertSelective(Animal animal);

    //id,name,effect,speak,jpg,description
    @Update("update animals set name=#{name},effect=#{effect},speak=#{speak},jpg=#{jpg},description=#{description} where id=#{id}")
    int updateByPrimaryKeySelective(Animal animal);



}