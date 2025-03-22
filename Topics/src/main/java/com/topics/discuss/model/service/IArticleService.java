package com.topics.discuss.model.service;

import com.topics.discuss.model.bean.ArticleBean;
import java.util.List;

public interface IArticleService {
	public ArticleBean inserArticle(ArticleBean articleBean);

	public List<ArticleBean> getAllArticles();

	public ArticleBean getArticleById(Integer articleId);

	public ArticleBean updateArticle(ArticleBean articleBean);

	public void deleteArticle(Integer articleId);
	
}
