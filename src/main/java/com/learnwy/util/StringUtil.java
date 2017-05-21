package com.learnwy.util;

/**
 * Created by 25973 on 2017-05-05.
 */
public class StringUtil {
    static String Empty = "";
    public final static String update = "update";
    public final static String add = "add";
    public final static String del = "del";
    public final static String query = "query";
    public final static String complete = "complete";
    public final static String order = "order";

    public final static String errPageUrlNoTableNo = "/index.jsp";
    public final static String custom = "custom";
    public final static String confirm = "confirm";

    public static boolean empty(String s) {
        return Empty.equals(s);
    }

    /**
     * 将long[] 转化成 id1,id2,id3的形式
     *
     * @param Ls
     * @param s
     * @return
     */
    public static String join(long[] Ls, String s) {
        StringBuffer sb = new StringBuffer(" ");
        for (long L : Ls) {
            sb.append(L).append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 将不是字和_去除
     * @param path
     * @return
     */
    public static String  trimPath(String path) {
        if (isNullOrEmpty(path)) {
            return Empty;
        }
        return path.replaceAll("[^\\w_/]*", "");
    }

    /**
     * 判断字符串是空指针或是空字符串
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || empty(s);
    }

    /**
     * 去掉一些奇奇怪怪的字符
     * @param display_name
     * @return
     */
    public static String trimPathLangCN(String display_name) {
        if (isNullOrEmpty(display_name)) {
            return Empty;
        }
        return display_name.replaceAll("[~`!@#$%^&*\\(\\)+\\-=\\{\\}\\[\\]\"':;'<>?/\\.,]*", "");
    }

    /**
     * 将字符串转为long
     * 1.空 为0
     * 2.去掉非数字，在用Long转换
     * @param pageS
     * @return
     */
    public static long parseToLong(String pageS) {
        return isNullOrEmpty(pageS) ? 0 : Long.valueOf(pageS.replaceAll("[\\D]*", ""));
    }

    /**
     * 判断是否可以转换成数字
     * @param dish_name
     * @return
     */
    public static boolean canParseLong(String dish_name) {
        if (isNullOrEmpty(dish_name)) {
            return false;
        }
        for (char c : dish_name.trim().toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
