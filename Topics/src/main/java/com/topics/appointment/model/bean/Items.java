package com.topics.appointment.model.bean;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Integer itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "ITEM_PRICE")
	private Double itemPrice;

	public Items() {
	}

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<ItemDetails> itemDetails;

	public Items(String packageName, Double packagePrice) {
		this.itemName = packageName;
		this.itemPrice = packagePrice;
	}

}
