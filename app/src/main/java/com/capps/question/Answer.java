package com.capps.question;

/**
 * Created by varun on 27/3/17.
 */

public class Answer {

    private String answer;
    private boolean isCurrect=false;
    private int questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCurrect() {
        return isCurrect;
    }

    public void setCurrect(boolean currect) {
        isCurrect = currect;
    }

    public int getQuestionId() {
        return questionId;
    }

//    public void setQuestionId(int questionId) {
//        this.questionId = questionId;
//    }
}
