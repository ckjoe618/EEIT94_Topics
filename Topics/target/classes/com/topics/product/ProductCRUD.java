package com.topic.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.io.PrintWriter;

import com.topic.dao.ProductsDAO;

@WebServlet("/ProductCRUD")
public class ProductCRUD extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductCRUD() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionType = request.getParameter("actionType");
		if(actionType == null) {
			actionType = "getAll";
		}
		System.out.println(actionType);
//		String btnUpdate = request.getParameter("btnUpdate");
//		String btnDelete = request.getParameter("btnDelete");
		int product_id;
		String product_name;
		String product_des;
		int price;
		int stock;
		String category_name;
		String photo;
		int total_star;
		int total_reviews;
//		float average_rating;
		

//		if("刪除".equals(btnDelete)) {
//			product_id = Integer.parseInt(request.getParameter("deleteOne"));
//			
//			ProductsDAO.deleteOne(product_id);
//			request.setAttribute("prods", ProductsDAO.getAll());
//			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
//		}
		
//		if("修改".equals(btnUpdate)){
//			
//			product_id = Integer.parseInt(request.getParameter("updateOne"));
//			request.setAttribute("prod", ProductsDAO.getOne(product_id));
//			request.getRequestDispatcher("/product/update/UpdateProd.jsp").forward(request, response);
//			product_id = Integer.parseInt(request.getParameter("updateOne"));
//			product_name = request.getParameter("product_name");
//			product_des = request.getParameter("product_des");
//			price = Integer.parseInt(request.getParameter("price"));
//			stock = Integer.parseInt(request.getParameter("stock"));
//			category_name = request.getParameter("category_name");
//			photo = request.getParameter("photo");
//			total_star = Integer.parseInt(request.getParameter("total_star"));
//			total_reviews = Integer.parseInt(request.getParameter("total_reviews"));
//		
//			ProductsDAO.UpdateOne(product_id, product_name, product_des, price, stock, category_name, photo, total_star, total_reviews);
//			request.setAttribute("prods", ProductsDAO.getAll());
//			request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
//		}
		
		
		switch(actionType) {
			
			
			case "getSingle":
				product_id = Integer.parseInt(request.getParameter("product_id"));
				
				request.setAttribute("prod", ProductsDAO.getOne(product_id));
				request.getRequestDispatcher("/product/select/GetProd.jsp").forward(request, response);
				break;
				
			case "getAll":
				request.setAttribute("prods", ProductsDAO.getAll());
				request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
				break;
							
			case "update":
				product_id = Integer.parseInt(request.getParameter("product_id"));
				request.setAttribute("prod", ProductsDAO.getOne(product_id));
				request.getRequestDispatcher("/product/update/UpdateProd.jsp").forward(request, response);
				break;
				
			case "delete":
				product_id = Integer.parseInt(request.getParameter("product_id"));
				
				ProductsDAO.deleteOne(product_id);
				request.setAttribute("prods", ProductsDAO.getAll());
				request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
				break;
				
			case "insert":				
				product_name = request.getParameter("product_name");
				product_des = request.getParameter("product_des");
				price = Integer.parseInt(request.getParameter("price"));
				stock = Integer.parseInt(request.getParameter("stock"));
				category_name = request.getParameter("category_name");
				photo = request.getParameter("photo");
				total_star = Integer.parseInt(request.getParameter("total_star"));
				total_reviews = Integer.parseInt(request.getParameter("total_reviews"));
				ProductsDAO.insertOne(product_name, product_des, price, stock, category_name, photo, total_star, total_reviews);
				request.setAttribute("prods", ProductsDAO.getAll());
				request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
				break;
				
			case "update2":
				
				product_id = Integer.parseInt(request.getParameter("product_id"));
				product_name = request.getParameter("product_name");
				product_des = request.getParameter("product_des");
				price = Integer.parseInt(request.getParameter("price"));
				stock = Integer.parseInt(request.getParameter("stock"));
				category_name = request.getParameter("category_name");
				photo = request.getParameter("photo");
				total_star = Integer.parseInt(request.getParameter("total_star"));
				total_reviews = Integer.parseInt(request.getParameter("total_reviews"));
			
				ProductsDAO.UpdateOne(product_id, product_name, product_des, price, stock, category_name, photo, total_star, total_reviews);
				request.setAttribute("prods", ProductsDAO.getAll());
				request.getRequestDispatcher("/product/select/GetAllProds.jsp").forward(request, response);
				break;
				
			
			default:
				break;
				
		
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
