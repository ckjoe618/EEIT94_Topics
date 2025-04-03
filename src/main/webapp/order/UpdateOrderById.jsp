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
      }
      .header {
        font-size: 24px;
        margin-bottom: 20px;
      }
	.Msg{
	color: red;
	font-size: 12px;
	}
</style>
</head>
<body>
	<div align="center" class="content">
		<div class="header">修改訂單</div>
		<jsp:useBean id="odb" scope="request" class="com.topics.order.model.bean.OrderBean" />
		<form action="OrderServlet" method="post" id="form"  onsubmit="return confirm('確認修改？')">
		
		<table>
			<tr><td>訂單編號：<td><input type="text" name="orderid" readonly value="<%= odb.getOrderId() %>">
			<tr><td>會員編號：<td><input type="text" name="memberid" value="<%= odb.getMemberId() %>" id="memberid" required><td><span id=mbMsg class="Msg"></span>
			<tr><td>訂單金額：<td><input type="text" name="totalprice" value="<%= odb.getPriceTotal() %>" id="totalprice" required><td><span id=ttpMsg class="Msg"></span>
			<tr><td>交易時間：<td><input type="text" name="transactiontime" readonly value="<%= odb.getTransactionTime() %>">
			<tr><td>訂單狀態：
				<td><select id="orderstatus" name="orderstatus">
    					<option value="訂單已成立" <%= "訂單已成立".equals(odb.getOrderStatus()) ? "selected" : "" %>>訂單已成立</option>
            			<option value="備貨中" <%= "備貨中".equals(odb.getOrderStatus()) ? "selected" : "" %>>備貨中</option>
            			<option value="已出貨" <%= "已出貨".equals(odb.getOrderStatus()) ? "selected" : "" %>>已出貨</option>
            			<option value="已送達" <%= "已送達".equals(odb.getOrderStatus()) ? "selected" : "" %>>已送達</option>
            			<option value="退貨中" <%= "退貨中".equals(odb.getOrderStatus()) ? "selected" : "" %>>退貨中</option>
            			<option value="退貨完成" <%= "退貨完成".equals(odb.getOrderStatus()) ? "selected" : "" %>>退貨完成</option>
            			<option value="訂單取消" <%= "訂單取消".equals(odb.getOrderStatus()) ? "selected" : "" %>>訂單取消</option></select>
			<tr><td>取貨方式：
				<td><label><input type="radio" name="pickupmethod" value="宅配"
						<%= "宅配".equals(odb.getPickupMethod()) ? "checked" : "" %>> 宅配 </label>
					<label><input type="radio" name="pickupmethod" value="超商取貨"
						<%= "超商取貨".equals(odb.getPickupMethod()) ? "checked" : "" %>> 超商取貨 </label>
			<tr><td>付款方式：
				<td><label><input type="radio" name="paymentmethod" value="信用卡"
						<%= "信用卡".equals(odb.getPaymentMethod()) ? "checked" : "" %>> 信用卡</label>
					<label><input type="radio" name="paymentmethod" value="貨到付款"
						<%= "貨到付款".equals(odb.getPaymentMethod()) ? "checked" : "" %>> 貨到付款</label>
			<tr><td>付款狀態：
				<td><select id="paymentstatus" name="paymentstatus">
						<option value="待付款" <%= "待付款".equals(odb.getPaymentStatus()) ? "selected" : "" %>>待付款</option>
    					<option value="已付款" <%= "已付款".equals(odb.getPaymentStatus()) ? "selected" : "" %>>已付款</option>
    					<option value="付款失敗" <%= "付款失敗".equals(odb.getPaymentStatus()) ? "selected" : "" %>>付款失敗</option></select>
			<tr><td>物流編號：<td><input type="text" name="trackingno" value="<%= odb.getTrackingNum()!=null?odb.getTrackingNum():"" %>"
										id="trackingno"><td><span id=trckMsg class="Msg"></span>
			<tr><td>更新時間：<td><input type="text" name="shipmentupdatetime" readonly value="<%= odb.getUpdateTime() %>">
		
			<tr><td colspan="2" style="text-align: center;padding: 20px;">
						<input type="hidden" value="update" name="action"/>
						<input style="padding:3px;" type="submit" value="確定" id="submit"/>
						<input style="padding:3px;" type="button" value="取消" onclick="history.back();" /></td></tr>
						
			<tr><td colspan="2" style="text-align: center;"><span id=subMsg class="Msg"></span></td></tr>
		
		</table>
		</form>
	</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$("#orderstatus").on("change", function(){
		let selectedValue = $(this).val();
		if(selectedValue==="已出貨" || selectedValue==="已送達" || selectedValue==="退貨中" || selectedValue==="退貨完成"){
			$("#trackingno").attr("required", true);
			$("#trackingno").attr("placeholder", "必填");
		}else{
			$("#trackingno").removeAttr("required");
			$("#trackingno").attr("placeholder", "");
		}
	})
	//格式驗證
	let memberidrule = /^[0-9]{1,3}$/;
	let totalpricerule = /^[0-9]+$/;
	let trackingnorule = /^[0-9]{10}$/;
	$("#memberid").on("input", function(){
		if(!memberidrule.test($(this).val())){
			$("#mbMsg").text("會員編號請輸入1至3位數字");
		}else{
			$("#mbMsg").text("");
		}
	})
	$("#totalprice").on("input", function(){
		if(!totalpricerule.test($(this).val())){
			$("#ttpMsg").text("訂單金額請輸入數字");
		}else{
			$("#ttpMsg").text("");
		}
	})
	$("#trackingno").on("input", function(){
		if(!trackingnorule.test($(this).val())){
			$("#trckMsg").text("物流編號為10位數字");
		}else{
			$("#trckMsg").text("");
		}
	})
	$("#form").submit(function(){
		if(memberidrule.test($("#memberid").val()) &&
				totalpricerule.test($("#totalprice").val())){
			$("#subMsg").text("");
			this.submit();
		}else{
			$("#subMsg").text("請確認輸入格式");
			event.preventDefault();
		}
	})
</script>
</body>
</html>