package com.topics.order.model.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.topics.order.model.bean.OrderBean;


public class OrderDao implements OrderDaoInterface {
	
	private Session session;
	
	public OrderDao(Session session) {
		this.session = session;
	}
	
	//單查詢 //多查詢
	@Override
	public List<OrderBean> getOrderByOrderId(Integer orderId){
		String hql = "from OrderBean where orderid = :oId";
		Query<OrderBean> query = session.createQuery(hql, OrderBean.class);
		query.setParameter("oId", orderId);
		return query.list();
	};
	@Override
	public List<OrderBean> getOrderByMemberId(Integer memberId){
		String hql = "from OrderBean where memberid = :mId";
		Query<OrderBean> query = session.createQuery(hql, OrderBean.class);
		query.setParameter("mId", memberId);
		return query.list();
	};
	@Override
	public List<OrderBean> getOrderByTxnTime(String startDate, String endDate){
		String hql = "from OrderBean where transactiontime between :sDate AND :eDate";
		Query<OrderBean> query = session.createQuery(hql, OrderBean.class);
		Timestamp startTimestamp = Timestamp.valueOf(LocalDate.parse(startDate).atStartOfDay());
		Timestamp endTimestamp = Timestamp.valueOf(LocalDate.parse(endDate).atTime(23, 59, 59));
		query.setParameter("sDate", startTimestamp);
		query.setParameter("eDate", endTimestamp);
		return query.list();
	};
	@Override
	public List<OrderBean> getOrderByMemberIdTxnTime(Integer memberId, String startDate, String endDate){
		String hql = "from OrderBean where memberid = :mId AND (transactiontime between :sDate and :eDate)";
		Query<OrderBean> query = session.createQuery(hql, OrderBean.class);
		Timestamp startTimestamp = Timestamp.valueOf(LocalDate.parse(startDate).atStartOfDay());
		Timestamp endTimestamp = Timestamp.valueOf(LocalDate.parse(endDate).atTime(23, 59, 59));
		query.setParameter("mId", memberId);
		query.setParameter("sDate", startTimestamp);
		query.setParameter("eDate", endTimestamp);
		return query.list();
	};
	@Override
	public List<OrderBean> getAllOrders(){
		Query<OrderBean> query = session.createQuery("from OrderBean", OrderBean.class);
		return query.list();
	};
	
	//新增
	@Override
	public void insertOrder(OrderBean insertBean) {
		if (insertBean != null) {
			session.persist(insertBean);
		}
	};
	
	//刪除
	@Override
	public void deleteOrderByOrderId(Integer orderId) {
		OrderBean orderBean = session.get(OrderBean.class, orderId);
		if (orderBean != null) {
			session.remove(orderBean);
		}
	};
	
	//修改
	@Override
	public OrderBean findOrderByOrderId(Integer orderId) {
		return session.get(OrderBean.class, orderId);
	};
	@Override
	public void updateOrderByOrderId(Integer orderId, Integer memberId, Integer priceTotal, Timestamp transactionTime, String paymentMethod, 
			String paymentStatus, String orderStatus, String pickupMethod, String trackingNum, Timestamp UpdateTime) {
		OrderBean orderBean = session.get(OrderBean.class, orderId);
		if (orderBean != null) {
			orderBean.setMemberid(memberId);
			orderBean.setPricetotal(priceTotal);
			orderBean.setTransactiontime(transactionTime);
			orderBean.setPaymentmethod(paymentMethod);
			orderBean.setPaymentstatus(paymentStatus);
			orderBean.setOrderstatus(orderStatus);
			orderBean.setPickupmethod(pickupMethod);
			orderBean.setTrackingnum(trackingNum);;
			orderBean.setUpdatetime(UpdateTime);;
		}
	};

}
