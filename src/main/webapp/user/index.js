$(function () {
    $("<script src='/user/user.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/user/user.html.section", {
        "dataType": "text", "error": function (e) {
            console.log(e);
        }, "success": function (data) {
            $(data).appendTo($("#content"));
        }, "type": "POST"
    });
});
