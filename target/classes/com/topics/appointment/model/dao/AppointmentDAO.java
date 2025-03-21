package com.topics.appointment.model.dao;

import org.apache.tomcat.jakartaee.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.topics.appointment.model.bean.Appointment;
import com.topics.appointment.model.bean.ItemDetails;
import com.topics.appointment.model.bean.Items;
import com.topics.appointment.model.bean.Owner;
import com.topics.appointment.model.bean.PackageDetails;
import com.topics.appointment.model.bean.Pet;
import com.topics.appointment.model.bean.ServicePackage;
import com.topics.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements IAppointmentDAO {
	  private SessionFactory sessionFactory;

	  public AppointmentDAO(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }

    // 插入新預約
	@SuppressWarnings("deprecation")
	@Override
	public int insertAppointment(Appointment appointment) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction transaction = session.beginTransaction();

	        appointment.setAppointmentTotal(0);
	        appointment.setAppointmentStatus(0);
	        appointment.setPaymentStatus(0);

	        session.save(appointment);

	        transaction.commit();
	        System.out.println("Saved Appointment ID: " + appointment.getAppointmentId());
	        return appointment.getAppointmentId(); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return 0;
	}



    // 新增預約服務
	@Override
	public void addServiceToAppointment(int appointmentId, int serviceId, int quantity) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction transaction = session.beginTransaction();

	        Appointment appointment = session.get(Appointment.class, appointmentId);
	        Items service = session.get(Items.class, serviceId);

	        if (appointment == null || service == null) {
	            throw new IllegalArgumentException("Appointment 或 Service 不存在！");
	        }

	        ItemDetails itemDetails = new ItemDetails();
	        itemDetails.setAppointment(appointment);
	        itemDetails.setItem(service);
	        itemDetails.setItemDetailQuantity(quantity);
	        session.save(itemDetails);

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    // 新增額外加購服務
	@Override
	public void addExtraPackageToAppointment(int appointmentId, int packageId, int quantity) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction transaction = session.beginTransaction();
	        
	        Appointment appointment = session.get(Appointment.class, appointmentId);
	        if (appointment == null) {
	            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
	        }
	        
	        ServicePackage servicePackage = session.get(ServicePackage.class, packageId);
	        if (servicePackage == null) {
	            throw new IllegalArgumentException("ServicePackage with ID " + packageId + " not found.");
	        }
	        
	        PackageDetails packageDetails = new PackageDetails();
	        packageDetails.setAppointment(appointment);
	        packageDetails.setServicePackage(servicePackage);  // 设置正确的 ServicePackage 实体
	        packageDetails.setPackageDetailsQuantity(quantity); // 设置数量

	        session.save(packageDetails);
	        
	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 更新預約總價格
	@SuppressWarnings("deprecation")
	@Override
	public void updateAppointmentPrice(int appointmentId, int totalPrice) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction transaction = session.beginTransaction();

	        // Update the appointment's total price using HQL
	        String hql = "UPDATE Appointment a SET a.appointmentTotal = :totalPrice WHERE a.appointmentId = :appointmentId";
	        
	        Query query = session.createQuery(hql);
	        query.setParameter("totalPrice", totalPrice);
	        query.setParameter("appointmentId", appointmentId);

	        int result = query.executeUpdate();

	        // Commit the transaction
	        transaction.commit();

	        if (result == 0) {
	            throw new IllegalArgumentException("No appointment found with ID: " + appointmentId);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 查詢特定預約
	@Override
	public List<Appointment> searchAppointmentsByPhoneNumber(String phoneNumber) {
	    List<Appointment> appointments = new ArrayList<>();
	    String hql = "SELECT a.appointmentId, a.memberId, a.petId, a.appointmentDate, " +
	    			 "a.appointmentTimeslot, a.appointmentTotal, a.appointmentStatus, a.paymentStatus, " +
	    			 "STRING_AGG(i.itemName, ', ') as serviceNames, " +
	    	         "STRING_AGG(sp.packageDescription, ', ') as additionalPackages " +
	    	         "FROM Appointment a " + 
	    	         "LEFT JOIN a.owner o " +  
	                 "LEFT JOIN a.pet p " +    
	                 "LEFT JOIN ItemDetails id ON id.appointment = a " + 
	                 "LEFT JOIN Items i ON i = id.item " + 
	                 "LEFT JOIN PackageDetails pd ON pd.appointment = a " +
	                 "LEFT JOIN ServicePackage sp ON sp.packageId = pd.servicePackage.packageId " + 
	             "WHERE o.phoneNumber = :phoneNumber " +
	             "GROUP BY a.appointmentId, a.memberId, a.petId, a.appointmentDate, " +
	             "a.appointmentTimeslot, a.appointmentTotal, a.appointmentStatus, a.paymentStatus";
	    try (Session session = sessionFactory.openSession()) {
	        Query query =session.createQuery(hql);
	        query.setParameter("phoneNumber", phoneNumber);
	        List<Object[]> result = query.list();

	        for (Object[] row : result) {
	            Appointment appointment = new Appointment();
	            appointment.setAppointmentId((Integer) row[0]);
	            Owner owner = new Owner();
	            owner.setMemberId((Integer) row[1]);
	            appointment.setOwner(owner);
	            Pet pet = new Pet();
	            pet.setPetId((Integer) row[2]);
	            appointment.setPet(pet);
	            appointment.setAppointmentDate((String) row[3]);
	            appointment.setAppointmentTimeslot((String) row[4]);
	            appointment.setAppointmentTotal((Integer) row[5]);
	            appointment.setAppointmentStatus((Integer) row[6]);
	            appointment.setPaymentStatus((Integer) row[7]);
	            appointment.setServiceNames((String) row[8]);
	            appointment.setAdditionalPackages((String) row[9]);
	            appointments.add(appointment);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return appointments;
	}



    // 刪除預約
	@SuppressWarnings("deprecation")
	@Override
    public boolean deleteAppointment(int appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, appointmentId);
            session.delete(appointment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	//全部資料
	public List<Appointment> getAllAppointments() {
	    List<Appointment> appointments;

	    String hql = "SELECT a.appointmentId, a.memberId, a.petId, a.appointmentDate, " +
	             "a.appointmentTimeslot, a.appointmentTotal, a.appointmentStatus, a.paymentStatus, " +
	             "STRING_AGG(i.itemName, ', ') as serviceNames, " +
	             "STRING_AGG(sp.packageDescription, ', ') as additionalPackages " +
	             "FROM Appointment a " + 
	             "LEFT JOIN ItemDetails id ON id.appointment = a " + 
                 "LEFT JOIN Items i ON i = id.item " + 
                 "LEFT JOIN PackageDetails pd ON pd.appointment = a " +
                 "LEFT JOIN ServicePackage sp ON sp.packageId = pd.servicePackage.packageId " + 
	             "GROUP BY a.appointmentId, a.memberId, a.petId, a.appointmentDate, " +
	             "a.appointmentTimeslot, a.appointmentTotal, a.appointmentStatus, a.paymentStatus";

	    try (Session session = sessionFactory.openSession()) {
	        Query<Object[]> query = session.createQuery(hql, Object[].class);
	        List<Object[]> result = query.getResultList();
	        
	        appointments = new ArrayList<>();
	        for (Object[] row : result) {
	            Appointment appointment = new Appointment();
	            appointment.setAppointmentId((Integer) row[0]);
	            Owner owner = new Owner();
	            owner.setMemberId((Integer) row[1]);
	            appointment.setOwner(owner);
	            Pet pet = new Pet();
	            pet.setPetId((Integer) row[2]);
	            appointment.setPet(pet);
	            appointment.setAppointmentDate((String) row[3]);
	            appointment.setAppointmentTimeslot((String) row[4]);
	            appointment.setAppointmentTotal((Integer) row[5]);
	            appointment.setAppointmentStatus((Integer) row[6]);
	            appointment.setPaymentStatus((Integer) row[7]);

	            appointment.setServiceNames((String) row[8]);
	            String additionalPackages = (String) row[9];
	            appointment.setAdditionalPackages(StringUtils.isBlank(additionalPackages) ? "無加購服務" : additionalPackages);


	            appointments.add(appointment);
	        }
	    }

	    return appointments;
	}



    // 根據 ID 查詢預約
	@Override
    public Appointment getAppointmentById(int appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, appointmentId);
        }
    }

    // 更新預約
	@Override
	public boolean updateAppointment(Appointment appointment, List<Integer> serviceIds, List<Integer> extraPackageIds) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Transaction transaction = session.beginTransaction();

	        try {
	            // 打印 memberId 以檢查是否正確
	            System.out.println("Updating appointment with Member ID: " + appointment.getMemberId());
	            
	            // 檢查 memberId 是否有效
	            Owner owner = session.get(Owner.class, appointment.getMemberId());
	            if (owner == null) {
	                throw new Exception("無效的會員 ID，請確認會員是否存在。");
	            }

	            session.update(appointment);  // 更新預約

	            String deleteServiceHQL = "DELETE FROM ItemDetails WHERE appointment.appointmentId = :appointmentId";
	            String deletePackageHQL = "DELETE FROM PackageDetails WHERE appointment.appointmentId = :appointmentId";
	            session.createQuery(deleteServiceHQL).setParameter("appointmentId", appointment.getAppointmentId()).executeUpdate();
	            session.createQuery(deletePackageHQL).setParameter("appointmentId", appointment.getAppointmentId()).executeUpdate();

	            if (serviceIds != null) {
	                for (int serviceId : serviceIds) {
	                    Items item = session.get(Items.class, serviceId);
	                    if (item != null) {
	                        ItemDetails itemDetail = new ItemDetails();
	                        itemDetail.setAppointment(appointment);
	                        itemDetail.setItem(item);
	                        itemDetail.setItemDetailQuantity(1);
	                        session.saveOrUpdate(itemDetail);
	                    }
	                }
	            }

	            if (extraPackageIds != null) {
	                for (int packageId : extraPackageIds) {
	                    ServicePackage sPackage = session.get(ServicePackage.class, packageId);
	                    if (sPackage != null) {
	                        PackageDetails packageDetail = new PackageDetails();
	                        packageDetail.setAppointment(appointment);
	                        packageDetail.setServicePackage(sPackage);
	                        packageDetail.setPackageDetailsQuantity(1);
	                        session.saveOrUpdate(packageDetail);
	                    }
	                }
	            }

	            transaction.commit();
	            return true;
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	            return false;
	        }
	    }
	}



    
    //取得服務預約號ID
	@Override
    public ItemDetails getServiceById(int appointmentId) {
        ItemDetails appointmentDetail = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ItemDetails> query = session.createQuery("FROM ItemDetails WHERE appointmentId = :appointmentId", ItemDetails.class);
            query.setParameter("appointmentId", appointmentId);
            appointmentDetail = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentDetail;
    }
    
    //查詢與特定預約相關聯的所有服務
	@Override
    public List<String> getServicesByAppointmentId(int appointmentId) {
        List<String> serviceNames = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT i.itemName FROM ItemDetails id JOIN id.item i WHERE id.appointmentId = :appointmentId";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("appointmentId", appointmentId);
            serviceNames = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceNames;
    }

    //查詢與特定預約相關聯的加購
	@Override
    public List<Integer> getSelectedExtraPackages(int appointmentId) {
        List<Integer> packageIds = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT pd.packageId FROM PackageDetails pd WHERE pd.appointmentId = :appointmentId";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("appointmentId", appointmentId);
            packageIds = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageIds;
    }
    
    //是否已經被添加到預約中
	@Override
    public boolean isServiceAlreadyAdded(int appointmentId, int serviceId) {
        boolean isServiceAdded = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(id) FROM ItemDetails id WHERE id.appointmentId = :appointmentId AND id.itemId = :serviceId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("appointmentId", appointmentId);
            query.setParameter("serviceId", serviceId);
            Long count = query.uniqueResult();
            isServiceAdded = count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isServiceAdded;
    }

    // 獲取已預定時段
	@Override
	public List<String> getBookedTimeslots(String appointmentDate) {
	    List<String> bookedTimeslots = new ArrayList<>();
	    Session session = null;
	    
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        Query<String> query = session.createQuery(
	            "SELECT a.timeSlot FROM Appointment a WHERE a.appointmentDate = :date", String.class);
	        query.setParameter("date", appointmentDate);
	        bookedTimeslots = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();  
	        }
	    }

	    return bookedTimeslots;
	}



    // 判斷時段是否已被預定
	@Override
    public boolean isTimeslotBooked(String appointmentDate, String appointmentTimeslot) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(*) FROM Appointment a WHERE a.appointmentDate = :date AND a.appointmentTimeslot = :timeslot";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("date", appointmentDate);
            query.setParameter("timeslot", appointmentTimeslot);
            return query.uniqueResult() > 0;
        }
    }
	
}
