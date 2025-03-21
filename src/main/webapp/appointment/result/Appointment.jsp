<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<html>
<head>
<title>預約結果</title>
<style>
body {
	display: flex;
	width: 100vw;
	height: 100vh;
	background-color: #f9f9f9;
	justify-content: center;
}

.message {
	font-size: 1.2em;
	padding: 10px;
	margin-bottom: 20px;
}

.message.success {
	color: green;
}

.message.error {
	color: red;
}

.success {
	color: green;
	font-weight: bold;
}

.error {
	color: red;
	font-weight: bold;
}

a {
	text-decoration: none;
	color: blue;
	margin: 0 10px;
}
</style>
</head>
<body>

	<div class="content" id="content">
		<div class="container">
			<h2>預約結果</h2>
			<%
			String success = request.getAttribute("success") != null ? (String) request.getAttribute("success") : "";
			if ("true".equals(success)) {
			%>
			<div class="message success">預約成功</div>
			<%
			} else if ("false".equals(success)) {
			%>
			<div class="message error">預約失敗</div>
			<%
			}

			Integer appointmentId = (Integer) request.getAttribute("appointmentId");
			Integer totalPrice = (Integer) request.getAttribute("totalPrice");
			String message = request.getAttribute("message") != null ? (String) request.getAttribute("message") : "";
			%>

			<%
			if (appointmentId != null && appointmentId > 0) {
			%>
			<p class="success"><%=message%></p>
			<p>
				預約編號: <strong><%=appointmentId%></strong>
			</p>
			<%
			if (totalPrice != null && totalPrice > 0) {
			%>
			<p>
				總價格: <strong><%=totalPrice%> 元</strong>
			</p>
			<%
			} else {
			%>
			<p></p>
			<%
			}
			%>
			<%
			} else {
			%>
			<p class="error"><%=message%></p>
			<%
			}
			%>

			<br> <a href="/Topics/appointment/home/Appointment.jsp">返回首頁</a>

		</div>
	</div>
</body>
</html>

