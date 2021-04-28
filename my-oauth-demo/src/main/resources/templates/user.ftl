<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body style="padding-top: 80px; display: flex; flex-direction: column;justify-content: center;align-items: center;">
<ul>
    <li>
        <b>USER_TOKEN：</b>
        <div>${USER_TOKEN!}</div>
    </li>
    <li>
        <b>CURRENT_USER：</b>
        <table border="4">
            <tr><td>id</td><td>${CURRENT_USER.id!}</td></tr>
            <tr><td>username</td><td>${CURRENT_USER.username!}</td></tr>
            <tr><td>password</td><td>${CURRENT_USER.password!}</td></tr>
            <tr><td>nickname</td><td>${CURRENT_USER.nickname!}</td></tr>
            <tr><td>email</td><td>${CURRENT_USER.email!}</td></tr>
            <tr><td>avatar</td><td><img src="${CURRENT_USER.avatar_url!}" alt="" style="height: 80px;"></td></tr>
            <tr><td>thirdUsername</td><td>${CURRENT_USER.thirdUserInfo.login!}</td></tr>
        </table>
    </li>
</ul>
<br>
<div>
    <form method="GET" action="/logout">
        <input type="submit" value="退出登录" style="cursor: pointer;"/>
    </form>
</div>
</body>
</html>
