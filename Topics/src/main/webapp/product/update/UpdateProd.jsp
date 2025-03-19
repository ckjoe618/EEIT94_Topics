<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>產品資料</title>
	</head>

	<body style="background-color:#fdf5e6">
		<div align="center">
			<h2>產品資料</h2>
			<jsp:useBean id="prod" scope="request" class="com.topics.product.model.bean.ProdBean" />

			<form method="post" class="updateForm" action="ProductCRUD">
				<input type="hidden" name="actionType" value="update2">
				<table>
					<tr>
						<td>產品編號
						<td><input type="text" readonly name="product_id" value="<%= prod.getProduct_id() %>">
					<tr>
						<td>產品名稱
						<td><input type="text" name="product_name" value="<%= prod.getProduct_name() %>">
					<tr>
						<td>描述
						<td><input type="text" name="product_des" value="<%= prod.getProduct_des() %>">
					<tr>
						<td>價格
						<td><input type="text" name="price" value="<%= prod.getPrice() %>">
					<tr>
						<td>庫存量
						<td><input type="text" name="stock" value="<%= prod.getStock() %>">
					<tr>
						<td>類別
						<td><input type="text" name="category_name" value="<%=prod.getCategory_name()%>">
					<tr>
						<td>照片位址
						<td><input type="text" name="photo" value="<%=prod.getPhoto()%>">
					<tr>
						<td>總星數
						<td><input type="text" name="total_star" value="<%=prod.getTotal_star()%>">
					<tr>
						<td>總評論數
						<td><input type="text" name="total_reviews" value="<%=prod.getTotal_review()%>">
					<tr>
						<td>平均星數
						<td><input type="text" readonly value="<%=prod.getAverage_rating()%>">
				</table>
				<input type="submit" class="update" value="修改" />
			</form>
		</div>

		<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script>

			$(document).on("click", ".update", function (event) {
				event.preventDefault();
				// 先攔截表單，不要立即提交

				let form = $(this).closest(".updateForm"); // 找到對應的表單

				console.log("即將提交表單：", form);
				Swal.fire({
					title: "確定要修改嗎?",
					text: "修改後將無法恢復!",
					icon: "warning",
					showCancelButton: true,
					confirmButtonColor: "#d33",
					cancelButtonColor: "#3085d6",
					confirmButtonText: "確定修改",
					cancelButtonText: "取消"
				}).then(function (result) {
					if (result.isConfirmed) { // 確認按鈕才送出
						Swal.fire({
							title: "已修改！",
							text: "這筆紀錄已被修改~",
							icon: "success",
							timer: 2000, // 顯示2秒
							showConfirmButton: false
						}).then(function () {
							form.submit();
						});
					}


				})

			});
		</script>
	</body>

	</html>