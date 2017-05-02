package com.rcp.rcparking.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;

/**
 * Created by gcarves on 13/04/2017.
 */

public class SelectCarParametersFragment extends Fragment implements View.OnClickListener{

    ImageView imgValide, imgViewPlus, imgViewMoins;
    TextView txtViewTempe;

    TextView txtViewMode1, txtViewMode2, txtViewMode3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_car_parameters, null);

        txtViewTempe = (TextView) view.findViewById(R.id.txtViewTempe);

        MainActivity.CAR_TEMP = String.valueOf("20.5");
        MainActivity.CAR_MODE = "1";

        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).sendTramParameters(MainActivity.CAR_MODE + MainActivity.CAR_TEMP);

                //SendMessage sendMessageCarParameters = new SendMessage();
                //sendMessageCarParameters.execute(MainActivity.CAR_MODE + MainActivity.CAR_TEMP);

                EndValetFragment endValetFragment = new EndValetFragment();
                ((MainActivity)getActivity()).replaceFragmentWithAnimation(endValetFragment,"endValetFragment");
            }
        });

        imgViewPlus = (ImageView) view.findViewById(R.id.imgViewPlus);
        imgViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double tempe = Double.parseDouble(txtViewTempe.getText().toString());
                if (tempe < 25){
                    double newTempe = tempe + 0.5;
                    txtViewTempe.setText(String.valueOf(newTempe));
                    MainActivity.CAR_TEMP = String.valueOf(newTempe);
                }
            }
        });

        imgViewMoins = (ImageView) view.findViewById(R.id.imgViewMoins);
        imgViewMoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double tempe = Double.parseDouble(txtViewTempe.getText().toString());

                if (tempe > 15){
                    double newTempe = tempe - 0.5;
                    txtViewTempe.setText(String.valueOf(newTempe));
                    MainActivity.CAR_TEMP = String.valueOf(newTempe);
                }

            }
        });

        txtViewMode1 = (TextView) view.findViewById(R.id.txtViewMode1);
        txtViewMode2 = (TextView) view.findViewById(R.id.txtViewMode2);
        txtViewMode3 = (TextView) view.findViewById(R.id.txtViewMode3);

        txtViewMode1.setOnClickListener(this);
        txtViewMode2.setOnClickListener(this);
        txtViewMode3.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.txtViewMode1:
                MainActivity.CAR_MODE = "1";
                txtViewMode1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                txtViewMode2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                txtViewMode3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;

            case R.id.txtViewMode2:
                MainActivity.CAR_MODE = "2";
                txtViewMode2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                txtViewMode1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                txtViewMode3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;

            case R.id.txtViewMode3:
                MainActivity.CAR_MODE = "3";
                txtViewMode3.setBackgroundColor(getResources().getColor(R.color.colorBackgroundModeSelected));
                txtViewMode2.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                txtViewMode1.setBackgroundColor(getResources().getColor(R.color.colorBackgroundUnselected));
                break;
        }
    }


    public class SendMessage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            ((MainActivity)getActivity()).sendTramParameters(params[0]);
            return null;
        }
    }
}
