package com.topics.product.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ProdBean {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;

	@Column(name = "product_name")
	@NonNull
	private String product_name;

	@Column(name = "product_des")
	@NonNull
	private String product_des;

	@Column(name = "price")
	@NonNull
	private Integer price;

	@Column(name = "stock")
	@NonNull
	private Integer stock;

	@Column(name = "category_name")
	@NonNull
	private String category_name;

	@Column(name = "photo")
	@NonNull
	private String photo;

	@Column(name = "total_star")
	@NonNull
	private Integer total_star;

	@Column(name = "total_reviews")
	@NonNull
	private Integer total_review;

	@Column(name = "average_rating")
	private Float average_rating;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_des() {
		return product_des;
	}

	public void setProduct_des(String product_des) {
		this.product_des = product_des;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getTotal_star() {
		return total_star;
	}

	public void setTotal_star(Integer total_star) {
		this.total_star = total_star;
	}

	public Integer getTotal_review() {
		return total_review;
	}

	public void setTotal_review(Integer total_review) {
		this.total_review = total_review;
	}

	public Float getAverage_rating() {
		return average_rating;
	}

	public void setAverage_rating(Float average_rating) {
		this.average_rating = average_rating;
	}

	@Override
	public String toString() {
		return "ProdBean [product_id=" + product_id + ", product_name=" + product_name + ", product_des=" + product_des
				+ ", price=" + price + ", stock=" + stock + ", category_name=" + category_name + ", photo=" + photo
				+ ", total_star=" + total_star + ", total_review=" + total_review + ", average_rating=" + average_rating
				+ "]";
	}

}