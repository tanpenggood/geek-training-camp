<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>注册成功</title>
</head>
<body>
<div>
    恭喜你，注册成功！
</div>
<div>
    <a href="${contextPath}/user/register-page">返回注册</a>
</div>
<div>
    <fieldset>
        <legend>全部用户</legend>
        <table border="1">
            <thead>
            <tr>
                <th style="width: 100px;">id</th>
                <th style="width: 120px;">name</th>
                <th style="width: 100px;">password</th>
                <th style="width: 200px;">email</th>
                <th style="width: 200px;">phoneNumber</th>
                <th style="width: 100px;">操作</th>
            </tr>
            </thead>
            <tbody id="data"></tbody>
        </table>
    </fieldset>
</div>
</body>
<script>
    onload = function () {
        loadAllUser();
    };

    /**
     * 加载所有用户
     */
    function loadAllUser() {
        fetch('${contextPath}/user/all', {
            method: 'POST',
            mode: 'cors',
            credentials: 'include',
        }).then(response => {
            return response.json();  // 先将结果转换为 JSON 对象
        }).then(res => {
            $('#data').empty();
            const success = res.success && Array.isArray(res.data) && res.data.length > 0;
            if (success) {
                res.data.forEach(user => {
                    $('#data').append(
                        '<tr>' +
                        '<td>' + user.id + '</td>' +
                        '<td>' + user.name + '</td>' +
                        '<td>' + user.password + '</td>' +
                        '<td>' + user.email + '</td>' +
                        '<td>' + user.phoneNumber + '</td>' +
                        '<td><a href="javascript:void(0);" onclick="deleteUser(' + user.id + ')">注销</a></td>' +
                        '</tr>'
                    );
                })
            } else {
                $('#data').append(`<tr><td colspan="6">暂无数据</td></tr>`);
            }
        }).catch(function (error) {
            console.log(error);
        });
    }

    /**
     * 注销用户
     * @param userId 用户id
     */
    function deleteUser(userId) {
        fetch('${contextPath}/user/delete?id=' + userId, {
            method: 'POST',
            mode: 'cors',
            credentials: 'include',
        }).then(response => {
            return response.json();  // 先将结果转换为 JSON 对象
        }).then(res => {
            alert(res.message);
            if (res.success) {
                loadAllUser();
            }
        }).catch(function (error) {
            console.log(error);
        });
    }
</script>
