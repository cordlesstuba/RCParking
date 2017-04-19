package com.rcp.rcparking.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.rcp.rcparking.Address;
import com.rcp.rcparking.Addresses;
import com.rcp.rcparking.PlacesDetails;
import com.rcp.rcparking.R;
import com.rcp.rcparking.activities.MainActivity;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by gcarves on 12/04/2017.
 */

public class SelectAddressFragment extends Fragment {

    ProgressBar progressBar;

    PlacesAutocompleteTextView autocompleteTxtViewDestination;

    ImageView imgValide;

    ImageView imgViewValidateDestination;

    EditText edtTxtTimeSelect;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_address, null);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        imgViewValidateDestination = (ImageView) view.findViewById(R.id.imgViewValidateDestination);
        imgViewValidateDestination.setVisibility(View.INVISIBLE);


        edtTxtTimeSelect = (EditText) view.findViewById(R.id.edtTxtTimeSelect);
        edtTxtTimeSelect.setFocusable(false);
        edtTxtTimeSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        DecimalFormat formatter = new DecimalFormat("00");

                        String sHour = formatter.format(selectedHour);
                        String sMinute = formatter.format(selectedMinute);

                        edtTxtTimeSelect.setText(sHour + ":" + sMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        autocompleteTxtViewDestination = (PlacesAutocompleteTextView) view.findViewById(R.id.autocompleteTxtViewDestination);
        autocompleteTxtViewDestination.setOnPlaceSelectedListener(
                place -> {

                    ((MainActivity)getActivity()).HIDE_KEYBOARD(getActivity());

                    PlacesDetails placesDetails = new PlacesDetails(place.place_id);
                    try {
                        placesDetails.loadDetails(imgViewValidateDestination);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                }

        );


        imgValide = (ImageView) view.findViewById(R.id.imgValide);
        imgValide.setOnClickListener(v -> {

            imgValide.setVisibility(View.INVISIBLE);

            try {
                ((MainActivity)getActivity()).getPath(progressBar,
                        Addresses.getAddressDepart().getLatLng().latitude,
                        Addresses.getAddressDepart().getLatLng().longitude,
                        Addresses.getAddressArrivee().getLatLng().latitude,
                        Addresses.getAddressArrivee().getLatLng().longitude,
                        0);
            } catch (URISyntaxException | JSONException e) {
                e.printStackTrace();
            }

        });

        return view;

    }

}
