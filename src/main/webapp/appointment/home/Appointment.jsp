<%@ page import="java.sql.*,com.topics.appointment.model.bean.*,com.topics.appointment.model.dao.AppointmentDAO,org.hibernate.SessionFactory, org.hibernate.cfg.Configuration, org.hibernate.Session,java.util.List,java.util.HashSet,java.util.Set"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Configuration configuration = new Configuration();
configuration.configure("hibernate.cfg.xml");
SessionFactory sessionFactory = configuration.buildSessionFactory();
AppointmentDAO appointmentDAO = new AppointmentDAO(sessionFactory);
List<Appointment> appointments = appointmentDAO.getAllAppointments();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>寵物美容預約系統</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11.3.0/dist/sweetalert2.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11.3.0/dist/sweetalert2.min.js"></script>

<style>
body {
	display: flex;
	width: 100vw;
	height: 100vh;
	background-color: #f9f9f9;
	flex-direction: column;
	align-items: center;
}

span {
	color: red;
	font-size: 12px;
	margin-left: 5px;
}

form {
	margin-bottom: 20px;
	width: 100%;
}

.header {
	font-size: 24px;
	margin-bottom: 20px;
}

#search-container {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 80%;
	max-width: auto;
	gap: 20px;
	margin-top: 20px;
}

#search-container form {
	flex-grow: 1;
	display: flex;
	justify-content: center;
	margin-top: 20px;
}

#search-container button {
	margin-left: 20px;
}

label {
	display: inline-block;
	margin-top: 10px;
	margin-bottom: 5px;
}

input[type="number"], input[type="date"], select {
	padding: 8px;
	margin: 5px 0;
	border: 1px solid #ccc;
	border-radius: 4px;
	width: 100%;
	max-width: 300px;
}

input[type="checkbox"] {
	margin-right: 10px;
}

#totalPrice {
	margin-top: 10px;
	font-weight: bold;
	font-size: 15px;
}

th, td {
	border: 1px solid black;
	padding: 10px;
	height: 50px;
	text-align: center;
}

table {
	width: 100%;
	border-collapse: collapse;
}

tr {
	background-color: #F0F0F0;
}

th {
	background-color: #BEBEBE;
}

td {
	text-align: center;
}

td:last-child, td:nth-last-child(2), td:first-child {
	width: 70px;
}

.form-buttons {
	display: flex;
	justify-content: space-between;
}

button#openModalBtn {
	width: 100px;
	height: 30px;
	font-size: 16px;
	background-color: #FFF4C1;
	border: 2px solid black;
	color: black;
	border-radius: 10px;
	cursor: pointer;
	text-align: center;
	display: inline-block;
	line-height: normal;
	vertical-align: middle;
}

button#openModalBtn:hover {
	background-color: #FFE66F;
	border-color: #666666;
}

.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
	background-color: #fefefe;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	max-width: 500px;
	border-radius: 10px;
}

.close-btn {
	color: #aaa;
	font-size: 28px;
	font-weight: bold;
	position: absolute;
	top: 0;
	right: 10px;
}

.close-btn:hover, .close-btn:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}

@media ( max-width : 768px) {
	input[type="number"], input[type="date"], select, button {
		width: 100%;
		max-width: 100%;
	}
	.form-buttons {
		flex-direction: column;
		align-items: stretch;
	}
	#search-container {
		flex-direction: column;
		align-items: stretch;
	}
	#search-container button {
		margin-left: 0;
		margin-top: 10px;
	}
}
</style>


</head>
<body>
	<div class="header">預約列表</div>
	<div id="search-container">
		<button type="button" id="openModalBtn">新增預約</button>
		<form action="/Topics/appointment/query/Appointment.jsp" method="GET">
			預約電話: <input type="text" id="phone_number" name="phone_number">
			<span id="nameError"></span>

			<button type="submit" id="submitBtn" disabled>查詢</button>
		</form>

	</div>
	<div id="orderModal" class="modal">
		<div class="modal-content">
			<span class="close-btn">&times;</span>
			<h2>新增預約</h2>
			<form action="../AppointmentServlet" method="POST">
				<input type="hidden" name="action" value="add"> 
				
				會員 ID<input type="number" id="memberId" name="memberId" required><br>
				 
				 寵物:<select id="appointmentpetId" name="appointmentpetId" required="">
    <option value="" disabled selected>請選擇寵物</option>
</select>

            </select><br>
            
				<label for="appointmentDate">
				預約日期:</label> <input type="date" id="appointmentDate" name="appointmentDate" required><br>
				
				<label for="appointmentTimeslot">
				預約時段:</label> <select id="appointmentTimeslot" name="appointmentTimeslot" required>
					<option value="" disabled selected>請選擇時段</option>
				</select><br> 
				
				選擇服務: <select name="services" id="serviceSelect">
					<option value="" disabled selected>請選擇服務</option>
					<option value="1" data-price="1000">基礎洗澡 +1000元</option>
					<option value="2" data-price="2000">基礎洗護含美容修剪 +2000元</option>
					<option value="3" data-price="1900">專業洗護 +1900元</option>
					<option value="4" data-price="2800">專業洗護含美容修剪 +2800元</option>
				</select><br> 
				
				額外加購:<br> 
					<label><input type="checkbox" name="extraPackages" value="1" data-price="100"> 7公斤以上 +100元</label><br> 
					<label><input type="checkbox" name="extraPackages" value="2" data-price="300"> 防蚤洗劑 +300元</label><br>
					<label><input type="checkbox" name="extraPackages" value="3" data-price="350"> 貓咪草本洗毛精 +350元</label><br> 
					<label><input type="checkbox" name="extraPackages" value="4" data-price="350"> 狗狗草本洗毛精 +350元</label><br>

				<div id="totalPrice">總價: 0元</div>
				<button type="submit">新增預約</button>
			</form>
		</div>
	</div>

	<form method="post" action="../AppointmentServlet">
		<div id="serviceList">
			<%
			if (appointments != null && !appointments.isEmpty()) {
			%>
			<table border="1">
				<tr>
					<th>預約編號</th>
					<th>預約日期</th>
					<th>時間</th>
					<th>預約服務</th>
					<th>加購服務</th>
					<th>總價格</th>
					<th>預約狀態</th>
					<th>付款狀態</th>
					<th>刪除</th>
					<th>修改</th>
				</tr>
				<%
				for (Appointment appointment : appointments) {
				%>
				<tr>
					<td><%=appointment.getAppointmentId()%></td>
					<td><%=appointment.getAppointmentDate()%></td>
					<td><%=appointment.getAppointmentTimeslot()%></td>
					<td><%
						Set<String> uniqueServices = new HashSet<>();
						String serviceNames = appointment.getServiceNames();
						if (serviceNames != null && !serviceNames.isEmpty()) {
							String[] services = serviceNames.split(",");
							for (String service : services) {
						uniqueServices.add(service.trim());
							}
						}
						for (String service : uniqueServices) {
							out.print(service + "<br>");
						}
					%></td>
					<td><%=appointment.getAdditionalPackages()%></td>
					<td><%=appointment.getAppointmentTotal()%></td>
					<td><%=appointment.getAppointmentStatus() == 0 ? "未完成" : (appointment.getAppointmentStatus() == 1 ? "已完成" : "已取消")%></td>
					<td><%=appointment.getPaymentStatus() == 0 ? "未付款" : "已付款"%></td>
					<td>
						<form class="deleteForm" action="../AppointmentServlet"
							method="POST" style="display: inline;">
							<input type="hidden" name="appointmentId"
								value="<%=appointment.getAppointmentId()%>"> <input
								type="hidden" name="action" value="delete">
							<button class="deletebtn insidebtn" type="button"
								data-appointmentid="<%=appointment.getAppointmentId()%>">刪除</button>
						</form>
					</td>
					<td>
						<form id="updateForm" action="" method="post"
							style="display: inline;">
							<input type="hidden" name="appointmentId"
								value="<%=appointment.getAppointmentId()%>">
							<button class="updatebtn insidebtn" type="button"
								data-appointmentid="<%=appointment.getAppointmentId()%>">修改</button>
						</form>
					</td>
				</tr>
				<%
				}
				%>
			</table>
			<%
			} else {
			%>
			<p>目前沒有預約資料。</p>
			<%
			}
			%>
		</div>

	</form>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        // Validate phone number input
        $("#phone_number").on("input", function () {
            let phoneInput = this.value; 
            let errorMsg = $("#nameError");
            let submitBtn = $("#submitBtn");
            let validPhonePattern = /^09\d{8}$/;

            if (!validPhonePattern.test(phoneInput)) {
                errorMsg.text("請輸入有效電話號碼");  
                $(this).css("borderColor", "red");  
                submitBtn.prop("disabled", true);   
            } else {
                errorMsg.text("");                 
                $(this).css("borderColor", "");    
                submitBtn.prop("disabled", false); 
            }
        });

        // Ensure IDs are positive integers
        $('#memberId, #appointmentpetId').on('input', function () {
            if ($(this).val() < 1) {
                $(this).val(1);
            }
        });

        // Set the minimum date to today's date for date input
        let today = new Date().toISOString().split("T")[0];
        $("input[type='date']").attr("min", today);

        // Show the modal when the "Open Modal" button is clicked
        $("#openModalBtn").click(function () {
            $("#orderModal").show();
        });

        // Hide the modal when the close button or outside the modal is clicked
        $(".close-btn").click(function () {
            $("#orderModal").hide();
        });
        $(window).click(function (event) {
            if ($(event.target).is("#orderModal")) {
                $("#orderModal").hide();
            }
        });

        // Calculate total price based on selected services and checkboxes
        function calculateTotalPrice() {
            let total = 0;
            let servicePrice = parseInt($("#serviceSelect option:selected").data("price")) || 0;
            total += servicePrice;
            $("input[type='checkbox']:checked").each(function () {
                total += parseInt($(this).data("price")) || 0;
            });
            $("#totalPrice").text("總價: " + total + "元");
        }

        // Trigger price calculation when checkboxes or service select change
        $("input[type='checkbox'], #serviceSelect").change(calculateTotalPrice);
        calculateTotalPrice();  // Initialize price calculation

        // AJAX request for available booking times based on selected date
        $("#appointmentDate").on("change", function() {
            var selectedDate = $(this).val(); 
            console.log("選擇的日期是: " + selectedDate);

            if (!selectedDate) {
                alert("請選擇一個日期");
                return;
            }

            $.ajax({
                type: "GET",
                url: "../AppointmentServlet",
                data: { action: "querybookingtime", appointmentDate: selectedDate },
                dataType: "json",
                success: function(response) {
                    console.log(response);
                    var bookedTimeslots = response.bookedTimeslots || []; 
                    var allTimeslots = ["10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00"];

                    var timeslotSelect = $("#appointmentTimeslot");
                    timeslotSelect.empty(); 

                    allTimeslots.forEach(function(slot) {
                        var option = $("<option></option>").val(slot).text(slot);

                        if (bookedTimeslots.includes(slot)) {
                            option.prop("disabled", true);
                        }

                        timeslotSelect.append(option); 
                    });
                },
                error: function(xhr, status, error) {
                    console.error("無法獲取預約資料: " + error);
                }
            });
        });

     // AJAX request for fetching pets based on selected member
        $("#memberId").on("change", function () {
            let memberId = $(this).val();
            let petSelect = $("#appointmentpetId");
            console.log("🔍 petSelect HTML:", petSelect.prop("outerHTML"));

            petSelect.empty().append('<option value="" disabled selected>請選擇寵物</option>');

            if (memberId) {
                $.ajax({
                    url: "../AppointmentServlet",
                    method: "GET",
                    data: { action: "querypet", memberId: memberId },
                    dataType: "json",
                    success: function (data) {
                        console.log("✅ AJAX 成功獲取寵物資料:", JSON.stringify(data, null, 2));

                        if (data.length > 0) {
                            $.each(data, function (index, pet) {
                                console.log("🔍 取得的寵物資料:", pet);

                                let petId = pet.petId ? parseInt(pet.petId, 10) : NaN;
                                let petName = pet.petName || "";

                                console.log("🔍 petId:", petId, "petName:", petName);

                                if (!isNaN(petId) && petName) {
                                    petSelect.append('<option value="' + petId + '">' + petName + '</option>');
                                    if (index === 0) {
                                        $('#appointmentpetId').val(petId);
                                    }
                                    console.log("🟢 生成的選項: <option value='" + petId + "'>" + petName + "</option>");
                                } else {
                                    console.error("❌ petId 或 petName 無效:", pet);
                                }
                            });

                            if (petSelect.val() === "") {
                                petSelect.val(""); 
                            }

                            petSelect.trigger("change");

                        } else {
                            console.warn("⚠️ 未找到寵物資料！");
                        }

                        console.log("选择的宠物ID: " + petSelect.val());
                    },
                    error: function () {
                        alert("無法載入寵物資料");
                    }
                });
            }
        });
        $("#appointmentpetId").on("change", function() {
            let selectedPetId = $(this).val();
            console.log("当前选择的宠物 ID:", selectedPetId);  

            if (!selectedPetId) {
                event.preventDefault();
                alert("請選擇寵物！");
            }
        });
        // Delete confirmation using SweetAlert
        $(document).on("click", ".deletebtn", function () {
            let appointmentId = $(this).data("appointmentid");
            let form = $(this).closest("form");

            Swal.fire({
                title: "確定要刪除？",
                text: "刪除後將無法復原！",
                icon: "warning",
                width: "400px",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "確定刪除",
                cancelButtonText: "取消"
            }).then((result) => {
                if (result.isConfirmed) {
                    form.find("input[name='appointmentId']").val(appointmentId);
                    form.submit();
                }
            });
        });

        // Update confirmation using SweetAlert
        $(document).on("click", ".updatebtn", function () {
            let appointmentId = $(this).data("appointmentid");
            let form = $(this).closest("form");
            form.attr("action", "/Topics/appointment/update/Appointment.jsp");
            form.find("input[name='appointmentId']").val(appointmentId);
            console.log("Form action: " + form.attr("action"));
            console.log("Appointment ID: " + appointmentId);
            Swal.fire({
                title: "確定要修改？",
                icon: "warning",
                width: "400px",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "確定修改",
                cancelButtonText: "取消"
            }).then((result) => {
                if (result.isConfirmed) {
                    form.submit();
                }
            });
        });
    });
</script>



</body>
</html>
