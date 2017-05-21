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
        String sql = "select distinct user_id,user_name,user_pwd,display_name from user";
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
        String sql = "select distinct user_id,user_name,user_pwd,display_name from user where user_id = " + id;
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
        String sql = "select distinct user_id,user_name,user_pwd,display_name from user where user_name = '" + name + "'";
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

    public static User getUserByLogin(User user) {
        String sql = "select distinct user_id,user_name,user_pwd,display_name from user where user_name = '" + user.getUserName() + "' and user_pwd = '" + user.getUserPwd() + "'";
        ResultSet rs = MySQL.excuteSQL(sql);

        try {
            if (rs.next()) {
                User u = new User();
                u.setDisplayName(rs.getString(4));
                u.setUserId(rs.getLong(1));
                u.setUserName(rs.getString(2));
                u.setUserPwd(rs.getString(3));
                // I will use user_pwd in user.jsp
                //user.setUserPwd("");
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User();
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

    //now in this system the user has only one Role
    public static long getUserPowerRoleIDByUserId(long user_id) {
        String sql = "SELECT ur.role_id FROM user u, user_role ur WHERE u.user_id = " + user_id + " AND u.user_id = ur.user_id";
        long ret = -1;
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            if (rs.next()) {
                ret = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static int updateUser(long id, String name, String display_name, String pwd, String last_pwd) {
        int ret = -1;
        String sql = "UPDATE `user` SET display_name = '" + display_name + "', user_name      = '" + name + "', " +
                "user_pwd = '" + pwd + "' WHERE user_id = " + id + " and user_pwd = '" + last_pwd + "'";
        return MySQL.updateSQL(sql);
    }
}
