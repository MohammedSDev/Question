package com.capps.question.Question;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 29/3/17.
 */

public class DetailsFrag extends Fragment {

    final static String QUESTION_KEY="question";
    final static String QUESTION_ID_KEY="Qid";
    private ListMultiEditViewsFrag listMultiEditViewsFrag;
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
            listMultiEditViewsFrag =new ListMultiEditViewsFrag();

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

        changeFragment(listMultiEditViewsFrag,mAnswers.length,mAnswers);
    }

    private void changeFragment(ListMultiEditViewsFrag frag, int editTextCount, Answer [] answers) {
        FragmentManager manager = getChildFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ListMultiEditViewsFrag.EDITTEXT_COUNT_KEY,editTextCount);
        bundle.putSerializable(ListMultiEditViewsFrag.ANSWER_DATA_KEY,answers);//TODO:: Should throw Exception..because it's not implement Serializable
        bundle.putBoolean(ListMultiEditViewsFrag.mNotAllowUserToInbut,true);
        bundle.putBoolean(ListMultiEditViewsFrag.mIsShowAnswers,true);
        frag.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentAnswer,frag);
        transaction.commit();
    }
}
