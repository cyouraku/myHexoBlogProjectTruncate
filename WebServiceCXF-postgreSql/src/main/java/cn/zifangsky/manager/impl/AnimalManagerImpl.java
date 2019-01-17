package cn.zifangsky.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.AnimalManager;
import cn.zifangsky.mapper.AnimalMapper;
import cn.zifangsky.model.Animal;

@Service("animalManager")
public class AnimalManagerImpl implements AnimalManager {

	@Autowired
    private AnimalMapper animalMapper;

	@Override
	public Animal findAnimal(int id) {
		return this.animalMapper.selectAnimalById(id);
	}

	@Override
	public Animal getAnimalByAnimalName(String name) {
		return this.animalMapper.selectAnimalByName(name);
	}

	@Override
	public List<Animal> getAllAnimals() {
		return this.animalMapper.selectAllAnimals();
	}

	@Override
	public Boolean addAnimal(Animal animal) {
		return this.animalMapper.insertSelective(animal) > 0;
	}

	@Override
	public Boolean deleteAnimal(int id) {
		return this.animalMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public Boolean updateAnimal(Animal animal) {
		return this.animalMapper.updateByPrimaryKeySelective(animal) > 0;
	}

}
