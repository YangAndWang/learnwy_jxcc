window["dealRoleManage"] = function (data) {
    var role_id;
    var role_display_name;
    var roles_html_s = [];
    var role_html = ['<li class="list-group-item" data-role_id='
        , role_id, ' data-role_display_name="', role_display_name, '">', role_display_name, '<', '/li>'];
    var role_manage_html = ['<ul class="list-group">', roles_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#role_pagination");
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
        role_html[1] = data[i][0];
        role_html[3] = data[i][1];
        role_html[5] = data[i][1];
        roles_html_s.push(role_html.join(''));
    }
    roles_html_s = roles_html_s.join('');
    $('#role_title_content').html(role_manage_html.join(''));
    var role_eles = $('#role_title_content li');
    role_eles.on('click', function (e) {
        var _this = $(this);
        role_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["role_init"].call(this, _this.data("role_id"), _this.data("role_display_name"));
    });
    hideAllContent();
    $("#role_manage").show();
}
window["role_init"] = function (role_id, role_display_name) {
    $("#role_id").val(role_id);
    $("#role_display_name").val(role_display_name);
}
window["addNewRole"] = function () {
    $("#role_title_content").find("li").removeClass("node-selected");
    $("#role_id").val(-1);
    $("#role_display_name").val("");
}
$(function () {
    $("#role_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#role_pagination");
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
        $.ajax('/role_manage', {
            data: {"page": page},
            dataType: "json",
            error: function (e) {
            },
            success: function (data) {
                dealRoleManage(data);
            },
            type: "POST"
        });
    });
});