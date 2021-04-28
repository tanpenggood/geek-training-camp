<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script src="/static/index.js"></script>
</head>
<body style="padding-top: 80px; display: flex; flex-direction: column;justify-content: center;align-items: center;">
<div>
    <form method="post" action="/login">
        <table>
            <tr><td>username：</td><td><input type="text" name="username"/></td></tr>
            <tr><td>password：</td><td><input type="password" name="password"/></td></tr>
            <tr><td></td><td><input type="submit" value="登录" style="cursor: pointer;"/></td></tr>
        </table>
    </form>
</div>
<hr>
<div>
    <div><b>第三方登录</b></div>
    <ul>
        <li>
            <form method="GET" action="/oauth/login">
                <input type="hidden" name="type" value="gitee"/>
                <button type="submit" style="cursor: pointer;">Gitee登录</button>
            </form>
        </li>
    </ul>
</div>
</body>
</html>
