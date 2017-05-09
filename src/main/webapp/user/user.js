// tragger after item clicked
window["user_init"] = function (user_id, user_display_name, user_pwd, user_name) {
    $("#user_id").val(user_id);
    $("#user_display_name").val(user_display_name);
    $("#user_pwd").val(user_pwd);
    $("#user_name").val(user_name).attr({"readonly": "readonly"});
};
window["addNewUser"] = function () {
    $("#user_title_content").find("li").removeClass("node-selected");
    $("#user_id").val(-1);
    $("#user_name").val("").removeAttr("readonly");
    $("#user_pwd").val("");
    $("#user_display_name").val("");
};
window["dealUserManage"] = function (data) {
    var user_name;
    var user_pwd;
    var user_id;
    var user_display_name;
    var users_html_s = [];
    var user_html = ['<li class="list-group-item" data-user_id='
        , user_id, ' data-user_display_name="', user_display_name, '" data-user_pwd="',
        user_pwd,
        '" data-user_name="', user_name, '">', user_display_name, '<', '/li>'];
    var user_manage_html = ['<ul class="list-group">', users_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#user_pagination");
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
        user_html[1] = data[i][0];
        user_html[3] = data[i][1];
        user_html[5] = data[i][2];
        user_html[7] = data[i][3];
        user_html[9] = data[i][1];
        users_html_s.push(user_html.join(''));
    }
    users_html_s = users_html_s.join('');
    $('#user_title_content').html(user_manage_html.join(''));
    var user_eles = $('#user_title_content li');
    user_eles.on('click', function (e) {
        var _this = $(this);
        user_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["user_init"].call(this, _this.data("user_id"), _this.data("user_display_name"), _this.data("user_pwd"), _this.data("user_name"));
    });
    hideAllContent();
    $("#user_manage").show();
}

//分页点击事件
$(function () {
    $("#user_pagination").on("click", "li", function () {
        var _this = $(this);
        var index = _this.data("page");
        var pages = $("#user_pagination");
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
        $.ajax('/user_manage', {
            data: {"page": page},
            dataType: "json",
            error: function (e) {
                console.log(e);
            },
            success: function (data) {
                dealUserManage(data);
            },
            type: "POST"
        });
    });
});
