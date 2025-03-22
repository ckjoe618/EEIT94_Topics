package com.topics.product.model.service;

import java.util.List;
import org.hibernate.Session;
import com.topics.product.model.bean.ProdBean;
import com.topics.product.model.dao.ProductsDAO;

public class ProductsService implements IProductsService {

	private ProductsDAO productsDAO;

	public ProductsService(Session session) {
		productsDAO = new ProductsDAO(session);
	}

	@Override
	public ProdBean getOne(Integer product_id) {
		return productsDAO.getOne(product_id);
	}

	@Override
	public List<ProdBean> getAll() {
		return productsDAO.getAll();
	}

	@Override
	public Boolean deleteOne(Integer product_id) {
		return productsDAO.deleteOne(product_id);
	}

	@Override
	public ProdBean insertOne(ProdBean prodBean) {
		return productsDAO.insertOne(prodBean);
	}

	@Override
	public ProdBean UpdateOne(ProdBean prodBean) {
		return productsDAO.UpdateOne(prodBean);
	}

}
