package com.example.circleseek.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public class HomeActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
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
    private Context mContext;
    private ImageButton practice_button;
    private AdView mAdView;
    private ImageButton memory_button;
    private ImageButton leaderboard_button;
    private TextView heading;
    private TextView timed_text;
    private TextView endless_text;
    private TextView sequence_text;
    private TextView leaderboard_text;
    String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"};
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        scount = this.getSharedPreferences("count", Context.MODE_PRIVATE);
        testing = scount.getInt("testing", 5);
        if (testing == 5) {
            Editor edit;
            edit = scount.edit();
            edit.putInt("testing", 0);
            edit.commit();
        } else {
            edit = scount.edit();
            edit.putInt("testing", testing + 1);
            edit.commit();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        init();
        setFonts();
        setAnimations();
        onClicks();
        loadAds();

        addRunTimePermission();
    }

    private void addRunTimePermission() {
        if (!checkPermission()) {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{perms[0], perms[1]}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean isReadExternalStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean isReadPhoneStateAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (isReadExternalStorageAccepted && isReadPhoneStateAccepted) {
                        Toast.makeText(mContext, "All permissions accepted", Toast.LENGTH_SHORT).show();
                    } else {
                        /*if (shouldShowRequestPermissionRationale(perms[0])) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        }
                                    });
                            return;
                        }*/
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{perms[0], perms[1]},
                                    PERMISSION_REQUEST_CODE);
                        }
                    }
                }
                break;
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);

            /*if (result != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(perms[0])){

                }
            }

            if(result1 != PackageManager.PERMISSION_GRANTED){

            }*/

            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void loadAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void onClicks() {
        practice_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(mContext, "", "Getting songs...", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();

                Intent i = new Intent(getApplicationContext(), PracticeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });

        challenge_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                Intent i = new Intent(getApplicationContext(), TimedActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        memory_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        leaderboard_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    // if connected with internet
                    if (mGoogleApiClient.isConnected()) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                                "CgkIlai1sPwaEAIQAQ"), 2);
                    } else {
                        Toast.makeText(mContext, "Unable to sign in", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAnimations() {
        Animation ranim = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        practice_button.setAnimation(ranim);

        Animation ranim_challenge = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        challenge_button.setAnimation(ranim_challenge);

        Animation ranim_memory = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        memory_button.setAnimation(ranim_memory);

        Animation ranim_leaderboard = (Animation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        leaderboard_button.setAnimation(ranim_leaderboard);
    }

    private void setFonts() {
        // Font path
        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";

        // Loading Font Face
        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);

        // Applying font
        heading.setTypeface(tf_bold);
        timed_text.setTypeface(tf_bold);
        endless_text.setTypeface(tf_bold);
        sequence_text.setTypeface(tf_bold);
        leaderboard_text.setTypeface(tf_bold);
    }

    private void init() {

        practice_button = (ImageButton) this.findViewById(R.id.practice_button);
        challenge_button = (ImageButton) this.findViewById(R.id.challenge_button);
        mAdView = (AdView) findViewById(R.id.adView);
        memory_button = (ImageButton) this.findViewById(R.id.memory_button);
        leaderboard_button = (ImageButton) this.findViewById(R.id.leaderboard_button);
        heading = (TextView) findViewById(R.id.heading);
        timed_text = (TextView) findViewById(R.id.timed_text);
        endless_text = (TextView) findViewById(R.id.endless_text);
        sequence_text = (TextView) findViewById(R.id.sequence_text);
        leaderboard_text = (TextView) findViewById(R.id.leaderboard_text);
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(mContext, "Connection failed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnected(Bundle arg0) {
        Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
    }


    @Override
    protected void onRestart() {
        mAutoStartSignInflow = true;
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoStartSignInflow = true;
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
            }
        }
    }
}