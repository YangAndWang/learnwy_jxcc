$(function () {
    $("<script src='/user_role/user_role.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/user_role/user_role.html.section", {
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