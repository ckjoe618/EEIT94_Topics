package com.topics.discuss.model.dao;

import com.topics.discuss.model.bean.ArticleBean;

import java.util.List;

public interface IArticleDAO {
	public ArticleBean inserArticle(ArticleBean articleBean);
	public List<ArticleBean> getAllArticles();
	public ArticleBean getArticleById(Integer articleId);
	public ArticleBean updateArticle(int articleId, int memberId, String title, String content, int categoryId, int viewCount, boolean isPinned, boolean isFeatured);
	public Boolean deleteArticle(Integer articleId);
}
