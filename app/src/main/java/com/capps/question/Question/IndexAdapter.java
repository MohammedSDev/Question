package com.capps.question.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by varun on 28/3/17.
 */

public class IndexAdapter extends BaseAdapter {

    private Question []mQuestion;
    private Context mContext;

    public IndexAdapter(Question []mQuestion, Context mContext) {
        this.mQuestion = mQuestion;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mQuestion.length;
    }

    @Override
    public Object getItem(int position) {
        return mQuestion[position];
    }

    @Override
    public long getItemId(int position) {
        return mQuestion[position].getmId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);

            holder=new Holder();
            holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        holder.textView.setText(mQuestion[position].getmQuestion());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO::show Details
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //TODO:: show Edit Question
                return false;
            }
        });

        return convertView;
    }



    class Holder{
        TextView textView;
    }
}
