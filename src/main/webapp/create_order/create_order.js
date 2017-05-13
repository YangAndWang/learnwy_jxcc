;$(function () {
    $("#create_order_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#create_order_pagination");
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
        $.ajax('/create_order', {
            data: {
                "page": page, "dish_name": $("#create_order_title input").val()
            },
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealCreateOrder(data);
            },
            type: "POST"
        });
    });
});
$(function () {
    window['create_order_query'] = function () {
        $.ajax('/create_order', {
            data: {
                "dish_name": $("#create_order_title input").val()
            },
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealCreateOrder(data);
            },
            type: "POST"
        });
    }
});

;window["dealCreateOrder"] = function (data) {
    var create_order_dish_id;
    var create_order_dish_name;
    var create_order_dish_path;
    var create_order_dish_price;
    var create_order_dish_discount;
    var create_orders_html_s = [];
    var create_order_html = ['<li class="list-group-item" data-create_order_dish_id='
        , create_order_dish_id, ' data-create_order_dish_name="', create_order_dish_name, '"' +
        ' data-create_order_dish_path="', create_order_dish_path, '"' +
        ' data-create_order_dish_price=', create_order_dish_price, '' +
        ' data-create_order_dish_discount=', create_order_dish_discount, '>', create_order_dish_name, '<', '/li>'];
    var create_order_manage_html = ['<ul class="list-group">', create_orders_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#create_order_pagination");
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
    if ((detail[0] / 10 ) > (pageList.length - 2 )) {
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
        create_order_html[1] = data[i][0];
        create_order_html[3] = data[i][1];
        create_order_html[5] = data[i][2];
        create_order_html[7] = data[i][3];
        create_order_html[9] = data[i][4];
        create_order_html[11] = data[i][1];
        create_orders_html_s.push(create_order_html.join(''));
    }
    create_orders_html_s = create_orders_html_s.join('');
    $('#create_order_title_content').html(create_order_manage_html.join(''));
    var create_order_eles = $('#create_order_title_content li');
    create_order_eles.on('click', function (e) {
        var _this = $(this);
        create_order_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["create_order_init"].call(this, _this.data("create_order_dish_id"),
            _this.data("create_order_dish_name"), _this.data("create_order_dish_path"), _this.data("create_order_dish_price"), _this.data("create_order_dish_discount"));
    });
    hideAllContent();
    $("#create_order").show();
};
$(function () {
    var dish_detail_temp;
    /**
     * 左边主表数据点击事件,初始化右边从表数据
     * 在这里是添加一个菜
     * @param data
     */
    window["create_order_init"] = function (dish_id, dish_name, dish_path, dish_price, dish_discount) {
        dish_detail_temp = dish_detail_temp || [' <div class="form-group">' +
            ' <div class="col-lg-2 col-sm-2">' +
            ' <input type="number" name="dish_id" value=', dish_id, ' readonly>' +
            ' </div>' +
            ' <div class="col-lg-2 col-sm-2">' +
            ' <input type="text" name="dish_name" value=', dish_name, ' readonly>' +
            ' </div>' +
            ' <div class="col-lg-2 col-sm-2">' +
            ' <input type="number" name="dish_price" value=', dish_price, '>' +
            ' </div>' +
            ' <div class="col-lg-2 col-sm-2">' +
            ' <input type="number" name="dish_count" value=1>' +
            ' </div>' +
            ' <div class="col-lg-4 col-sm-4">' +
            ' <button class="btn btn-danger" onclick="deleteGrandParent(this);">删除</button>' +
            ' </div>' +
            ' </div>'];
        var hasAdded = false;
        $("#create_order_connection input[name='dish_id']").each(function (i, o) {
            var dish_count_ele;
            if (o.value == dish_id) {
                dish_count_ele = $(o).parent().parent().find("input[name='dish_count']");
                dish_count_ele.val(+dish_count_ele.val() + 1);
                hasAdded = true;
            }
        });
        if (hasAdded) {
            return;
        }
        dish_detail_temp[1] = dish_id;
        dish_detail_temp[3] = dish_name;
        dish_detail_temp[5] = (+dish_price) * (1 - (+dish_discount));
        $(dish_detail_temp.join('')).insertBefore($("#create_order_connection > div").get(0));
    };
})
$(function () {
    window['deleteGrandParent'] = function (_this) {
        $(_this).parent().parent().remove();
    }
    window['create_order_removeAddDish'] = function () {
        $("#create_order_connection .btn-danger").parent().parent().remove();
    }
});

