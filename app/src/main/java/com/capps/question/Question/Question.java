package com.capps.question.Question;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.capps.question.Answer;
import com.capps.question.AppDataBase;

/**
 * Created by varun on 25/3/17.
 */

public class Question {

    private Context mContext;
    private String mQuestion;
    private int mId;



    public Question(Context context) {
        mContext=context;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    @Override
    public String toString() {
        return mQuestion;
    }



    //DB Method

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
    //Get all questions in DB
    public static Question []allQuestion(Context context){
        Cursor values;
        Question [] questions;
        int counter = 0;

        AppDataBase db = AppDataBase.getInstance(context);
        values = db.getAllTable(AppDataBase.QUESTION_T);
        questions=new Question[values.getCount()];

        if (values.moveToFirst()){
            do {
                questions[counter]=new Question(context);
                questions[counter].setmId(values.getInt(values.getColumnIndex(AppDataBase.ID_COLUMN)));
                questions[counter].setmQuestion(values.getString(values.getColumnIndex(AppDataBase.QUESTION_COLUMN_QUESTION)));
                counter++;
            }while (values.moveToNext());
        }

        return questions;

    }
    //Get first Question in DB OR null
    @Nullable
    public static Question firstQuestion(Context context) {

        /*Steps
        get FIRST question from DB
        create&return next new Question OR null*/

        AppDataBase db = AppDataBase.getInstance(context);
        String sql = "SELECT * FROM " + AppDataBase.QUESTION_T + " ORDER BY " + AppDataBase.ID_COLUMN + " LIMIT 1;";
        Cursor value = db.getRow(sql);

        if (value.moveToFirst()) {
            Question nextQuestion = new Question(context);
            nextQuestion.setmQuestion(value.getString(value.getColumnIndex(AppDataBase.QUESTION_COLUMN_QUESTION)));
            nextQuestion.setmId(value.getInt(value.getColumnIndex(AppDataBase.ID_COLUMN)));
            return nextQuestion;
        }
        else
            return null;

    }
    //Get next Question..Or null if it is last one, also return null if no id exists for this question
    public Question nextQuestion() {

        /*Steps
        check if this question has id
        get next question from DB
        create&return next new Question OR null*/

        if (this.getmId() != 0) {

            AppDataBase db = AppDataBase.getInstance(mContext);
            String nextID = (this.getmId() + 1) + "";
            String sql = "SELECT * FROM " + AppDataBase.QUESTION_T + " WHERE " + AppDataBase.ID_COLUMN + " = ?";
            Cursor value = db.getRow(sql, nextID + "");

            if (value.moveToFirst()) {
                Question nextQuestion = new Question(mContext);
                nextQuestion.setmQuestion(value.getString(value.getColumnIndex(AppDataBase.QUESTION_COLUMN_QUESTION)));
                nextQuestion.setmId(value.getInt(value.getColumnIndex(AppDataBase.ID_COLUMN)));
                return nextQuestion;
            } else
                return null;
        } else
            return null;
    }



}
