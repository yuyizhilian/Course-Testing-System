package com.example.backdesign.service;

import com.example.backdesign.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    public Boolean registerService(String userID,String password){
        String s=mapper.loginMapper(userID);
        if(s!=null){
            return false;
        }else{
            int r= mapper.registerMapper(userID, password);
            return r == 1;
        }
    }

    public Boolean logonService(String userID,String password){
        String str= mapper.loginMapper(userID);
        return password.equals(str);
    }

    public Boolean resetService(String userID,String password){
        String s=mapper.loginMapper(userID);
        if(s!=null){
            int r= mapper.resetMapper(userID, password);
            return r==1;
        }else{
            return false;
        }

    }
}
