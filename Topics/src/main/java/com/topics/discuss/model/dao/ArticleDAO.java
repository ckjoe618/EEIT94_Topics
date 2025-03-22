package com.topics.discuss.model.dao;

import com.topics.discuss.model.bean.ArticleBean;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ArticleDAO implements IArticleDAO {

	private Session session;

	public ArticleDAO(Session session) {
		this.session = session;
	}

	@Override
	public ArticleBean inserArticle(ArticleBean articleBean) {
		if (articleBean != null) {
			session.persist(articleBean);
			return articleBean;
		}
		return null;
	}

	@Override
	public List<ArticleBean> getAllArticles() {
		Query<ArticleBean> query = session.createQuery("from ArticleBean WHERE isDeleted=false", ArticleBean.class);
		return query.list();
	}

	@Override
	public ArticleBean getArticleById(Integer articleId) {
		return session.get(ArticleBean.class, articleId);
	}

	@Override
	public ArticleBean updateArticle(ArticleBean articleBean) {
		ArticleBean articleToUpdate = session.get(ArticleBean.class, articleBean.getArticleId());

		if (articleToUpdate != null) {
			articleToUpdate.setMemberId(articleBean.getMemberId());
			articleToUpdate.setTitle(articleBean.getTitle());
			articleToUpdate.setContent(articleBean.getContent());
			articleToUpdate.setCategoryId(articleBean.getCategoryId());
			articleToUpdate.setViewCount(articleBean.getViewCount());
			articleToUpdate.setPinned(articleBean.isPinned());
			articleToUpdate.setFeatured(articleBean.isFeatured());
			articleToUpdate.setUpdatedAt(LocalDateTime.now());
		}
		return articleToUpdate;
	}

	@Override
	public void deleteArticle(Integer articleId) {
		ArticleBean deleteBean = session.get(ArticleBean.class, articleId);

		if (deleteBean != null) {
			deleteBean.setDeleted(true);
			session.merge(deleteBean);
		}
	}

}
