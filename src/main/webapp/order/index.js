$(function () {
    $("<script src='/order/order.js'><" + "/script>").appendTo($(document.head));
    $.ajax("/order/order.html.section", {
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