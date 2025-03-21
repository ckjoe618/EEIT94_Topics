package com.topics.order.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.OrderService;
import com.topics.util.HibernateUtil;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OrderServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		
		switch(action) {
			case "insert":
				insertOrder(request, response);
				break;
			case "delete":
				deleteOrder(request, response);
				break;
			case "getOrder":
				getOrderById(request, response);
				break;
			case "update":
				updateOrder(request, response);
				break;
			case "getByCons":
				getOrderByCons(request, response);
				break;
			default:
				getAllOrders(request, response);
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//GetAllOrders
	private void getAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		List<OrderBean> orders = orderService.getAllOrders();
 		request.setAttribute("orders", orders);
 		request.getRequestDispatcher("/order/GetAllOrders.jsp").forward(request, response);
	}
	
	//新增
	private void insertOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		String memberidrule = "^[0-9]{1,3}$";
		String totalpricerule = "^[0-9]+$";
		String trackingnorule = "^[0-9]{10}$";
		
		Integer memberid = null;
		if(request.getParameter("memberid").matches(memberidrule)) {
			memberid = Integer.parseInt(request.getParameter("memberid"));
		}else {
			request.getRequestDispatcher("../ErrorMessage.html").forward(request, response);
			return;
		}
		Integer totalprice = null;
		if(request.getParameter("totalprice").matches(totalpricerule)) {
			totalprice = Integer.parseInt(request.getParameter("totalprice"));
		}else {
			request.getRequestDispatcher("../ErrorMessage.html").forward(request, response);
			return;
		}
		String orderstatus = request.getParameter("orderstatus");
		String pickupmethod = request.getParameter("pickupmethod");
		String paymentmethod = request.getParameter("paymentmethod");
		String paymentstatus = request.getParameter("paymentstatus");
		String trackingno = request.getParameter("trackingno");
		if(trackingno != "") {
			if(!trackingno.matches(trackingnorule)) {
				request.getRequestDispatcher("../ErrorMessage.html").forward(request, response);
				return;
			}
		}
		
		OrderBean orderBean = new OrderBean(memberid, totalprice, Timestamp.valueOf(LocalDateTime.now().withNano(0)), paymentmethod, 
				paymentstatus, orderstatus, pickupmethod, trackingno, Timestamp.valueOf(LocalDateTime.now().withNano(0)));
		orderService.insertOrder(orderBean);
		getAllOrders(request, response);
	}
	
	//刪除
	private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		Integer orderid = Integer.parseInt(request.getParameter("orderid"));
		orderService.deleteOrderByOrderId(orderid);
		getAllOrders(request, response);
	}
	
	//修改
	private void getOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		Integer orderid = Integer.parseInt(request.getParameter("orderid"));
		OrderBean odb = orderService.findOrderByOrderId(orderid);
		request.setAttribute("odb", odb);
		request.getRequestDispatcher("/order/UpdateOrderById.jsp").forward(request, response);	
	}
	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		String trackingnorule = "^[0-9]{10}$";
		Integer orderid = Integer.parseInt(request.getParameter("orderid"));
		Integer memberid = Integer.parseInt(request.getParameter("memberid"));
		Integer totalprice = Integer.parseInt(request.getParameter("totalprice"));
		Timestamp transactiontime = Timestamp.valueOf(request.getParameter("transactiontime"));
		String orderstatus = request.getParameter("orderstatus");
		String pickupmethod = request.getParameter("pickupmethod");
		String paymentmethod = request.getParameter("paymentmethod");
		String paymentstatus = request.getParameter("paymentstatus");
		String trackingno = request.getParameter("trackingno");
		if(trackingno != "") {
			if(!trackingno.matches(trackingnorule)) {
				request.getRequestDispatcher("../ErrorMessage.html").forward(request, response);
				return;
			}
		}
		
		orderService.updateOrderByOrderId(orderid, memberid, totalprice, transactiontime, paymentmethod, 
				paymentstatus, orderstatus, pickupmethod, trackingno, Timestamp.valueOf(LocalDateTime.now().withNano(0)));
		getAllOrders(request, response);
	}
	
	//條件查詢
	private void getOrderByCons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		
		String orderfilter = request.getParameter("orderfilter");
		String memberfilter = request.getParameter("memberfilter");
		String txntimefilter = request.getParameter("txntimefilter");
		
		String startdate = "";
		String enddate = "";
		if (txntimefilter != null && !txntimefilter.isEmpty()) {
			startdate = txntimefilter.substring(0, 10);
			enddate = txntimefilter.substring(txntimefilter.length() -10);
		}
		
		if (orderfilter == "" && memberfilter == "" && txntimefilter == "") {
			getAllOrders(request, response);
		}else {
			if(memberfilter == null && txntimefilter == null) {
				List<OrderBean> orders = orderService.getOrderByOrderId(Integer.parseInt(orderfilter));
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			}else if (orderfilter == null && txntimefilter == "") {
				List<OrderBean> orders = orderService.getOrderByMemberId(Integer.parseInt(memberfilter));
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			}else if (orderfilter == null && memberfilter == "") {
				List<OrderBean> orders = orderService.getOrderByTxnTime(startdate, enddate);
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			}else if (orderfilter == null) {
				List<OrderBean> orders = orderService.getOrderByMemberIdTxnTime(Integer.parseInt(memberfilter), startdate, enddate);
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			}
		}
	}

}
