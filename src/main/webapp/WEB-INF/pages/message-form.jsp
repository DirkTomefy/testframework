<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.test.model.Message" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Messages</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background: #fafafa; }
        h1 { color: #333; }
        form { display: flex; flex-direction: column; max-width: 480px; gap: 10px;
               margin-bottom: 30px; background: #fff; padding: 16px;
               border: 1px solid #e0e0e0; border-radius: 6px; }
        label { font-weight: bold; }
        input, textarea, select { padding: 6px; font-size: 14px; font-family: inherit; }
        textarea { min-height: 80px; resize: vertical; }
        button { padding: 8px 14px; cursor: pointer; background: #1a73e8; color: #fff;
                 border: none; border-radius: 4px; }
        button:hover { background: #1663c7; }
        table { border-collapse: collapse; width: 100%; background: #fff; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .links { margin: 20px 0; display: flex; gap: 15px; }
        .links a { color: #1a73e8; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<h1>Créer un message</h1>

<form action="${pageContext.request.contextPath}/api/message/create" method="post">
    <label for="userId">Utilisateur (ID)</label>
    <input type="number" id="userId" name="user.id" required min="1">

    <label for="content">Contenu</label>
    <textarea id="content" name="content" required></textarea>

    <button type="submit">Publier</button>
</form>

<div class="links">
    <a href="${pageContext.request.contextPath}/message/form">Rafraîchir</a>
    <a href="${pageContext.request.contextPath}/api/message">API JSON</a>
</div>

<h1>Messages existants</h1>

<%
    List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<% if (messages == null || messages.isEmpty()) { %>
    <p>Aucun message.</p>
<% } else { %>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Contenu</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
        <% for (Message m : messages) { %>
            <tr>
                <td><%= m.getId() %></td>
                <td><%= m.getUser() != null ? m.getUser().getId() : "-" %></td>
                <td><%= m.getContent() %></td>
                <td><%= m.getCreatedAt() != null ? m.getCreatedAt().toString() : "-" %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>

</body>
</html>