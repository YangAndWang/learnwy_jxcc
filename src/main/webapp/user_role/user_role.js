;$(function () {
    $("#user_role_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#user_role_pagination");
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
        $.ajax('/user_role_manage', {
            data: {"page": page},
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealUserRoleManage(data[0]);
            },
            type: "POST"
        });
    });
});

;window["dealUserRoleManage"] = function (data) {
    var user_role_id;
    var user_role_display_name;
    var user_roles_html_s = [];
    var user_role_html = ['<li class="list-group-item" data-user_role_id='
        , user_role_id, ' data-user_role_display_name="', user_role_display_name, '">', user_role_display_name, '<', '/li>'];
    var user_role_manage_html = ['<ul class="list-group">', user_roles_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#user_role_pagination");
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
        user_role_html[1] = data[i][0];
        user_role_html[3] = data[i][1];
        user_role_html[5] = data[i][1];
        user_roles_html_s.push(user_role_html.join(''));
    }
    user_roles_html_s = user_roles_html_s.join('');
    $('#user_role_title_content').html(user_role_manage_html.join(''));
    var user_role_eles = $('#user_role_title_content li');
    user_role_eles.on('click', function (e) {
        var _this = $(this);
        user_role_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["user_role_init"].call(this, _this.data("user_role_id"), _this.data("user_role_display_name"));
    });
    hideAllContent();
    $("#user_role_manage").show();
};

/**
 * 左边主表数据点击事件,初始化右边从表数据
 * @param data
 */
window["user_role_init"] = function (user_id, display_name) {
    $.ajax('/user_role_manage?action=query', {
        data: {
            "id": user_id
        },
        dataType: "json",
        error: function (e) {
        },
        success: function (data) {
            dealUserRoleConnection(data, user_id, display_name);
        },
        type: "POST"
    });
};
$(function () {
    var role_user_temp = '<form class="form-group" action="/user_role_manage?action=del" method="post">' +
        '<div class="control-box"> ' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="user_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="user_display_name" disabled> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="role_id" readonly> </div>' +
        ' <div class="col-lg-2 col-sm-2"> <input type="text" name="role_display_name" disabled></div>' +
        ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
        ' btn-danger">删除</button> </div>' +
        '</div>' +
        '</form>';
    var role_add;
    window['dealUserRoleConnection'] = function (data, user_id, display_name) {
        var _user_id = data[0];
        var user_roles = data[1];
        var $sys;
        var inputs
        var i = 0;
        var connection = $("#user_role_connection");
        connection.children().remove();
        for (i = 0; i < user_roles.length; i++) {
            $sys = $(role_user_temp);
            inputs = $sys.find("input");
            inputs.get(0).value = _user_id == user_id ? user_id : _user_id;
            inputs.get(1).value = display_name;
            inputs.get(2).value = user_roles[i][0];
            inputs.get(3).value = user_roles[i][1];
            connection.append($sys);
        }
    };
    window['addNewUserRole'] = function () {
        window['select_user_role_roles'] = function ($select) {
            var _this = $($select);
            var role_id = +_this.val().split('_wy_')[0];
            _this.parent().parent().find('input[name="role_id"]').val(role_id);
        }
        role_add = role_add || '<form class="form-group" action="/user_role_manage?action=add"' +
            ' method="post">' +
            '<div class="control-box"> ' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control" type="text" name="user_id" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="user_display_name" readonly> </div>' +
            ' <div class="col-lg-2 col-sm-2"> ' +
            '   <select class="form-control" onchange="select_user_role_roles(this);" name="role_display_name">' +
            user_role_roles.reduce(function (pre, next) {
                pre.push('<option value="' + next[0] + '_wy_' + next[1] + '">' + next[1] + '</option>');
                return role_add;
            }, role_add = []).join('') +
            ' </select> </div>' +
            ' <div class="col-lg-2 col-sm-2"> <input class="form-control"  type="text" name="role_id" readonly></div>' +
            ' <div class="col-lg-4 col-sm-4 text-center"> <button type="submit" class="btn' +
            ' btn-default">新增</button> </div>' +
            '</div>' +
            '</form>';
        var pages = $("#user_role_title_content");
        var activePage = pages.find("li.node-selected");
        var $sys;
        var inputs;
        var user_id;
        var user_display_name;
        if (activePage.length == 1) {
            $sys = $(role_add);
            inputs = $sys.find("input");
            user_id = +activePage.data('user_role_id');
            user_display_name = activePage.data('user_role_display_name');
            inputs.get(0).value = user_id;
            inputs.get(1).value = user_display_name;
            inputs.get(2).value = user_role_roles[0][0];
            $("#user_role_connection").append($sys);
        } 
    };
});

