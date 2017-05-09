package com.learnwy.model;

import com.learnwy.util.Key;

public class SysMenu implements Key {
    public SysMenu() {
    }

    @Override
    public long getKey() {
        return sys_menu_id;
    }

    public SysMenu(long sys_menu_id, String display_name, String path, long parent_id) {
        this.sys_menu_id = sys_menu_id;
        this.display_name = display_name;
        this.path = path;
        this.parent_id = parent_id;
    }

    private long sys_menu_id;
    private String display_name;
    private String path;
    private long parent_id;

    public long getSysMenuId() {
        return this.sys_menu_id;
    }

    public void setSysMenuId(long sys_menu_id) {
        this.sys_menu_id = sys_menu_id;
    }

    public String getDisplayName() {
        return this.display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getParentId() {
        return this.parent_id;
    }

    public void setParentId(long parent_id) {
        this.parent_id = parent_id;
    }

    public String toJson() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(sys_menu_id).append(",\"").append(display_name).append("\",\"").append(path).append
                ("\",").append(parent_id).append("]");
        return sb.toString();
    }
}
