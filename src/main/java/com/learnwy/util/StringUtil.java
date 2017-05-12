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

    public static boolean empty(String s) {
        return Empty.equals(s);
    }

    public static String join(long[] Ls, String s) {
        StringBuffer sb = new StringBuffer();
        for (long L : Ls) {
            sb.append(L).append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String trimPath(String path) {
        if (isNullOrEmpty(path)) {
            return Empty;
        }
        return path.replaceAll("[^\\w_/]*", "");
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || empty(s);
    }

    public static String trimPathLangCN(String display_name) {
        if (isNullOrEmpty(display_name)) {
            return Empty;
        }
        return display_name.replaceAll("[~`!@#$%^&*\\(\\)+\\-=\\{\\}\\[\\]\"':;'<>?/\\.,]*", "");
    }

    public static long parseToLong(String pageS) {
        return isNullOrEmpty(pageS) ? 0 : Long.valueOf(pageS.replaceAll("[\\D]*", ""));
    }

    public static boolean canParseLong(String dish_name) {
        for (char c : dish_name.trim().toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
