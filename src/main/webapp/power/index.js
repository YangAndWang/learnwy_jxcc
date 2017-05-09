$(function () {
    $("<script src='/power/power.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/power/power.html.section", {
        "dataType": "text", "error": function (e) {
            console.log(e);
        }, "success": function (data) {
            $(data).appendTo($("#content"));
        }, "type": "POST"
    });
});
window["dealPowerManage"] = function (data) {
    var power_id;
    var power_display_name;
    var powers_html_s = [];
    var power_html = ['<li class="list-group-item" data-power_id='
        , power_id, ' data-power_display_name="', power_display_name, '">', power_display_name, '<', '/li>'];
    var power_manage_html = ['<ul class="list-group">', powers_html_s, '<', '/ul>']
    var i = 0;
    var detail = data;
    var pages = $("#power_pagination");
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
        power_html[1] = data[i][0];
        power_html[3] = data[i][1];
        power_html[5] = data[i][1];
        powers_html_s.push(power_html.join(''));
    }
    powers_html_s = powers_html_s.join('');
    $('#power_title_content').html(power_manage_html.join(''));
    var power_eles = $('#power_title_content li');
    power_eles.on('click', function (e) {
        var _this = $(this);
        power_eles.removeClass("node-selected");
        _this.addClass("node-selected");
        window["power_init"].call(this, _this.data("power_id"), _this.data("power_display_name"));
    });
    hideAllContent();
    $("#power_manage").show();
};
