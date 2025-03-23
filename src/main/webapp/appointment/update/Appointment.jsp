<%@ page import="java.sql.*,com.topics.appointment.model.bean.*,com.topics.appointment.model.dao.AppointmentDAO, org.hibernate.SessionFactory, org.hibernate.cfg.Configuration, org.hibernate.Session, java.util.List, java.util.HashSet, java.util.Set, com.topics.util.HibernateUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String appointmentId = request.getParameter("appointmentId");
    String memberId = request.getParameter("memberId");

    // 驗證 appointmentId 和 memberId 是否有效
    if (appointmentId == null || appointmentId.isEmpty() || memberId == null || memberId.isEmpty()) {
        request.setAttribute("errorMessage", "預約 ID 或 會員 ID 無效");
        request.getRequestDispatcher("/appointment/error/appointment.jsp").forward(request, response);
        return;
    }

    Appointment appointment = null;
    ItemDetails itemdetails = null;
    List<Integer> appointmentpackages = null;

    // 使用 HibernateUtil 獲取 SessionFactory
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    if (sessionFactory == null) {
        request.setAttribute("errorMessage", "SessionFactory not found.");
        request.getRequestDispatcher("/appointment/error/appointment.jsp").forward(request, response);
        return;
    }

    // 使用 try-with-resources 自動關閉 Session
    try (Session userSession = sessionFactory.openSession()) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(sessionFactory);

        // 獲取 Appointment 對象
        appointment = userSession.get(Appointment.class, Integer.parseInt(appointmentId));

        if (appointment == null) {
            request.setAttribute("errorMessage", "找不到該預約記錄");
            request.getRequestDispatcher("/appointment/error/Appointment.jsp").forward(request, response);
            return;
        }

        // 獲取 ItemDetails
        itemdetails = appointmentDAO.getServiceById(Integer.parseInt(appointmentId));

        // 取得額外服務包
        appointmentpackages = appointmentDAO.getSelectedExtraPackages(Integer.parseInt(appointmentId));

        // 將數據放入 request
        request.setAttribute("appointment", appointment);
        request.setAttribute("itemdetails", itemdetails);
        request.setAttribute("appointmentpackages", appointmentpackages);

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "無法獲取預約資料: " + e.getMessage());
        request.getRequestDispatcher("/appointment/error/appointment.jsp").forward(request, response);
        return;
    }
%>


<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改預約資訊</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.5.5/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.5.5/dist/sweetalert2.min.js"></script>
    <style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	display: flex;
	width: 100vw;
	height: 100vh;
	background-color: #f9f9f9;
	justify-content: center;
}

.content {
	flex: 1;
	padding: 20px;
	overflow-y: auto;
	justify-content: center;
}

form {
	width: 100%;
	max-width: 600px;
	margin: 0 auto;
	text-align: left;
}

.header {
	font-size: 24px;
	margin-bottom: 20px;
	text-align: center;
}

table {
	margin: 0 auto;
}

button {
	cursor: pointer;
	margin: 20px auto 0 auto;
}

#totalPrice2 {
	margin-top: 15px;
	font-size: 16px;
	font-weight: bold;
}
</style>
</head>
<body>
<div align="center" class="content">
 <div class="header">預約修改</div>
<form method="post" action="../AppointmentServlet">
    <input type="hidden" name="appointmentId" value="<%= appointment.getAppointmentId() %>">
    <input type="hidden" name="action" value="edit">
    
    <table>
    <tr><td>預約日期:<input type="date" id="appointmentDate" name="appointmentDate" value="<%= appointment.getAppointmentDate().toString() %>" required>
    
   <tr><td>預約時段:<select id="timeSlot" name="timeSlot" required>
        <option value="10:00-12:00" <%= "10:00-12:00".equals(appointment.getAppointmentTimeslot()) ? "selected" : "" %>>10:00-12:00</option>
        <option value="12:00-14:00" <%= "12:00-14:00".equals(appointment.getAppointmentTimeslot()) ? "selected" : "" %>>12:00-14:00</option>
        <option value="14:00-16:00" <%= "14:00-16:00".equals(appointment.getAppointmentTimeslot()) ? "selected" : "" %>>14:00-16:00</option>
        <option value="16:00-18:00" <%= "16:00-18:00".equals(appointment.getAppointmentTimeslot()) ? "selected" : "" %>>16:00-18:00</option>
        <option value="18:00-20:00" <%= "18:00-20:00".equals(appointment.getAppointmentTimeslot()) ? "selected" : "" %>>18:00-20:00</option>
    </select>
    
    <tr><td>總價格:<input type="number" id="totalPrice" name="totalPrice" value="<%= appointment.getAppointmentTotal() %>" required readonly>
	<tr><td>預約狀態:<select id="appointmentStatus" name="appointmentStatus" required>
        <option value="0" <%= appointment.getAppointmentStatus() == 0 ? "selected" : "" %>>未完成</option>
        <option value="1" <%= appointment.getAppointmentStatus() == 1 ? "selected" : "" %>>已完成</option>
        <option value="2" <%= appointment.getAppointmentStatus() == 2 ? "selected" : "" %>>已取消</option>
    </select>
    
    <tr><td>付款狀態:<select id="paymentStatus" name="paymentStatus" required>
        <option value="0" <%= appointment.getPaymentStatus() == 0 ? "selected" : "" %>>未付款</option>
		<option value="1" <%= appointment.getPaymentStatus() == 1 ? "selected" : "" %>>已付款</option>

    </select>
        <tr><td>選擇服務:<%Integer selectedItemId = (itemdetails != null) ? itemdetails.getItemId() : null;%><select name="services" id="serviceSelect" required>
            <option value="" disabled <%= (selectedItemId == null) ? "selected" : "" %>>請選擇服務</option>
            <option value="1" data-price="1000" <%= (selectedItemId != null && selectedItemId == 1) ? "selected" : "" %>>基礎洗澡 +1000元</option>
 		   	<option value="2" data-price="2000" <%= (selectedItemId != null && selectedItemId == 2) ? "selected" : "" %>>基礎洗護含美容修剪 +2000元</option>
    		<option value="3" data-price="1900" <%= (selectedItemId != null && selectedItemId == 3) ? "selected" : "" %>>專業洗護 +1900元</option>
    		<option value="4" data-price="2800" <%= (selectedItemId != null && selectedItemId == 4) ? "selected" : "" %>>專業洗護含美容修剪 +2800元</option>
        </select>

     <tr><td>額外加購:<br>
    <input type="checkbox" name="extraPackages" value="1" data-price="100" 
        <%= (appointmentpackages != null && appointmentpackages.contains(1)) ? "checked" : "" %>> 7公斤以上 +100元<br>
    <input type="checkbox" name="extraPackages" value="2" data-price="300" 
        <%= (appointmentpackages != null && appointmentpackages.contains(2)) ? "checked" : "" %>> 防蚤洗劑 +300元<br>
    <input type="checkbox" name="extraPackages" value="3" data-price="350" 
        <%= (appointmentpackages != null && appointmentpackages.contains(3)) ? "checked" : "" %>> 貓咪草本洗毛精 +350元<br>
    <input type="checkbox" name="extraPackages" value="4" data-price="350" 
        <%= (appointmentpackages != null && appointmentpackages.contains(4)) ? "checked" : "" %>> 狗狗草本洗毛精 +350元<br>
        <div id="totalPrice2">總價: 0元</div>
    <button type="submit">更新預約</button>
</table>
</form>
 </div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function () {
	 let today = new Date().toISOString().split("T")[0];
     $("input[type='date']").attr("min", today);
    function calculateTotalPrice() {
        let total = 0;
        let servicePrice = $("#serviceSelect option:selected").data("price");
        console.log("服務價格:", servicePrice);
        total += servicePrice || 0;

        $("input[type='checkbox']:checked").each(function () {
            let addonPrice = parseInt($(this).data("price"));
            console.log("加購價格:", addonPrice);
            total += addonPrice|| 0;
        });

        console.log("總價:", total);
        $("#totalPrice2").text("總價: " + total + " 元");
    }

    calculateTotalPrice();

    $(document).on("change", "#serviceSelect, input[type='checkbox']", function () {
        console.log("選擇變更，計算總價");
        calculateTotalPrice();
    });
});
let isBasicServiceAdded = false;

function updateServiceList() {
    $("#serviceList").empty(); 

    if (!isBasicServiceAdded) {
        let serviceText = $("#serviceSelect option:selected").text();
        if (serviceText) {
            $("#serviceList").append(`<li>${serviceText}</li>`);
            isBasicServiceAdded = true;
        }
    }

    $("input[type='checkbox']:checked").each(function () {
        let addonText = $(this).parent().text().trim();
        $("#serviceList").append(`<li>${addonText}</li>`);
    });
}

</script>