;window["dealSysMenu"] = function (data) {
    var tree = [];
    var i = 0;
    var menu = data[4];
    var tree_data = [];

    function node(text, href) {
        var n = {
            icon: "glyphicon glyphicon-stop",
            selectedIcon: "glyphicon glyphicon-stop",
            color: "#000000",
            backColor: "#FFFFFF",
            "text": text,
            "href": href || "#"
        }
        return n;
    }

    var menus_i;
    if (menu != undefined) {
        for (; i < menu.length; i++) {
            var n = node(menu[i][0] + ' ' + menu[i][1], "#sys_menu_init" + "&" + menu[i][0] + "&" + menu[i][1] + "&" + menu[i][2] + "&" + menu[i][3]);
            // "#" + menu[i][0] + "&" + menu[i][1] + "&" + menu[i][2] + "&" + menu[i][3]
            if (menu[i][4] != undefined) {
                n.nodes = [];
                for (menus_i = 0; menus_i < menu[i][4].length; menus_i++) {
                    n.nodes.push(node(menu[i][4][menus_i][0] + ' ' + menu[i][4][menus_i][1],
                        "#sys_menu_init" + "&" + menu[i][4][menus_i][0] + "&" +
                        menu[i][4][menus_i][1] + "&" + menu[i][4][menus_i][2] + "&" +
                        menu[i][4][menus_i][3]));
                }
            }
            tree.push(n);
        }
    }
    hideAllContent();
    $("#sys_menu_manage_tree").treeview({data: tree});
    $("#sys_menu_manage").show();
}
;window["sys_menu_init"] = function (sys_menu_id, sys_menu_name, sys_menu_path, sys_menu_pid) {
    //console.log(sys_menu_id, sys_menu_name, sys_menu_path, sys_menu_pid);
    $("#sys_menu_id").val(sys_menu_id);
    $("#sys_menu_name").val(sys_menu_name);
    $("#sys_menu_path").val(sys_menu_path);
    $("#sys_menu_pid").val(sys_menu_pid);
};