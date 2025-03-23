<%@ page import="java.sql.*,com.topics.appointment.model.bean.*,com.topics.appointment.model.dao.AppointmentDAO, org.hibernate.SessionFactory, org.hibernate.cfg.Configuration, org.hibernate.Session, java.util.List, java.util.HashSet, java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	Configuration configuration = new Configuration();
	configuration.configure("hibernate.cfg.xml");
	SessionFactory sessionFactory = configuration.buildSessionFactory();
	String phoneNumber = request.getParameter("phone_number");
	AppointmentDAO appointmentDAO = new AppointmentDAO(sessionFactory);
	List<Appointment> appointments = appointmentDAO.getAllAppointments();
	if (phoneNumber != null && !phoneNumber.isEmpty()) {
        appointments = appointmentDAO.searchAppointmentsByPhoneNumber(phoneNumber);
    }
%>

<style>


body {
	display: flex;
	width: 100vw;
	height: 100vh;
	background-color: #f9f9f9;
	justify-content: center;
}
h2 {
	margin-bottom: 20px;
	text-align: center;
	
}
a {
    display: block; 
    text-align: center; 
}
table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}

th {
	background-color: #f2f2f2;
	font-weight: bold;
}

tr:hover {
	background-color: #f5f5f5;
}
</style>

<div class="content">
	<h2>預約資訊</h2>
	<%
	if (appointments != null && !appointments.isEmpty()) {
	%>
	<table>
		<tr>
			<th>預約編號</th>
			<th>預約日期</th>
			<th>時間</th>
			<th>預約服務</th>
			<th>加購服務</th>
			<th>總價格</th>
			<th>預約狀態</th>
			<th>付款狀態</th>
		</tr>
		<%
		for (Appointment appointment : appointments) {
		%>
		<tr>
			<td><%=appointment.getAppointmentId()%></td>
			<td><%=appointment.getAppointmentDate()%></td>
			<td><%=appointment.getAppointmentTimeslot()%></td>
			<td>
				<%
				String serviceNames = appointment.getServiceNames();
				if (serviceNames != null && !serviceNames.isEmpty()) {
					Set<String> uniqueServices = new HashSet<>();
					String[] services = serviceNames.split(",");
					for (String service : services) {
						uniqueServices.add(service.trim());
					}
					out.print(String.join(", ", uniqueServices));
				} else {
					out.print("無服務資料");
				}
				%>
			</td>
			<td><%=appointment.getAdditionalPackages() != null ? appointment.getAdditionalPackages() : "無加購服務"%></td>
			<td><%=appointment.getAppointmentTotal()%></td>
			<td><%=appointment.getAppointmentStatus() == 0 ? "未完成" : (appointment.getAppointmentStatus() == 1 ? "已完成" : "已取消")%></td>
			<td><%=appointment.getPaymentStatus() == 0 ? "未付款" : "已付款"%></td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	} else {
	%>
	<p style="text-align: center; color: #888;">找不到與此會員相關的預約資訊。</p>
	<%
	}
	%>
	<br> <a href="/Topics/appointment/home/Appointment.jsp">返回首頁</a>
</div>
