package com.topics.product.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.topics.product.model.service.IProductsService;
import com.topics.product.model.service.ProductsService;
import com.topics.product.model.bean.*;
import com.topics.util.HibernateUtil;

@WebServlet("/ProductCRUD")
public class ProductCRUD extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();

		String actionType = request.getParameter("actionType");
		if (actionType == null) {
			actionType = "getAll";
		}
		System.out.println(actionType);

		int product_id;

		IProductsService iProductsService = new ProductsService(session);

		switch (actionType) {

		case "getSingle":
			product_id = Integer.parseInt(request.getParameter("product_id"));
			request.setAttribute("prod", iProductsService.getOne(product_id));
			request.getRequestDispatcher("/product/select/GetProd.jsp").forward(request, response);
			break;

		case "getAll":
			request.setAttribute("prods", iProductsService.getAll());
			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
			break;

		case "update":
			product_id = Integer.parseInt(request.getParameter("product_id"));
			request.setAttribute("prod", iProductsService.getOne(product_id));
			request.getRequestDispatcher("/product/update/UpdateProd.jsp").forward(request, response);
			break;

		case "update2":
			ProdBean prodBean = new ProdBean();
			prodBean.setProduct_id(Integer.parseInt(request.getParameter("product_id")));
			prodBean.setProduct_name(request.getParameter("product_name"));
			prodBean.setProduct_des(request.getParameter("product_des"));
			prodBean.setPrice(Integer.parseInt(request.getParameter("price")));
			prodBean.setStock(Integer.parseInt(request.getParameter("stock")));
			prodBean.setCategory_name(request.getParameter("category_name"));
			prodBean.setPhoto(request.getParameter("photo"));
			prodBean.setTotal_star(Integer.parseInt(request.getParameter("total_star")));
			prodBean.setTotal_review(Integer.parseInt(request.getParameter("total_reviews")));
			iProductsService.UpdateOne(prodBean);
			request.setAttribute("prods", iProductsService.getAll());
			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
			break;

		case "delete":
			product_id = Integer.parseInt(request.getParameter("product_id"));
			iProductsService.deleteOne(product_id);
			request.setAttribute("prods", iProductsService.getAll());
			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
			break;

		case "insert":
			ProdBean product = new ProdBean();
			product.setProduct_name(request.getParameter("product_name"));
			product.setProduct_des(request.getParameter("product_des"));
			product.setPrice(Integer.parseInt(request.getParameter("price")));
			product.setStock(Integer.parseInt(request.getParameter("stock")));
			product.setCategory_name(request.getParameter("category_name"));
			product.setPhoto(request.getParameter("photo"));
			product.setTotal_star(Integer.parseInt(request.getParameter("total_star")));
			product.setTotal_review(Integer.parseInt(request.getParameter("total_reviews")));
			product.setAverage_rating((float) (Math.round((float) Integer.parseInt(request.getParameter("total_star"))
					/ Integer.parseInt(request.getParameter("total_reviews")) * 10.0) / 10.0));
			iProductsService.insertOne(product);
			request.setAttribute("prods", iProductsService.getAll());
			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
