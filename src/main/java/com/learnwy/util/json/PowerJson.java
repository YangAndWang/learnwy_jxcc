package com.learnwy.util.json;

import com.learnwy.model.Power;

import java.util.List;

/**
 * Created by 25973 on 2017-05-07.
 */
public class PowerJson {
    public static String ListToJson(List<Power> powers) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (Power r : powers) {
            sb.append("[");
            sb.append(r.getPowerId()).append(",\"");
            sb.append(r.getDisplayName()).append("\"],");

        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}