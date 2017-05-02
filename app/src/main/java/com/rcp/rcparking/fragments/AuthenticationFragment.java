package com.rcp.rcparking.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rcp.rcparking.customViews.KeyboardView;
import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 12/04/2017.
 */

public class AuthenticationFragment extends Fragment {

    KeyboardView keyboardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication, null);

        keyboardView = (KeyboardView) view.findViewById(R.id.keyboard);

        keyboardView.txt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (keyboardView.txt4.getText().length()>0){

                    System.out.println("keyboardView : " + keyboardView.getInputText());

                    if ( keyboardView.getInputText().equals("1234")){
                        //Toast.makeText(getContext(),"Good pass",Toast.LENGTH_SHORT).show();

                        SelectModeFragment selectModeFragment = new SelectModeFragment();

                        ((MainActivity)getActivity()).launchTCPConnexion();
                        ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectModeFragment,"selectModeFragment");

                    }

                    else {
                        //Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();

                        keyboardView.changeColor(Color.RED);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                keyboardView.changeColor(getActivity().getResources().getColor(R.color.colorContourIconPerson));
                                keyboardView.clear();
                            }
                        }, 500);




                    }

                }
            }
        });

        return view;
    }

}