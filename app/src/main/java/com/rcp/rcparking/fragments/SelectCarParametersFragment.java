package com.rcp.rcparking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class SelectCarParametersFragment extends Fragment {

    ImageView imgValide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_car_parameters, null);

        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EndValetFragment endValetFragment = new EndValetFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(endValetFragment,"");

            }
        });


        return view;

    }
}
