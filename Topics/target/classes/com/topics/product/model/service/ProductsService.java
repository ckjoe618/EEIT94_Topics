package com.topics.product.model.service;

import java.util.List;

import org.hibernate.Session;

import com.topics.product.model.bean.ProdBean;
import com.topics.product.model.dao.ProductsDAO;

public class ProductsService implements IProductsService {
	
	private ProductsDAO productsDAO;
	
	public ProductsService(Session session) {
		productsDAO = new ProductsDAO(session);
	}
	
	public ProdBean getOne(Integer product_id) {
		return productsDAO.getOne(product_id);
	}
	public List<ProdBean> getAll(){
		return productsDAO.getAll();
	}
	
	public Boolean deleteOne(Integer product_id) {
		return productsDAO.deleteOne(product_id);
	}
	public ProdBean insertOne(String product_name, String product_des, Integer price, Integer stock, String category_name, String photo, Integer total_star, Integer total_reviews) {
		return productsDAO.insertOne(product_name, product_des, price, stock, category_name, photo, total_star, total_reviews);
	}
	public ProdBean UpdateOne(Integer product_id, String product_name, String product_des, Integer price, Integer stock, String category_name, String photo, Integer total_star, Integer total_reviews) {
		return productsDAO.UpdateOne(product_id, product_name, product_des, price, stock, category_name, photo, total_star, total_reviews);
	}
}
