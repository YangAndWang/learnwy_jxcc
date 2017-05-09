<%@ page import="com.learnwy.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.learnwy.model.SysMenu" %>
<%@ page import="com.learnwy.controller.SysMenuController" %>
<%@ page import="com.learnwy.util.json.MenuTreeJson" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <title>老四川家常菜</title>
    <link href="js/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="js/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
    <link href="js/bootstrap/css/bootstrap-treeview.css">
    <style type="text/css">
        .node-selected {
            color: #fff;
            background-color: #428bca;
        }

        #content > div {
            display: none;
        }
    </style>
    <script src="/js/jquery/jquery-3.2.1.js"></script>
    <script src="js/bootstrap/js/bootstrap.js"></script>
    <script src="js/bootstrap/js/bootstrap-treeview.js"></script>
    <%
        HttpSession httpSession = request.getSession();
        String menu = "";
        User u = null;
        if (!(httpSession == null)) {
            u = (User) httpSession.getAttribute("u");
            if (u == null) {
                response.sendRedirect("/login.jsp");
            }
            List<SysMenu> menus = SysMenuController.getSysMenusByUser(u);
            menu = MenuTreeJson.getSysMenuJson(menus);
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
                <a class="navbar-brand" href="#">老四川家常菜</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                <ul class="nav navbar-nav">
                    <li>
                        <ul id="user_menu" class="nav navbar-nav navbar-right">
                        </ul>
                    </li>
                    <li class="right">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a><%= u.getDisplayName() %>
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
<div class="container" id="content">

    <%
        for (SysMenu sysMenu : menus) {
    %>
    <%--<%@include file="sys_menu/sys_menu.html.section" %>--%>
    <script src="<%=sysMenu.getPath().replace("_manage","")%>/index.js"></script>

    <%
            }
        }
    %>
</div>

<script>

    var sys_menu = <%= menu %>;
    //隐藏所有内容
    function hideAllContent() {
        $("#content > div").hide();
    }
    $(function () {
        //隐藏所有内容
        hideAllContent();
    });
    $(function () {
        var date;
        var date_arr = [, , , , ,];
        //显示当前时间
        function current_time() {
            date = new Date();
            $(".current_time").html(date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + "  " +
                date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
            setTimeout(current_time, 500);
        }

        current_time();

    });


    function dealUserRoleManage(data) {

    }


    //添加菜单
    $(function () {
        var i = 0;
        var menus = sys_menu[4];
        var menu_html = [];
        var id = undefined;
        var display_name = undefined;
        var path = undefined;
        var pid = undefined;
        var children_menus;
        var href;
        var nav = ['<li><a  class="navbar-link" href="#', href, '" data-id=', id, ' data-path="', path, '" data-pid=',
            pid,
            '>',
            display_name, '<', '/a><',
            '/li>'];
        var nav_dropDown =
            ['<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#', href, '" role="button" aria-haspopup="true" aria-expanded="false" data-id=', id, ' data-path="', path, '" data-pid=',
                pid, '>',
                display_name,
                '<span class="caret"><', '/span><', '/a><ul class="dropdown-menu">', children_menus, '<', '/ul><',
                '/li>'];
        var menu;
        var menus_i = 0;
        for (; i < menus.length; i++) {
            menu = menus[i]
            if (menu[4] !== undefined) {
                nav_dropDown[1] = menu[2];
                nav_dropDown[3] = menu[0];
                nav_dropDown[5] = menu[2];
                nav_dropDown[7] = menu[3];
                nav_dropDown[9] = menu[1];
                children_menus = [];
                menu = menu[4];
                for (menus_i = 0; menus_i < menu.length; menus_i++) {
                    nav[1] = menu[menus_i][2];
                    nav[3] = menu[menus_i][0];
                    nav[5] = menu[menus_i][2];
                    nav[7] = menu[menus_i][3];
                    nav[9] = menu[menus_i][1];
                    children_menus.push(nav.join(''));
                }
                nav_dropDown[13] = children_menus.join('');
                menu_html.push(nav_dropDown.join(''));
            } else {
                nav[1] = menu[2];
                nav[3] = menu[0];
                nav[5] = menu[2];
                nav[7] = menu[3];
                nav[9] = menu[1];
                menu_html.push(nav.join(''));
            }
        }
        $("#user_menu").html(menu_html.join(''));
        //添加菜单点击事件
        $("#user_menu").find("a[data-path!='#']").click(function (data) {
            var url = $(this).data("path");
            $.ajax($(this).data("path"), {
                async: false,
                data: "",
                dataType: "json",
                error: function (e) {
                    console.log(e);
                },
                success: function (data) {
                    if (url == "/sys_menu_manage") {
                        dealSysMenu(data);
                    }
                    else if (url == "/user_manage") {
                        dealUserManage(data);
                    } else if (url == "/user_role_manage") {
                        dealUserRoleManage(data);
                    } else if (url == "/role_power_manage") {
                        dealRolePowerManage(data);
                    } else if (url == "/power_sys_menu_manage") {
                        dealPowerSysMenuManage(data);
                    } else if (url == "/role_manage") {
                        dealRoleManage(data);
                    } else if (url == "/power_manage") {
                        dealPowerManage(data);
                    }
                },
                type: "POST"
            });
        });
    });
</script>


</body>
</html>
