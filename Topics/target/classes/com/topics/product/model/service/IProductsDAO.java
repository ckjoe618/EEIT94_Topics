package com.topics.product.model.service;

import java.util.List;

import com.topics.product.model.bean.ProdBean;

public interface IProductsDAO {
	public ProdBean getOne(Integer product_id);
	public List<ProdBean> getAll();
	public Boolean deleteOne(Integer product_id);
	public ProdBean insertOne(String product_name, String product_des, Integer price, Integer stock, String category_name, String photo, Integer total_star, Integer total_reviews );
	public ProdBean UpdateOne(Integer product_id, String product_name, String product_des, Integer price, Integer stock, String category_name, String photo, Integer total_star, Integer total_reviews);
}
