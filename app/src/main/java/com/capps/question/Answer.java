package com.capps.question;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by varun on 27/3/17.
 */

public class Answer implements Cloneable {

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

    public static Answer [] getAllAnswers(Context context,int questionId){
        AppDataBase db = AppDataBase.getInstance(context);
        Cursor valuse= db.getAllTable(AppDataBase.ANSWER_T,AppDataBase.ANSWER_COLUMN_QUESTION_ID,questionId + "");
        Answer []answers = new Answer[valuse.getCount()];
        short counter = 0;
        valuse.moveToFirst();
        do {
            answers[counter] = new Answer();
            answers[counter].setAnswer(valuse.getString(valuse.getColumnIndex(AppDataBase.ANSWER_COLUMN_ANSWER)));
            boolean result = valuse.getInt(valuse.getColumnIndex(AppDataBase.ANSWER_COLUMN_CURRECT)) == 1 ? true : false;
            answers[counter].setCurrect(result);
            counter++;
        } while (valuse.moveToNext());
        return answers;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
