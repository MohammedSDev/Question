package com.capps.question.Question;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
 * Created by varun on 1/4/17.
 */

public class MultiEditViewAdapter extends BaseAdapter {



    private int mCount;
    private Context mContext;
    static Answer[] Answers;
    private boolean mNotAllowUserToInbut =false;
    private boolean mIsShowAnswers = false;
    private boolean is_change_text_programmatically =false;//Beacuse textBox has listener text change,and we don't want to listen to programmatically changing.


    public MultiEditViewAdapter(int mCount, Context mContext, Answer[] answers, boolean mNotAllowUserToInbut, boolean mIsShowAnswers) {
        this.mCount = mCount;
        this.mContext = mContext;
        this.mNotAllowUserToInbut = mNotAllowUserToInbut;
        this.mIsShowAnswers = mIsShowAnswers;
        if (answers != null)
        {
            if (mCount > answers.length){
                Answer []oldANswers = MultiEditViewAdapter.Answers;
                MultiEditViewAdapter.Answers = new Answer[mCount];
                for (short i=0;i<oldANswers.length;i++)
                    MultiEditViewAdapter.Answers[i] = oldANswers[i];
            }
            else if (mCount < answers.length)
            {
                Answer []oldANswers = MultiEditViewAdapter.Answers;
                MultiEditViewAdapter.Answers = new Answer[mCount];
                for (short i=0;i < mCount;i++)
                    MultiEditViewAdapter.Answers[i] = oldANswers[i];
            }
        }
        else
            MultiEditViewAdapter.Answers = new Answer[mCount];
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        if (Answers != null)
            return Answers[position];
        else
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("position", position + "");
        final int local_position = position;

        Holder holder;
        if (convertView == null) {
//            convertView = parent.inflate(mContext, R.layout.row_listview_questions, parent); //TODO::Why Error...
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_listview_questions,parent);//TODO Why Error
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_questions, parent, false);

            holder = new MultiEditViewAdapter.Holder();
            holder.editText = (EditText) convertView.findViewById(R.id.rowListEditTExtQuestion);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.rowListCheckBoxtQuestion);


            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }


        //put answers in EditBox and checkbox
        if (Answers[position] != null) {
            is_change_text_programmatically =true;
            holder.editText.setText(Answers[position].getAnswer());
            is_change_text_programmatically =false;
            holder.checkBox.setChecked(Answers[position].isCurrect());
        }

        //this to pervient user from change answers..(showQuestion Screen:textBox:false & DetailQuestion Screen textBox_CheckBox:false)
        // Another way..user is Allow To change Answers..(CreateQuestion Screen & EditQuestion Screen)
        if (mNotAllowUserToInbut) {
            holder.editText.setEnabled(false);
            if (!mIsShowAnswers)
                holder.checkBox.setEnabled(false);
        }


        //put listener
        //if view is not enable..no needed listener any more.
        if (holder.checkBox.isEnabled())
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (Answers[local_position] == null)
                        Answers[local_position] = new Answer();
                    Answers[local_position].setCurrect(isChecked);
                }
            });

        if (holder.editText.isEnabled())
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!is_change_text_programmatically){
                        if (Answers[local_position] == null) {
                            Answers[local_position] = new Answer();
                        }
                        Answers[local_position].setAnswer(s.toString());
                    }
                }
            });


        return convertView;
    }


    public Answer[] getAnswers(){
        return Answers;
    }

    class Holder {
        EditText editText;
        CheckBox checkBox;

    }

}
