package com.topics.discuss.controller;

import com.topics.discuss.model.bean.ArticleBean;
import com.topics.discuss.model.dao.ArticleDAO;
import com.topics.discuss.model.service.ArticleService;
import com.topics.discuss.model.service.IArticleService;
import com.topics.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
//    private ArticleDAO articleDAO;

//    SessionFactory factory = HibernateUtil.getSessionFactory();
//    Session session = factory.getCurrentSession();
//    IArticleService iArticleService = new ArticleService(session);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertArticle(request, response);
                break;
            case "delete":
                deleteArticle(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateArticle(request, response);
                break;
            default:
                listArticles(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // 文章列表
    private void listArticles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        IArticleService iArticleService = new ArticleService(session);
        List<ArticleBean> articles = iArticleService.getAllArticles();
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/discuss/ArticleList.jsp").forward(request, response);
    }

    // 顯示新增文章表單
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/discuss/ArticleForm.jsp").forward(request, response);
    }

    // 插入新文章
    private void insertArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        IArticleService iArticleService = new ArticleService(session);
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        boolean isPinned = request.getParameter("isPinned") != null;
        boolean isFeatured = request.getParameter("isFeatured") != null;

        ArticleBean article = new ArticleBean(0, memberId, title, content, categoryId, 0, isPinned, isFeatured, LocalDateTime.now(), LocalDateTime.now(), false);
        iArticleService.inserArticle(article);
        response.sendRedirect("ArticleServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        IArticleService iArticleService = new ArticleService(session);
        int articleId = Integer.parseInt(request.getParameter("id"));
        ArticleBean existingArticle = iArticleService.getArticleById(articleId);

        if (existingArticle == null) {
            request.setAttribute("errorMessage", "查無此文章，請輸入正確的文章 ID");

            List<ArticleBean> articles = iArticleService.getAllArticles();
            request.setAttribute("articles", articles);

            request.getRequestDispatcher("/discuss/ArticleList.jsp").forward(request, response);
            return;
        }

        request.setAttribute("article", existingArticle);
        request.getRequestDispatcher("/discuss/ArticleForm.jsp").forward(request, response);
    }



    // 更新文章
    private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        IArticleService iArticleService = new ArticleService(session);
        int articleId = Integer.parseInt(request.getParameter("id"));
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int viewCount = Integer.parseInt(request.getParameter("viewCount"));
        boolean isPinned = request.getParameter("isPinned") != null;
        boolean isFeatured = request.getParameter("isFeatured") != null;

//        ArticleBean article = new ArticleBean(articleId, memberId, title, content, categoryId, viewCount, isPinned, isFeatured, LocalDateTime.now(), LocalDateTime.now(), false);
        iArticleService.updateArticle(articleId, memberId, title, content, categoryId, viewCount, isPinned, isFeatured);
        response.sendRedirect("ArticleServlet");
    }

    // 刪除文章
    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();
        IArticleService iArticleService = new ArticleService(session);
        int articleId = Integer.parseInt(request.getParameter("id"));
        iArticleService.deleteArticle(articleId);
        response.sendRedirect("ArticleServlet");
    }
}
