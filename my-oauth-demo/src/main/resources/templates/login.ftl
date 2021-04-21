<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body style="padding-top: 80px; display: flex; flex-direction: column;justify-content: center;align-items: center;">
<div>
    <form method="POST" action="/form/login">
        <table>
            <tr><td>username：</td><td><input type="text" name="username"/></td></tr>
            <tr><td>password：</td><td><input type="password" name="password"/></td></tr>
            <tr><td></td><td><input type="submit" value="登录" style="cursor: pointer;"/></td></tr>
        </table>
    </form>
</div>
<hr>
<div>
    <form method="GET" action="/oauth/login">
        <ul>
            <#--<li>Github登录：<input type="submit" name="type" value="github" style="cursor: pointer;"/></li>-->
            <li>Gitee登录：<input type="submit" name="type" value="gitee" style="cursor: pointer;"/></li>
        </ul>
    </form>
</div>
</body>
</html>
