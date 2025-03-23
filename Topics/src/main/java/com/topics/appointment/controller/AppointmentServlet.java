package com.topics.appointment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.google.gson.Gson;
import com.topics.appointment.model.bean.Appointment;
import com.topics.appointment.model.bean.ItemDetails;
import com.topics.appointment.model.bean.Owner;
import com.topics.appointment.model.bean.PackageDetails;
import com.topics.appointment.model.bean.Pet;
import com.topics.appointment.model.dao.AppointmentDAO;
import com.topics.appointment.model.dao.PetDAO;
import com.topics.util.HibernateUtil;

@WebServlet("/appointment/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AppointmentServlet.class.getName());
	private static SessionFactory factory;

	@Override
	public void init() throws ServletException {
		try {
			factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Appointment.class)
					.buildSessionFactory();
		} catch (Exception e) {
			throw new ServletException("初始化 Hibernate 失敗", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		AppointmentDAO appointmentDAO = new AppointmentDAO(HibernateUtil.getSessionFactory());
		PetDAO petDAO = new PetDAO(HibernateUtil.getSessionFactory());

		switch (action) {
		case "querybookingtime":
			handleQueryBookingTime(request, response, appointmentDAO);
			break;
		case "querypet":
			handleQueryPetById(request, response, petDAO);
			break;
		default:
			request.setAttribute("message", "無效的操作！");
			request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		AppointmentDAO appointmentDAO = new AppointmentDAO(HibernateUtil.getSessionFactory());
		PetDAO petDAO = new PetDAO(HibernateUtil.getSessionFactory());

		try {
			switch (action) {
			case "add":
				insertAppointmentById(request, response, appointmentDAO, petDAO);
				break;
			case "delete":
				deleteAppointmentById(request, response, appointmentDAO);
				break;
			case "edit":
				updateAppointmentById(request, response, appointmentDAO, petDAO);
				break;
			default:
				request.setAttribute("message", "無效的操作！");
				request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "處理請求時發生錯誤", e);
			request.setAttribute("message", "錯誤發生: " + e.getMessage());
			request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}

	// 預約時間
	private void handleQueryBookingTime(HttpServletRequest request, HttpServletResponse response,
			AppointmentDAO appointmentDAO) throws IOException {
		String appointmentDate = request.getParameter("appointmentDate");
		List<String> bookedTimeslots = appointmentDAO.getBookedTimeslots(appointmentDate);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("bookedTimeslots", bookedTimeslots);

		Gson gson = new Gson();
		String jsonResponse = gson.toJson(responseData);

		response.setContentType("application/json");
		response.getWriter().write(jsonResponse);
	}

	// 找寵物
	private void handleQueryPetById(HttpServletRequest request, HttpServletResponse response, PetDAO petDAO)
			throws IOException, ServletException {
		String memberIdStr = request.getParameter("memberId");
		if (memberIdStr == null || memberIdStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "memberId 不能為空");
			return;
		}

		int memberId;
		try {
			memberId = Integer.parseInt(memberIdStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "memberId 格式錯誤");
			return;
		}
		List<Pet> pets = petDAO.getPetsByMemberId(memberId);
		System.out.println("查詢到的寵物數量: " + pets.size());

		List<Map<String, Object>> petList = new ArrayList<>();
		for (Pet pet : pets) {
			Map<String, Object> petData = new HashMap<>();
			petData.put("petId", pet.getPetId());
			petData.put("petName", pet.getPetName());
			System.out.println("petId: " + pet.getPetId() + ", petName: " + pet.getPetName());

			petList.add(petData);
		}
		String json = new Gson().toJson(petList);
		System.out.println(json);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	// 新增預約
	private void insertAppointmentById(HttpServletRequest request, HttpServletResponse response,
			AppointmentDAO appointmentDAO, PetDAO petDAO) throws SQLException, IOException, ServletException {

		String memberIdStr = request.getParameter("memberId");
		String petIdStr = request.getParameter("appointmentpetId");
		String appointmentDate = request.getParameter("appointmentDate");
		String appointmentTimeslot = request.getParameter("appointmentTimeslot");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Map<String, Object> jsonResponse = new HashMap<>();

		if (memberIdStr == null || memberIdStr.trim().isEmpty()) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "memberId 不能為空");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}
		if (petIdStr == null || petIdStr.trim().isEmpty()) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "petId 不能為空");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}
		if (appointmentDate == null || appointmentDate.trim().isEmpty() || appointmentTimeslot == null
				|| appointmentTimeslot.trim().isEmpty()) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "日期或時段不能為空");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}

		int memberId, petId;
		try {
			memberId = Integer.parseInt(memberIdStr);
			petId = Integer.parseInt(petIdStr);
		} catch (NumberFormatException e) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "memberId 或 petId 格式錯誤");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}
		Owner owner = appointmentDAO.getOwnerById(memberId);
		Pet pet = petDAO.getPetById(petId);
		if (owner == null) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "找不到對應的會員");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}
		if (appointmentDAO.isTimeslotBooked(appointmentDate, appointmentTimeslot)) {
			jsonResponse.put("success", false);
			jsonResponse.put("message", "該時段已被預約，請選擇其他時段！");
			response.getWriter().write(gson.toJson(jsonResponse));
			return;
		}

		Appointment appointment = new Appointment(owner, pet, appointmentDate, appointmentTimeslot, 0, 0, 0);
		int appointmentId = appointmentDAO.insertAppointment(appointment);
		int totalPrice = 0;

		String selectedService = request.getParameter("services");
		if (selectedService != null && !selectedService.isEmpty()) {
			try {
				int serviceId = Integer.parseInt(selectedService);
				int servicePrice = getServicePrice(serviceId);
				totalPrice += servicePrice;
				appointmentDAO.addServiceToAppointment(appointmentId, serviceId, 1);
			} catch (NumberFormatException e) {
				LOGGER.log(Level.WARNING, "無效的服務 ID: " + selectedService);
			}
		}

		String[] selectedExtras = request.getParameterValues("extraPackages");
		if (selectedExtras != null) {
			for (String extraIdStr : selectedExtras) {
				try {
					int extraId = Integer.parseInt(extraIdStr);
					int extraPrice = getExtraPrice(extraId);
					totalPrice += extraPrice;
					appointmentDAO.addExtraPackageToAppointment(appointmentId, extraId, 1);
				} catch (NumberFormatException e) {
					LOGGER.log(Level.WARNING, "無效的加購 ID: " + extraIdStr);
				}
			}
		}
		appointmentDAO.updateAppointmentPrice(appointmentId, totalPrice);
		request.setAttribute("success", "true");
		request.setAttribute("appointmentId", appointmentId);
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("message", "預約成功！總價為: " + totalPrice + "元");
		request.getRequestDispatcher("/appointment/result/Appointment.jsp").forward(request, response);

	}

	// 刪除預約
	private void deleteAppointmentById(HttpServletRequest request, HttpServletResponse response,
			AppointmentDAO appointmentDAO) throws SQLException, IOException, ServletException {
		int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
		boolean isDeleted = appointmentDAO.deleteAppointment(appointmentId);

		if (isDeleted) {
			request.setAttribute("message", "預約已成功刪除！");
		} else {
			request.setAttribute("message", "預約刪除失敗！");
		}
		response.sendRedirect("/Topics/appointment/home/Appointment.jsp");
	}

	private void updateAppointmentById(HttpServletRequest request, HttpServletResponse response,
			AppointmentDAO appointmentDAO, PetDAO petDAO) throws SQLException, IOException, ServletException {
		try {
			String appointmentIdParam = request.getParameter("appointmentId");
			String appointmentStatusParam = request.getParameter("appointmentStatus");
			String paymentStatusParam = request.getParameter("paymentStatus");
			if (appointmentIdParam == null || appointmentIdParam.isEmpty()) {
				request.setAttribute("errorMessage", "預約 ID 不能為空。");
				request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
				return;
			}
			int appointmentId = Integer.parseInt(appointmentIdParam);

			int appointmentStatus = (appointmentStatusParam != null && !appointmentStatusParam.isEmpty())
					? Integer.parseInt(appointmentStatusParam)
					: 0;
			int paymentStatus = (paymentStatusParam != null && !paymentStatusParam.isEmpty())
					? Integer.parseInt(paymentStatusParam)
					: 0;

			String appointmentDate = request.getParameter("appointmentDate");
			String timeSlot = request.getParameter("timeSlot");
			if (appointmentDate == null || appointmentDate.isEmpty()) {
				request.setAttribute("errorMessage", "預約日期不能為空。");
				request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
				return;
			}
			if (timeSlot == null || timeSlot.isEmpty()) {
				request.setAttribute("errorMessage", "預約時段不能為空。");
				request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
				return;
			}

			int totalPrice = calculateTotalPrice(request);

			Appointment updatedAppointment = new Appointment();
			updatedAppointment.setAppointmentId(appointmentId);
			updatedAppointment.setAppointmentDate(appointmentDate);
			updatedAppointment.setAppointmentTimeslot(timeSlot);
			updatedAppointment.setAppointmentTotal(totalPrice);
			updatedAppointment.setAppointmentStatus(appointmentStatus);
			updatedAppointment.setPaymentStatus(paymentStatus);

			List<ItemDetails> itemDetails = getItemSelectedIds(request.getParameterValues("services"));
			List<PackageDetails> packageDetails = getPackageSelectedIds(request.getParameterValues("extraPackages"));
			boolean success = appointmentDAO.updateAppointment(updatedAppointment, itemDetails, packageDetails);

			if (success) {
				request.setAttribute("appointmentId", appointmentId);
				request.setAttribute("totalPrice", totalPrice);
				request.setAttribute("successMessage", "預約更新成功！總價為: " + totalPrice + "元");
				request.getRequestDispatcher("/appointment/result/Appointment.jsp").forward(request, response);

			} else {
				request.setAttribute("errorMessage", "更新失敗，請檢查預約 ID 是否正確。");
				request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "無效的數字格式。請檢查您輸入的預約 ID、預約狀態或付款狀態。");
			request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
		}
	}

	// 計算總價
	private int calculateTotalPrice(HttpServletRequest request) {
		List<ItemDetails> itemDetailsList = getItemSelectedIds(request.getParameterValues("services"));
		String[] selectedPackages = request.getParameterValues("extraPackages");
		List<PackageDetails> extraPackageList = (selectedPackages != null && selectedPackages.length > 0)
				? getPackageSelectedIds(selectedPackages)
				: new ArrayList<>();
		int total = 0;

		for (ItemDetails itemDetails : itemDetailsList) {
			total += getServicePrice(itemDetails.getItemId());
		}

		for (PackageDetails extraPackage : extraPackageList) {
			total += getExtraPrice(extraPackage.getPackageId());
		}

		return total;
	}

	// 取得選擇的服務 ID
	private List<ItemDetails> getItemSelectedIds(String[] selectedItems) {
		List<ItemDetails> itemDetailsList = new ArrayList<>();
		for (String itemId : selectedItems) {
			ItemDetails itemDetails = new ItemDetails();
			itemDetails.setItemId(Integer.parseInt(itemId));
			itemDetailsList.add(itemDetails);
		}
		return itemDetailsList;
	}

	// 取得選擇的加購 ID
	private List<PackageDetails> getPackageSelectedIds(String[] selectedPackages) {
		List<PackageDetails> packageDetailsList = new ArrayList<>();
		if (selectedPackages == null || selectedPackages.length == 0) {
			return packageDetailsList;
		}
		for (String packageId : selectedPackages) {
			PackageDetails packageDetails = new PackageDetails();
			packageDetails.setPackageId(Integer.parseInt(packageId));
			packageDetailsList.add(packageDetails);
		}
		return packageDetailsList;
	}

	// 服務價格
	private int getServicePrice(int serviceId) {
		switch (serviceId) {
		case 1:
			return 1000;
		case 2:
			return 2000;
		case 3:
			return 1900;
		case 4:
			return 2800;
		default:
			LOGGER.log(Level.WARNING, "未知的服務 ID: " + serviceId);
			return 0;
		}
	}

	// 加購價格
	private int getExtraPrice(int extraId) {
		switch (extraId) {
		case 1:
			return 100;
		case 2:
			return 300;
		case 3:
			return 350;
		case 4:
			return 350;
		default:
			LOGGER.log(Level.WARNING, "未知的加購 ID: " + extraId);
			return 0;
		}
	}

}
