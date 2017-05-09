package com.learnwy.util.json;

import com.learnwy.controller.UserController;
import com.learnwy.model.User;
import org.junit.Test;

import java.util.List;

/**
 * Created by 25973 on 2017-05-06.
 */
public class UserJsonTest {
    @Test
    public void test() {
        List<User> listUser = UserController.getAllUser(new User("ww", "ww", "pwd", 1L));
        System.out.println(UserJson.ListToJson((listUser)));
    }
}
