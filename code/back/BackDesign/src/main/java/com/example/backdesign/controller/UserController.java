package com.example.backdesign.controller;

import com.example.backdesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService service;

    @ResponseBody
    @RequestMapping(value="login",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, Boolean> login(@RequestParam("userID") String userID,
                                         @RequestParam("userPwd") String userPwd){
        Map<String, Boolean> map=new HashMap<>();
        Boolean test=service.logonService(userID, userPwd);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="register",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, Boolean> register(@RequestParam("userID") String userID,
                                             @RequestParam("userPwd") String userPwd){
        Map<String, Boolean> map=new HashMap<>();
        Boolean test=service.registerService(userID, userPwd);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="reset",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, Boolean> reset(@RequestParam("userID") String userID,
                                         @RequestParam("userNewPwd") String userNewPwd){
        Map<String, Boolean> map=new HashMap<>();
        Boolean test=service.resetService(userID, userNewPwd);
        map.put("data", test);
        return map;
    }


}
