package cn.zifangsky.webservice.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.zifangsky.manager.AnimalManager;
import cn.zifangsky.model.Animal;
import cn.zifangsky.webservice.AnimalService;

public class AnimalServiceImpl implements AnimalService {

	@Resource(name="animalManager")
	private AnimalManager animalManager;

	@Override
	public Animal getAnimalById(String id) {
		return animalManager.findAnimal(Integer.parseInt(id));
	}

	@Override
	public Animal getAnimalByAnimalName(String name) {
		return animalManager.getAnimalByAnimalName(name);
	}

	@Override
	public List<Animal> getAllAnimals() {
		return animalManager.getAllAnimals();
	}

	@Override
	public Boolean addAnimal(Animal animal) {
		return animalManager.addAnimal(animal);
	}

	@Override
	public Boolean deleteAnimal(String id) {
		return animalManager.deleteAnimal(Integer.parseInt(id));
	}

	@Override
	public Boolean updateAnimal(Animal animal) {
		return animalManager.updateAnimal(animal);
	}


}
