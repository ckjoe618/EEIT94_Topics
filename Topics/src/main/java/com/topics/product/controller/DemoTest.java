package com.topics.product.controller;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topics.product.model.bean.ProdBean;
import com.topics.product.model.service.IProductsService;
import com.topics.product.model.service.ProductsService;
import com.topics.util.HibernateUtil;





public class DemoTest {

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			IProductsService iProductsService = new ProductsService(session);
			
//			List<ProdBean> lists = iProductsService.getAll();
//			Iterator<ProdBean> i1 = lists.iterator();
//			while(i1.hasNext()) {
//				ProdBean prodBean = i1.next();
//				System.out.println(prodBean);
//			}
			
//			ProdBean resultBean = iProductsService.getOne(4);
//			if(resultBean != null) {
//				System.out.println(resultBean);
//			}else {
//				System.out.println("No Result");
//			}
			
//			ProdBean insertBean = new ProdBean();
//			
//			insertBean.setProduct_des("777");
//			insertBean.setAverage_rating(3.0f);
//			insertBean.setCategory_name("ddd");
//			insertBean.setPhoto("serser");
//			insertBean.setPrice(777);
//			insertBean.setProduct_name("7878");
//			insertBean.setStock(666);
//			insertBean.setTotal_star(45);
//			insertBean.setTotal_review(10);
//			
//			System.out.println(iProductsService.insertOne(insertBean));
			
			
			
//			System.out.println(iProductsService.UpdateOne(5, "8787", "8787", 8787, 8787, "8787", "8787", 500, 100));
			
			System.out.println(iProductsService.deleteOne(5));
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSessionFactory();
		}
	}

}
