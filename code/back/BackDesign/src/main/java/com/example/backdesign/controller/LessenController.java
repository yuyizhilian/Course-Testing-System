package com.example.backdesign.controller;

import com.example.backdesign.bean.ShowLessenBean;
import com.example.backdesign.service.LessenService;
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
public class LessenController {

    @Autowired
    LessenService service;


    @ResponseBody
    @RequestMapping(value="seeLessen",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Map<String, List<ShowLessenBean>> seeGoodsInfo_inTransit(){
        Map<String, List<ShowLessenBean>> map=new HashMap<>();
        List<ShowLessenBean> lessen=service.getLessen();
        map.put("data", lessen);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeLessenName",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public Map<String,String> seeOrder(@RequestParam("lessenID") String lessenID){
        String lessenName=service.getLessenName(lessenID);
        Map<String,String> map=new HashMap<>();
        map.put("data",lessenName);
        return map;
    }

}
