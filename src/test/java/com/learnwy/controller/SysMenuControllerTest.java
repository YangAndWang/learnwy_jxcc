package com.learnwy.controller;

import com.learnwy.model.SysMenu;
import junit.framework.TestCase;

/**
 * Created by 25973 on 2017-05-05.
 */
public class SysMenuControllerTest extends TestCase {
    public void testGetSysMenusByUserId() throws Exception {
        for(SysMenu sysMenu : SysMenuController.getSysMenusByUserId(1)){
            System.out.println(sysMenu.getDisplayName());
        }
    }

}