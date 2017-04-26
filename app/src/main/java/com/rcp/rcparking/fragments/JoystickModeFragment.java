package com.rcp.rcparking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rcp.rcparking.activities.SplashScreenActivity;
import com.rcp.rcparking.customViews.JoyStick;
import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;
import com.rcp.rcparking.customViews.JoystickView;

import java.text.DecimalFormat;

/**
 * Created by gcarves on 12/04/2017.
 */

public class JoystickModeFragment extends Fragment {

    SeekBar seekBar;
    TextView txtViewValue;

    int wait = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_joystick_mode, null);


        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        txtViewValue = (TextView) view.findViewById(R.id.txtViewValue);




        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                txtViewValue.setText(String.valueOf(progress));
                wait = progress;


                JoystickView joystick = (JoystickView) view.findViewById(R.id.jostickView);
                joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {

                        //System.out.println(progress);

                        String sAngle = String.format("%03d", angle);
                        String sStrength = String.format("%03d", strength);

                        ((MainActivity)getActivity()).sendMessage(sAngle,sStrength);

                    }
                }, progress);




            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}
