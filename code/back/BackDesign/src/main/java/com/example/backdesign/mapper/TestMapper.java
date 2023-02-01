package com.example.backdesign.mapper;

import com.example.backdesign.bean.AlreadyTestBean;
import com.example.backdesign.bean.TestBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TestMapper {

    TestBean getTestItemMapper(String testID);

    ArrayList<String> getTestID(String lessenID);

    ArrayList<String> getAlreadyTestID(String lessenID);

    AlreadyTestBean getAlreadyTestMapper(String testID);

    int storageTestMapper(String testID,String testName,String userID);

    int storageProblemMapper(String problemID,String testID, int numbers,String titles);

    int storageAnswerMapper(String answerID,String problemID,String tag,String content);

    int storageRightMapper(String problemID,String rights);

    int storageUnreleasedTestMapper(String unreleasedID,String lessenID,String testID,String times);

}
