package com.rcp.rcparking;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rcp.rcparking.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

/**
 * Created by gcarves on 12/04/2017.
 */

public class PlacesDetails {


    private static String PLACE_ID = null;
    static LatLng latLng = null;

    public PlacesDetails(String PLACE_ID) {
        PlacesDetails.PLACE_ID = PLACE_ID;
    }

    public LatLng loadDetails(final ImageView imageViewOk) throws URISyntaxException {

            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("maps.googleapis.com")
                    .setPath("/maps/api/place/details/json")
                    .setParameter("placeid",PLACE_ID)
                    .setParameter("key","AIzaSyAzFKnN_R1Sn2GLegJFcCO9QWIgBFFeUJ8")
                    .build();

            System.out.println("URI BUILDER NEW: " + uri);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(String.valueOf(uri), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("STATUS CODE : " + statusCode);
                    try {
                        String str = new String(responseBody, "UTF-8");


                        JSONObject reponse = new JSONObject(str);
                        Double lat = reponse.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        Double lng = reponse.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                        System.out.println("latlng " + lat + " " + lng);

                        latLng = new LatLng(lat,lng);

                        LatLng latLngDepart = new LatLng(45.0600981052287,7.655775882303715);

                        imageViewOk.setVisibility(View.VISIBLE);

                        Addresses.setAddressDepart("depart",latLngDepart);
                        Addresses.setAddressArrivee("arrivee",latLng);

                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("ERROR HTTP : ", String.valueOf(statusCode));
                }


            });

        return latLng;

    }


}
