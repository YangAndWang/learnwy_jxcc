package com.learnwy.model;

public class User {
    public User() {
    }

    public User(String display_name, String user_name, String user_pwd, long user_id) {
        this.display_name = display_name;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_id = user_id;
    }

    private String display_name;
    private String user_name;
    private String user_pwd = "";
    private long user_id = -1;

    public String getDisplayName() {
        return this.display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getUserName() {
        return this.user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserPwd() {
        return this.user_pwd;
    }

    public void setUserPwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public long getUserId() {
        return this.user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public boolean isNot(User u) {
        return u.user_id == user_id && u.user_name.equals(user_name) && u.display_name.equals(display_name);
    }

    public void setUser(User user) {
        this.display_name = user.display_name;
        this.user_name = user.user_name;
        this.user_id = user.user_id;
        this.user_pwd = "";
    }
}
