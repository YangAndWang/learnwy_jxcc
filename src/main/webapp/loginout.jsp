<%--
  Created by IntelliJ IDEA.
  User: 25973
  Date: 2017-05-09
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <title>退出登录</title>
    <%
        HttpSession session1 = request.getSession();
        if (session1 != null) {
            if (session1.getAttribute("u") != null) {
                session1.setAttribute("u", null);
                response.sendRedirect("/login.jsp");
            }
        }
    %>
</head>
<body>

</body>
</html>
