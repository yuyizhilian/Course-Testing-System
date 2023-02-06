package com.example.backdesign.service;

import com.example.backdesign.bean.ChatMessageBean;
import com.example.backdesign.bean.MessageBean;
import com.example.backdesign.bean.ShowMessageBean;
import com.example.backdesign.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageMapper mapper;

    public List<ShowMessageBean>  getMessage(){
        List<ShowMessageBean> messageBeanList = new ArrayList<>();
        String messageID,image,times,user,msg,userID,lessenName;
        MessageBean bean;
        List<String> list=new ArrayList<>();
        ArrayList<String> lessenID=mapper.getLessenID();
        list.add(lessenID.get(0));
        for(int i=1;i<lessenID.size();i++){
            if(!list.contains(lessenID.get(i))){
                list.add(lessenID.get(i));
            }
        }
        for(String s:list){
            image=mapper.getImage(s);
            lessenName=mapper.getLessenName(s);
            bean=mapper.getMessageItem(s);
            userID= bean.getUserID();
            times=bean.getTimes();
            messageID= bean.getMessageID();
            msg= bean.getMsg();
            user=mapper.getUserName(userID);
            ShowMessageBean bean1=new ShowMessageBean(messageID,s,userID,image,lessenName,user,times,msg);
            messageBeanList.add(bean1);
        }
        return messageBeanList;
    }

    public List<ChatMessageBean> chatMessageBeanService(String lessenID){
        List<ChatMessageBean> list=new ArrayList<>();
        List<MessageBean> messageBeanList=mapper.getLessenMessage(lessenID);
        for(int i=0;i<messageBeanList.size();i++){
            String userID=messageBeanList.get(i).getUserID();
            String userName=mapper.getUserName(userID);
            String image=mapper.getUserImage(userID);
            ChatMessageBean bean=new ChatMessageBean(userID,image,userName,messageBeanList.get(i).getMsg(),messageBeanList.get(i).getTimes());
            list.add(bean);
        }
        return list;
    }
}
