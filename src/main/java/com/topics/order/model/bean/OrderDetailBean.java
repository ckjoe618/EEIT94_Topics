package com.topics.order.model.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetailBean {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailId;
	
	private Integer orderId;
	
	private Integer productId;
	
	private Integer quantity;
	
	private Integer unitPrice;
	
	private Integer subtotal;
	
	private Integer rating;
	
	private String comment;
	

}
