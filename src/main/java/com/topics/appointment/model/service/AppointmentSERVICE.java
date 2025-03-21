package com.topics.appointment.model.service;


import java.util.List;

import org.hibernate.SessionFactory;

import com.topics.appointment.model.bean.Appointment;
import com.topics.appointment.model.bean.ItemDetails;
import com.topics.appointment.model.dao.AppointmentDAO;

public class AppointmentSERVICE implements IAppointmentSERVICE {

	private AppointmentDAO appointmentDAO;

	public AppointmentSERVICE(SessionFactory sessionFactory) {
		appointmentDAO = new AppointmentDAO(sessionFactory);
	}
	public int insertAppointment(Appointment appointment) {
		return appointmentDAO.insertAppointment(appointment);
	}
	public void addServiceToAppointment(int appointmentId, int serviceId, int quantity){
		appointmentDAO.addServiceToAppointment(appointmentId, serviceId, quantity);
	}
	public void addExtraPackageToAppointment(int appointmentId, int packageId, int quantity){
		appointmentDAO.addExtraPackageToAppointment(appointmentId, packageId, quantity);
	}
	public void updateAppointmentPrice(int appointmentId, int totalPrice){
		 appointmentDAO.updateAppointmentPrice(appointmentId, totalPrice);
	}
	public List<Appointment> searchAppointmentsByPhoneNumber(String phoneNumber){
		return appointmentDAO.searchAppointmentsByPhoneNumber(phoneNumber);
	}
	public boolean deleteAppointment(int appointmentId){
		return appointmentDAO.deleteAppointment(appointmentId);
	}
	public List<Appointment> getAllAppointments(){
		return appointmentDAO.getAllAppointments();
	}
	public Appointment getAppointmentById(int appointmentId){
		return appointmentDAO.getAppointmentById(appointmentId);
	}
	public boolean updateAppointment(Appointment appointment, List<Integer> serviceIds, List<Integer> extraPackageIds) {
		return appointmentDAO.updateAppointment(appointment, serviceIds, extraPackageIds);
	}
	public ItemDetails getServiceById(int appointmentId){
		return appointmentDAO.getServiceById(appointmentId);
	}
	public List<String> getServicesByAppointmentId(int appointmentId){
		return appointmentDAO.getServicesByAppointmentId(appointmentId);
	}
	public List<Integer> getSelectedExtraPackages(int appointmentId){
		return appointmentDAO.getSelectedExtraPackages(appointmentId);
	}
	public boolean isServiceAlreadyAdded(int appointmentId, int serviceId) {
		return appointmentDAO.isServiceAlreadyAdded(appointmentId, serviceId);
	}
	public List<String> getBookedTimeslots(String date){
		return appointmentDAO.getBookedTimeslots(date);
	}
	public boolean isTimeslotBooked(String appointmentDate, String appointmentTimeslot){
		return appointmentDAO.isTimeslotBooked(appointmentDate, appointmentTimeslot);
	}
	
}
