<%@ page import="com.learnwy.model.User" %><%--
  Created by IntelliJ IDEA.
  User: 25973
  Date: 2017-05-15
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>老四川家常菜-个人中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="js/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="js/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
    <script src="/js/jquery/jquery-3.2.1.js"></script>
    <script src="js/bootstrap/js/bootstrap.js"></script>
    <script src="js/jquery/js.cookie.js"></script>
    <%
        HttpSession httpSession = request.getSession();
        String userName = "";
        String name = "";
        String pwd = "";
        long id = -1;
        User u = null;
        if (!(httpSession == null)) {
            u = (User) httpSession.getAttribute("u");
            if (u == null) {
    %>
    window.location.href = "/login.jsp";
    <%
            } else {
                userName = u.getDisplayName();
                name = u.getUserName();
                pwd = u.getUserPwd();
                id = u.getUserId();
            }
        }
    %>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/index.jsp">老四川家常菜</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                <ul class="nav navbar-nav">
                    <li>
                        <ul id="user_menu" class="nav navbar-nav navbar-right">
                        </ul>
                    </li>
                    <li class="right">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a><%= userName %>
                            </a></li>
                            <li><a class="navbar-link" href="loginout.jsp">注销</a></li>
                            <li><a class="current_time"></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="form-group">
        <div class="col-sm-6">
            登录名
        </div>
        <div class="col-sm-6">
            <input id="name" type="text" name="name" value="<%=name %>">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-6">
            显示姓名
        </div>
        <div class="col-sm-6">
            <input id="display_name" type="text" name="display_name" value="<%=userName%>">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-6">
            密码
        </div>
        <div class="col-sm-6">
            <input id="pwd" type="text" name="pwd" value="<%=pwd%>">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-3">
            <button onclick="user_manageUpdate()" class="btn btn-primary">确认修改
            </button>
        </div>
        <div class="col-sm-6">
            <button class="btn btn-primary" type="reset">重置</button>
        </div>
    </div>
    <div class="hidden">
        <input id="id" type="number" name="id" value="<%=id%>">
    </div>
</div>
<script>
    // state 0初始化,1完成,2失败,4进行中
    var state = 0;
    function user_manageUpdate() {
        if (!(state === 4)) {
            state = 4;
            $.ajax('/user_manage?action=custom', {
                async: true,
                data: {
                    "id": $("#id").val(),
                    "display_name": $("#display_name").val(),
                    "name": $("#name").val(),
                    "pwd": $("#pwd").val()
                },
                dataType: "json",
                error: function (e) {
                    console.log(e);
                    $("#msgBox p").val("更新失败");
                    state = 2;
                },
                success: function (data) {
                    if (data[0] == 0) {
                        //失败
                        $("#msgBox p").text("更新失败");
                    } else if (data[0] == 1) {
                        //成功
                        $("#msgBox p").text("更新成功");
                    }
                    state = 1;
                },
                complete: function () {
                    $("#msgBox").modal('show');
                },
                type: "POST"
            });
        }
    }
</script>
<div class="modal fade" tabindex="-1" id="msgBox" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">修改信息</h4>
            </div>
            <div class="modal-body">
                <p></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>