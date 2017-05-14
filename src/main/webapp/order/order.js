;$(function () {
    $("#order_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#order_pagination");
        var activePage = pages.find("li.active");
        var page = activePage.length == 1 ? ((+activePage.data("page")) ) : 0;
        if (index == "-1") {
            if (page - 1 < 0) {
                return;
            } else {
                pages.find("li").removeClass("active");
                page--;
                $(pages.find('li').get(page + 1)).addClass("active");
            }
        } else if (index === "+1") {
            if (page > (pages.find("li").length - 4)) {
                return;
            } else {
                pages.find("li").removeClass("active");
                page++;
                $(pages.find('li').get(page + 1)).addClass("active");
            }
        } else {
            pages.find("li").removeClass("active");
            _this.addClass("active");
            var activePage = pages.find("li.active");
            var page2 = activePage.length == 1 ? ((+activePage.data("page")) ) : 0;
            if (page2 == page) {
                return;
            } else {
                page = page2;
            }
        }
        $.ajax('/order_manage', {
            data: {"page": page},
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealOrderManage(data[0]);
            },
            type: "POST"
        });
    });
});

;window["dealOrderManage"] = function (data) {
    var order_id;
    var order_table_no;
    var order_create_date;
    var orders_html_s = [];
    var order_html = ['<li class="list-group-item" data-order_id='
        , order_id, ' data-order_table_no=', order_table_no, ' data-order_create_date=', order_create_date, '>', order_id, '<', '/li>'];
    var order_manage_html = ['<ul class="list-group">', orders_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#order_pagination");
    var pageList = pages.find("li");
    var pageIndexs;
    var pageHTML =
        ['  <ul class="pagination"> <li data-page="-1"><a href="#" aria-label="Previous"><span aria-hidden="true">上一页<!--&laquo;--><',
            '/span><', '/a><',
            '/li> ', pageIndexs
            , '<li data-page="+1"><a href="#" aria-label="Next"><span aria-hidden="true">下一页<!--&raquo;--><',
            '/span> <', '/a><',
            '/li> <', '/ul>'];
    var i = 0;
    if ((detail[0] / 10  ) != (pageList.length - 2 )) {
        var activePage = pages.find("li.active");
        var page = activePage.length == 1 ? ((+activePage.data("page")) ) : 0;
        pageIndexs = [];
        for (i = 0; i < (detail[0] / 10 );) {
            pageIndexs.push(['<li', page == i ? ' class="active" ' : '', ' data-page="', i,
                '"><a href="#"><span aria-hidden="true">',
                ++i, '<',
                '/span><', '/a><',
                '/li> '].join(''));
        }
        pageIndexs = pageIndexs.join('');
    }
    if (!(pageIndexs == undefined)) {
        pageHTML[4] = pageIndexs;
        pages.html(pageHTML.join(''));
    }
    data = data[1];
    for (i = 0; i < data.length; i++) {
        order_html[1] = data[i][0];
        order_html[3] = data[i][2];
        order_html[5] = data[i][1];
        order_html[7] = data[i][0];
        orders_html_s.push(order_html.join(''));
    }
    orders_html_s = orders_html_s.join('');
    $('#order_title_content').html(order_manage_html.join(''));
    var order_eles = $('#order_title_content li');
    order_eles.on('click', function (e) {
        var _this = $(this);
        order_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["order_init"].call(this, _this.data("order_id"), _this.data("order_table_no"), _this.data("order_create_date"));
    });
    hideAllContent();
    $("#order_manage").show();
};

/**
 * 左边主表数据点击事件,初始化右边从表数据
 * @param data
 */
window["order_init"] = function (order_id, order_table_no, create_date) {
    $.ajax('/order_manage?action=query', {
        data: {
            "id": order_id
        },
        dataType: "json",
        error: function (e) {
        },
        success: function (data) {
            dealOrderConnection(data, order_id, order_table_no, create_date);
        },
        type: "POST"
    });
};
$(function () {
    // del on this is complete
    var order_detail_temp = '<form class="form-group" onsubmit="beforeOrderDishSubmit()"' +
        ' action="/order_manage?action=del"' +
        ' method="post">' +
        '<div class="control-box"> ' +
        ' <div class="hidden"><input type="number" name="order_id"><input type="number" name="dish_id"></div> ' +
        ' <div class="col-sm-4"> <input type="text" name="dish_name" readonly> </div>' +
        ' <div class="col-sm-4"> <input type="number" name="dish_nums" disabled> </div>' +
        ' <div class="col-sm-4 text-center"> <button type="submit" class="btn btn-default">炒完</button> </div>' +
        '</div>' +
        '</form>';
    var sys_menu_add;
    window['dealOrderConnection'] = function (data, order_id, table_no, create_date) {
        var _order_id = data[0];
        var orders = data[1];
        var $sys;
        var inputs;
        var i = 0;
        $("#order_create_date").html(create_date);
        $("#order_order_id").html(_order_id);
        var connection = $("#order_connection");
        connection.children().remove();
        for (i = 0; i < orders.length; i++) {
            $sys = $(order_detail_temp);
            inputs = $sys.find("input");
            inputs.get(2).value = orders[i][0]
            inputs.get(3).value = orders[i][1];
            inputs.get(0).value = order_id;
            inputs.get(1).value = orders[i][2];
            if (orders[i][3] == 2) {
                $sys.find("button").text("送完");
            } else if (orders[i][3] == 4) {
                $sys.find("button").attr("type", "button").text("已完成");
            }
            connection.append($sys);
        }
    };
    window['beforeOrderDishSubmit'] = function () {
        webSocket.send('complete:');
        var d = new Date().getTime() + 500;
        for (; new Date().getTime() < d;) {

        }
        return true;
    }
});

