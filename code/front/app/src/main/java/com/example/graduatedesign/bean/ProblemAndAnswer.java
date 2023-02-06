package com.example.graduatedesign.bean;

import java.util.List;

public class ProblemAndAnswer {
    private Problem problem;
    private List<Answer> answer;

    public ProblemAndAnswer(Problem problem,List<Answer> answer){
        this.problem=problem;
        this.answer=answer;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public List<Answer> getAnswer() {
        return answer;
    }
}
