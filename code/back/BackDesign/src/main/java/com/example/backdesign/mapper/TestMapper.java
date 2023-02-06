package com.example.backdesign.mapper;

import com.example.backdesign.bean.AlreadyTestBean;
import com.example.backdesign.bean.AnswerBean;
import com.example.backdesign.bean.ProblemBean;
import com.example.backdesign.bean.TestBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TestMapper {

    TestBean getTestItemMapper(String testID);

    ArrayList<String> getTestID(String lessenID);

    int storageTestTime(String testID,String times);

    ArrayList<String> getAlreadyTestID(String lessenID);

    String seeTestName(String testID);

    AlreadyTestBean getAlreadyTestMapper(String testID);

    int storageTestMapper(String testID,String testName,String userID);

    int storageProblemMapper(String problemID,String testID, int numbers,String titles);

    int storageAnswerMapper(String answerID,String problemID,String tag,String content);

    int storageRightMapper(String problemID,String rights,int type);

    int storageUnreleasedTestMapper(String unreleasedID,String lessenID,String testID,String times);

    String seePublishName(String testID);

    int storageIssueMapper(String publishID,String starts, String ends,String passs,String fulls,String numbers, String highest,String paste,String cases,String mark,String gain,String testID, String lessenID,String publishName);

    int deleteUnreleased(String testID,String lessenID);

    List<ProblemBean> seeProblemMapper(String testID);

    List<AnswerBean> seeAnswerMapper(String problemID);
}
