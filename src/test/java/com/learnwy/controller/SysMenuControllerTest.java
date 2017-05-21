package com.learnwy.controller;

import com.learnwy.model.SysMenu;
import com.learnwy.util.json.MenuTreeJson;
import com.learnwy.util.json.SysMenuJson;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by 25973 on 2017-05-05.
 */
public class SysMenuControllerTest {
    @Test
    public void getSysMenusByUserId() throws Exception {
    }

    @Test
    public void getSysMenusByUser() throws Exception {
        System.out.print(MenuTreeJson.getSysMenuJson(SysMenuController.getSysMenusByUser(OrderControllerTest.cs)));
        System.out.print(MenuTreeJson.getSysMenuJson(SysMenuController.getSysMenusByUser(OrderControllerTest.fwy)));
//        System.out.print(SysMenuJson.listToJson(SysMenuController.getSysMenusByUser(OrderControllerTest.cs)));
//        System.out.print(SysMenuJson.listToJson(SysMenuController.getSysMenusByUser(OrderControllerTest.fwy)));
    }

    @Test
    public void getSysMenusByPowers() throws Exception {
    }

    @Test
    public void getSysMenusByPowerIds() throws Exception {
    }

    @Test
    public void getAllSysMenus() throws Exception {
    }

    @Test
    public void updateSysMenu() throws Exception {
    }

    @Test
    public void addOrUpdateSysMenu() throws Exception {
    }

    public void testGetSysMenusByUserId() throws Exception {
        for (SysMenu sysMenu : SysMenuController.getSysMenusByUserId(1)) {
            System.out.println(sysMenu.getDisplayName());
        }
    }

}