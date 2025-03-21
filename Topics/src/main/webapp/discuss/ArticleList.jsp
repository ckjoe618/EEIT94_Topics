<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.topics.discuss.model.bean.ArticleBean" %>
<jsp:useBean id="articles" scope="request" type="java.util.List<com.topics.discuss.model.bean.ArticleBean>" />

<html>
<head>
    <title>文章列表</title>
    <style>
        table { width: 100%; border-collapse: collapse; table-layout: fixed; }
        th, td { padding: 8px; border: 1px solid #ddd; text-align: center; white-space: nowrap; }
        th { background-color: #f4f4f4; }
        th:nth-child(1), td:nth-child(1) { width: 5%; }
		th:nth-child(2), td:nth-child(2) { width: 8%; }
		th:nth-child(3), td:nth-child(3) { width: 15%; }
		th:nth-child(4), td:nth-child(4) { width: 20%; }
		th:nth-child(5), td:nth-child(5) { width: 6%; }
		th:nth-child(6), td:nth-child(6) { width: 6%; }
		th:nth-child(7), td:nth-child(7) { width: 5%; }
		th:nth-child(8), td:nth-child(8) { width: 5%; }
		th:nth-child(9), td:nth-child(9) { width: 12%; }
		th:nth-child(10), td:nth-child(10) { width: 12%; }
		th:nth-child(11), td:nth-child(11) { width: 10%; }
		td:nth-child(4) { white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
		td:nth-child(9), td:nth-child(10) { font-size: 12px; white-space: nowrap; }
        .btn { padding: 5px 10px; text-decoration: none; color: white; border-radius: 5px; }
        .btn-edit { background-color: #4CAF50; }
        .btn-delete { background-color: #f44336; }
        .btn-new, .btn-search { background-color: #008CBA; padding: 10px; display: inline-block; margin-bottom: 10px; }
    </style>
</head>
<body>

    <h2>文章列表</h2>
    
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
	<% if (errorMessage != null) { %>
    <p style="color: red;"><%= errorMessage %></p>
	<% } %>
    
    <a href="ArticleServlet?action=new" class="btn btn-new">新增文章</a>
    
    <form action="ArticleServlet" method="get" style="display: inline;">
    	<input type="hidden" name="action" value="edit">
    	<input type="number" name="id" placeholder="輸入文章ID" required>
    	<button type="submit" class="btn btn-search">查詢</button>
	</form>

    <table>
        <tr>
            <th>ID</th>
            <th>使用者 ID</th>
            <th>標題</th>
            <th>內容</th>
            <th>分類</th>
            <th>觀看數</th>
            <th>置頂</th>
            <th>精選</th>
            <th>建立時間</th>
            <th>更新時間</th>
            <th>操作</th>
        </tr>
        <%
            for (ArticleBean article : articles) {
        %>
            <tr>
                <td><%= article.getArticleId() %></td>
                <td><%=article.getMemberId()%></td>
                <td><%= article.getTitle() %></td>
                <td><%= article.getContent().length() > 30 ? article.getContent().substring(0, 30) + "..." : article.getContent() %></td>
                <td><%= article.getCategoryId() %></td>
                <td><%= article.getViewCount() %></td>
                <td><%= article.isPinned() ? "✔" : "✘" %></td>
                <td><%= article.isFeatured() ? "✔" : "✘" %></td>
                <td><%= article.getCreatedAt().toString().substring(0, 19) %></td>
                <td><%= article.getUpdatedAt().toString().substring(0, 19) %></td>
                <td>
                    <a href="ArticleServlet?action=edit&id=<%= article.getArticleId() %>" class="btn btn-edit">編輯</a>
                    <a href="ArticleServlet?action=delete&id=<%= article.getArticleId() %>" class="btn btn-delete" onclick="return confirm('確定要刪除嗎？')">刪除</a>
                </td>
            </tr>
        <%
            }
        %>
    </table>

</body>
</html>
