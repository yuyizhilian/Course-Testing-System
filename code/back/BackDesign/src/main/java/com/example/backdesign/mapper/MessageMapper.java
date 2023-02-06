package com.example.backdesign.mapper;

import com.example.backdesign.bean.ChatMessageBean;
import com.example.backdesign.bean.MessageBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessageMapper {

    ArrayList<String> getLessenID();

    String getImage(String lessenID);

    MessageBean getMessageItem(String lessenID);

    String getUserName(String userID);

    String getLessenName(String lessenID);

    List<MessageBean>  getLessenMessage(String lessenID);

    String getUserImage(String userID);


}
