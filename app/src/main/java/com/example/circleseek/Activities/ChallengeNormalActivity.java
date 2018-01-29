package com.example.circleseek.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.circleseek.Views.CircularProgressBar;
import com.example.circleseek.Views.CircularProgressBar.ProgressAnimationListener;
import com.example.circleseek.SongsManager;
import com.example.circleseek.Utils.Utilities;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.guessursongs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChallengeNormalActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    InterstitialAd mInterstitialAd;
    public static int count_status = 0;
    public MyCountDownTimer countDownTimer;
    public String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

    public Typeface tf_bold;

    public Vibrator v;
    public Animation anim_score;


    SharedPreferences cnormal_preference;

    public int best = 0;
    public int check_normal_onfinish = 0;
    public long seconds_normal = 0;
    public ImageButton btnPlay;
    public MediaPlayer mp;
    private Handler mHandler = new Handler();
    private SongsManager songManager;
    private Utilities utils;
    private int currentSongIndex = 0;
    public int x = 0;
    String variable = "ruthvik";
    public long startTime = 60000;
    public long interval = 1000;
    public CountDownTimer cntr_aCounter;
    public TextView textViewTime;
    public TextView score;
    public TextView score_current;
    public int ncorrect_normal = 0, nwrong = 0;

    private AudioRequestActivity ar;

    private Button mButton1, mButton2, mButton3, mButton4;

    private int variable_count = 0;
    private int song_time = 10000;
    private int song_interval = 1000;
    private int individual_score = 0;
    private int total_score = 0;
    public int cnormal_best_total_score = 0;
    public int songs_inbank = 0;

    public String option1 = null;
    public String option2 = null;
    public String option3 = null;
    public String option4 = null;

    public static String answer = null;

    private CircularProgressBar c1;

    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> songsListnew = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> options = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> map = new HashMap<String, String>();

    private Context mContext;

    public void force_stop() {
        countDownTimer.cancel();
        Intent i = new Intent(getApplicationContext(), ErrorCatchingActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContext = ChallengeNormalActivity.this;
        phonestatelisteners();

        setAds();

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();

        init();

        check_normal_onfinish = 0; // variable that checks whether onfinish is triggered or not

        cnormal_preference = this.getSharedPreferences("cnormal_mypref", Context.MODE_PRIVATE);

        cnormal_best_total_score = cnormal_preference.getInt("cnormal_best_total_score", 0); //0 is the default value.

        // Loading Font Face
        tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);

        initialize_player();

        countDownTimer = new MyCountDownTimer(startTime, interval);

        countDownTimer.start();

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        OnAudioFocusChangeListener afChangeListener = null;

        // Request audio focus for playback
        int result = am.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // other app had stopped playing song now , so u can do u stuff now .
        }
        function(song_time, song_interval, btnPlay);
    }

    private void init() {
        textViewTime = (TextView) findViewById(R.id.textView1);
        score = (TextView) findViewById(R.id.score);

        score.setText("0");
        score_current = (TextView) findViewById(R.id.score_current);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
    }

    private void setAds() {
        //interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2293516715213176/5262717248");
        requestNewInterstitial();
        // end of enterstitial ads
    }

    private void phonestatelisteners() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    variable_count = 1;
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), TimedActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    variable_count = 1;
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), TimedActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public void initialize_player() {
        mp = new MediaPlayer();
        songManager = new SongsManager();
        utils = new Utilities();
        songsList = songManager.getPlayList();
    }

    public void function(long time, long interval, final ImageButton btnPlay) {
        if (check_normal_onfinish == 0) {
            c1 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
            c1.progress_duration = 12000;
            c1.animateProgressTo(0, 100, new ProgressAnimationListener() {
                public void onAnimationStart() {

                }

                public void onAnimationProgress(int progress) {
                    c1.setTitle(progress + "%");
                }

                @Override
                public void onAnimationFinish() {
                }
            });

            Random randobj = new Random();
            songsListnew = new ArrayList<HashMap<String, String>>(songsList);
            songs_inbank = songsListnew.size();

            if (songs_inbank == 1) {
                force_stop();
            } else if (variable_count == 0) {
                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
                int FirstsongIndex = currentSongIndex;
                String songTitle = songsListnew.get(currentSongIndex).get("songTitle");
                songsListnew.remove(currentSongIndex);
                map = new HashMap<String, String>();
                map.put("constant", variable);
                map.put("songtitle", songTitle);
                options.add(map);
                answer = songTitle;
                playSong(FirstsongIndex);

                mp.seekTo(60000);

                final int t = 0;

                cntr_aCounter = new CountDownTimer(12000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        x = t + 1;
                        long millis = millisUntilFinished;
                        seconds_normal = millis / 1000;
                        if (seconds_normal > 8) {
                            individual_score = 30;

                        } else if (seconds_normal > 4) {
                            individual_score = 15;

                        } else {
                            individual_score = 10;
                        }
                    }

                    public void onFinish() {
                        mp.stop();
                        function(song_time, song_interval, btnPlay);
                    }
                };
                cntr_aCounter.start();

                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
                String songTitle1 = songsListnew.get(currentSongIndex).get("songTitle");
                songsListnew.remove(currentSongIndex);
                map = new HashMap<String, String>();
                map.put("constant", variable);
                map.put("songtitle", songTitle1);
                options.add(map);

                for (int i = 0; i < songsListnew.size(); i++) {
                    //Log.v("Data in songsListnew",""+songsListnew.get(i));

                }

                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
                String songTitle2 = songsListnew.get(currentSongIndex).get("songTitle");
                songsListnew.remove(currentSongIndex);
                map = new HashMap<String, String>();
                map.put("constant", variable);
                map.put("songtitle", songTitle2);
                options.add(map);

                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
                String songTitle3 = songsListnew.get(currentSongIndex).get("songTitle");
                songsListnew.remove(currentSongIndex);
                map = new HashMap<String, String>();
                map.put("constant", variable);
                map.put("songtitle", songTitle3);
                options.add(map);

                for (int i = 0; i < options.size(); i++) {
                    //Log.v("Data in options",""+options.get(i));

                }

                option_select(options);


                btnPlay.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // check for already playing
                        variable_count = 1;
                        countDownTimer.cancel();
                        cntr_aCounter.cancel();
                        mp.stop();
                        Intent i = new Intent(getApplicationContext(), TimedActivity.class);

                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
            }
        }
    }


    @Override
    public void onBackPressed() {
        // check for already playing
        variable_count = 1;
        countDownTimer.cancel();
        cntr_aCounter.cancel();
        mp.stop();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        Intent i = new Intent(getApplicationContext(), TimedActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }


    /**
     * Receiving song index from playlist view
     * and play the song
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            // play selected song
            playSong(currentSongIndex);
        }

    }

    /**
     * Function to play a song
     *
     * @param songIndex - index of song
     */
    public void playSong(int songIndex) {
        // Play song
        try {
            if (mp != null) {
                mp.reset();
                mp.setDataSource(songsList.get(songIndex).get("songPath"));
                mp.prepare();
                mp.start();
                btnPlay.setImageResource(R.drawable.btn_stop);

                updateProgressBar();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 10);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     */
    public void option_select(ArrayList<HashMap<String, String>> options) {
        for (int i = 0; i < options.size(); i++) {
            Log.v("Function data options", "" + options.get(i));

        }

        Log.v("Answer is ", "" + answer);

        Random randd = new Random();

        currentSongIndex = randd.nextInt(options.size());
        option1 = options.get(currentSongIndex).get("songtitle");
        option1 = option1.toUpperCase();
        Log.v("Option one is ", "" + option1);

        options.remove(currentSongIndex);


        currentSongIndex = randd.nextInt(options.size());
        option2 = options.get(currentSongIndex).get("songtitle");
        option2 = option2.toUpperCase();
        Log.v("Option two is ", "" + option2);
        options.remove(currentSongIndex);

        currentSongIndex = randd.nextInt(options.size());
        option3 = options.get(currentSongIndex).get("songtitle");
        option3 = option3.toUpperCase();
        Log.v("Option three is ", "" + option3);
        options.remove(currentSongIndex);

        currentSongIndex = randd.nextInt(options.size());
        option4 = options.get(currentSongIndex).get("songtitle");
        option4 = option4.toUpperCase();
        Log.v("Option four is ", "" + option4);
        options.remove(currentSongIndex);

        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);

        mButton1 = (Button) findViewById(R.id.imageButton1);
        mButton1.startAnimation(animAnticipateOvershoot_one);
        mButton1.setTypeface(tf_bold);
        mButton1.setText(option1);
        //mButton1.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked

        mButton2 = (Button) findViewById(R.id.imageButton2);
        mButton2.startAnimation(animAnticipateOvershoot_one);
        mButton2.setTypeface(tf_bold);
        //mButton2.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked
        mButton2.setText(option2);

        mButton3 = (Button) findViewById(R.id.imageButton3);
        mButton3.startAnimation(animAnticipateOvershoot_one);
        mButton3.setTypeface(tf_bold);
        //mButton3.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked
        mButton3.setText(option3);

        mButton4 = (Button) findViewById(R.id.imageButton4);
        mButton4.startAnimation(animAnticipateOvershoot_one);
        mButton4.setTypeface(tf_bold);
        //mButton4.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked
        mButton4.setText(option4);

        mButton1.setEnabled(true);
        mButton2.setEnabled(true);
        mButton3.setEnabled(true);
        mButton4.setEnabled(true);
        score_current.setVisibility(View.INVISIBLE);
        mButton1.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton2.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton3.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton4.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background

        button_onclick();


    }

    public void progressstop() {

        c1.animateProgressTo(0, 0, new ProgressAnimationListener() {
            public void onAnimationStart() {

            }

            public void onAnimationProgress(int progress) {
                c1.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //c1.setSubTitle("done");
            }
        });
        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {

                function(song_time, song_interval, btnPlay);// DO DELAYED STUFF
            }
        }, 1500);


    }


    public void button_onclick() {

        anim_score = AnimationUtils.loadAnimation(this, R.anim.anim_score);

        Log.v("check here", "option1 is:" + option1);
        Log.v("check here", "option2 is:" + option2);
        Log.v("check here", "option3 is:" + option3);
        Log.v("check here", "option4 is:" + option4);
        Log.v("check here", "Answer  is:" + answer);

        mButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option1)) {
                    mButton1.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background


                    //	total_score = total_score + individual_score;
                    Log.v("answewr clicked", ">8 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    //	total_score = total_score + individual_score;
                    Log.v("answewr clicked", ">4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    total_score = total_score + individual_score;
                    Log.v("answewr clicked", "<4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#008000"));
                    ncorrect_normal = ncorrect_normal + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    //	Toast.makeText(ChallengeNormalActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();
                    mp.stop();
                    cntr_aCounter.cancel();

                    progressstop();


                } else {
                    mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background

                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);

                    //	individual_score = -20;
                    total_score = total_score - 20;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -20);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#FF0000"));
                    //	Toast.makeText(ChallengeNormalActivity.this, "Wrong answer dude...", Toast.LENGTH_LONG).show();

                    mp.stop();

                    cntr_aCounter.cancel();
                    progressstop();


//            		function(song_time,song_interval,btnPlay);


                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option2)) {
                    mButton2.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background


                    //total_score = total_score + individual_score;
                    Log.v("option2 answewr clicked", ">8 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    //total_score = total_score + individual_score;
                    Log.v("option2 answewr clicked", ">4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    total_score = total_score + individual_score;
                    Log.v("option2 answewr clicked", "<4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#008000"));

                    ncorrect_normal = ncorrect_normal + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //	Toast.makeText(ChallengeNormalActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();
                    progressstop();
                } else {
                    mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background

                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v1.vibrate(150);
                    //individual_score = -20;
                    total_score = total_score - 20;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    mp.stop();
                    cntr_aCounter.cancel();
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -20);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#FF0000"));
                    progressstop();

                }
            }
        });


        mButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option3)) {
                    mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background

                    //total_score = total_score + individual_score;
                    Log.v("option3 answewr clicked", ">8 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    //total_score = total_score + individual_score;
                    Log.v("option3 answewr clicked", ">4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    total_score = total_score + individual_score;
                    Log.v("option3 answewr clicked", "<4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#008000"));
                    ncorrect_normal = ncorrect_normal + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();

                    progressstop();
                } else {
                    mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background

                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v1.vibrate(150);

                    total_score = total_score - 20;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -20);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#FF0000"));

                    progressstop();

                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option4)) {
                    mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background

                    //total_score = total_score + individual_score;
                    Log.v("option4 answewr clicked", ">8 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    //total_score = total_score + individual_score;
                    Log.v("option4 answewr clicked", ">4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    total_score = total_score + individual_score;
                    Log.v("option4 answewr clicked", "<4 seconds" + seconds_normal);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#008000"));
                    ncorrect_normal = ncorrect_normal + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //		Toast.makeText(ChallengeNormalActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();

                    progressstop();
                } else {
                    mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background

                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v1.vibrate(150);

                    //individual_score = -20;
                    total_score = total_score - 20;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -20);
                    score_current.startAnimation(anim_score);
                    score_current.setTextColor(Color.parseColor("#FF0000"));
                    mp.stop();
                    cntr_aCounter.cancel();
                    //  		Toast.makeText(ChallengeNormalActivity.this, "Wrong answer dude...", Toast.LENGTH_SHORT).show();


                    progressstop();

                }
            }
        });

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_DEVICE_HASH")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }


    public class MyCountDownTimer extends CountDownTimer {
        private long timeleft_countDownTimer = 0;
        private long start_time = 0;
        private long interval_time = 1000;
        Editor editor;

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        public void pause() {
            countDownTimer.cancel();
            startTime = timeleft_countDownTimer;
        }

        public void resume() {
            countDownTimer = new MyCountDownTimer(startTime, interval_time);
            countDownTimer.start();
        }


        @Override
        public void onFinish() {
            check_normal_onfinish = 1;
            mp.stop();
            cntr_aCounter.cancel();

            editor = cnormal_preference.edit();
            if (mGoogleApiClient.isConnected()) {
                Games.Leaderboards.submitScore(mGoogleApiClient,
                        getString(R.string.leaderboard_id),
                        total_score);
            }

            if (cnormal_best_total_score < total_score) {
                cnormal_best_total_score = total_score;

                editor.putInt("cnormal_best_total_score", cnormal_best_total_score);

            }
            editor.commit();

            if (mInterstitialAd.isLoaded() && count_status == 1) {
                count_status = 0;
                mInterstitialAd.show();

            } else {
                count_status++;
                Intent i = new Intent(getApplicationContext(), ChallengeNormalScoreboardActivity.class);
                i.putExtra("ncorrect_normal", ncorrect_normal);
                i.putExtra("cnormal_best_total_score", cnormal_best_total_score);
                i.putExtra("cnormal_total_score", total_score);
                i.putExtra("songs_inbank", songs_inbank);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    Intent i = new Intent(getApplicationContext(), ChallengeNormalScoreboardActivity.class);
                    i.putExtra("ncorrect_normal", ncorrect_normal);
                    i.putExtra("cnormal_best_total_score", cnormal_best_total_score);
                    i.putExtra("cnormal_total_score", total_score);
                    i.putExtra("songs_inbank", songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }
            });


        }

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            timeleft_countDownTimer = millis;
            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            textViewTime.setText("" + millisUntilFinished / 1000);
            textViewTime.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    @Override
    public void onConnected(Bundle arg0) {

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}