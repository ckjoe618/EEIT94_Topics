<%@page import="java.util.*,com.topics.order.model.bean.OrderBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂單管理</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
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
	.btn{
		width: 80px;
		height: 30px;
		font-size: 16px;
		background-color: #FFF4C1;
		border-radius: 10px;
		margin-right: 90%;
		cursor: pointer;
	}
	.btn:hover{
	background-color:#FFE66F;
	}
	th, td {
        border: 1px solid black;
        padding: 3px;
    }
	.insidebtn, #query{
		font-size: 13px;
	}
</style>
</head>
<body>
	<div align="center" class="content">
		<div class="header">訂單列表</div>
		<div>
			<button class="btn" onclick="window.location.href='order/InsertOrder.html'">新增訂單</button>
			<form action="OrderServlet" method="POST">
			訂單編號：<input type="text" name="orderfilter" id="orderfilter" style="width:100px" placeholder="請輸入"/>
			<span style="margin: 40px;font-weight: bold;color:#4F4F4F">或</span>
			會員編號：<input type="text" name="memberfilter" id="memberfilter" style="width:100px" placeholder="請輸入"/>
			交易期間：<input type="text" name="txntimefilter" id="txntimefilter" placeholder="請選擇" style="width:210px"/>
			<button type="button" id="clearDate" style="background: none; border: none; font-size: 12px; cursor: pointer">✘</button>
			<span style="margin: 15px"></span>
			<input type="hidden" value="getByCons" name="action"/>
			<input type="submit" value="查詢" id="query" style="cursor: pointer"/>
			</form>
		</div><br>
		<table border="1" style="border-collapse: collapse">
		<tr style="background-color:#BEBEBE">
			<th>訂單編號<th>會員編號<th>訂單金額<th>交易時間<th>付款方式<th>付款狀態<th>訂單狀態<th>取貨方式
				<th>物流編號<th>更新時間<th>明細<th>修改<th>刪除
			<% List<OrderBean> orders = (ArrayList<OrderBean>)request.getAttribute("orders");
			for(OrderBean order : orders){ %>
				<tr><td><%= order.getOrderid() %>
				<td><%= order.getMemberid() %>
				<td><%= order.getPricetotal() %>
				<td><%= order.getTransactiontime() %>
				<td><%= order.getPaymentmethod() %>
				<td><%= order.getPaymentstatus() %>
				<td><%= order.getOrderstatus() %>
				<td><%= order.getPickupmethod() %>
				<td><%= order.getTrackingnum() != null ? order.getTrackingnum() : "" %>
				<td><%= order.getUpdatetime() %>
				<td><button class="insidebtn">查看</button>
				<td><form action="OrderServlet" method="POST">
        				<input type="hidden" value="<%= order.getOrderid() %>" name="orderid">
        				<input type="hidden" value="getOrder" name="action"/>
       	 				<button class="insidebtn" type="submit">修改</button>
    				</form>
				</td>
				<td><form id="deleteForm" action="OrderServlet" method="POST" onsubmit="return confirm('確定要刪除此訂單嗎？')">
        				<input type="hidden" value="<%= order.getOrderid() %>" name="orderid">
        				<input type="hidden" value="delete" name="action"/>
       	 				<button class="insidebtn" type="submit" >刪除</button>
    				</form>
				</td></tr>
			<% } %>
		</table>
		</div>
	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script>
		//篩選條件日期選擇
		let fp = flatpickr("#txntimefilter", {
			mode: "range",         // 啟用範圍選擇
		    dateFormat: "Y-m-d",   // 設定日期格式
		    minDate: "2024-01-01",      // 限制最小可選日期
		    maxDate: "2025-12-31", // 限制最大可選日期
	  	});
		$("#clearDate").on("click", function() {
	        fp.clear();
	    });
		//篩選條件輸入限制
		$("#orderfilter").on("input", function(){
            if ($(this).val().trim() !== "") {
                $("#memberfilter").prop("disabled", true);
                $("#txntimefilter").prop("disabled", true);
            } else {
                $("#memberfilter").prop("disabled", false);
                $("#txntimefilter").prop("disabled", false);
            }
        });
		$("#memberfilter, #txntimefilter").on("input", function(){
            if ($("#memberfilter").val().trim() !== "" || $("#txntimefilter").val().trim() !== "") {
                $("#orderfilter").prop("disabled", true);
            } else {
                $("#orderfilter").prop("disabled", false);
            }
        });
	</script>
</body>
</html>