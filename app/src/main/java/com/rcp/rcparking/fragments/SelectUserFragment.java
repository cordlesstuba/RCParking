package com.rcp.rcparking.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 12/04/2017.
 */

public class SelectUserFragment extends Fragment implements View.OnClickListener {

    LinearLayout line1, line2, line3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_user, null);


        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/seguisb.ttf");

        line1 = (LinearLayout) view.findViewById(R.id.userLine1);
        line1.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.userLine1:
                AuthenticationFragment authenticationFragment = new AuthenticationFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(authenticationFragment,"authenticationFragment");
                break;

        }

    }
}
