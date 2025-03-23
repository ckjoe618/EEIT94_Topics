package com.topics.appointment.model.dao;

import java.util.List;
import com.topics.appointment.model.bean.Appointment;
import com.topics.appointment.model.bean.ItemDetails;
import com.topics.appointment.model.bean.Owner;
import com.topics.appointment.model.bean.PackageDetails;

public interface IAppointmentDAO {
	public int insertAppointment(Appointment appointment);

	public void addServiceToAppointment(int appointmentId, int serviceId, int quantity);

	public void addExtraPackageToAppointment(int appointmentId, int packageId, int quantity);

	public void updateAppointmentPrice(int appointmentId, int totalPrice);

	public List<Appointment> searchAppointmentsByPhoneNumber(String phoneNumber);

	public boolean deleteAppointment(int appointmentId);

	public List<Appointment> getAllAppointments();

	public Appointment getAppointmentById(int appointmentId);

	public boolean updateAppointment(Appointment appointment, List<ItemDetails> itemDetails,
			List<PackageDetails> packageDetails);

	public ItemDetails getServiceById(int appointmentId);

	public List<String> getServicesByAppointmentId(int appointmentId);

	public List<Integer> getSelectedExtraPackages(int appointmentId);

	public boolean isServiceAlreadyAdded(int appointmentId, int serviceId);

	public List<String> getBookedTimeslots(String date);

	public boolean isTimeslotBooked(String appointmentDate, String appointmentTimeslot);

	public Owner getOwnerById(int memberId);
	
}
