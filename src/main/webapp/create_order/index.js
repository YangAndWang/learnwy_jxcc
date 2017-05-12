$(function () {
    $("<script src='/create_order/create_order.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/create_order/create_order.html.section", {
        "dataType": "text", "error": function (e) {
            console.log(e);
        }, "success": function (data) {
            $(data).appendTo($("#content"));
        }, "type": "POST"
    });
});