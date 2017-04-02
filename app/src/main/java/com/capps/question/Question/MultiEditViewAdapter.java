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
        MultiEditViewAdapter.Answers = new Answer[mCount];

        if (answers != null)
        {
            if (mCount > answers.length){
                for (short i=0;i<answers.length;i++)
                    MultiEditViewAdapter.Answers[i] = answers[i];
            }
            else if (mCount < answers.length)
            {
                for (short i=0;i < mCount;i++)
                    MultiEditViewAdapter.Answers[i] = answers[i];
            }
            else
                MultiEditViewAdapter.Answers = answers;
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (Answers[position] != null)
            Log.d("position", "position:" + position + ",AnswerItem:" + Answers[position].getAnswer());
        else
            Log.d("position", "position:" + position + ",AnswerItem:" + Answers[position]);

        Holder holder;
        if (convertView == null) {
//            convertView = parent.inflate(mContext, R.layout.row_listview_questions, parent); //TODO::Why Error...
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_listview_questions,parent);//TODO Why Error
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_questions, parent, false);
            holder = new MultiEditViewAdapter.Holder(convertView);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
            holder.editText.clearFocus();
            holder.editText.removeTextChangedListener(holder.textWatcher);
            Log.d("position","oldText:" + holder.editText.getText().toString());

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
            if (holder.checkBox.isEnabled()){
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (Answers[position] == null)
                            Answers[position] = new Answer();
                        if (!is_change_text_programmatically)
                            Answers[position].setCurrect(isChecked);
                    }
                });
            }


            if (holder.editText.isEnabled()){
                holder.textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!is_change_text_programmatically){
                            if (Answers[position] == null) {
                                Answers[position] = new Answer();
                            }
                            Answers[position].setAnswer(s.toString());
                        }
                    }
                };
                holder.editText.addTextChangedListener(holder.textWatcher);
            }






        //put answers in EditBox and checkbox
        is_change_text_programmatically =true;
        if (Answers[position] != null) {
            holder.editText.setText(Answers[position].getAnswer());
            holder.checkBox.setChecked(Answers[position].isCurrect());
        }else
        {
            Answers[position] = new Answer();
            holder.editText.setText("");
            holder.checkBox.setChecked(false);
        }
        is_change_text_programmatically =false;








        return convertView;
    }




    public Answer[] getAnswers(){
        return Answers;
    }

    class Holder {
        EditText editText;
        CheckBox checkBox;
        TextWatcher textWatcher;

        public Holder(View view) {
            this.editText = (EditText) view.findViewById(R.id.rowListEditTExtQuestion);
            this.checkBox = (CheckBox) view.findViewById(R.id.rowListCheckBoxtQuestion);
        }
    }

}
