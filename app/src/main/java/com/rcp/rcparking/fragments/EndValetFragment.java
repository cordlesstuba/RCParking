package com.rcp.rcparking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class EndValetFragment extends Fragment {

    EditText edtTxtEndTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_end_valet, null);

        edtTxtEndTime = (EditText) view.findViewById(R.id.edtTxtEndTime);
        edtTxtEndTime.setFocusable(false);

        int timeReady = MainActivity.totalTime - (MainActivity.takenTimeTravel+MainActivity.chauffe);


        int hours = timeReady / 3600;
        int minutes = (timeReady % 3600) / 60;
        int seconds = timeReady % 60;

        String timeString = String.format("%02dh %02dmin", hours, minutes);

        edtTxtEndTime.setText(timeString);

        return view;

    }
}
