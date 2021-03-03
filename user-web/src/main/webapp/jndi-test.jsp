<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>My Home Page</title>
</head>
<body>
<div class="container-lg">
    <!-- Content here -->
    Hello,JNDI.
</div>
<div>
    <%
        Context ctx = new InitialContext();
        String tjndi = (String) ctx.lookup("java:comp/env/tjndi");
    %>
    <%=tjndi %>
</div>
<div>
    <%
        DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/UserPlatformDB");
        Connection conn = dataSource.getConnection();
        if (conn != null) {
            out.print("与数据库建立连接成功！");
        }
    %>
</div>
</body>
