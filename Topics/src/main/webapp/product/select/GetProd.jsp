<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>產品資料</title>
</head>
<body style="background-color:#fdf5e6">
	<div align="center">
		<h2>產品資料</h2>
		<jsp:useBean id="prod" scope="request" class="com.topics.product.model.bean.ProdBean"/>
		<table>
			<tr><td>產品編號
				<td><input type="text" disabled value="<%= prod.getProduct_id() %>">
			<tr><td>產品名稱<td><input type="text" disabled value="<%= prod.getProduct_name() %>">
			<tr><td>描述<td><input type="text" disabled value="<%= prod.getProduct_des() %>" >
			<tr><td>價格<td><input type="text" disabled value="<%= prod.getPrice() %>" >
			<tr><td>庫存量<td><input type="text"disabled value="<%= prod.getStock() %>">
			<tr><td>類別<td><input type="text" disabled value="<%=prod.getCategory_name()%>">
			<tr><td>照片位址<td><input type="text" disabled value="<%=prod.getPhoto()%>">
			<tr><td>總星數<td><input type="text" disabled value="<%=prod.getTotal_star()%>">
			<tr><td>總評論數<td><input type="text" disabled value="<%=prod.getTotal_review()%>">
			<tr><td>平均星數<td><input type="text" disabled value="<%=prod.getAverage_rating()%>">
		</table>
	</div>

</body>
</html>