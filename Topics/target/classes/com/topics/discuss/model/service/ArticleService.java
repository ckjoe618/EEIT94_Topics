package com.topics.discuss.model.service;

import java.util.List;

import org.hibernate.Session;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.dao.ArticleDAO;

public class ArticleService implements IArticleService{
	
	private ArticleDAO articleDAO;
	
	public ArticleService(Session session) {
		articleDAO = new ArticleDAO(session);
	}
	
	public ArticleBean inserArticle(ArticleBean articleBean) {
		return articleDAO.inserArticle(articleBean);
	}
	
	public List<ArticleBean> getAllArticles(){
		return articleDAO.getAllArticles();
	}
	
	public ArticleBean getArticleById(Integer articleId) {
		return articleDAO.getArticleById(articleId);
	}
	
	public ArticleBean updateArticle(ArticleBean articleBean) {
		return articleDAO.updateArticle(articleBean);
	}
	
	public void deleteArticle(Integer articleId) {
		articleDAO.deleteArticle(articleId);
	}

}
