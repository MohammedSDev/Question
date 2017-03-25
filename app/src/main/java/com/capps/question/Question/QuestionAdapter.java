package com.capps.question.Question;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class QuestionAdapter extends BaseAdapter {

    private int mCount;
    private Context mContext;

    public QuestionAdapter(int mCount, Context mContext) {
        this.mCount = mCount;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = parent.inflate(mContext, R.layout.row_listview_questions, parent);

            holder = new Holder();
            holder.editText = (EditText) convertView.findViewById(R.id.rowListEditTExtQuestion);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.rowListEditTExtQuestion);
            convertView.setTag(holder);
        }else
            holder = (Holder) convertView.getTag();

        return convertView;
    }


    class Holder{
        EditText editText;
        CheckBox checkBox;
        }
}
