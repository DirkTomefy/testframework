<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer un utilisateur</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        h1 { color: #333; }
        form { display: flex; flex-direction: column; max-width: 320px; gap: 10px; margin-bottom: 20px; }
        label { font-weight: bold; }
        input { padding: 6px; font-size: 14px; }
        button { padding: 8px 14px; cursor: pointer; }
        #result {
            display: none;
            white-space: pre-wrap;
            font-family: monospace;
            padding: 10px;
            border-radius: 4px;
            margin-top: 10px;
        }
        .ok  { background: #e6ffed; border: 1px solid #34a853; color: #0a6b2f; }
        .err { background: #ffebe9; border: 1px solid #d93025; color: #a50e0e; }
        .links { margin-top: 20px; display: flex; gap: 15px; }
        .links a { color: #1a73e8; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<h1>Créer un utilisateur</h1>

<form id="userForm">
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Password</label>
    <input type="password" id="password" name="password" required minlength="3">

    <button type="submit">Créer</button>
</form>

<div id="result"></div>

<div class="links">
    <a href="test">Voir la liste des utilisateurs</a>
    <a href="api/user">API JSON : /api/user</a>
</div>

<script>
    const form   = document.getElementById('userForm');
    const result = document.getElementById('result');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const params = new URLSearchParams();
        params.append('username', document.getElementById('username').value);
        params.append('password', document.getElementById('password').value);

        try {
  
            const resp = await fetch('api/user/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params.toString()
            });

            const text = await resp.text();

            if (resp.ok) {
                let pretty = text;
                try { pretty = JSON.stringify(JSON.parse(text), null, 2); } catch (_) {}
                result.className = 'ok';
                result.textContent = ' Utilisateur créé :\n' + pretty;
            } else {
                result.className = 'err';
                result.textContent = ' Erreur ' + resp.status + ' :\n' + text;
            }
            result.style.display = 'block';
            form.reset();
        } catch (err) {
            result.className = 'err';
            result.textContent = ' Erreur réseau : ' + err;
            result.style.display = 'block';
        }
    });
</script>

</body>
</html>