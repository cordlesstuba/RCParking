package com.rcp.rcparking.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.rcp.rcparking.R;

import static android.os.SystemClock.sleep;

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000000;

    ArcProgress arcProgress;

    TextView titleSplashScreen, poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/segoe_ui_light.ttf");

        titleSplashScreen = (TextView) findViewById(R.id.titleSplashScreen);
        poweredBy = (TextView) findViewById(R.id.poweredBy);

        titleSplashScreen.setTypeface(face);
        poweredBy.setTypeface(face);





        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);
        arcProgress.setStrokeWidth(20);

        new LoadViewTask().execute();

        /*

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                for (int i=0; i<=100;i++){
                    sleep(1000);
                    arcProgress.setProgress(i);

                }
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                //startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }
    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params)
        {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try
            {
                //Get the current thread's token
                synchronized (this)
                {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while(counter <= 100)
                    {
                        //Wait 850 milliseconds
                        this.wait(10);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter);
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            //set the current progress of the progress dialog
            arcProgress.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {

            arcProgress.setProgress(100);

            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            //close the progress dialog
            //arcProgress.dismiss();
            //initialize the View
            //setContentView(R.layout.main);
        }
    }
}