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
	private Integer orderid;
	
	@Column(name = "MEMBER_ID")
	private Integer memberid;
	
	@Column(name = "PRICE_TOTAL")
	private Integer pricetotal;
	
	@Column(name = "TRANSACTION_TIME")
	private Timestamp transactiontime;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentmethod;
	
	@Column(name = "PAYMENT_STATUS")
	private String paymentstatus;
	
	@Column(name = "ORDER_STATUS")
	private String orderstatus;
	
	@Column(name = "PICKUP_METHOD")
	private String pickupmethod;
	
	@Column(name = "TRACKING_NUM")
	private String trackingnum;
	
	@Column(name = "UPDATE_TIME")
	private Timestamp updatetime;

	public OrderBean(Integer memberid, Integer pricetotal, Timestamp transactiontime, String paymentmethod,
			String paymentstatus, String orderstatus, String pickupmethod, String trackingnum, Timestamp updatetime) {
		super();
		this.memberid = memberid;
		this.pricetotal = pricetotal;
		this.transactiontime = transactiontime;
		this.paymentmethod = paymentmethod;
		this.paymentstatus = paymentstatus;
		this.orderstatus = orderstatus;
		this.pickupmethod = pickupmethod;
		this.trackingnum = trackingnum;
		this.updatetime = updatetime;
	}

	
	
}
