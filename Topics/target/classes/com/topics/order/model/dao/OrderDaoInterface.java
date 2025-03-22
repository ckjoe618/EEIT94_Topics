package com.topics.order.model.dao;

import java.util.List;
import com.topics.order.model.bean.OrderBean;

public interface OrderDaoInterface {

	// 查詢
	public List<OrderBean> getOrderByOrderId(Integer orderid);

	public List<OrderBean> getOrderByMemberId(Integer memberId);

	public List<OrderBean> getOrderByTxnTime(String startDate, String endDate);

	public List<OrderBean> getOrderByMemberIdTxnTime(Integer memberId, String startDate, String endDate);

	public List<OrderBean> getAllOrders();

	// 新增
	public void insertOrder(OrderBean insertBean);

	// 刪除
	public void deleteOrderByOrderId(Integer orderId);

	// 修改
	public OrderBean findOrderByOrderId(Integer orderId);

	public void updateOrderByOrderId(OrderBean orderBean);

}
