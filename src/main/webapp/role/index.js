$(function () {
    $("<script src='/role/role.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/role/role.html.section", {
        "dataType": "text", "error": function (e) {
            console.log(e);
        }, "success": function (data) {
            $(data).appendTo($("#content"));
        }, "type": "POST"
    });
});
