package com.topics.product.model.dao;

import java.util.List;

import com.topics.product.model.bean.ProdBean;

public interface IProductsDAO {
	public ProdBean getOne(Integer product_id);

	public List<ProdBean> getAll();

	public Boolean deleteOne(Integer product_id);

	public ProdBean insertOne(ProdBean prodBean);

	public ProdBean UpdateOne(ProdBean prodBean);

}
