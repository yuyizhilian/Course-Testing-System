package com.example.backdesign.controller;

import com.example.backdesign.bean.AlreadyTestBean;
import com.example.backdesign.bean.AnswerBean;
import com.example.backdesign.bean.ProblemBean;
import com.example.backdesign.bean.TestBean;
import com.example.backdesign.service.TestService;
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
public class TestController {

    @Autowired
    TestService service;

    @ResponseBody
    @RequestMapping(value="seeTestItem",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, List<TestBean>> seeTestItem(@RequestParam("lessenID") String lessenID){
        Map<String, List<TestBean>> map=new HashMap<>();
        List<TestBean> test=service.getTestItemService(lessenID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeAlreadyTestItem",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, List<AlreadyTestBean>> seeAlreadyTestItem(@RequestParam("lessenID") String lessenID){
        Map<String, List<AlreadyTestBean>> map=new HashMap<>();
        List<AlreadyTestBean> test=service.getAlreadyTestItemService(lessenID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="storageTest",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> storageTest(@RequestParam("testName") String testName,
                                           @RequestParam("userID") String userID){
        Map<String, String> map=new HashMap<>();
        String test=service.storageTestService(testName,userID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="storageProblem",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> storageProblem(@RequestParam("testID") String testID,
                                           @RequestParam("number") int number,
                                              @RequestParam("title") String title){
        Map<String, String> map=new HashMap<>();
        String test=service.storageProblemService(testID, number, title);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="storageAnswer",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> storageAnswer(@RequestParam("problemID") String problemID,
                                              @RequestParam("tag") String tag,
                                              @RequestParam("content") String content){
        Map<String, String> map=new HashMap<>();
        String test=service.storageAnswerService(problemID, tag, content);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="storageRight",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, Boolean> storageRight(@RequestParam("problemID") String problemID,
                                             @RequestParam("right") String rights,
                                             @RequestParam("type") String type){
        Map<String, Boolean> map=new HashMap<>();
        int types= Integer.parseInt(type);
        Boolean test=service.storageRightService(problemID,rights,types);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="storageUnreleasedTest",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> storageUnreleasedTest(@RequestParam("lessenID") String lessenID,
                                             @RequestParam("testID") String testID){
        Map<String, String> map=new HashMap<>();
        String test=service.storageUnreleasedTestService(lessenID, testID);
        map.put("data", test);
        return map;

    }

    @ResponseBody
    @RequestMapping(value="storageIssue",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> storageIssue(@RequestParam("starts") String starts,
                                            @RequestParam("ends") String ends,
                                            @RequestParam("pass") String pass,
                                            @RequestParam("full") String full,
                                            @RequestParam("numbers") String numbers,
                                            @RequestParam("highest") String highest,
                                            @RequestParam("paste") String paste,
                                            @RequestParam("case") String cases,
                                            @RequestParam("mark") String mark,
                                            @RequestParam("gain") String gain,
                                            @RequestParam("testID") String testID,
                                            @RequestParam("lessenID") String lessenID){
        Map<String, String> map=new HashMap<>();
        System.out.println(starts);
        System.out.println(ends);
        String test=service.storageIssueService(starts, ends, pass, full, numbers, highest, paste, cases, mark, gain, testID, lessenID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeTestName",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, String> seeTestName(@RequestParam("testID") String testID){
        Map<String, String> map=new HashMap<>();
        String test=service.seeTestNameService(testID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeProblem",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, List<ProblemBean>> seeProblem(@RequestParam("testID") String testID){
        Map<String, List<ProblemBean>> map=new HashMap<>();
        List<ProblemBean> test=service.seeProblemService(testID);
        map.put("data", test);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="seeAnswer",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String, List<AnswerBean>> seeAnswer(@RequestParam("problemID") String problemID){
        Map<String, List<AnswerBean>> map=new HashMap<>();
        List<AnswerBean> test=service.seeAnswerService(problemID);
        map.put("data", test);
        return map;
    }
}
