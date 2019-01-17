package cn.zifangsky.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zifangsky.manager.AnimalManager;
import cn.zifangsky.model.Animal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/context_webservice.xml", "classpath:context/context.xml", "classpath:context/springmvc-servlet.xml", "classpath:context/sql-map-config.xml"})
//@WebAppConfiguration
//@WebService
public class TestAnimalManager {

	private Logger logger = Logger.getLogger(TestAnimalManager.class.getName());
	@Autowired
	private AnimalManager animalManager;

	@Before
	public void testAddAnimal() {
		//add animal
		Animal animal = new Animal();
		animal.setId(4);
		animal.setName("test animal");
		animal.setEffect("effect path");
		animal.setSpeak("speak path");
		animal.setJpg("jpg path");
		animal.setDescription("This is description test!");
		this.animalManager.addAnimal(animal);
		animal = this.animalManager.findAnimal(4);
		assertEquals("test animal",animal.getName());
		logger.info(String.format("Add animal %s", animal.getName()));
		//add another animal
		animal.setId(5);
		animal.setName("test animal 2");
		animal.setEffect("effect path 2");
		animal.setSpeak("speak path 2");
		animal.setJpg("jpg path 2");
		animal.setDescription("This is description test! 2");
		this.animalManager.addAnimal(animal);
		animal = this.animalManager.findAnimal(5);
		assertEquals("test animal 2",animal.getName());
		logger.info(String.format("Add animal %s", animal.getName()));
	}


	@Test
	public void testFindAnimal() {
		Animal animal = this.animalManager.findAnimal(1);
		logger.info(String.format("Find animal %s", animal.getName()));
		assertEquals("baboon monkey",animal.getName());
	}

	@Test
	public void testGetAnimalByAnimalName() {
		Animal animal = this.animalManager.getAnimalByAnimalName("baboon monkey");
		logger.info(String.format("Find animal id %d", animal.getId()));
		assertEquals(1,animal.getId());
	}

	@Test
	public void testGetAllAnimals() {
		List<Animal> list = this.animalManager.getAllAnimals();
		logger.info(String.format("The size of animals is %d", list.size()));
		assertEquals(5,list.size());
	}

	@Test
	public void testUpdateAnimal() {
		Animal animal = new Animal();
		animal.setId(4);
		animal.setName("update animal");
		animal.setEffect("update effect path");
		animal.setSpeak("update speak path");
		animal.setJpg("update jpg path");
		animal.setDescription("update This is description test!");
		this.animalManager.updateAnimal(animal);
		animal = this.animalManager.findAnimal(4);
		assertEquals("update animal",animal.getName());
		logger.info(String.format("Update animal %s", animal.getName()));
	}

	@After
	public void testDeleteAnimal() {
		assertEquals(true,this.animalManager.deleteAnimal(4));
		if(this.animalManager.findAnimal(4) == null){
			logger.info(String.format("delete animal id = %d", 4));
		}
		assertEquals(true,this.animalManager.deleteAnimal(5));
		if(this.animalManager.findAnimal(5) == null){
			logger.info(String.format("delete animal id = %d", 5));
		}
	}
}
