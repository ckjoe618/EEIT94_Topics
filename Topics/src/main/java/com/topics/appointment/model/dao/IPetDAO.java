package com.topics.appointment.model.dao;

import java.util.List;
import com.topics.appointment.model.bean.Pet;

public interface IPetDAO {
	public int insertPet(Pet pet);

	public List<Pet> getPetsByMemberId(int memberId);

	public List<Pet> getAllPets();

	public Pet getPetById(int petId);

}
