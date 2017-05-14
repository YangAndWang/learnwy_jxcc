<%--
  Created by IntelliJ IDEA.
  User: 25973
  Date: 2017-05-14
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <title>老四川家常菜</title>
    <link href="js/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="js/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
    <%--<link href="js/bootstrap/css/bootstrap-treeview.css">--%>
    <script src="/js/jquery/jquery-3.2.1.js"></script>
    <script src="js/bootstrap/js/bootstrap.js"></script>
    <%--<script src="js/bootstrap/js/bootstrap-treeview.js"></script>--%>
    <%--<script src="js/jquery/js.cookie.js"></script>--%>
    <style type="text/css">
        html, body {
            height: 100%;
            width: 100%;
        }

        .wrap {
            height: 100%;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="header">

</div>
<div class="content">

</div>
<div class="footer">

</div>
<div id="open_zhifubao" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">扫二维码付款</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
    function open_zhifubao() {
        $("#open_zhifubao").modal({
            keyboard: false
        });
    }
</script>
</body>
</html>
