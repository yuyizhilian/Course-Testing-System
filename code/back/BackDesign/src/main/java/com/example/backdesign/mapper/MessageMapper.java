package com.example.backdesign.mapper;

import com.example.backdesign.bean.MessageBean;
import com.example.backdesign.bean.MessageItemBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MessageMapper {

    ArrayList<String> getLessenID();

    String getImage(String lessenID);

    MessageBean getMessageItem(String lessenID);

    String getUserName(String userID);

    String getLessenName(String lessenID);
}
