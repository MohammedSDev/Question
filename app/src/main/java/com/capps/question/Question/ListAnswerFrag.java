package com.capps.question.Question;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 28/3/17.
 */

public class ListAnswerFrag extends ListFragment {

    private final String EDITTEXT_COUNT_KEY="ETCount";
    private final String ANSWER_DATA_KEY="ANSWER_DATA";
    //static final String DONT_SHOW_CORRECT_ANSWER_KEY="SHOW_ANSWERS";//Don't show correct answers
    private QuestionAdapter mQuestionAdapter;
    private boolean mIsCreateFrag = false;
    private final String IS_CREATE_FRAG_KEY = "IS_CREATE";

    public ListAnswerFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*Steps
        * get number of EditText answers it want
        * get answers if it was(ex: DetailsSreen,showScreen,EditScreen...)
         * get answer = null if no answer is throw to here
         * put showAnswer = false if current fragment in QuestionActivity is ShowFrag
         * put noAllowUserToInPut opposite of showAnswer..because it is not allow in this constrictor except for showFrag
         * */


        if (savedInstanceState == null) {


            int count = getArguments().getInt(EDITTEXT_COUNT_KEY);
            Answer[] answers = (Answer[]) getArguments().getSerializable(ANSWER_DATA_KEY); //TODO:: Should Throw Exception..Beacuse it's not..Serializable
            if (answers != null) {
            /*showScreen
            * detailsScreen
            * editScreen..not created yet...and also it has different values in show corect and not allow input..be careful */
                mQuestionAdapter = new QuestionAdapter(count, answers, getActivity());
            } else {
            /*createScreen */
                mIsCreateFrag = true;
                mQuestionAdapter = new QuestionAdapter(count, getActivity());
            }



        }
        else
        {
            mIsCreateFrag = savedInstanceState.getBoolean(IS_CREATE_FRAG_KEY,false);
            Answer [] answers = (Answer[]) savedInstanceState.get(ANSWER_DATA_KEY);
            if (answers != null) {
            /*showScreen
            * detailsScreen
            * createScreen..!!!!!.Because user may enter some answers..so answers it wull not null.
            * editScreen..not created yet...and also it has different values in show corect and not allow input..be careful */

                int count = answers.length;
                mQuestionAdapter = new QuestionAdapter(count, answers, getActivity());
                if (mIsCreateFrag)
                    mQuestionAdapter.setNotAllowUserToInbut();

            } else {
            /*createScreen */
                mIsCreateFrag = true;
                mQuestionAdapter = new QuestionAdapter(1, getActivity());
            }



        }
        setListAdapter(mQuestionAdapter);

        View view =inflater.inflate(R.layout.list2,container,false);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /*Steps
        * getvalues
        * save it*/

        outState.putSerializable(ANSWER_DATA_KEY,mQuestionAdapter.getData());
        outState.putBoolean(IS_CREATE_FRAG_KEY,mIsCreateFrag);

    }
}
