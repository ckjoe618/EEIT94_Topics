<%@page import="java.util.*,com.topics.order.model.bean.OrderBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂單管理</title>
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
      th, td {
        border: 1px solid black;
        padding: 3px;
	  }
	  .insidebtn{
		font-size: 13px;
	  }
	  #noneMsg{
	  	display:none;
	  	color:#FF5151;
	  	font-size: 18px;
	  }
</style>
</head>
<body>
	<div align="center" class="content">
		<div class="header">訂單查詢結果</div>
		<table border="1" style="border-collapse: collapse">
		<tr style="background-color:#BEBEBE">
			<th>訂單編號<th>會員編號<th>訂單金額<th>交易時間<th>付款方式<th>付款狀態<th>訂單狀態<th>取貨方式
				<th>物流編號<th>更新時間<th>明細<th>修改<th>刪除
			<%
			List<OrderBean> orders = (ArrayList<OrderBean>)request.getAttribute("orders");
					for(OrderBean order : orders){
			%>
				<tr><td><%= order.getOrderId() %>
				<td><%= order.getMemberId() %>
				<td><%= order.getPriceTotal() %>
				<td><%= order.getTransactionTime() %>
				<td><%= order.getPaymentMethod() %>
				<td><%= order.getPaymentStatus() %>
				<td><%= order.getOrderStatus() %>
				<td><%= order.getPickupMethod() %>
				<td><%= order.getTrackingNum() != null ? order.getTrackingNum() : "" %>
				<td><%= order.getUpdateTime() %>
				<td><button class="insidebtn">查看</button>
				<td><form action="OrderServlet" method="POST">
        				<input type="hidden" value="<%= order.getOrderId() %>" name="orderid">
        				<input type="hidden" value="getOrder" name="action"/>
       	 				<button class="insidebtn" type="submit">修改</button>
    				</form>
				</td>
				<td><form action="OrderServlet" method="POST" onsubmit="return confirm('確定要刪除此訂單嗎？')">
        				<input type="hidden" value="<%= order.getOrderId() %>" name="orderid">
        				<input type="hidden" value="delete" name="action"/>
       	 				<button class="insidebtn" type="submit" >刪除</button>
    				</form>
				</td></tr>
			<% } %>
		</table>
		<p id="noneMsg">☹ 查無訂單資料</p>
		<br><br>
		<form action="OrderServlet" method="POST">
			<button type="submit">返回全訂單列表</button>
		</form>
	</div>
	<script>
		let orders = ${orders};
		if(orders.length === 0){
			document.querySelector("#noneMsg").style.display = "block";
		}
	</script>
</body>
</html>