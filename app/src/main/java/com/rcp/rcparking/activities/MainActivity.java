package com.rcp.rcparking.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rcp.rcparking.R;
import com.rcp.rcparking.Travel;
import com.rcp.rcparking.customViews.JoystickView;
import com.rcp.rcparking.fragments.AuthenticationFragment;
import com.rcp.rcparking.fragments.EndValetFragment;
import com.rcp.rcparking.fragments.JoystickModeFragment;
import com.rcp.rcparking.fragments.SelectAddressFragment;
import com.rcp.rcparking.fragments.SelectCarFragment;
import com.rcp.rcparking.fragments.SelectCarParametersFragment;
import com.rcp.rcparking.fragments.SelectModeFragment;
import com.rcp.rcparking.fragments.SelectUserFragment;
import com.rcp.rcparking.fragments.TCPParemetersFragment;
import com.rcp.rcparking.fragments.TravelInfoFragment;
import com.rcp.rcparking.tcp.TcpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

public class MainActivity extends AppCompatActivity {

    public static String ip;
    public static int port;
    public static String CAR_MODE;
    public static String CAR_TEMP;

    public static int totalTime;
    public static int takenTimeTravel;
    public static int chauffe = 300;

    private TextView titleFragmentMain;

    TcpClient mTcpClient;

    Fragment holdFragment = null;
    Fragment currentFragment = null;
    JSONObject pathGoogleMap;

    public static ImageView imgConnexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        titleFragmentMain = (TextView) findViewById(R.id.titleFragmentMain);
        imgConnexion = (ImageView) findViewById(R.id.imgConnexion);


        SelectUserFragment selectUserFragment = new SelectUserFragment();

        //TCPParemetersFragment tcpParemetersFragment = new TCPParemetersFragment();

        //launchTCPConnexion();

        //System.out.println("iiiiiii " + getIpAddr());
        //System.out.println("tetete" + getWifiApIpAddress());

        titleFragmentMain.setText("User \nSelection");

        //getSupportFragmentManager().beginTransaction()
          //      .add(R.id.fragment_container, selectUserFragment).commit();

        replaceFragmentWithAnimation(selectUserFragment,"selectUserFragment");



    }

    public void launchTCPConnexion(){
        System.out.println("launchTCPConnexion");
        new ConnectTask().execute("");

       // imgConnexion.setImageResource(R.drawable.circle_connexion_ok);

    }





    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {



            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            },imgConnexion);
            mTcpClient.run();


            return null;
        }



        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

            System.out.println("tttttttttt "+ values[0]);


            if (values[0].equals("car_ready")){
                createNotification("Car ready !");
            }


        }



    }

    public static void disconnet(){


        System.out.println("connexionChanged");
        imgConnexion.setImageResource(R.drawable.circle_connexion);



    }

    public void sendMessage(String angle, String power){
        //DecimalFormat df = new DecimalFormat("#.###");
        //df.setRoundingMode(RoundingMode.CEILING);

        //System.out.println("bob");

        if (mTcpClient != null) {
            long time= System.currentTimeMillis();
            //android.util.Log.i("bob", " Time value in millisecinds "+time);
            mTcpClient.sendMessage("angle : " + angle + " power : " + power);

        }
    }

    public void sendModeMessage(String mode){
        if (mTcpClient != null) {
            mTcpClient.sendMessage("mode : " + mode);
        }
    }

    public void sendTramParameters(String mode){
        if (mTcpClient != null) {
            mTcpClient.sendMessage(mode);
        }
    }

    public void replaceFragmentWithAnimation(android.support.v4.app.Fragment fragment, String tag) {




        holdFragment = getVisibleFragment();

        System.out.println("holdFragment" + getVisibleFragment());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
        currentFragment = fragment;
        System.out.println("currentFragment " + currentFragment);
        titleFragmentMain.setText(setTitleMain(fragment));

    }

    public void replaceBackFragmentWithAnimation(android.support.v4.app.Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
        currentFragment = fragment;
        System.out.println("currentFragment " + currentFragment);
        titleFragmentMain.setText(setTitleMain(fragment));

    }

    public String setTitleMain(android.support.v4.app.Fragment fragment){

        String title = null;

        if (fragment instanceof SelectUserFragment){
            title = "User \nSelection";
            System.out.println("testestestest " + "SelectUserFragment");
        }

        else if (fragment instanceof AuthenticationFragment){
            title = "User \nAuthentification";
            System.out.println("testestestest " + "AuthenticationFragment");
        }

        else if (fragment instanceof SelectModeFragment){
            title = "Parking \nControl";
            System.out.println("testestestest " + "SelectModeFragment");
        }

        else if (fragment instanceof SelectAddressFragment){
            title = "Travel \nConfiguration";
            System.out.println("testestestest " + "SelectAddressFragment");
        }

        else if (fragment instanceof TravelInfoFragment){
            title = "Travel \nInformation";
            System.out.println("testestestest " + "TravelInfoFragment");
        }

        else if (fragment instanceof SelectCarFragment){
            title = "Car \nSelection";
            System.out.println("testestestest " + "SelectCarFragment");
        }

        else if (fragment instanceof SelectCarParametersFragment){
            title = "Car \nSet-Up";
            System.out.println("testestestest " + "SelectCarFragment");
        }

        else if (fragment instanceof EndValetFragment){
            title = "Valet \nParking";
            System.out.println("testestestest " + "SelectCarFragment");
        }

        else if (fragment instanceof JoystickModeFragment){
            title = "Joystick \nMode";
            System.out.println("testestestest " + "SelectCarFragment");
        }

        return title;

    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void HIDE_KEYBOARD(Activity activity){
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public void getPath(final ProgressBar progressBar, Double lat_dep, Double lng_dep, Double lat_arr, Double lng_arr, int type_path) throws URISyntaxException, JSONException {

        progressBar.setVisibility(View.VISIBLE);

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(getResources().getString(R.string.IP_LOCAL))
                .setPort(getResources().getInteger(R.integer.PORT_HTTP))
                .setPath("/api/0.1/path")
                .setParameter("lat_dep", String.valueOf(lat_dep))
                .setParameter("lon_dep", String.valueOf(lng_dep))
                .setParameter("lat_arr", String.valueOf(lat_arr))
                .setParameter("lon_arr", String.valueOf(lng_arr))
                .setParameter("type_path", String.valueOf(type_path))
                .build();

        System.out.println("URI BUILDER NEW: " + uri);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.valueOf(uri), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("STATUS CODE : " + statusCode);
                try {
                    String str = new String(responseBody, "UTF-8");
                    System.out.println("responseBody" + str);

                    JSONObject jsonObject = new JSONObject(str);
                    pathGoogleMap = jsonObject;

                    System.out.println("jsonObject " + jsonObject);
                    System.out.println("jsonObject2 " + pathGoogleMap);



                    Double length = jsonObject.getJSONObject("properties").getDouble("length");
                    Double time = jsonObject.getJSONObject("properties").getDouble("time");

                    progressBar.setVisibility(View.INVISIBLE);

                    Travel travel = new Travel(length,time);

                    TravelInfoFragment travelInfoFragment = new TravelInfoFragment();
                    replaceFragmentWithAnimation(travelInfoFragment,"travelInfoFragment");


                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("ERROR HTTP : ", String.valueOf(statusCode));
            }

        });

    }


    public JSONObject getPathGoogle(){
        return pathGoogleMap;
    }

    private final void createNotification(String message){

        Intent intent = new Intent(this, MainActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Altran Parking Control")
                .setContentText("Alert : " + message)
                .setSmallIcon(R.drawable.ic_notif_motor)
                .setContentIntent(pIntent)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }




    /*
    @Override
    public void onBackPressed(){
        replaceBackFragmentWithAnimation(holdFragment,"");
    }
    */


    public void getBounds(final GoogleMap googleMap) throws URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(getResources().getString(R.string.IP_LOCAL))
                .setPort(getResources().getInteger(R.integer.PORT_HTTP))
                .setPath("/api/0.1/bbox")
                .build();

        System.out.println("URI BUILDER NEW: " + uri);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.valueOf(uri), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("STATUS CODE : " + statusCode);
                try {
                    String str = new String(responseBody, "UTF-8");
                    System.out.println("responseBody" + str);

                    JSONObject jsonObject = new JSONObject(str);


                    PolygonOptions polygonOptions =  new PolygonOptions()

                            .add(new LatLng(jsonObject.getDouble("minLat"),jsonObject.getDouble("minLon")))
                            .add(new LatLng(jsonObject.getDouble("maxLat"),jsonObject.getDouble("minLon")))
                            .add(new LatLng(jsonObject.getDouble("maxLat"),jsonObject.getDouble("maxLon")))
                            .add(new LatLng(jsonObject.getDouble("minLat"),jsonObject.getDouble("maxLon")))

                            .strokeColor(Color.TRANSPARENT)
                            ;

                    googleMap.addPolygon(polygonOptions);


                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng latLng : polygonOptions.getPoints()) {
                        builder.include(latLng);
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 100; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    googleMap.moveCamera(cu);
                    googleMap.animateCamera(cu);



                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("ERROR HTTP : ", String.valueOf(statusCode));
            }

        });

    }


}
