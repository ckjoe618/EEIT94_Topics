package com.topics.order.model.bean;

import java.sql.Timestamp;

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
@Table(name = "shoppingcart_item")
public class ShoppingcartItemBean {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartItemId;
	
	private Integer memberId;
	
	private Integer productId;
	
	private Integer quantity;
	
	private Timestamp createdTime;

}
