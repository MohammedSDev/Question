package com.capps.question;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.capps.question.Login.AdminContentFrag;
import com.capps.question.Login.EmployeeContentFrag;
import com.capps.question.Login.User;
import com.capps.question.Question.QuestionActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {


    private FragmentManager mManager;
    private EditText mPass,mEmail,mName;
    private String tagAdmin = "admin", tagEmployee = "employee";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Button login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rbgADminEmbloyee);
        radioGroup.setOnCheckedChangeListener(this);

        //set Default Content.. Admin Content
        setAdminContentFragment();

    }

    private void setAdminContentFragment() {

        mManager=getFragmentManager();

        if(mManager.findFragmentByTag(tagAdmin) ==null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.content, new AdminContentFrag(), tagAdmin);
            transaction.commit();
        }
    }

    private void setEmployeeContentFragment() {

        mManager=getFragmentManager();

        if (mManager.findFragmentByTag(tagEmployee) == null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.replace(R.id.content, new EmployeeContentFrag(), tagEmployee);
            transaction.commit();
        }
    }




    @Override
    public void onClick(View v) {
        //Login Button
        mManager=getFragmentManager();
        AppDataBase db = AppDataBase.getInstance(this);
        //if Admin Logged
        if(mManager.findFragmentByTag(tagAdmin)!= null){

            if (mPass == null) {
               mPass = (EditText) findViewById(R.id.editTextPassword);
            }


            if (mPass.getText() != null && db.checkAdminPass(mPass.getText().toString())){

                Intent questionIntent=new Intent(this, QuestionActivity.class);
                questionIntent.putExtra(QuestionActivity.IS_ADMIN_KEY,true);

                startActivity(questionIntent);
            }else
                Toast.makeText(this,R.string.errorPass,Toast.LENGTH_SHORT).show();

        }
        //Employee Logged
        else {

            if (mEmail == null) {
                mEmail = (EditText) findViewById(R.id.editTextEmail);
                mName = (EditText) findViewById(R.id.editTextName);
            }
            /*Steps
            * check Email Not Empty
            * check user is Exists
            * check name if he/she is new one
            * create user if he/she is new one
            * show message state to user(Success Log/Welcome User)
            * next ..Activity/Fragment*/

            if (mEmail.getText().toString().isEmpty() || mEmail.getText().toString().equals(" ")) {
                Toast.makeText(this, R.string.emailEmpty, Toast.LENGTH_LONG).show();
                return;
            }

            User user = User.isExists(this, mEmail.getText().toString());
            if (user != null) {
                welcomeEmployee(user);
                return;
            } else {
                if (mName.getText().toString().isEmpty() || mName.getText().toString().equals(" ")) {
                    Toast.makeText(this, R.string.emailName, Toast.LENGTH_LONG).show();
                    return;
                }
                user = new User(mEmail.getText().toString(), mName.getText().toString());
                long idRow = User.createUser(this, user);
                if (idRow > -1) {
                    welcomeEmployee(user);
                    return;
                }

            }

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rbAdmin:
                setAdminContentFragment();break;
            case R.id.rbEmbloyee:
                setEmployeeContentFragment();break;
        }
    }




    private void welcomeEmployee(User user){
        String welcome = getString(R.string.welcome);
        welcome += " " + user.getName();
        Toast.makeText(this,welcome,Toast.LENGTH_SHORT).show();

        Intent questionIntent=new Intent(this, QuestionActivity.class);
        questionIntent.putExtra(QuestionActivity.IS_ADMIN_KEY,false);
        startActivity(questionIntent);
    }

}
