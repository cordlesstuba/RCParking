package com.rcp.rcparking.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class SelectCarFragment extends Fragment implements View.OnClickListener{

    ImageView imgValide;

    LinearLayout userCar1, userCar2, userCar3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_car, null);

        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectCarParametersFragment selectCarParametersFragment = new SelectCarParametersFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectCarParametersFragment,"selectCarParametersFragment");

            }
        });

        userCar1 = (LinearLayout) view.findViewById(R.id.userCar1);
        userCar2 = (LinearLayout) view.findViewById(R.id.userCar2);
        userCar3 = (LinearLayout) view.findViewById(R.id.userCar3);

        userCar1.setOnClickListener(this);
        userCar2.setOnClickListener(this);
        userCar3.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.userCar1:
                userCar1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                userCar2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                userCar3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;

            case R.id.userCar2:
                userCar2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                userCar1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                userCar3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;

            case R.id.userCar3:
                userCar3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                userCar2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                userCar1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;

        }
    }
}