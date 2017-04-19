package com.rcp.rcparking.fragments;

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

public class SelectModeFragment extends Fragment implements View.OnClickListener{

    LinearLayout linearLayoutRemoteCtrl, linearLayoutValetCtrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_mode, null);

        linearLayoutRemoteCtrl = (LinearLayout) view.findViewById(R.id.linearLayoutRemoteCtrl);
        linearLayoutValetCtrl = (LinearLayout) view.findViewById(R.id.linearLayoutValetCtrl);

        linearLayoutRemoteCtrl.setOnClickListener(this);
        linearLayoutValetCtrl.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        System.out.println("onClick");

        switch (v.getId()){

            case R.id.linearLayoutRemoteCtrl:

                JoystickModeFragment joystickModeFragment = new JoystickModeFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(joystickModeFragment,"");

                break;

            case R.id.linearLayoutValetCtrl:

                SelectAddressFragment selectAddressFragment = new SelectAddressFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectAddressFragment,"");

                break;

        }

    }
}