$(function () {
    $("<script src='/sys_menu/sys_menu.js'><" + "/script>").appendTo($(document.head));

    $.ajax("/sys_menu/sys_menu.html.section", {
        "dataType": "text",
        "error": function (e) {
            console.log(e);
        },
        "success": function (data) {
            $(data).appendTo($("#content"));
        },
        "type": "POST"
    });
});