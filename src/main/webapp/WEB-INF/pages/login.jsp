<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; max-width: 420px; }
        h1 { color: #333; }
        form { display: flex; flex-direction: column; gap: 10px; }
        label { font-weight: bold; }
        input { padding: 8px; font-size: 14px; }
        button { padding: 10px; cursor: pointer; background: #1a73e8;
                 color: #fff; border: none; border-radius: 4px; }
        button:hover { background: #1663c7; }
    </style>
</head>
<body>

<h1>Login</h1>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="name">Nom</label>
    <input type="text" id="name" name="name" required autofocus>
    <button type="submit">Se connecter</button>
</form>

</body>
</html>