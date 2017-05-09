package com.learnwy.util;

import com.learnwy.model.SysMenu;

/**
 * Created by 25973 on 2017-05-08.
 */
public class Gen {
    public static void main(String[] args) {
        System.out.println(genIndexJs("role"));
        System.out.println(genIndexJs("user"));
    }

    public static String genIndexJs(String tableName) {
        return ("$(function () {    $(\"<script src='/#{tableName}/#{tableName}.js'><\" + \"/script>\").appendTo($" +
                "(document" +
                ".head));    $.ajax(\"/#{tableName}/#{tableName}.html.section\", {        \"dataType\": \"text\",        \"error\": function (e) {            console.log(e);        },        \"success\": function (data) {            $(data).appendTo($(\"#content\"));        },        \"type\": \"POST\"    });});")
                .replaceAll("#\\{tableName\\}", tableName);
    }
}
