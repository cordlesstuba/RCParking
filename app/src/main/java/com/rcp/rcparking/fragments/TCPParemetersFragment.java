package com.rcp.rcparking.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class TCPParemetersFragment extends Fragment {

    EditText edtTxtIP, edtTxtPort;

    TextView txtViewValider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tcp_parameters, null);

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/segoe_ui_light.ttf");

        edtTxtIP = (EditText) view.findViewById(R.id.edtTxtIP);
        edtTxtPort = (EditText) view.findViewById(R.id.edtTxtPort);

        edtTxtPort.setTypeface(face);


        txtViewValider = (TextView) view.findViewById(R.id.txtViewValider);


        txtViewValider.setOnClickListener(v -> {

            MainActivity.ip = edtTxtIP.getText().toString();
            MainActivity.port = Integer.parseInt(edtTxtPort.getText().toString());

            SelectModeFragment selectModeFragment = new SelectModeFragment();
            ((MainActivity)getActivity()).launchTCPConnexion();
            ((MainActivity)getActivity()).replaceFragmentWithAnimation(selectModeFragment,"");

        });

        return view;
    }

}
