package com.rcp.rcparking.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rcp.rcparking.R;
import com.rcp.rcparking.Travel;
import com.rcp.rcparking.fragments.SelectUserFragment;
import com.rcp.rcparking.fragments.TCPParemetersFragment;
import com.rcp.rcparking.fragments.TravelInfoFragment;
import com.rcp.rcparking.tcp.TcpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

public class MainActivity extends AppCompatActivity {

    public static String ip;
    public static int port;

    public static int totalTime;
    public static int takenTimeTravel;
    public static int chauffe = 300;

    TcpClient mTcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectUserFragment selectUserFragment = new SelectUserFragment();

        //TCPParemetersFragment tcpParemetersFragment = new TCPParemetersFragment();

        //launchTCPConnexion();


        //System.out.println("iiiiiii " + getIpAddr());
        //System.out.println("tetete" + getWifiApIpAddress());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, selectUserFragment).commit();
        createNotification();
    }

    public void launchTCPConnexion(){
        System.out.println("launchTCPConnexion");
        new ConnectTask().execute("");
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
            });
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

        }

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

    public void replaceFragmentWithAnimation(android.support.v4.app.Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void HIDE_KEYBOARD(Activity activity){
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public void getPath(final ProgressBar progressBar, Double lat_dep, Double lng_dep, Double lat_arr, Double lng_arr, int type_path) throws URISyntaxException, JSONException {

        progressBar.setVisibility(View.VISIBLE);

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("192.168.137.1")
                .setPort(8000)
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

                    Double length = jsonObject.getJSONObject("properties").getDouble("length");
                    Double time = jsonObject.getJSONObject("properties").getDouble("time");

                    progressBar.setVisibility(View.INVISIBLE);

                    Travel travel = new Travel(length,time);

                    TravelInfoFragment travelInfoFragment = new TravelInfoFragment();
                    replaceFragmentWithAnimation(travelInfoFragment,"");



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

    private final void createNotification(){

        Intent intent = new Intent(this, MainActivity.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Altran Parking Control")
                .setContentText("Alert : Engine Started")
                .setSmallIcon(R.drawable.ic_notif_motor)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
