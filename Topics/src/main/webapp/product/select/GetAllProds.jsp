<%@page import="java.util.*,com.topics.product.model.bean.ProdBean" %>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<%!@SuppressWarnings("unchecked")%>
			<!DOCTYPE html>
			<html>

			<head>
				<meta charset="UTF-8">
				<title>產品資料</title>
				<style>
					* {
						margin: 0;
						padding: 0;
						box-sizing: border-box;
						/* font-family: Arial, sans-serif; */
					}

					body {
						display: flex;
						width: 100vw;
						height: 100vh;
						background-color: #f9f9f9;
					}

					.sidebar {
						width: 250px;
						height: 100vh;
						background-color: #212121;
						color: white;
						padding: 20px;
					}

					.sidebar h2 {
						font-size: 28px;
						margin-bottom: 20px;
					}

					.sidebar ul {
						list-style: none;
					}

					.sidebar ul li {
						padding: 10px 0;
						cursor: pointer;
					}

					.sidebar ul li:hover {
						background-color: #333;
					}

					.content {
						/* flex-grow ｜ flex-shrink ｜ flex-basis: 1 1 0%; */
						flex: 1;
						padding: 20px;
						overflow-y: auto;
						font-size: 16px;
					}

					.header {
						font-size: 24px;
						margin-bottom: 20px;
					}

					.btn {
						width: 80px;
						height: 30px;
						font-size: 16px;
						background-color: #FFF4C1;
						border-radius: 10px;
						margin-right: 90%;
						cursor: pointer;
					}

					.btn:hover {
						background-color: #FFE66F;
					}

					th,
					td {
						border: 1px solid black;
						padding: 3px;
						width: 100px;
					}

					.insidebtn,
					#query {
						font-size: 13px;
					}

					.inTd {
						width: 100px;
					}

					.topBtn {

						margin-bottom: 5px;
						display: flex;
						justify-content: space-between;
						/* 讓 form 在左，button 在右 */
						align-items: center;
						/* 垂直置中 */
						width: 100%;
						/* 讓容器填滿可用寬度 */
					}

					.topBtn form {
						display: flex;
						align-items: center;
						gap: 5px;
						/* 調整輸入框與按鈕之間的距離 */
					}

					.topBtn a {
						text-decoration: none;
					}

					.topBtn button {

						cursor: pointer;
					}
				</style>
			</head>

			<body>


				<div align="center" class="content">
					<h2>產品資料</h2>
					<div class="topBtn">
						<form method="post" action="ProductCRUD">
							<input type="hidden" name="actionType" value="getSingle">
							產品ID:<input type="text" name="product_id" /><input type="submit" value="查詢">
						</form>
						<a href="http://localhost:8080/Topics/product/insert/insert.html"><button>新增單筆</button></a>
					</div>
					
					<table border="1" style="border-collapse: collapse">
						<tr style="background-color:#BEBEBE">
							<th>產品編號
							<th>產品名稱
							<th>描述
							<th>價格
							<th>庫存量
							<th>類別
							<th>照片位址
							<th>總星數
							<th>總評論數
							<th>平均星數
							<th>修改
							<th>刪除
								<% List<ProdBean> prods = (ArrayList<ProdBean>)request.getAttribute("prods");
										for(ProdBean prod :prods) {
										%>
						<tr>
							
								<td>
								<%= prod.getProduct_id() %>
									<input class="inTd" type="hidden" name="product_id" readonly
										value="<%= prod.getProduct_id() %>">

								<td>
								<%= prod.getProduct_name() %>
									<input class="inTd" type="hidden" name="product_name"
										value="<%= prod.getProduct_name() %>">

								<td>
								<%= prod.getProduct_des() %>
									<input class="inTd" type="hidden" name="product_des"
										value="<%= prod.getProduct_des() %>">

								<td>
								<%= prod.getPrice() %>
									<input class="inTd" type="hidden" name="price" value="<%= prod.getPrice() %>">

								<td>
								<%= prod.getStock() %>
									<input class="inTd" type="hidden" name="stock" value="<%= prod.getStock() %>">

								<td>
								<%= prod.getCategory_name() %>
									<input class="inTd" type="hidden" name="category_name"
										value="<%= prod.getCategory_name() %>">

								<td>
									<img src="<%= prod.getPhoto() %>" alt="產品圖片" width="100">
									<input class="inTd" type="hidden" name="photo" value="<%= prod.getPhoto() %>">

								<td>
								<%= prod.getTotal_star() %>
									<input class="inTd" type="hidden" name="total_star"
										value="<%= prod.getTotal_star() %>">

								<td>
								<%= prod.getTotal_review() %>
									<input class="inTd" type="hidden" name="total_reviews"
										value="<%= prod.getTotal_review() %>">

								<td>
								<%= prod.getAverage_rating() %>
									<input class="inTd" type="hidden" readonly value="<%= prod.getAverage_rating() %>">
								<td>
								
									<form method="post" action="ProductCRUD">
										
										<input type="hidden" name="actionType" value="update">
										<input type="hidden" name="product_id" value="<%= prod.getProduct_id() %>">
										<input type="submit" name="btnUpdate" value="修改" />	
									</form>
								<td>
									<form class="closestForm" method="post" action="ProductCRUD">
									<input type="hidden" name="actionType" value="delete">
									<input type="hidden" name="product_id" value="<%= prod.getProduct_id() %>"><input type="submit"
										class="btnDelete" name="btnDelete" value="刪除" />
									</form>
							
							<%}%>

					</table>


					<h3>共<%=prods.size() %>筆產品資料</h3>
				</div>


				<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
				<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
				<script>

					$(document).on("click", ".btnDelete", function (event) {
						event.preventDefault();
						// 先攔截表單，不要立即提交

						let form = $(this).closest(".closestForm"); // 找到對應的表單

						console.log("即將提交表單：", form);
						Swal.fire({
							title: "確定要刪除嗎?",
							text: "刪除後將無法恢復!",
							icon: "warning",
							showCancelButton: true,
							confirmButtonColor: "#d33",
							cancelButtonColor: "#3085d6",
							confirmButtonText: "確定刪除",
							cancelButtonText: "取消"
						}).then(function (result) {
							if (result.isConfirmed) { // 確認按鈕才送出
								Swal.fire({
									title: "已刪除！",
									text: "這筆紀錄已被刪除~",
									icon: "success",
									timer: 2000, // 顯示2秒
									showConfirmButton: false
								}).then(function () {
									console.log("表單即將提交");
									form.submit();
								});
							}


						})

					});
				</script>
			</body>

			</html>