package com.topics.member.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topics.member.model.bean.MemberBean;
//import com.topics.member.model.dao.MemberDao;
import com.topics.member.model.service.MemberService;
import com.topics.util.HibernateUtil;
import com.topics.util.JsonUtil;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("action: " + action); // 驗證前端

		switch (action) {
		case "insertMember":
			insertMember(request, response);
			break;
		case "deleteMemberById":
			deleteMemberById(request, response);
			break;
		case "updateMemberById":
			updateMemberById(request, response);
			break;
		case "getMemberBy":
			getMemberBy(request, response);
			break;
		case "getMember":
			getMember(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/member/Member.jsp");
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void insertMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		MemberBean member = new MemberBean();
		member.setName(request.getParameter("name"));
		member.setGender(request.getParameter("gender"));
		member.setIdno(request.getParameter("idno"));
		member.setEmail(request.getParameter("email"));
		member.setPhone(request.getParameter("phone"));
		member.setBirthday(LocalDate.parse(request.getParameter("birthday")));
		member.setAccount(request.getParameter("account"));
		member.setPassword(request.getParameter("password"));

		MemberService memberService = new MemberService(session);
		memberService.insertMember(member);

		response.sendRedirect(request.getContextPath() + "/member/Member.jsp");
	}
	
	private void deleteMemberById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		
		int id = Integer.parseInt(request.getParameter("memberId"));
		MemberService memberService = new MemberService(session);
		memberService.deleteMemberById(id);
	}
	
	private void updateMemberById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		
		MemberBean member = new MemberBean();
		member.setMemberId(Integer.parseInt(request.getParameter("memberId")));
		member.setName(request.getParameter("name"));
		member.setGender(request.getParameter("gender"));
		member.setIdno(request.getParameter("idno"));
		member.setEmail(request.getParameter("email"));
		member.setPhone(request.getParameter("phone"));
		member.setBirthday(LocalDate.parse(request.getParameter("birthday")));
		member.setAccount(request.getParameter("account"));
		member.setPassword(request.getParameter("password"));
		member.setActive(Integer.parseInt(request.getParameter("isActive")) == 1);
		
		MemberService memberService = new MemberService(session);
		memberService.updateMemberById(member);
		
		response.sendRedirect(request.getContextPath() + "/member/Member.jsp");
	}
	
	private void getMemberBy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		
		MemberBean member = new MemberBean();
		MemberService memberService = new MemberService(session);
		
		int id = 0;
		String account = "";
		String idno = "";
		
		if (request.getParameter("memberId") != null && !request.getParameter("memberId").isEmpty()) {
			id = Integer.parseInt(request.getParameter("memberId"));
		} else if (request.getParameter("account") != null && !request.getParameter("account").isEmpty()) {
			account = request.getParameter("account");
		} else if (request.getParameter("idno") != null && !request.getParameter("idno").isEmpty()) {
			idno = request.getParameter("idno");
		}
		
		if(id>0) {
			member = memberService.selectMemberById(id);
		} else if (account != null && !account.isEmpty()) {
			member = memberService.selectMemberByAccount(account);
	    } else if (idno != null && !idno.isEmpty()) {
	    	member = memberService.selectMemberByIdno(idno);
	    }
		
		PrintWriter out = response.getWriter();
		out.print(JsonUtil.toJson(member));
		out.flush();
	}
	
	private void getMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		List<MemberBean> list = new ArrayList<>();

		MemberService memberService = new MemberService(session);
		list = memberService.selectMember();

		PrintWriter out = response.getWriter();
		out.print(JsonUtil.toJson(list));
		out.flush();
	}
	
}
