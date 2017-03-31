package com.capps.question.Question;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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


    static short POINTS = 0;//it's assigning to zero in QuestionActivity after login
    final static String QUESTION_KEY="question";
    final static String QUESTION_ID_KEY="Qid";
    private ListAnswerFrag mListAnswerFrag;
    private Answer []mAnswers;
    short question_id;


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
        buttonNextQuestion.setOnClickListener(this);

        if (bundle != null){

            //put Question
            question.setText(bundle.getString(QUESTION_KEY));
            //put answers
            question_id = (short) bundle.getInt(QUESTION_ID_KEY);
            mAnswers = Answer.getAllAnswers(getActivity(),question_id);
            mListAnswerFrag =new ListAnswerFrag();

            includeFragment(mListAnswerFrag,mAnswers.length,mAnswers.clone());

            //put users//TODO::
        }
        else
        {
            Question firstQuestion =Question.firstQuestion(getActivity());
            if (firstQuestion != null) {
                question_id = (short) firstQuestion.getmId();
                //put Question
                question.setText(firstQuestion.getmQuestion());
                //put answers
                mAnswers = Answer.getAllAnswers(getActivity(), firstQuestion.getmId());
                mListAnswerFrag = new ListAnswerFrag();

                includeFragment(mListAnswerFrag,mAnswers.length,mAnswers.clone());
                //put users
            }else{
                question.setText(R.string.emptyQuestionDB);
                buttonNextQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),R.string.emptyQuestionDB,Toast.LENGTH_SHORT).show();
                    }
                });


            }




        }



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        includeFragment(mListAnswerFrag,mAnswers.length,mAnswers.clone());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO:: save Posint Value
        // TODO: 29/3/17 check: is this method call when app go to backstatce ground
    }



    private void includeFragment(ListAnswerFrag frag, int editTextCount, Answer [] answers)  {

        FragmentManager manager = getChildFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ListAnswerFrag.EDITTEXT_COUNT_KEY,editTextCount);

        //create copy from answers and remove correct from it
        Answer[] answersClone = new Answer[answers.length];
        try {
            for (short i = 0; i < answers.length; i++){
                answersClone[i] = (Answer) answers[i].clone();
                answersClone[i].setCurrect(false);
            }
        }
        catch (CloneNotSupportedException er){Log.d("error","error clone answers on showfrag");}
        bundle.putSerializable(ListAnswerFrag.ANSWER_DATA_KEY,answersClone);//TODO:: Should throw Exception..because it's not implement Serializable
        frag.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentAnswer,frag);
        transaction.commit();
    }


    private void testCopy() {


       /*


        int[] bb = {1, 2}; // = new Answer[answers.length];

        List<Integer> aa = Arrays.asList(1,2);

//        aa.set(0,11);
        bb[0]=11;

        for (int x:bb ){
            System.out.println(x);
        }



        System.out.println("...................^^..");
        for (int z:aa) {
            System.out.println(z);
        }

        System.out.println("..................,");


        List nn = Arrays.asList(bb);

        bb[0]=111;


        System.out.println("...................;;..");
        for (Object z:nn) {
            System.out.println(z);
        }


        //try way

        int []i = {1,2,3};
        int []ii = new int[3];
        ii = i;

        i[0] = 11;

        Log.d("array","i Array");
        for (int z:i) {
            Log.d("array",z + "");
        }

        Log.d("array","ii Array");
        for (int z:ii) {
            Log.d("array",z + "");
        }
*/

       /* int[] x = {1,2,3, 4,5};
        int[] y = x.clone();

        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(x) + "\n");

        x[1] = 22;

        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(x) + "\n");*/

     //   System.out.println();

        //bb = Arrays.copyOf(answers, answers.length);

//        //System.arraycopy(answers,0,bb,0,answers.length);
//        int counter = 0;
//        for (Answer a:answers) {
//            try {
//                bb[counter]= (Answer) a.clone();
//                counter++;
//            }catch (CloneNotSupportedException er){
//                System.out.println("Crack");
//            }
//
//        }
//
//
//        //remove correct answer.
//        // new
//        for (short i=0;i<answers.length;i++){
//            answers[i].setCurrect(false);
//        }
//        answers = new Answer[10];
//        System.out.println(answers);
//        System.out.println(bb);
//        //End
    }

    @Override
    public void onClick(View v) {

        /*Steps
        * check is at less one answer is checked
        * add one point if correct
        * next Question*/


        Answer []userAnswers = mListAnswerFrag.mQuestionAdapter.getData();
        if (!isUserChooseAnyAnswer(userAnswers)){
            Toast.makeText(getActivity(),R.string.choiceAtLessOneAnswer,Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            POINTS += getPoint(userAnswers);
            nextQuesion();
        }




    }
    //no description more then it's name..
    private boolean isUserChooseAnyAnswer(Answer []answers ){

        for (Answer a : answers) {
            if(a.isCurrect())
                return true; //TODO:: check is break loop.
        }
        return false;
    }
    //check answer and return the point (0 or 1)
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
//    request host to change this fragment object to new one with next question
    private void nextQuesion(){

        Question question = new Question(getActivity());
        question.setmId(question_id);
        question = question.nextQuestion();
        ShowInterface host = (ShowInterface) getActivity();
        host.moveToNextQuestionFrag(question);
    }




    interface ShowInterface{
        void moveToNextQuestionFrag(Question question);
    }


}
