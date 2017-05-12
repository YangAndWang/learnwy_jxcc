;$(function () {
    $("#power_sys_menu_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#power_sys_menu_pagination");
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
        $.ajax('/power_sys_menu_manage', {
            data: {"page": page},
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealPowerSysMenuManage(data[0]);
            },
            type: "POST"
        });
    });
});

;window["dealPowerSysMenuManage"] = function (data) {
    var power_sys_menu_id;
    var power_sys_menu_display_name;
    var power_sys_menus_html_s = [];
    var power_sys_menu_html = ['<li class="list-group-item" data-power_sys_menu_id='
        , power_sys_menu_id, ' data-power_sys_menu_display_name="', power_sys_menu_display_name, '">', power_sys_menu_display_name, '<', '/li>'];
    var power_sys_menu_manage_html = ['<ul class="list-group">', power_sys_menus_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#power_sys_menu_pagination");
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
        power_sys_menu_html[1] = data[i][0];
        power_sys_menu_html[3] = data[i][1];
        power_sys_menu_html[5] = data[i][1];
        power_sys_menus_html_s.push(power_sys_menu_html.join(''));
    }
    power_sys_menus_html_s = power_sys_menus_html_s.join('');
    $('#power_sys_menu_title_content').html(power_sys_menu_manage_html.join(''));
    var power_sys_menu_eles = $('#power_sys_menu_title_content li');
    power_sys_menu_eles.on('click', function (e) {
        var _this = $(this);
        power_sys_menu_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["power_sys_menu_init"].call(this, _this.data("power_sys_menu_id"), _this.data("power_sys_menu_display_name"));
    });
    hideAllContent();
    $("#power_sys_menu_manage").show();
};

/**
 * 左边主表数据点击事件,初始化右边从表数据
 * @param data
 */
window["power_sys_menu_init"] = function (power_id, display_name) {
    $.ajax('/power_sys_menu_manage?action=query', {
        data: {
            "id": power_id
        },
        dataType: "json",
        error: function (e) {
        },
        success: function (data) {
            dealPowerSysMenuConnection(data, power_id, display_name);
        },
        type: "POST"
    });
};
$(function () {
    var sys_menu_power_temp = '<form class="form-group" action="/power_sys_menu_manage?action=del" method="post">' +
        '<div class="control-box"> ' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="power_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="power_display_name" disabled> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="sys_menu_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="sys_menu_display_name" disabled></div>' +
        ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
        ' btn-danger">删除</button> </div>' +
        '</div>' +
        '</form>';
    var sys_menu_add;
    window['dealPowerSysMenuConnection'] = function (data, power_id, display_name) {
        var _power_id = data[0];
        var power_sys_menus = data[1];
        var $sys;
        var inputs
        var i = 0;
        var connection = $("#power_sys_menu_connection");
        connection.children().remove();
        for (i = 0; i < power_sys_menus.length; i++) {
            $sys = $(sys_menu_power_temp);
            inputs = $sys.find("input");
            inputs.get(0).value = _power_id == power_id ? power_id : _power_id;
            inputs.get(1).value = display_name;
            inputs.get(2).value = power_sys_menus[i][0];
            inputs.get(3).value = power_sys_menus[i][1];
            connection.append($sys);
        }
    };
    window['addNewPowerSysMenu'] = function () {
        window['select_power_sys_menu_menus'] = function ($select) {
            var _this = $($select);
            var sys_menu_id = +_this.val().split('_wy_')[0];
            _this.parent().parent().find('input[name="sys_menu_id"]').val(sys_menu_id);
        }
        sys_menu_add = sys_menu_add || '<form class="form-group" action="/power_sys_menu_manage?action=add"' +
            ' method="post">' +
            '<div class="control-box"> ' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control" type="text" name="power_id" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="power_display_name" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> ' +
            '   <select class="form-control" onchange="select_power_sys_menu_menus(this);" name="sys_menu_display_name">' +
            power_sys_menu_menus.reduce(function (pre, next) {
                pre.push('<option value="' + next[0] + '_wy_' + next[1] + '">' + next[1] + '</option>');
                return sys_menu_add;
            }, sys_menu_add = []).join('') +
            ' </select> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="sys_menu_id" readonly></div>' +
            ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
            ' btn-default">新增</button> </div>' +
            '</div>' +
            '</form>';
        var pages = $("#power_sys_menu_title_content");
        var activePage = pages.find("li.node-selected");
        var $sys;
        var inputs;
        var power_id;
        var power_display_name;
        if (activePage.length == 1) {
            $sys = $(sys_menu_add);
            inputs = $sys.find("input");
            power_id = +activePage.data('power_sys_menu_id');
            power_display_name = activePage.data('power_sys_menu_display_name');
            inputs.get(0).value = power_id;
            inputs.get(1).value = power_display_name;
            inputs.get(2).value = power_sys_menu_menus[0][0];
            $("#power_sys_menu_connection").append($sys);
        }

    };
});

