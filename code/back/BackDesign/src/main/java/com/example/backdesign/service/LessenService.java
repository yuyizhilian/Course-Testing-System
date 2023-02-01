package com.example.backdesign.service;

import com.example.backdesign.bean.ShowLessenBean;
import com.example.backdesign.mapper.LessenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessenService {

   @Autowired
   LessenMapper mapper;

   public List<ShowLessenBean> getLessen(){
        return mapper.getLessenMsg();
    }

    public String getLessenName(String lessenID){
       return mapper.getLessenNameMsg(lessenID);
    }
}
