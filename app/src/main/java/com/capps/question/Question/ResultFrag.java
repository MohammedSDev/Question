package com.capps.question.Question;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.capps.question.R;

/**
 * Created by varun on 30/3/17.
 */

public class ResultFrag extends Fragment {

    final static String RESULT_KEY="result";
    final static String FULL_MARK_KEY="full_mark";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.result_frag,container,false);
        TextView textViewResult = (TextView) view.findViewById(R.id.textViewResult);
        TextView textViewResultSuccessFailed = (TextView) view.findViewById(R.id.textViewResultSuccessFailed);

        /*Steps
        * get values
        * setResult
        * setColor*/

        int fullMark = getArguments().getInt(FULL_MARK_KEY);
        int result = getArguments().getInt(RESULT_KEY);

        textViewResult.setText(result + "/" + fullMark);

        if (result >= ((float)fullMark/2))
        {
            textViewResultSuccessFailed.setText(R.string.success);
            setTheColor(textViewResult, textViewResultSuccessFailed,R.color.colorGreen);
        }
        else
        {
            textViewResultSuccessFailed.setText(R.string.failed);
            setTheColor(textViewResult, textViewResultSuccessFailed,R.color.colorRed);
        }


        return view;
    }

    private void setTheColor(TextView textViewResult, TextView textViewResultSuccessFailed,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewResultSuccessFailed.setTextColor(getResources().getColor(color,null));
            textViewResult.setTextColor(getResources().getColor(color,null));
        }else{
            textViewResultSuccessFailed.setTextColor(getResources().getColor(color));
            textViewResult.setTextColor(getResources().getColor(color));
        }
    }
}
