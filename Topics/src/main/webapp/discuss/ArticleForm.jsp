<%@page import="com.topics.discuss.model.bean.ArticleBean"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    ArticleBean article = (ArticleBean) request.getAttribute("article");
    boolean isEdit = (article != null);
%>

<html>
<head>
    <title><%= isEdit ? "編輯文章" : "新增文章" %></title>
    <style>
        form { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        label { display: block; margin: 10px 0 5px; }
        input, textarea { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { padding: 10px 20px; border: none; color: white; border-radius: 5px; cursor: pointer; }
        .btn-submit { background-color: #4CAF50; }
        .btn-cancel { background-color: #f44336; }
    </style>
</head>
<body>

    <h2><%= isEdit ? "編輯文章" : "新增文章" %></h2>

    <form action="ArticleServlet?action=<%= isEdit ? "update" : "insert" %>" method="post">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= article.getArticleId() %>">
        <% } %>

        <label for="memberId">使用者ID：</label>
        <input type="number" name="memberId" value="<%=isEdit ? article.getMemberId() : ""%>" required>

        <label for="title">標題：</label>
        <input type="text" name="title" value="<%= isEdit ? article.getTitle() : "" %>" required>

        <label for="content">內容：</label>
        <textarea name="content" rows="5" required><%= isEdit ? article.getContent() : "" %></textarea>

        <label for="categoryId">分類ID：</label>
        <input type="number" name="categoryId" value="<%= isEdit ? article.getCategoryId() : "" %>" required>

        <% if (isEdit) { %>
            <label for="viewCount">觀看數：</label>
            <input type="number" name="viewCount" value="<%= article.getViewCount() %>" required>
        <% } %>

        <label>
            <input type="checkbox" name="isPinned" value="true" <%= isEdit && article.isPinned() ? "checked" : "" %>> 置頂
        </label>

        <label>
            <input type="checkbox" name="isFeatured" value="true" <%= isEdit && article.isFeatured() ? "checked" : "" %>> 精選
        </label>

        <button type="submit" class="btn btn-submit"><%= isEdit ? "更新文章" : "新增文章" %></button>
        <a href="ArticleServlet" class="btn btn-cancel">取消</a>
    </form>

</body>
</html>
