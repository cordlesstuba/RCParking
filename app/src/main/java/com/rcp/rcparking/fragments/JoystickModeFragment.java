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
        View view = inflater.inflate(R.layout.fragment_joystick_mode, null);


        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        txtViewValue = (TextView) view.findViewById(R.id.txtViewValue);







        //joystick.setOnMoveListener(new JoystickView.OnMoveListener() { ... }, 17); // around 60/sec




        JoyStick joyStick = (JoyStick) view.findViewById(R.id.joy1);

        joyStick.setOnMoveListener(new JoyStick.JoyStickListener() {
            @Override
            public void onMove(double angle, double power, int direction) {

                /*
                String roundedAngle;

                if (angle<0){
                    DecimalFormat toTheFormat = new DecimalFormat("0.000");
                    roundedAngle = toTheFormat.format(angle);
                }else {
                    DecimalFormat toTheFormat = new DecimalFormat("0.0000");
                    roundedAngle = toTheFormat.format(angle);
                }

                Double d = new Double(power);
                int roundedPower = d.intValue();

                ((MainActivity)getActivity()).sendMessage(roundedAngle,roundedPower);

*/
            }

            @Override
            public void onTap() {

            }

            @Override
            public void onDoubleTap() {

            }
        },17);



        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtViewValue.setText(String.valueOf(progress));
                wait = progress;


                JoystickView joystick = (JoystickView) view.findViewById(R.id.jostickView);
                joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
                    @Override
                    public void onMove(int angle, int strength) {

                        System.out.println(progress);

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
