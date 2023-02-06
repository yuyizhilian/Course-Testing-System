package com.example.backdesign.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int registerMapper(String userID,String password);

    String loginMapper(String userID);

    int resetMapper(String userID,String password);
}
