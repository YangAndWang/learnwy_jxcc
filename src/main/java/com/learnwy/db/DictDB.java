package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Dict;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class DictDB {
    public static List<Dict> getAllDicts() {
        LinkedList<Dict> ret = new LinkedList<Dict>();
        String sql = " select dict_id,dict_name  from `dict` order by dict_id ";
        ResultSet rs = MySQL.excuteSQL(sql);
        Dict dict = null;
        try {
            while (rs.next()) {
                dict = new Dict(rs.getLong(1), rs.getString(2));
                ret.add(dict);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteDict(Dict dict) {
        if (dict == null) {
            return -1;
        }
        String sql = "delete from `dict` where dict_id = " + dict.getDictId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddDict(Dict dict) {
        if (dict == null) {
            return -1;
        } else if (dict.getDictId() == -1) {
            return addDict(dict);
        } else {
            return updateDict(dict);
        }
    }

    public static int addDict(Dict dict) {
        String sql = "insert into `dict`(dict_name)values(" + "'" + dict.getDictName() + "'" + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateDict(Dict dict) {
        if (dict == null) {
            return -1;
        } else if (dict.getDictId() == -1) {
            return addDict(dict);
        }
        String sql = "update `dict` set " + "dict_name = '" + dict.getDictName() + "'" + " where dict_id = " + dict.getDictId();
        return MySQL.updateSQL(sql);
    }
}