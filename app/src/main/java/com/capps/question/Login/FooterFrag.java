package com.capps.question.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capps.question.R;

/**
 * Created by varun on 24/3/17.
 */

public class FooterFrag extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_footer_frag,container,false);
        Button login = (Button) view.findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {


    }
}
