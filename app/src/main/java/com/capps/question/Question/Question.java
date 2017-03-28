package com.capps.question.Question;

import android.content.ContentValues;
import android.content.Context;

import com.capps.question.Answer;
import com.capps.question.AppDataBase;

/**
 * Created by varun on 25/3/17.
 */

public class Question {

    private Context mContext;

    public Question(Context context) {
        mContext=context;
    }

    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question;
    }


    //save Question only
    long save(){
        AppDataBase db = AppDataBase.getInstance(mContext);
        long rowID = db.saveQuestion(this);

        return rowID;
    }


    //save Question & Answers
    boolean save(Answer []answers){
        long rowID = save();
        boolean resuly = false;
        if (rowID> 0){
            AppDataBase db =AppDataBase.getInstance(mContext);
            resuly = db.saveAnswer(answers,rowID);
        }
        return resuly;
    }


}
