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
        form { display: flex; flex-direction: column; max-width: 620px; gap: 14px;
               margin-bottom: 30px; background: #fff; padding: 18px;
               border: 1px solid #e0e0e0; border-radius: 6px; }
        fieldset { border: 1px solid #e0e0e0; border-radius: 6px; padding: 10px 14px; }
        legend { font-weight: bold; color: #555; padding: 0 6px; }
        label { font-weight: bold; display: block; margin-top: 8px; }
        input, textarea { padding: 6px; font-size: 14px; font-family: inherit; width: 100%;
                          box-sizing: border-box; }
        textarea { min-height: 80px; resize: vertical; }
        button { padding: 8px 14px; cursor: pointer; background: #1a73e8; color: #fff;
                 border: none; border-radius: 4px; font-size: 14px; }
        button:hover { background: #1663c7; }
        button.secondary { background: #6c757d; }
        button.secondary:hover { background: #5a6268; }
        .message-block { border: 1px dashed #ccc; border-radius: 5px;
                         padding: 10px; margin-top: 10px; position: relative; }
        .message-block .remove { position: absolute; top: 6px; right: 6px;
                                 background: #e74c3c; padding: 2px 8px; font-size: 12px; }
        .message-block .remove:hover { background: #c0392b; }
        table { border-collapse: collapse; width: 100%; background: #fff; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .links { margin: 20px 0; display: flex; gap: 15px; }
        .links a { color: #1a73e8; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<h1>Créer plusieurs messages</h1>

<form action="${pageContext.request.contextPath}/api/message/create" method="post">

    <fieldset>
        <legend>Utilisateur (commun)</legend>
        <label for="userName">Nom</label>
        <input type="text" id="userName" name="user.name" required>

        <label for="userEmail">Email</label>
        <input type="email" id="userEmail" name="user.email" required>
    </fieldset>

    <fieldset>
        <legend>Catégorie (commune)</legend>
        <label for="catName">Nom de la catégorie</label>
        <input type="text" id="catName" name="category.name" required>
    </fieldset>

    <fieldset id="messages-container">
        <legend>Messages</legend>
        <button type="button" class="secondary" onclick="addMessage()">+ Ajouter un message</button>
    </fieldset>

    <button type="submit">Publier tout</button>
</form>

<div class="links">
    <a href="${pageContext.request.contextPath}/message/form">Rafraîchir</a>
    <a href="${pageContext.request.contextPath}/api/message">API JSON</a>
</div>

<h1>Messages existants</h1>

<%
    @SuppressWarnings("unchecked")
    List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<% if (messages == null || messages.isEmpty()) { %>
    <p>Aucun message.</p>
<% } else { %>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Utilisateur</th>
                <th>Catégorie</th>
                <th>Titre</th>
                <th>Contenu</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
        <% for (Message m : messages) { %>
            <tr>
                <td><%= m.getId() %></td>
                <td>
                    <% if (m.getUser() != null) { %>
                        <%= m.getUser().getName() %>
                    <% } else { %>-<% } %>
                </td>
                <td><%= m.getCategory() != null ? m.getCategory().getName() : "-" %></td>
                <td><%= m.getTitle() != null ? m.getTitle() : "" %></td>
                <td><%= m.getContent() != null ? m.getContent() : "" %></td>
                <td><%= m.getCreatedAt() != null ? m.getCreatedAt().toString() : "-" %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>

<script>
    let nextIndex = 0;

    function addMessage() {
        const container = document.getElementById('messages-container');
        const block = document.createElement('div');
        block.className = 'message-block';
        block.innerHTML =
            '<button type="button" class="remove" onclick="this.parentNode.remove()">✕</button>' +
            '<label>Titre</label>' +
            '<input type="text" name="messages[' + nextIndex + '].title" required>' +
            '<label>Contenu</label>' +
            '<textarea name="messages[' + nextIndex + '].content" required></textarea>';
        container.appendChild(block);
        nextIndex++;
    }

    // Un premier bloc par défaut
    addMessage();
</script>

</body>
</html>