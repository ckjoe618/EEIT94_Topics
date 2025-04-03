package com.topics.order.model.bean;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity @Table(name = "orders")
public class OrderBean {
	
	@Id @Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	@Column(name = "MEMBER_ID")
	private Integer memberId;
	
	@Column(name = "PRICE_TOTAL")
	private Integer priceTotal;
	
	@Column(name = "TRANSACTION_TIME")
	private Timestamp transactionTime;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	@Column(name = "PICKUP_METHOD")
	private String pickupMethod;
	
	@Column(name = "TRACKING_NUM")
	private String trackingNum;
	
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

	public OrderBean(Integer memberid, Integer pricetotal, Timestamp transactiontime, String paymentmethod,
			String paymentstatus, String orderstatus, String pickupmethod, String trackingnum, Timestamp updatetime) {
		super();
		this.memberId = memberid;
		this.priceTotal = pricetotal;
		this.transactionTime = transactiontime;
		this.paymentMethod = paymentmethod;
		this.paymentStatus = paymentstatus;
		this.orderStatus = orderstatus;
		this.pickupMethod = pickupmethod;
		this.trackingNum = trackingnum;
		this.updateTime = updatetime;
	}

	
	
}
