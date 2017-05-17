<%@ page import="com.learnwy.util.StringUtil" %>
<%@ page import="com.learnwy.controller.DictController" %><%--
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
    <link href="css/reset.css" rel="stylesheet">
    <%--<link href="js/bootstrap/css/bootstrap.css" rel="stylesheet">--%>
    <%--<link href="js/bootstrap/css/bootstrap-theme.css" rel="stylesheet">--%>
    <%--<link href="js/bootstrap/css/bootstrap-treeview.css">--%>
    <%--<script src="/js/jquery/jquery-3.2.1.js"></script>--%>
    <%--<script src="js/bootstrap/js/bootstrap.js"></script>--%>
    <%--<script src="js/bootstrap/js/bootstrap-treeview.js"></script>--%>
    <%--<script src="js/jquery/js.cookie.js"></script>--%>
    <style type="text/css">
        html, body {
            height: 100%;
            width: 100%;
            background-color: #fff;
            overflow: hidden;
        }

        .header > p, .header > h4 {
            color: #777;
            font-size: 18px;
            width: 100%;
            padding: 15px;
            border: 1px solid #e7e7e7;
            margin: 5px;
            box-sizing: border-box;
        }

        .wrap {
            height: 100%;
            width: 100%;
        }

        .header {
            height: 120px;
            width: 100%;
            position: absolute;
            top: 0;
        }

        .content {
            position: absolute;
            top: 120px;
            bottom: 50px;
            width: 100%;
            display: flex;
        }

        .content > .left-type {
            flex: 0 0 100px;
            margin: 0 15px;
            width: 100px;
            overflow-x: hidden;
            overflow-y: scroll;
        }

        .left-type li {
            color: #222;
            box-sizing: border-box;
            border: 1px solid #e7e7e7;
            border-radius: 3px;
            margin: 5px;
            cursor: pointer;
        }

        .left-type li, .left-type a {
            width: 100%;
            height: 30px;
            line-height: 30px;
            font-size: 16px;
        }

        .left-type a {
            padding-left: 5px;
        }

        .right-dish-list {
            flex: 1;
            overflow-x: hidden;
            overflow-y: scroll;
        }

        .right-dish-list > .item > .dish-header {
            background-color: #eee;
            color: #777;
            height: 20px;
            font-size: 16px;
        }

        .dish-content > li {
            display: flex;
            height: 60px;
        }

        .dish-content > li > img {
            flex: 0 0 60px;
        }

        .dish-content > li > div {
            padding-top: 10px;
            flex: 1;
            display: flex;
        }

        .dish-content > li > div > div.dish-item {
            flex: 1;
        }

        .dish-content > li > div > div.dish-add {
            flex: 0 0 40px;
        }

        .dish-content > li > div > div > h3 {
            color: black;
        }

        .dish-content > li > div > div > p {
            color: red;
        }

        .dish-content > li > div > div > a.icon-plus {
            display: block;
            height: 40px;
            width: 40px;
            line-height: 40px;
            font-size: 24px;
            cursor: pointer;
        }

        .footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 50px;
        }

    </style>
    <%
        String table_no = request.getParameter("table_no");
        long _table_no = -1;
        if (StringUtil.canParseLong(table_no)) {
            _table_no = Long.valueOf(table_no);
        }
        // dictListJson is an Array(Array(dict_id,dict_name))
        String dictListJson = DictController.getDictJson();
        String dishListJson = DictController.getDishJson();
    %>
    <script>
        function menuClick(_this) {
            var attrs = _this.attributes;
            var dict_id;
            var i = -1;
            var beforeHeight = 0;
            for (i = 0; i < attrs.length; i++) {
                if (attrs[i].nodeName == "data-dict_id") {
                    dict_id = +attrs[i].value;
                }
            }
            if (dict_id != -1) {
                for (i = dict_id - 1; i >= 0; i--) {
                    beforeHeight += scrollDict[i] || 0;
                }
                document.getElementById("dish_items").scrollTop = beforeHeight;
            }
        }
        //
        var addItems = [];
        // dish_id and return dish_address in addItems[]
        function findDishInItem(dish_id) {
            var i = 0;
            var len = addItems.length - 1;
            var min = 0;
            var max = len;
            i = Math.round(len / 2);
            while (min <= max) {
                if (dish_id > addItems[i][0]) {
                    min = i + 1;
                    i = Math.round((min + max) / 2);
                } else if (dish_id < addItems[i][0]) {
                    max = i - 1;
                    i = Math.round((min + max) / 2);
                } else {
                    return i;
                }
            }
            return -1;
        }
        function addDishToItem(dish) {
            var find_id = findDishInItem(dish[0]);
            if (find_id !== -1) {
                addItems[find_id][4] = addItems[find_id][4] + 1 || 1;
            } else {
                addItems.push(dish);
                addItems.sort(function (o1, o2) {
                    return o1[0] - o2[0];
                })
            }
        }

        function addDish(_this) {
            var item = _this.parentNode.parentNode.parentNode;
            var attrs = item.attributes;
            var dish_id = 0;
            var i = 0;
            for (i = 0; i < attrs.length; i++) {
                if (attrs[i].nodeName == "data-dish_id") {
                    dish_id = +attrs[i].value;
                }
            }
            var dish_name = item.children[1].children[0].children[0].innerHTML;
            var dish_price = +item.children[1].children[0].children[1].innerHTML;
            var img_path = item.children[0].src;
            addDishToItem([dish_id, dish_name, dish_price, img_path, 1]);
        }

        //记录对应dict_id的height
        var scrollDict = [];
        // 原生JS:
        function imgError(image) {
            // 隐藏图片
            // image.style.display = 'none';
            // 替换为默认图片
            image.setAttribute("src", "/images/dish/404-3.png");
        }
        window.onload = function (e) {
            var dicts =
                <%=dictListJson%>;
            var dishHasDictIDs =
                <%=dishListJson%>;
            // ths format is Array[dict_id:dict_name]
            var dictMap = [];
            var dict_id;
            var dict_name;
            var dictLi = ['<li><a onclick="menuClick(this);" data-dict_id="', dict_id, '">', dict_name, '<' + '/a><' +
            '/li>'];
            //all Item
            var dish_items = [];
            // One Item PlaceHolder
            var dish_items_html = "";
            // all ItemLi
            var dishLi = [];
            var dish_price;
            var dish_name;
            var dish_path;
            var dish_id;
            var dishItem = [' <div data-dict_id="', dict_id, '" class="item">' +
            ' <h2 class="dish-header">', dict_name, '</h2>' +
            ' <ul class="dish-content">', dish_items_html, ' </ul>' +
            ' </div>'];
            var dishItemLI = [' <li data-dish_id="', dish_id, '">' +
            ' <img width="57" height="57" alt="', dish_name, '" onerror="imgError(this)"'
            + ' src="', dish_path, '"> <div> <div class="dish-item"><h3>', dish_name, '</h3>' +
            ' <p>', dish_price,
                '</p> </div><div class="dish-add"><a class="icon-plus" onclick="addDish(this);">+</a></div></div> </li>'];
            var i = 0;
            var dictLis = [];
            for (i = 0; i < dicts.length; i++) {
                dictLi[1] = dicts[i][0];
                dictLi[3] = dicts[i][1];
                dictLis.push(dictLi.join(''));
                dictMap[dicts[i][0]] = dicts[i][1];
            }
            // 显示类别
            document.getElementById('dict_list').innerHTML = dictLis.join('');
            //显示所有菜
            var preDictID;
            var itemCount = 0
            for (i = 0; i <= dishHasDictIDs.length; i++) {
                if (i == dishHasDictIDs.length || !(preDictID == dishHasDictIDs[i][0] || preDictID === undefined)) {
                    //当前item的高度
                    scrollDict[preDictID] = 20 + itemCount * 60;
                    //新的item之前需要把前面的item加入dish_items
                    dishItem[1] = preDictID;
                    dishItem[3] = dictMap[preDictID];
                    dishItem[5] = dishLi.join('');
                    dish_items.push(dishItem.join(''));
                    dishLi.length = 0;
                    if (i != dishHasDictIDs.length) {
                        preDictID = dishHasDictIDs[i][0];
                        i--;
                        itemCount = 0;
                    }
                } else {
                    preDictID = preDictID || dishHasDictIDs[i][0];
                    itemCount++;
                    dishItemLI[1] = dishHasDictIDs[i][1];
                    dishItemLI[3] = dishHasDictIDs[i][2];
                    dishItemLI[5] = dishHasDictIDs[i][4];
                    dishItemLI[7] = dishHasDictIDs[i][2];
                    dishItemLI[9] = dishHasDictIDs[i][3];
                    dishLi.push(dishItemLI.join(''));
                }
            }
            document.getElementById("dish_items").innerHTML = dish_items.join('');
        }
    </script>
</head>
<body>
<div class="wrap">
    <div class="header">
        <h4>老四川家常菜</h4>
        <p>
            桌号:
            <a id="table_no"><%=table_no%>
            </a></p>
    </div>
    <div class="content">
        <div class="left-type">
            <ul id="dict_list">

            </ul>
        </div>
        <div id="dish_items" class="right-dish-list">

        </div>
    </div>
    <div class="footer">
        <button onclick="showDishCart();">查看已点菜单</button>
    </div>
</div>
<style type="text/css">
    .dish_Cart {
        padding-top: 40px;
        color: #000;
        background-color: #fff;
        opacity: 1;
        text-align: center;
        position: fixed;
        z-index: -1;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }

    .dish_Cart ul {
        display: flex;
    }

    .dish_Cart ul > li {
        flex: 1;
    }

    .dish_Cart ul > li.option {
        flex: 2;
    }

    .dish_Cart > div {
        position: absolute;
        width: 100%;
    }

    .dish_Cart #dish_Cart_Items {
        top: 20px;
        bottom: 80px;
        padding-top: 40px;
        overflow-y: scroll;
    }

    .dish_Cart .dish_Cart-header {
        height: 30px;
    }

    #dish_Cart_Items li {
        height: 80px;
    }

    #dish_Cart_Items li input, #dish_Cart_Items li img {
        display: block;
    }

    .dish_Cart .dish_Cart-footer {
        height: 40px;
        bottom: 20px;
    }

    #dish_Cart_Items .dish_name {
        width: 120px;
    }

    #dish_Cart_Items .dish_price, #dish_Cart_Items .dish_count {
        width: 60px;
    }

    #dish_Cart_Items .dish_options {
        width: 120px;
    }
</style>
<script src="/js/fetch.js"></script>
<script>
    var dishCartItem = [' <ul>' +
    ' <li><input type="number" readonly style="display: none;" value="', , '">' +
    '<input class="dish_name" type="text" name="dish_name" readonly value="', , '">' +
    '<img width="57" height="57" src="', , '"></li>' +
    ' <li><input class="dish_price" type="number" name="dish_price" readonly value="', , '"></li>' +
    ' <li><input class="dish_count" type="number" name="count" value="', , '"></li>' +
    ' <li>' +
    ' <button class="dish_option" onclick="removeParentParent(this);">删除</button>' +
    ' </li>' +
    ' </ul>'];
    function showDishCart() {
        showDishItems();
        document.getElementById("dish_Cart").style.zIndex = 9;
    }
    function showDishItems() {
        var i = 0;
        var allItems = ['<ul class="dish_Cart-header">' +
        ' <li><input class="dish_name" type="text" value="菜名"></li>' +
        ' <li><input class="dish_price" type="text" value="价格"></li>' +
        ' <li><input class="dish_count" type="text" value="数量"></li>' +
        ' <li><input class="dish_options" type="text" value="操作"></li>' +
        ' </ul>'];
        for (i = 0; i < addItems.length; i++) {
            dishCartItem[1] = addItems[i][0];
            dishCartItem[3] = addItems[i][1];
            dishCartItem[5] = addItems[i][3];
            dishCartItem[7] = addItems[i][2];
            dishCartItem[9] = addItems[i][4];
            allItems.push(dishCartItem.join(''));
        }
        document.getElementById("dish_Cart_Items").innerHTML = allItems.join('');
    }
    function removeParentParent(_this) {
        _this.parentNode.parentNode.parentNode.removeChild(_this.parentNode.parentNode);
    }
    function backMenuList() {
        document.getElementById("dish_Cart").style.zIndex = -1;
    }
    function getDishCartFormString() {
        var json = [];
        var i = 0;
        for (i = 0; i < addItems.length; i++) {
            json.push(
                "dish_id=" + encodeURI(addItems[i][0]) +
                "&dish_count=" + encodeURI(addItems[i][4]) +
                "&dish_price=" + encodeURI(addItems[i][2])
            );
        }
        return json.join('&');
    }
    function submitCart() {
        fetch('/customer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: getDishCartFormString()
        }).then(function (response) {
            return response.json();
        }).then(function (data) {
            console.log(data);
        }).catch(function (err) {
            console.log(err);
        });
    }
</script>
<div id="dish_Cart" class="dish_Cart">

    <div id="dish_Cart_Items">
    </div>
    <div class="dish_Cart-footer">
        <ul>
            <li>
                <button onclick="backMenuList();">返回</button>
            </li>
            <li>
                <button onclick="submitCart();">提交</button>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
