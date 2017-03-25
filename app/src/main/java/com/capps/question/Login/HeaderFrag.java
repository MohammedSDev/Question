package com.capps.question.Login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class HeaderFrag extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private LoginInterface mHost;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginInterface)
            mHost = (LoginInterface) context;
        else
            throw new ClassCastException("Host Must Implements LoginInterface");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof LoginInterface)
            mHost = (LoginInterface) activity;
        else
            throw new ClassCastException("Host Must Implements LoginInterface");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_header_frag,container,false);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rbgADminEmbloyee);
        radioGroup.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbAdmin:
                mHost.changeContent(true);break;
            case R.id.rbEmbloyee:
                mHost.changeContent(false );break;
        }
    }


    public interface LoginInterface{
        void changeContent(boolean isAdmin);
    }
}
