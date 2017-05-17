package com.learnwy.util.json;

import com.learnwy.model.Dict;

import java.util.List;

/**
 * Created by 25973 on 2017-05-15.
 */
public class DictJson {
    public static String ListToJson(List<Dict> allDicts) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (Dict d : allDicts) {
            sb.append("[");
            sb.append(d.getDictId()).append(",\"");
            sb.append(d.getDictName()).append("\"],");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
