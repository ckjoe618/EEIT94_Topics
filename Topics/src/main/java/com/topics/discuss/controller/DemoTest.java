package com.topics.discuss.controller;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.service.ArticleService;
import com.topics.discuss.model.service.IArticleService;
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
			IArticleService iArticleService = new ArticleService(session);
			
//			List<ProdBean> lists = iProductsService.getAll();
//			Iterator<ProdBean> i1 = lists.iterator();
//			while(i1.hasNext()) {
//				ProdBean prodBean = i1.next();
//				System.out.println(prodBean);
//			}
//			List<ArticleBean> lists = iArticleService.getAllArticles();
//			Iterator<ArticleBean> i1 = lists.iterator();
//			while (i1.hasNext()) {
//				ArticleBean article = i1.next();
//				System.out.println(article);
//			}
			
//			ProdBean resultBean = iProductsService.getOne(4);
//			if(resultBean != null) {
//				System.out.println(resultBean);
//			}else {
//				System.out.println("No Result");
//			}
//			ArticleBean resultBean = iArticleService.getArticleById(3);
//			if(resultBean != null) {
//				System.out.println(resultBean);
//			}else {
//				System.out.println("NO");
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
//			ArticleBean articleBean = new ArticleBean();
//			articleBean.setMemberId(1);
//			articleBean.setTitle("123");
//			articleBean.setContent("123");
//			articleBean.setCategoryId(1);
//			articleBean.setViewCount(0);
//			articleBean.setCreatedAt(LocalDateTime.now());
//			articleBean.setUpdatedAt(LocalDateTime.now());
//			articleBean.setPinned(false);
//			articleBean.setFeatured(false);
//			articleBean.setDeleted(false);
//			System.out.println(iArticleService.inserArticle(articleBean));
			
			
			
//			System.out.println(iProductsService.UpdateOne(5, "8787", "8787", 8787, 8787, "8787", "8787", 500, 100));
//			System.out.println(iArticleService.updateArticle(12, 2, "234", "456", 8, 10, true, true));
			 
//			System.out.println(iArticleService.deleteOne(5));
//			System.out.println(iArticleService.deleteArticle(12));
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			HibernateUtil.closeSessionFactory();
		}
	}

}
