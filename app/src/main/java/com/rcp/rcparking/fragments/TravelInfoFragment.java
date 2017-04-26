package com.rcp.rcparking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcp.rcparking.R;
import com.rcp.rcparking.Travel;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class TravelInfoFragment extends Fragment{

    EditText txtViewLength, txtViewDuration;
    ImageView imgValide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_travel_info, null);

        txtViewLength = (EditText) view.findViewById(R.id.txtViewLength);
        txtViewDuration = (EditText) view.findViewById(R.id.txtViewDuration);

        txtViewLength.setFocusable(false);
        txtViewDuration.setFocusable(false);

        Double distance = Travel.getDistance();
        Double time = Travel.getDuration();

        Double hours = time / 3600;
        Double minutes = (time % 3600) / 60;
        Double seconds = time % 60;

        int intHour = hours.intValue();
        int intMin = minutes.intValue();
        int intSec = seconds.intValue();

        MainActivity.takenTimeTravel = time.intValue();


        String timeString = String.format("%02dh %02dmin %02ds", intHour, intMin, intSec);

        txtViewLength.setText(String.valueOf(distance) + " km");
        txtViewDuration.setText(String.valueOf(timeString));


        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCarFragment selectCarFragment = new SelectCarFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectCarFragment,"");
            }
        });


        return view;

    }

}
