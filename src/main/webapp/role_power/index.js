$(function () {
    $("<script src='/role_power/role_power.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/role_power/role_power.html.section", {
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