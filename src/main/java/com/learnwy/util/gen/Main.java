package com.learnwy.util.gen;

import java.io.IOException;

/**
 * Created by 25973 on 2017-05-11.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        GenDB.genMMM("sys_menu_power");
        GenModel.genMMM("sys_menu_power");
    }
}
