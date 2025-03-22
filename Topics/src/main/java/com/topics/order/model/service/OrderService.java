package com.topics.order.model.service;

import java.util.List;
import org.hibernate.Session;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.dao.OrderDao;

public class OrderService implements OrderServiceInterface {

	private OrderDao orderDao;

	public OrderService(Session session) {
		orderDao = new OrderDao(session);
	}

	// 查詢
	@Override
	public List<OrderBean> getOrderByOrderId(Integer orderId) {
		return orderDao.getOrderByOrderId(orderId);
	};

	@Override
	public List<OrderBean> getOrderByMemberId(Integer memberId) {
		return orderDao.getOrderByMemberId(memberId);
	};

	@Override
	public List<OrderBean> getOrderByTxnTime(String startDate, String endDate) {
		return orderDao.getOrderByTxnTime(startDate, endDate);
	};

	@Override
	public List<OrderBean> getOrderByMemberIdTxnTime(Integer memberId, String startDate, String endDate) {
		return orderDao.getOrderByMemberIdTxnTime(memberId, startDate, endDate);
	};

	@Override
	public List<OrderBean> getAllOrders() {
		return orderDao.getAllOrders();
	};

	// 新增
	@Override
	public void insertOrder(OrderBean insertBean) {
		orderDao.insertOrder(insertBean);
	};

	// 刪除
	@Override
	public void deleteOrderByOrderId(Integer orderId) {
		orderDao.deleteOrderByOrderId(orderId);
	};

	// 修改
	@Override
	public OrderBean findOrderByOrderId(Integer orderId) {
		return orderDao.findOrderByOrderId(orderId);
	};

	@Override
	public void updateOrderByOrderId(OrderBean orderBean) {
		orderDao.updateOrderByOrderId(orderBean);
	};

}
