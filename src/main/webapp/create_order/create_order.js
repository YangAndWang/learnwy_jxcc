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
                dealCreateOrder(data[0]);
            },
            type: "POST"
        });
    });
});

;window["dealCreateOrder"] = function (data) {
    var create_order_id;
    var create_order_display_name;
    var create_orders_html_s = [];
    var create_order_html = ['<li class="list-group-item" data-create_order_id='
        , create_order_id, ' data-create_order_display_name="', create_order_display_name, '">', create_order_display_name, '<', '/li>'];
    var create_order_html = ['<ul class="list-group">', create_orders_html_s, '<', '/ul>']
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
    if ((detail[0] / 10 + 1) != (pageList.length - 2 )) {
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
        create_order_html[5] = data[i][1];
        create_orders_html_s.push(create_order_html.join(''));
    }
    create_orders_html_s = create_orders_html_s.join('');
    $('#create_order_title_content').html(create_order_html.join(''));
    var create_order_eles = $('#create_order_title_content li');
    create_order_eles.on('click', function (e) {
        var _this = $(this);
        create_order_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["create_order_init"].call(this, _this.data("create_order_id"), _this.data("create_order_display_name"));
    });
    hideAllContent();
    $("#create_order").show();
};

/**
 * 左边主表数据点击事件,初始化右边从表数据
 * 在这里是添加一个菜
 * @param data
 */
window["create_order_init"] = function (role_id, display_name) {
    // $.ajax('/create_order?action=query', {
    //     data: {
    //         "id": role_id
    //     },
    //     dataType: "json",
    //     error: function (e) {
    //     },
    //     success: function (data) {
    //         dealCreateOrderConnection(data, role_id, display_name);
    //     },
    //     type: "POST"
    // });
};
$(function () {
    var power_role_temp = '<form class="form-group" action="/create_order?action=del" method="post">' +
        '<div class="control-box"> ' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="role_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="role_display_name" disabled> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="power_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="power_display_name" disabled></div>' +
        ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
        ' btn-danger">删除</button> </div>' +
        '</div>' +
        '</form>';
    var power_add;
    window['dealRolePowerConnection'] = function (data, role_id, display_name) {
        var _role_id = data[0];
        var create_orders = data[1];
        var $sys;
        var inputs
        var i = 0;
        var connection = $("#create_order_connection");
        connection.children().remove();
        for (i = 0; i < create_orders.length; i++) {
            $sys = $(power_role_temp);
            inputs = $sys.find("input");
            inputs.get(0).value = _role_id == role_id ? role_id : _role_id;
            inputs.get(1).value = display_name;
            inputs.get(2).value = create_orders[i][0];
            inputs.get(3).value = create_orders[i][1];
            connection.append($sys);
        }
    };
    window['addNewRolePower'] = function () {
        window['select_create_order_powers'] = function ($select) {
            var _this = $($select);
            var power_id = +_this.val().split('_wy_')[0];
            _this.parent().parent().find('input[name="power_id"]').val(power_id);
        }
        power_add = power_add || '<form class="form-group" action="/create_order?action=add"' +
            ' method="post">' +
            '<div class="control-box"> ' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control" type="text" name="role_id" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="role_display_name" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> ' +
            '   <select class="form-control" onchange="select_create_order_powers(this);" name="power_display_name">' +
            create_order_powers.reduce(function (pre, next) {
                pre.push('<option value="' + next[0] + '_wy_' + next[1] + '">' + next[1] + '</option>');
                return power_add;
            }, power_add = []).join('') +
            ' </select> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="power_id" readonly></div>' +
            ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
            ' btn-default">新增</button> </div>' +
            '</div>' +
            '</form>';
        var pages = $("#create_order_title_content");
        var activePage = pages.find("li.node-selected");
        var $sys;
        var inputs;
        var role_id;
        var role_display_name;
        if (activePage.length == 1) {
            $sys = $(power_add);
            inputs = $sys.find("input");
            role_id = +activePage.data('create_order_id');
            role_display_name = activePage.data('create_order_display_name');
            inputs.get(0).value = role_id;
            inputs.get(1).value = role_display_name;
            inputs.get(2).value = create_order_powers[0][0];
            $("#create_order_connection").append($sys);
        }
    };
});

