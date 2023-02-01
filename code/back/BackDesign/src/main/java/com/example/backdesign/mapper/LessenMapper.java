package com.example.backdesign.mapper;

import com.example.backdesign.bean.ShowLessenBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LessenMapper {

    List<ShowLessenBean> getLessenMsg();

    String getLessenNameMsg(String lessenID);
}
