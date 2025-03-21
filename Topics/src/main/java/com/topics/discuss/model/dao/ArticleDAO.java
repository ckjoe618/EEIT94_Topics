package com.topics.discuss.model.dao;

import com.topics.discuss.model.bean.ArticleBean;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class ArticleDAO implements IArticleDAO{


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
		Query<ArticleBean> query = session.createQuery("from ArticleBean", ArticleBean.class);
		return query.list();
	}

	@Override
	public ArticleBean getArticleById(Integer articleId) {
		return session.get(ArticleBean.class, articleId);
	}

	@Override
	public ArticleBean updateArticle(int articleId, int memberId, String title, String content, int categoryId, int viewCount, boolean isPinned, boolean isFeatured) {
        ArticleBean articleToUpdate = session.get(ArticleBean.class, articleId);

        if (articleToUpdate != null) {
            articleToUpdate.setMemberId(memberId);
            articleToUpdate.setTitle(title);
            articleToUpdate.setContent(content);
            articleToUpdate.setCategoryId(categoryId);
            articleToUpdate.setViewCount(viewCount);
            articleToUpdate.setPinned(isPinned);
            articleToUpdate.setFeatured(isFeatured);
            articleToUpdate.setUpdatedAt(LocalDateTime.now());
        }

        return articleToUpdate;
    }

	@Override
	public Boolean deleteArticle(Integer articleId) {
		ArticleBean deleteBean = session.get(ArticleBean.class, articleId);
		
		if (deleteBean != null) {
			session.remove(deleteBean);
			return true;
		}
		return false;
	}
}
