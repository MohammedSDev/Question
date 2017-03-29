package com.capps.question.Question;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.capps.question.Answer;
import com.capps.question.AppDataBase;
import com.capps.question.R;

/**
 * Created by varun on 29/3/17.
 */

public class DetailsFrag extends Fragment {

    final static String QUESTION_KEY="question";
    final static String QUESTION_ID_KEY="Qid";
    private ListAnswerFrag mListAnswerFrag;
    private Answer []mAnswers;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_detail_frag,container,false);
        Bundle bundle = getArguments();

        TextView question = (TextView) view.findViewById(R.id.textViewQuestion);
        if (bundle != null){
            //put Question
            question.setText(bundle.getString(QUESTION_KEY));
            //put answers
            mAnswers = Answer.getAllAnswers(getActivity(),bundle.getInt(QUESTION_ID_KEY));
            mListAnswerFrag =new ListAnswerFrag();

            //put users//TODO::
        }
        else
        {
            //put Question
            question.setText(R.string.noQuestion);
            //put answers
            //..nothing to do
            //put users
        }



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeFragment(mListAnswerFrag,mAnswers.length,mAnswers);
    }

    private void changeFragment(ListAnswerFrag frag, int editTextCount, Answer [] answers) {
        FragmentManager manager = getChildFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ListAnswerFrag.EDITTEXT_COUNT_KEY,editTextCount);
        bundle.putSerializable(ListAnswerFrag.ANSWER_DATA_KEY,answers);//TODO:: Should throw Exception..because it's not implement Serializable
        frag.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentAnswer,frag);
        transaction.commit();
    }
}
