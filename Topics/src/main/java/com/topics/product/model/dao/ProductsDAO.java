package com.topics.product.model.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.topics.product.model.bean.ProdBean;

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
	public List<ProdBean> getAll() {
		Query<ProdBean> query = session.createQuery("from ProdBean", ProdBean.class);
		return query.list();
	}

	@Override
	public Boolean deleteOne(Integer product_id) {
		ProdBean deleteBean = session.get(ProdBean.class, product_id);
		if (deleteBean != null) {
			session.remove(deleteBean);
			return true;
		}
		return false;
	}

	@Override
	public ProdBean insertOne(ProdBean prodBean) {
		if (prodBean != null) {
			session.persist(prodBean);
			return prodBean;
		}
		return null;
	}

	@Override
	public ProdBean UpdateOne(ProdBean prodBean) {
		ProdBean resultBean = session.get(ProdBean.class, prodBean.getProduct_id());
		if (resultBean != null) {
			resultBean.setProduct_name(prodBean.getProduct_name());
			resultBean.setProduct_des(prodBean.getProduct_des());
			resultBean.setCategory_name(prodBean.getCategory_name());
			resultBean.setPhoto(prodBean.getPhoto());
			resultBean.setPrice(prodBean.getPrice());
			resultBean.setStock(prodBean.getStock());
			resultBean.setTotal_star(prodBean.getTotal_star());
			resultBean.setTotal_review(prodBean.getTotal_review());
			resultBean.setAverage_rating(
					(float) (Math.round((float) prodBean.getTotal_star() / prodBean.getTotal_review() * 10.0) / 10.0));
		}
		return resultBean;
	}

}
