package com.rcp.rcparking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcp.rcparking.R;
import com.rcp.rcparking.Travel;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class TravelInfoFragment extends Fragment{

    TextView txtViewLength, txtViewDuration;
    ImageView imgValide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_travel_info, null);

        txtViewLength = (TextView) view.findViewById(R.id.txtViewLength);
        txtViewDuration = (TextView) view.findViewById(R.id.txtViewDuration);

        Double distance = Travel.getDistance();
        Double time = Travel.getDuration();

        txtViewLength.setText(String.valueOf(distance));
        txtViewDuration.setText(String.valueOf(time));


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
