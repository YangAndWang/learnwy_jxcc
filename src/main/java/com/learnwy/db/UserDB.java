package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDB {
    public static void main(String[] args) throws InterruptedException {
        // List<User> users = new UserDB().getUsers();
        // for (User u : users) {
        // System.out.println(u.getDisplayName());
        // }
        // User user = new User("name", "display_name", "pwd", 1);
        // int update = new UserDB().updateUser(user);
        // System.out.println(update);
        // User addUser = new User("learnwy", "learnwy", "learnwy", -1);
        // int insert = new UserDB().addUser(addUser);
        // System.out.println(insert);
        // int del = new UserDB().delUser(addUser);
        // System.out.println(del);
    }

    public static List<User> getUsers() {
        LinkedList<User> ret = new LinkedList<User>();
        String sql = "select user_id,user_name,user_pwd,display_name from user";
        ResultSet rs = MySQL.excuteSQL(sql);
        User user = null;
        try {
            while (rs.next()) {
                user = new User(rs.getString(2), rs.getString(4), rs.getString(3), rs.getLong(1));
                ret.add(user);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int delUser(long id) {
        String sql = "delete from user where user_id = " + id;
        return MySQL.updateSQL(sql);
    }

    public static int delUser(String name) {
        String sql = "delete from user where user_name = " + name;
        return MySQL.updateSQL(sql);
    }

    public static int delUser(User user) {
        if (user == null) {
            return -1;
        }
        return user.getUserId() == -1 ? delUser(user.getUserName()) : delUser(user.getUserId());
    }

    public static int updateUser(User user) {
        if (user == null) {
            return -1;
        } else if (user.getUserId() == -1) {
            return addUser(user);
        }
        String sql = "update user set display_name = '" + user.getDisplayName() + "' ,user_pwd = '"
                + user.getUserPwd() + "' where user_id = " + user.getUserId();
        return MySQL.updateSQL(sql);
    }

    public static int updateUser2(User user) {
        if (user == null) {
            return -1;
        } else if (user.getUserId() == -1) {
            return addUser(user);
        }
        String sql = "update user set display_name = '" + user.getDisplayName() + "' ,user_name = '"
                + user.getUserName() + "' ,user_pwd = '" + user.getUserPwd() + "' where user_id = " + user.getUserId();
        return MySQL.updateSQL(sql);
    }

    public static int addUser(User user) {
        String sql = "insert into user (display_name,user_name,user_pwd)values('" + user.getDisplayName() + "','"
                + user.getUserName() + "','" + user.getUserPwd() + "')";
        int ret = MySQL.updateSQL(sql);
        User realUser = getUserByName(user.getUserName());
        if (realUser != null) {
            user.setUserId(realUser.getUserId());
        }
        return ret;
    }

    public static User getUserById(long id) {
        String sql = "select user_id,user_name,user_pwd,display_name from user where user_id = " + id;
        ResultSet rs = MySQL.excuteSQL(sql);
        User user = null;
        try {
            if (rs.next()) {
                user = new User(rs.getString(2), rs.getString(4), rs.getString(3), rs.getLong(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public static User getUserByName(String name) {
        String sql = "select user_id,user_name,user_pwd,display_name from user where user_name = '" + name + "'";
        ResultSet rs = MySQL.excuteSQL(sql);
        User user = null;
        try {
            if (rs.next()) {
                user = new User(rs.getString(2), rs.getString(4), rs.getString(3), rs.getLong(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public static boolean getUserByLogin(User user) {
        String sql = "select  user_id,user_name,user_pwd,display_name from user where user_name = '" + user.getUserName() + "' and user_pwd = '" + user.getUserPwd() + "'";
        ResultSet rs = MySQL.excuteSQL(sql);

        try {
            if (rs.next()) {
                user.setDisplayName(rs.getString(4));
                user.setUserId(rs.getLong(1));
                user.setUserName(rs.getString(2));
                user.setUserPwd("");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<User> getUsers(long page, TranValueClass rows) {
        List<User> users = new LinkedList<>();
        String sql = "SELECT count(user_id) from user";
        String dataSQL = " SELECT user_id, user_name, user_pwd, display_name FROM user limit  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(dataSQL);
            while (rs.next()) {
                users.add(new User(rs.getString(2), rs.getString(4), rs.getString(3), rs.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
