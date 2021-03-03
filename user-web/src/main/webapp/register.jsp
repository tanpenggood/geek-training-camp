<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>用户注册</title>
    <style>
        .form-item {
            display: flex;
            flex-direction: row;
            margin-bottom: 16px;
        }

        /*文本居中*/
        .text-center {
            text-align: center;
        }

        /*两端对齐*/
        .justified {
            text-align-last: justify;
        }

        .label-width {
            width: 80px;
        }

        .input-width {
            width: 300px;
        }
    </style>
</head>
<body>
<fieldset style="display: flex; justify-content: center;">
    <legend class="text-center">用户注册</legend>
    <form action="${contextPath}/user/register">
        <div class="form-item">
            <span class="justified label-width">用户名</span>
            <span>：</span>
            <input type="text" name="name" class="input-width">
        </div>
        <div class="form-item">
            <span class="justified label-width">密码</span>
            <span>：</span>
            <input type="password" name="password" class="input-width">
        </div>
        <div class="form-item">
            <span class="justified label-width">邮箱</span>
            <span>：</span>
            <input type="text" name="email" class="input-width">
        </div>
        <div class="form-item">
            <span class="justified label-width">手机号</span>
            <span>：</span>
            <input type="text" name="phoneNumber" class="input-width">
        </div>
        <div class="text-center">
            <button type="submit">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
        </div>
    </form>
</fieldset>
</body>
