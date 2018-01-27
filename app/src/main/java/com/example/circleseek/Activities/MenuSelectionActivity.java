package com.example.circleseek.Activities;
//package com.google.android.gms:play-services:6.5.87;
//import com.google.android.gms.games.leaderboard;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.guessursongs.R;

//import com.google.example.games.basegameutils.BaseGameActivity;
//import com.google.example.games.basegameutils.BaseGameUtils;

public class MenuSelectionActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //Boolean x_isInternetPresent = false;
    //ConnectionDetector x_cd;
    public GoogleApiClient mGoogleApiClient;
    private int x = 0;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    private ImageButton challenge_button;
    private static int testing = 5;
    ProgressDialog progress;
    Editor edit;
    SharedPreferences scount;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selection);
        scount = this.getSharedPreferences("count", Context.MODE_PRIVATE);
        testing = scount.getInt("testing", 5);
        if (testing == 5) {
            Editor edit;
            edit = scount.edit();
            edit.putInt("testing", 0);
            edit.commit();
        } else {

            //getGameHelper().setMaxAutoSignInAttempts(0);
            //Toast.makeText(MenuSelectionActivity.this, "testing in condition "+testing, Toast.LENGTH_SHORT).show();
            edit = scount.edit();
            edit.putInt("testing", testing + 1);
            edit.commit();

            //Toast.makeText(MenuSelectionActivity.this, "testing outside if "+testing, Toast.LENGTH_SHORT).show();

        }


        //progress = new ProgressDialog(this);

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();


        // Font path
        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";


        // Loading Font Face
        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);

        // text view label
        TextView heading = (TextView) findViewById(R.id.heading);

        // Applying font
        heading.setTypeface(tf_bold);


        TextView timed_text = (TextView) findViewById(R.id.timed_text);
        timed_text.setTypeface(tf_bold);

        TextView endless_text = (TextView) findViewById(R.id.endless_text);
        endless_text.setTypeface(tf_bold);

        TextView sequence_text = (TextView) findViewById(R.id.sequence_text);
        sequence_text.setTypeface(tf_bold);

        TextView leaderboard_text = (TextView) findViewById(R.id.leaderboard_text);
        leaderboard_text.setTypeface(tf_bold);


        //	memory_button.setOnClickListener(this);
        //countDownTimer = new MalibuCountDownTimer(startTime, interval);
        //text.setText(text.getText() + String.valueOf(startTime));
        ImageButton practice_button = (ImageButton) this.findViewById(R.id.practice_button);
        Animation ranim = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        practice_button.setAnimation(ranim);


        // advertisement ki code here.......
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // End of advertisements ki code here....

        challenge_button = (ImageButton) this.findViewById(R.id.challenge_button);


        //Toast.makeText(MenuSelectionActivity.this, "check it"+String.valueOf(x), Toast.LENGTH_SHORT).show();

        Animation ranim_challenge = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        challenge_button.setAnimation(ranim_challenge);

        //challenge_button.setOnClickListener(this);

			/*	challenge_button.setOnTouchListener(new OnTouchListener()
                {

		           
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
		                    { 	Log.d("Pressed", "Button pressed");
		                    	challenge_button.setBackgroundResource(R.drawable.timed_onclick);
		                    }
		                else if (event.getAction() == MotionEvent.ACTION_UP)
		                {
		                 Log.d("Released", "Button released");
		                 challenge_button.setBackgroundResource(R.drawable.timed_before_onclick);
		                }
		               
						// TODO Auto-generated method stub
		                return false;
					}
		        });
				
				*/
        ImageButton memory_button = (ImageButton) this.findViewById(R.id.memory_button);
        Animation ranim_memory = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        memory_button.setAnimation(ranim_memory);


        ImageButton leaderboard_button = (ImageButton) this.findViewById(R.id.leaderboard_button);
        Animation ranim_leaderboard = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        leaderboard_button.setAnimation(ranim_leaderboard);

        //	x_cd = new ConnectionDetector(getApplicationContext());
        //   x_isInternetPresent = x_cd.isConnectingToInternet();


        practice_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                // MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                //  mp.start();
                        /* progress.setTitle("Loading");
		                 progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		                 progress.setMessage("Getting songs...");
		                 progress.setIndeterminate(false);
		                 progress.setProgress(100);
		                 progress.show();
		                */
                final ProgressDialog ringProgressDialog = ProgressDialog.show(MenuSelectionActivity.this, "", "Getting songs...", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Here you should write your time consuming task...
                            // Let the progress ring for 10 seconds...
                            Thread.sleep(10000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();

                Intent i = new Intent(getApplicationContext(), PracticeActivity.class);
                //    i.putExtra("id", position);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });


        challenge_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                //challenge_button.animate().x(0).y(1);
                // challenge_button.animate().x(0).y(0);
                //challenge_button.animate().alpha(0);
                Intent i = new Intent(getApplicationContext(), TimedActivity.class);
                //  i.putExtra("id", position);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //  challenge_button.animate().alpha(1);

            }
        });

        memory_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);
                //    i.putExtra("id", position);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        leaderboard_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                // if (x_isInternetPresent)
                // {
                // do you work here
                // get Connectivity Manager object to check connection
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    //Toast.makeText(MenuSelectionActivity.this, "dudee internet connected dudee", Toast.LENGTH_SHORT).show();
                    // if connected with internet

                    // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                    if (mGoogleApiClient.isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                                "CgkIlai1sPwaEAIQAQ"), 2);
                    } else {
                        Toast.makeText(MenuSelectionActivity.this, "Unable to sign in", Toast.LENGTH_SHORT).show();
                        //connect to internet to check leaderboard
                    }

                } else {
                    Toast.makeText(MenuSelectionActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                }


                //   } else {
                //  	Toast.makeText(MenuSelectionActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                // no internet,please try again.

                //   }


            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }


    /*@Override
    public void onSignInFailed() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSignInSucceeded() {
        // TODO Auto-generated method stub
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        //findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }*/


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        //Toast.makeText(MenuSelectionActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        // Attempt to reconnect
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStart() {
        // x = 22;

        super.onStart();
        //testing = 2;
        mGoogleApiClient.connect();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        // getGameHelper().setMaxAutoSignInAttempts(2);
        //Toast.makeText(MenuSelectionActivity.this, "onstart", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart() {
        // x = 22;
        mAutoStartSignInflow = true;
        super.onRestart();

        //Toast.makeText(MenuSelectionActivity.this, "onrestart"+testing, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        // x = 22;


        super.onResume();
        //testing = 2;
        mAutoStartSignInflow = true;
        //Toast.makeText(MenuSelectionActivity.this, "onresumee", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."


                //BaseGameUtils.showActivityResultError(this,
                //  requestCode, resultCode, R.string.signin_failure);
            }
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}