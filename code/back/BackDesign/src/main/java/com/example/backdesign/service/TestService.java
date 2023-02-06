package com.example.backdesign.service;

import com.example.backdesign.bean.AlreadyTestBean;
import com.example.backdesign.bean.AnswerBean;
import com.example.backdesign.bean.ProblemBean;
import com.example.backdesign.bean.TestBean;
import com.example.backdesign.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TestService {

    @Autowired
    TestMapper mapper;

    public List<TestBean> getTestItemService(String lessenID){

        ArrayList<String> list=mapper.getTestID(lessenID);
        List<TestBean> beans=new ArrayList<>();
        TestBean bean;
        for(int i=0;i< list.size();i++){
            bean=mapper.getTestItemMapper(list.get(i));
            beans.add(bean);
        }
        return beans;
    }

    public List<AlreadyTestBean> getAlreadyTestItemService(String lessenID){
        ArrayList<String> list=mapper.getAlreadyTestID(lessenID);
        List<AlreadyTestBean> beans=new ArrayList<>();
        AlreadyTestBean bean;
        for(int i=0;i< list.size();i++){
            bean=mapper.getAlreadyTestMapper(list.get(i));
            beans.add(bean);
        }
        return beans;
    }

    public String storageTestService(String testName,String userID){
        String uuid = "";
        uuid = UUID.randomUUID().toString();
        int r=mapper.storageTestMapper(uuid,testName,userID);
        if(r==1){
            return uuid;
        }else{
            return null;
        }
    }

    public String storageProblemService(String testID,int number,String title){
        String uuid = "";
        uuid = UUID.randomUUID().toString();
        int r= mapper.storageProblemMapper(uuid,testID,number,title);
        if(r==1){
            return uuid;
        }else{
            return null;
        }
    }

    public String storageAnswerService(String problemID,String tag,String content){
        String uuid = "";
        uuid = UUID.randomUUID().toString();
        int r= mapper.storageAnswerMapper(uuid,problemID,tag,content);
        if(r==1){
            return uuid;
        }else{
            return null;
        }
    }

    public Boolean storageRightService(String problemID,String rights,int type){
        int r= mapper.storageRightMapper(problemID,rights,type);
        return r == 1;
    }

    public String storageUnreleasedTestService(String lessenID,String testID){
        String unreleasedID = "";
        unreleasedID = UUID.randomUUID().toString();
        Date utilDate = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times=dateFormat.format(utilDate );
        mapper.storageTestTime(testID,times);
        int r= mapper.storageUnreleasedTestMapper(unreleasedID,lessenID,testID,times);
        if(r==1){
            return unreleasedID;
        }else{
            return null;
        }
    }

    public String storageIssueService(String starts, String ends,String passs,String fulls,String numbers, String highest,String paste,String cases,String mark,String gain,String testID, String lessenID){
        String uuid = "";
        uuid = UUID.randomUUID().toString();
        String publishName=mapper.seePublishName(testID);
        int r= mapper.storageIssueMapper(uuid,starts,ends,passs,fulls,numbers,highest,paste,cases,mark,gain,testID,lessenID,publishName);
        if(r==1){
            int result= mapper.deleteUnreleased(testID, lessenID);
            if(result==1){
                return uuid;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public String seeTestNameService(String testID){
        return mapper.seePublishName(testID);
    }

    public List<ProblemBean> seeProblemService(String testID){
        return mapper.seeProblemMapper(testID);
    }

    public List<AnswerBean> seeAnswerService(String problemID){
        return mapper.seeAnswerMapper(problemID);
    }
}
