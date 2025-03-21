package com.topics.appointment.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.topics.appointment.model.bean.Pet;
import com.topics.util.HibernateUtil;

import java.util.List;

public class PetDAO implements IPetDAO {
	 @SuppressWarnings("unused")
	private SessionFactory sessionFactory;

	  public PetDAO(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	 
    // 新增寵物
	@SuppressWarnings("deprecation")
	@Override
    public int insertPet(Pet pet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(pet);
            transaction.commit();
            return pet.getPetId(); 
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 根據會員ID取得寵物
	@Override
	public List<Pet> getPetsByMemberId(int memberId) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        return session.createQuery("FROM Pet p WHERE p.owner.memberId = :memberId", Pet.class)
	                      .setParameter("memberId", memberId)
	                      .getResultList();
	    }
	}


    // 獲取所有寵物
	@Override
    public List<Pet> getAllPets() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Pet"; 
            Query<Pet> query = session.createQuery(hql, Pet.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根據寵物ID獲取寵物
	@Override
    public Pet getPetById(int petId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pet.class, petId); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
