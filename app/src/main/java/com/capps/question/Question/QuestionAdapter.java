package com.capps.question.Question;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class QuestionAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private int mCount;
    private Context mContext;
    private Answer [] mAnswers;
    private boolean mNotAllowUserToInbut =false;
    private boolean mShowAnswer = true;


    //Use this Constrictor when you dont have any pervaio answers(ex: new Question with new Answers)
    public QuestionAdapter(int mCount, Context mContext) {
        this.mCount = mCount;
        this.mContext = mContext;
        mAnswers = new Answer[mCount];

    }

    //Use this Constrictor when you  have any pervaio answers(ex: Detail the Question OR Edit it Or Show Question)
    public QuestionAdapter(int mCount,Answer []answers, Context mContext,boolean notAllowUserToInbut,boolean showAnswer) {
        this.mCount = answers.length;
        this.mContext = mContext;
        this.mAnswers = answers;
        mNotAllowUserToInbut = notAllowUserToInbut;//TO break user change the answer,just show the answer(ex::details QuestionScreen & Show Question Secreen)
        mShowAnswer = showAnswer; //this to display/hide answer(ex: show Question);
        if (!showAnswer){
            forea
        }
    }




    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {

        return mAnswers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("position",position+"");
        final int local_position = position;

        Holder holder;
        if (convertView == null) {
//            convertView = parent.inflate(mContext, R.layout.row_listview_questions, parent); //TODO::Why Error...
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_listview_questions,parent);//TODO Why Error
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_questions,parent,false);

            holder = new Holder();
            holder.editText = (EditText) convertView.findViewById(R.id.rowListEditTExtQuestion);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.rowListCheckBoxtQuestion);


            convertView.setTag(holder);
        }else
        {
            holder = (Holder) convertView.getTag();
        }
        if (mAnswers[position]!= null){
            holder.editText.setText(mAnswers[position].getAnswer());
            if (mShowAnswer)
                holder.checkBox.setChecked(mAnswers[position].isCurrect());
        }

        //this to pervient user from change answers..(showQuestion Screen:textBox:false & DetailQuestion Screen textBox_CheckBox:false)
        if (!mShowAnswer)
            holder.editText.setEnabled(false);

        if (mNotAllowUserToInbut){
            holder.editText.setEnabled(false);
            holder.checkBox.setEnabled(false);
        }
        // Another way..user is Allow To change Answers..(CreateQuestion Screen & EditQuestion Screen)
        else
        {
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mAnswers[local_position] == null)
                        mAnswers[local_position] = new Answer();
                    mAnswers[local_position].setCurrect(isChecked);
                }
            });

            holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        if (mAnswers[local_position] == null){
                            mAnswers[local_position]=new Answer();
                        }
                        mAnswers[local_position].setAnswer( ((EditText)v).getText().toString() );
                    }
                }
            });
        }



        return convertView;
    }







    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        int position = (int) buttonView.getTag(); //TODO:: How can I access posision from here..or i should declare & use  new variable
//        if (mAnswers[position] == null)
//            mAnswers[position] = new Answer();
//        mAnswers[position].setCurrect(isChecked);
    }




    class Holder {
        EditText editText;
        CheckBox checkBox;

        }



    public Answer[] getData(){
        return mAnswers;
    }

}
