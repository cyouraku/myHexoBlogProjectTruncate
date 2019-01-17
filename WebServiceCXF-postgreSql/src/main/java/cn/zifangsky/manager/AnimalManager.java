package cn.zifangsky.manager;

import java.util.List;

import cn.zifangsky.model.Animal;

public interface AnimalManager {

    /**
     * inquire animal by id
     * @return
     */
	public Animal findAnimal(int id);

    /**
     * inquire animal by name
     * @return
     */
	public Animal getAnimalByAnimalName(String name);

    /**
     * inquire all animal
     * @return
     */
	public List<Animal> getAllAnimals();

    /**
     * add animal
     * @return
     */
	public Boolean addAnimal(Animal video);

    /**
     * delete animal
     * @return
     */
	public Boolean deleteAnimal(int id);

    /**
     * update animal
     * @return
     */
	public Boolean updateAnimal(Animal animal);
}
