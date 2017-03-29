package com.capps.question.Question;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capps.question.Answer;
import com.capps.question.R;

/**
 * Created by varun on 29/3/17.
 */

public class ShowFrag extends Fragment implements View.OnClickListener {

    private static short POINTS = 0;
    final static String QUESTION_KEY="question";
    final static String QUESTION_ID_KEY="Qid";
    private ListAnswerFrag mListAnswerFrag;
    private Answer []mAnswers;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (! (context instanceof ShowInterface))
            throw new ClassCastException("Host( " + context.toString() + " ) Activity Should Implement ShowInterface");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (! (activity instanceof ShowInterface))
            throw new ClassCastException("Host( " + activity.toString() + " ) Activity Should Implement ShowInterface");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_detail_frag,container,false);
        /*Steps
        * getArguments
        * get question&questionID from argument if Arguments not null
        * get question&questionID from DB if Argument is null
        * set Question on TextVew
        * include List os answers*/
        Bundle bundle = getArguments();

        TextView question = (TextView) view.findViewById(R.id.textViewQuestion);
        Button buttonNextQuestion = (Button) view.findViewById(R.id.buttonNextQuestion);
        buttonNextQuestion.setVisibility(View.VISIBLE);//Because this is Invisible in it is layout.and only used here.

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
            Question firstQuestion =Question.firstQuestion(getActivity());
            //put Question
            question.setText(firstQuestion.getmQuestion());
            //put answers
            mAnswers = Answer.getAllAnswers(getActivity(),firstQuestion.getmId());
            mListAnswerFrag =new ListAnswerFrag();
            //put users



        }



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        includeFragment(mListAnswerFrag,mAnswers.length,mAnswers);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO:: save Posint Value
        // TODO: 29/3/17 check: is this method call when app go to backstatce ground
    }



    private void includeFragment(ListAnswerFrag frag, int editTextCount, Answer [] answers) {
        FragmentManager manager = getChildFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ListAnswerFrag.EDITTEXT_COUNT_KEY,editTextCount);
        bundle.putSerializable(ListAnswerFrag.ANSWER_DATA_KEY,answers);//TODO:: Should throw Exception..because it's not implement Serializable
        frag.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentAnswer,frag);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

        /*Steps
        * check is at less one answer is checked
        * add one point if correct
        * next Question*/


        Answer []userAnswers = mListAnswerFrag.mQuestionAdapter.getData();
        if (!isUserChoiseAnyAnswer(userAnswers)){
            Toast.makeText(getActivity(),R.string.choiceAtLessOneAnswer,Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            POINTS = getPoint(userAnswers);
            nextQuesion();
        }




    }

    private boolean isUserChoiseAnyAnswer(Answer []answers ){

        for (Answer a : answers) {
            if(a.isCurrect())
                return true; //TODO:: check is break loop.
        }
        return false;
    }

    private short getPoint(Answer []answer){
        boolean result=true;

        for (short i=0;i<mAnswers.length;i++){
            if (mAnswers[i].isCurrect() != answer[i].isCurrect()){
                result= false;
                break;
            }
        }

        if (result)
            return 1;
        else
            return 0;
    }

    private void nextQuesion(){

        Question question = new Question(getActivity());
        question.setmId(getArguments().getInt(QUESTION_ID_KEY));
        question = question.nextQuestion();
        ShowInterface host = (ShowInterface) getActivity();
        host.moveToNextQuestionFrag(question);
    }




    interface ShowInterface{
        void moveToNextQuestionFrag(Question question);
    }


}
