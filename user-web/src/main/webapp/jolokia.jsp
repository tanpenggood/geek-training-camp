<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>Jolokia</title>
</head>
<body>
<div>
    All MBean.
</div>
<div>
    <p><a href="${contextPath}/my-jolokia/memory">Memory HeapMemoryUsage</a></p>
    <p><a href="${contextPath}/jolokia/read/jolokia:name=HelloWorld">com.itplh.projects.user.management.demo.HelloJolokia</a>
    </p>
    <p><a href="${contextPath}/jolokia/read/com.itplh.projects.user.management:type=User">com.itplh.projects.user.management.demo.UserManager</a>
    </p>
</div>
</body>
