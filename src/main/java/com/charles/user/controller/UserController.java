package com.charles.user.controller;

import com.charles.netty.util.Response;
import com.charles.netty.util.ResponseUtil;
import com.charles.user.bean.User;
import com.charles.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    public Response saveUser(User user){
        userService.saveUser(user);
        return ResponseUtil.createSuccessResult(user);
    }

    public Response saveUsers(List<User> users){
        userService.saveList(users);
        return ResponseUtil.createSuccessResult(users);
    }
}
