<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.test.model.User" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des utilisateurs</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        h1 { color: #333; }
        table { border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px 14px; text-align: left; }
        th { background: #f2f2f2; }
        tr:nth-child(even) { background: #fafafa; }
        .links { margin-top: 20px; display: flex; gap: 15px; }
        .links a { color: #1a73e8; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
        .empty { color: #888; font-style: italic; }
    </style>
</head>
<body>

<h1>Liste des utilisateurs</h1>

<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<% if (users != null && !users.isEmpty()) { %>
    <table>
        <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Password</th>
        </tr>
        <% for (User u : users) { %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getUsername() %></td>
            <td><%= u.getPassword() %></td>
        </tr>
        <% } %>
    </table>
    <p>Total : <%= users.size() %> utilisateur(s)</p>
<% } else { %>
    <p class="empty">Aucun utilisateur trouvé.</p>
<% } %>

<div class="links">
    <a href="form">➕ Créer un utilisateur</a>
    <a href="api/user">API JSON : /api/user</a>
</div>

</body>
</html>