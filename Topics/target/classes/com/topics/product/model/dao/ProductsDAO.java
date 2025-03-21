package com.topics.product.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.topics.product.model.bean.ProdBean;
//import com.topic.utils.ConnectUtils;






public class ProductsDAO implements IProductsDAO {
	
	private Session session;
	
	public ProductsDAO(Session session) {
		this.session = session;
	}
	
	@Override
	public ProdBean getOne(Integer product_id) {
		return session.get(ProdBean.class, product_id);
	}
	
	@Override
	public List<ProdBean> getAll(){
		Query<ProdBean> query = session.createQuery("from ProdBean", ProdBean.class);
		return query.list();
	}
	
	@Override
	public Boolean deleteOne(Integer product_id) {
		ProdBean deleteBean = session.get(ProdBean.class, product_id);
		
		if(deleteBean != null) {
			session.remove(deleteBean);
			return true;
		}
		
		return false;
	}
	
	@Override
	public ProdBean insertOne(ProdBean prodBean) {
		
		if(prodBean != null) {
			session.persist(prodBean);
			return prodBean;			
		}
		
		return null;
		
		
		
	}
	
	@Override
	public ProdBean UpdateOne(Integer product_id, String product_name, String product_des, Integer price, Integer stock, String category_name, String photo, Integer total_star, Integer total_reviews) {
		ProdBean resultBean = session.get(ProdBean.class, product_id);
		
		if(resultBean != null) {
			resultBean.setProduct_name(product_name);
			resultBean.setProduct_des(product_des);
			resultBean.setCategory_name(category_name);
			resultBean.setPhoto(photo);
			resultBean.setPrice(price);
			resultBean.setStock(stock);
			resultBean.setTotal_star(total_star);
			resultBean.setTotal_review(total_reviews);
			resultBean.setAverage_rating((float)(Math.round((float)total_star/total_reviews * 10.0) / 10.0));
		}
		
		return resultBean;
	}
	
	
}
