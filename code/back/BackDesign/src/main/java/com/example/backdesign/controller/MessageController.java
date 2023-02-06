package com.example.backdesign.controller;

import com.example.backdesign.bean.ChatMessageBean;
import com.example.backdesign.bean.ProblemBean;
import com.example.backdesign.bean.ShowLessenBean;
import com.example.backdesign.bean.ShowMessageBean;
import com.example.backdesign.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    MessageService service;

    @ResponseBody
    @RequestMapping(value="seeMessage",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Map<String, List<ShowMessageBean>> seeGoodsInfo_inTransit(){
        Map<String, List<ShowMessageBean>> map=new HashMap<>();
        List<ShowMessageBean> message=service.getMessage();
        map.put("data", message);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeMessage",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, List<ChatMessageBean>> seeMessage(@RequestParam("lessenID") String lessenID){
        Map<String, List<ChatMessageBean>> map=new HashMap<>();
        List<ChatMessageBean> test=service.chatMessageBeanService(lessenID);
        map.put("data", test);
        return map;
    }
}
