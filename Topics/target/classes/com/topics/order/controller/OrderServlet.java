package com.topics.order.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.OrderService;
import com.topics.util.HibernateUtil;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		switch (action) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// GetAllOrders
	private void getAllOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);

		List<OrderBean> orders = orderService.getAllOrders();
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/order/GetAllOrders.jsp").forward(request, response);
	}

	// 新增
	private void insertOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		OrderBean orderBean = new OrderBean();

		String memberidrule = "^[0-9]{1,3}$";
		String totalpricerule = "^[0-9]+$";
		String trackingnorule = "^[0-9]{10}$";
		

		if (request.getParameter("memberid").matches(memberidrule)) {
			orderBean.setMemberid(Integer.parseInt(request.getParameter("memberid")));
		} else {
			request.getRequestDispatcher("/ErrorMessage.html").forward(request, response);
			return;
		}
		if (request.getParameter("totalprice").matches(totalpricerule)) {
			orderBean.setPricetotal(Integer.parseInt(request.getParameter("totalprice")));
		} else {
			request.getRequestDispatcher("/ErrorMessage.html").forward(request, response);
			return;
		}
		
		System.out.println("memberId = " + request.getParameter("memberid"));
		
		orderBean.setOrderstatus(request.getParameter("orderstatus"));
		orderBean.setPickupmethod(request.getParameter("pickupmethod"));
		orderBean.setPaymentmethod(request.getParameter("paymentmethod"));
		orderBean.setPaymentstatus(request.getParameter("paymentstatus"));
		orderBean.setTrackingnum(request.getParameter("trackingno"));
		
		if (request.getParameter("trackingno") != "") {
			if (!request.getParameter("trackingno").matches(trackingnorule)) {
				request.getRequestDispatcher("/ErrorMessage.html").forward(request, response);
				return;
			}
		}

		orderService.insertOrder(orderBean);
		getAllOrders(request, response);
	}

	// 刪除
	private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);

		Integer orderid = Integer.parseInt(request.getParameter("orderid"));
		orderService.deleteOrderByOrderId(orderid);
		getAllOrders(request, response);
	}

	// 修改
	private void getOrderById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);

		Integer orderid = Integer.parseInt(request.getParameter("orderid"));
		OrderBean odb = orderService.findOrderByOrderId(orderid);
		request.setAttribute("odb", odb);
		request.getRequestDispatcher("/order/UpdateOrderById.jsp").forward(request, response);
	}

	private void updateOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		OrderService orderService = new OrderService(session);
		OrderBean orderBean = new OrderBean();

		String trackingnorule = "^[0-9]{10}$";
		orderBean.setOrderid(Integer.parseInt(request.getParameter("orderid")));
		orderBean.setMemberid(Integer.parseInt(request.getParameter("memberid")));
		orderBean.setPricetotal(Integer.parseInt(request.getParameter("totalprice")));
		orderBean.setTransactiontime(Timestamp.valueOf(request.getParameter("transactiontime")));
		orderBean.setOrderstatus(request.getParameter("orderstatus"));
		orderBean.setPickupmethod(request.getParameter("pickupmethod"));
		orderBean.setPaymentmethod(request.getParameter("paymentmethod"));
		orderBean.setPaymentstatus(request.getParameter("paymentstatus"));
		orderBean.setTrackingnum(request.getParameter("trackingno"));
		
		if (request.getParameter("trackingno") != "") {
			if (!request.getParameter("trackingno").matches(trackingnorule)) {
				request.getRequestDispatcher("/ErrorMessage.html").forward(request, response);
				return;
			}
		}

		orderService.updateOrderByOrderId(orderBean);
		getAllOrders(request, response);
	}

	// 條件查詢
	private void getOrderByCons(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			enddate = txntimefilter.substring(txntimefilter.length() - 10);
		}

		if (orderfilter == "" && memberfilter == "" && txntimefilter == "") {
			getAllOrders(request, response);
		} else {
			if (memberfilter == null && txntimefilter == null) {
				List<OrderBean> orders = orderService.getOrderByOrderId(Integer.parseInt(orderfilter));
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			} else if (orderfilter == null && txntimefilter == "") {
				List<OrderBean> orders = orderService.getOrderByMemberId(Integer.parseInt(memberfilter));
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			} else if (orderfilter == null && memberfilter == "") {
				List<OrderBean> orders = orderService.getOrderByTxnTime(startdate, enddate);
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			} else if (orderfilter == null) {
				List<OrderBean> orders = orderService.getOrderByMemberIdTxnTime(Integer.parseInt(memberfilter),
						startdate, enddate);
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/order/GetOrdersByCons.jsp").forward(request, response);
			}
		}
	}

}
