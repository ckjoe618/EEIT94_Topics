package com.topics.appointment.model.service;


import java.util.List;
import org.hibernate.SessionFactory;

import com.topics.appointment.model.bean.Pet;
import com.topics.appointment.model.dao.PetDAO;

public class PetSERVICE implements IPetSERVICE {

	private PetDAO petDAO;

	public PetSERVICE(SessionFactory sessionFactory) {
		petDAO = new PetDAO(sessionFactory);
	}
	public int insertPet(Pet pet) {
		return petDAO.insertPet(pet);
	}
	public List<Pet> getPetsByMemberId(int memberId){
		return petDAO.getPetsByMemberId(memberId);
	}
	public List<Pet> getAllPets(){
		return petDAO.getAllPets();
	}
	public Pet getPetById(int petId) {
		return petDAO.getPetById(petId);
	}
}
