package com.example.circleseek.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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

//import com.google.example.games.basegameutils.BaseGameActivity;


public class ChallengeHardActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    InterstitialAd mInterstitialAd;
    public static int count_status = 0;
    private int variable_count = 0;
    private GoogleApiClient mGoogleApiClient;
    //setting preferences
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

    public MyCountDownTimer countDownTimer;

    //setting preferences
    public Animation anim_score, bounce;

//	SharedPreferences bestt;

    SharedPreferences chard_preference;
    //SharedPreferences highscore;

    //SharedPreferences best_total_score;

    public int best = 0;
    public int check_hard_onfinish = 0;

    public Handler h_challenge_hard;
    public Runnable runnable;
    public int songs_inbank = 0;
    public ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;

    private TextView songTitleLabel;
    private TextView songTitleLabe3;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player
    public MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ;
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    public int x = 0;
    private boolean isShuffle = true;
    private boolean isRepeat = false;
    String variable = "ruthvik";
    public long startTime = 60000;
    public long interval = 1000;
    public CountDownTimer cntr_aCounter;
    private CountDownTimer challenge_hard;
    public TextView textViewTime;
    public TextView score;
    public TextView score_current;
    public int ncorrect = 0, nwrong = 0;
    private long seconds_hard;

    private Button mButton1, mButton2, mButton3, mButton4;

    private View button1, button2, button3, button4;


    private int song_time = 10000;
    private int song_interval = 1000;
    // textViewTime.setText("");
    private int individual_score = 0;
    private static int total_score = 0;
    public int chard_best_total_score = 0;

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

    public void force_stop() {
        countDownTimer.cancel();
        //cntr_aCounter.cancel();
        //Toast.makeText(ChallengeNormalActivity.this, "Congrats entered into force stop", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), ErrorCatchingActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_game);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    variable_count = 1;
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), TimedActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    mp.stop();

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
                    mp.stop();                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }


        //interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2293516715213176/5262717248");
        requestNewInterstitial();
        // end of enterstitial ads


//		        getGameHelper().setMaxAutoSignInAttempts(0);


        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();


        // advertisement ki code here.......
        //AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        // End of advertisements ki code here....


        //bestt = this.getSharedPreferences("highscore", Context.MODE_PRIVATE);
        //best = bestt.getInt("highscore", 0); //0 is the default value
        //best_total_score = bestt.getInt("best_total_score",0); //best_total_score

        // highscore = this.getSharedPreferences("highscore", Context.MODE_PRIVATE);
        total_score = 0;
        individual_score = 0;
        check_hard_onfinish = 0;

        chard_preference = this.getSharedPreferences("chard_mypref", Context.MODE_PRIVATE);

        chard_best_total_score = chard_preference.getInt("chard_best_total_score", 0); //0 is the default value.


        // All player buttons
        textViewTime = (TextView) findViewById(R.id.textView1);
        score = (TextView) findViewById(R.id.score);
        score.setText("0");
        score_current = (TextView) findViewById(R.id.score_current);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        initialize_player();

        countDownTimer = new MyCountDownTimer(startTime, interval);

        countDownTimer.start();

        //Stop background playing songs and play current songs
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

    public void initialize_player() {

        mp = new MediaPlayer();
        songManager = new SongsManager();
        utils = new Utilities();
        songsList = songManager.getPlayList();

    }

    public void function(long time, long interval, final ImageButton btnPlay) {
        if (check_hard_onfinish == 0) {

            c1 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
            c1.progress_duration = 8000;
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

            //countDownTimer.cancel();
            Random randobj = new Random();
            //Log.v("function tarwaata","after function");
            for (int i = 0; i < songsList.size(); i++) {
                //Log.v("Data in songsList",""+songsList.get(i));

            }

            songsListnew = new ArrayList<HashMap<String, String>>(songsList);
            long duration = 0;

            songs_inbank = songsListnew.size();

            if (songs_inbank == 1) {
                //Toast.makeText(ChallengeNormalActivity.this, "Congrats entered into force stop", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ChallengeNormalActivity.this, "Sorry. Your playlist contains no songs. Or your songs are not in the folder 'Music' or 'Bluetooth'", Toast.LENGTH_SHORT).show();
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
                duration = mp.getDuration();

                mp.seekTo(65000);

                final int t = 0;

                cntr_aCounter = new CountDownTimer(8000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        x = t + 1;
                        long millis = millisUntilFinished;
                        seconds_hard = millis / 1000;

                        //Log.v("display","seconds "+seconds);
                        //Log.v("millissss","millissss"+millis);
                        //
                        if (seconds_hard > 4) {
                            individual_score = 50;
                        } else {
                            individual_score = 25;

                        }

                    }

                    public void onFinish() {
                        //code fire after finish
                        // Log.v("Verify","Number of onTick: "+x);
                        mp.stop();
                        function(song_time, song_interval, btnPlay);
                    }
                };
                cntr_aCounter.start();


                //mp.setProgress(currentPosition);
                // mHandler.removeCallbacks(mUpdateTimeTask);
                // Log.v("After first","First song play"+FirstsongIndex);

                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
                String songTitle1 = songsListnew.get(currentSongIndex).get("songTitle");
                songsListnew.remove(currentSongIndex);
                map = new HashMap<String, String>();
                map.put("constant", variable);
                map.put("songtitle", songTitle1);
                options.add(map);
                // Log.v("After second","Second song option"+currentSongIndex);

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

                //Log.v(" name is ","jgjgjhgjg"+songTitle);
                //Log.v("song name is1 ","jgjgjhgjg"+songTitle1);


                // Log.v("song name is2 ","jgjgjhgjg"+songTitle2);


                for (int i = 0; i < options.size(); i++) {
                    //Log.v("Data in options",""+options.get(i));

                }

                option_select(options);


                //Log.v("song 3 ","jgjgjhgjg"+songTitle3);

                /**
                 * Play button click event
                 * plays a song and changes button to pause image
                 * pauses a song and changes button to play image
                 * */
                btnPlay.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        variable_count = 1;
                        countDownTimer.cancel();
                        cntr_aCounter.cancel();
                        mp.stop();
                        Intent i = new Intent(getApplicationContext(), TimedActivity.class);

                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        mp.stop();
                        // Changing button image to play button
                        //btnPlay.setImageResource(R.drawable.btn_stop);

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
        if (mp != null) {
            mp.stop();
        }
        // mp.release();
        // mp = null;
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
                //   BaseGameUtils.showActivityResultError(this,
                //     requestCode, resultCode, R.string.signin_failure);
            }
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
                // Displaying Song title
                //	String songTitle = songsList.get(songIndex).get("songTitle");
                //	songTitleLabel.setText(songTitle);

                // Changing Button Image to pause image
                btnPlay.setImageResource(R.drawable.btn_stop);


                // Updating progress bar
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

            // Displaying Total Duration time
            //   songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            //   songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */


    /**
     * When user starts moving the progress handler
     * */


    /**
     * When user stops moving the progress hanlder
     * */


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
        //button1 = findViewById(R.id.imageButton1);
        //button2 = findViewById(R.id.imageButton2);
        //button3 = findViewById(R.id.imageButton3);
        //button4 = findViewById(R.id.imageButton4);

        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);

        mButton1 = (Button) findViewById(R.id.imageButton1);
        mButton1.startAnimation(animAnticipateOvershoot_one);
        //((ViewStub) findViewById(R.id.stub_import)).setVisibility(View.VISIBLE);
        //final View importPanel = ((ViewStub) findViewById(R.id.imageButton1)).inflate();
        mButton1.setText(option1);
        //mButton1.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked

        mButton2 = (Button) findViewById(R.id.imageButton2);
        mButton2.startAnimation(animAnticipateOvershoot_one);
        //mButton2.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked
        mButton2.setText(option2);

        mButton3 = (Button) findViewById(R.id.imageButton3);
        mButton3.startAnimation(animAnticipateOvershoot_one);
        //mButton3.setBackgroundResource(R.drawable.btn_before_clicked);   // set background before button is clicked
        mButton3.setText(option3);

        mButton4 = (Button) findViewById(R.id.imageButton4);
        mButton4.startAnimation(animAnticipateOvershoot_one);
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

        // time batti score display avvalii...


        button_onclick();


    }

    public void progressstop() {
        //countDownTimer.pause();
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
        //final Animation anim_button_go = AnimationUtils.loadAnimation(this, R.anim.left_out);
        h_challenge_hard = new Handler();
        h_challenge_hard.postDelayed(new Runnable() {

            @Override
            public void run() {
                //countDownTimer.resume();

                //	 h_challenge_hard.removeCallbacksAndMessages(null);

                // 	 mButton1.startAnimation(anim_button_go);
                //	 mButton2.startAnimation(anim_button_go);
                // mButton3.startAnimation(anim_button_go);
                // mButton4.startAnimation(anim_button_go);
                function(song_time, song_interval, btnPlay);// DO DELAYED STUFF

            }
        }, 1500);


    }


    public void button_onclick() {
        anim_score = AnimationUtils.loadAnimation(this, R.anim.anim_score);

        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
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


                    Log.v("answewr clicked", ">8 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    //	individual_score = 25;
                    //	total_score = total_score + individual_score;
                    Log.v("answewr clicked", ">4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    total_score = total_score + individual_score;
                    Log.v("answewr clicked", "<4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    //Toast.makeText(ChallengeHardActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();
                    mp.stop();
                    cntr_aCounter.cancel();

                    //	ChallengeHardActivity.cancel();
                    progressstop();


                } else {
                    mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background

                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);

                    //individual_score = -20;
                    total_score = total_score - 25;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -25);
                    score_current.startAnimation(anim_score);
                    //Toast.makeText(ChallengeHardActivity.this, "Wrong answer dude...", Toast.LENGTH_LONG).show();

                    mp.stop();

                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
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
                    Log.v("option2 answewr clicked", ">8 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    //total_score = total_score + individual_score;
                    Log.v("option2 answewr clicked", ">4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    total_score = total_score + individual_score;
                    Log.v("option2 answewr clicked", "<4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //	ChallengeHardActivity.cancel();
                    //Toast.makeText(ChallengeHardActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();
                    progressstop();
                } else {
                    mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);

                    //individual_score = -20;
                    total_score = total_score - 25;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    mp.stop();
                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -25);
                    score_current.startAnimation(anim_score);
                    //Toast.makeText(ChallengeHardActivity.this, "Wrong answer dude...", Toast.LENGTH_SHORT).show();
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
                    Log.v("option3 answewr clicked", ">8 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    //total_score = total_score + individual_score;
                    Log.v("option3 answewr clicked", ">4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    total_score = total_score + individual_score;
                    Log.v("option3 answewr clicked", "<4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
                    //Toast.makeText(ChallengeHardActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();

                    progressstop();
                } else {
                    mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);
                    //individual_score = -20;
                    total_score = total_score - 25;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -25);
                    score_current.startAnimation(anim_score);
                    //	Toast.makeText(ChallengeHardActivity.this, "Wrong answer dude...", Toast.LENGTH_SHORT).show();

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
                    Log.v("option4 answewr clicked", ">8 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    //		total_score = total_score + individual_score;
                    Log.v("option4 answewr clicked", ">4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);


                    total_score = total_score + individual_score;
                    Log.v("option4 answewr clicked", "<4 seconds" + seconds_hard);
                    Log.v("answewr clicked", "Individual score" + individual_score);
                    Log.v("answewr clicked", "Total score" + total_score);

                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("+" + individual_score);
                    score_current.startAnimation(anim_score);
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
                    //Toast.makeText(ChallengeHardActivity.this, "Congrats :)", Toast.LENGTH_SHORT).show();

                    progressstop();
                } else {
                    mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);
                    //individual_score = -20;
                    total_score = total_score - 25;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    score.setText("" + total_score);
                    score_current.setVisibility(View.VISIBLE);
                    score_current.setText("" + -25);
                    score_current.startAnimation(anim_score);
                    mp.stop();
                    cntr_aCounter.cancel();
                    //ChallengeHardActivity.cancel();
                    //Toast.makeText(ChallengeHardActivity.this, "Wrong answer dude...", Toast.LENGTH_SHORT).show();


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


    //public int getCurrentPosition() {
    //   return   // Return current position
    // }

    public class MyCountDownTimer extends CountDownTimer {
        private long timeleft_countDownTimer = 0;
        private long start_time = 0;
        private long interval_time = 1000;
        Editor hard_editor;

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        public void pause() {
            countDownTimer.cancel();
            //ChallengeHardActivity.cancel();
            startTime = timeleft_countDownTimer;
            //start_time = timeleft_countDownTimer;
            // TODO Auto-generated method stub

        }

        public void resume() {
            countDownTimer = new MyCountDownTimer(startTime, interval_time);
            countDownTimer.start();
        }

        @Override
        public void onFinish() {
            check_hard_onfinish = 1; //onFinish function called
            //h_challenge_hard.removeCallbacksAndMessages(null);
            // h_challenge_hard.removeCallbacks(runnable);
            mButton1.setEnabled(false);
            mButton2.setEnabled(false);
            mButton3.setEnabled(false);
            mButton4.setEnabled(false);
            mp.stop();
            cntr_aCounter.cancel();
            //ChallengeHardActivity.cancel();
            hard_editor = chard_preference.edit();

            //editor = highscore.edit();
            //if(best < ncorrect)
            //	{

            //	editor.putInt("highscore", ncorrect);

            //	best = ncorrect;
            //	}

            if (mGoogleApiClient.isConnected()) {
                Games.Leaderboards.submitScore(mGoogleApiClient,
                        getString(R.string.leaderboard_id),
                        total_score);
            }

            if (chard_best_total_score < total_score) {
                chard_best_total_score = total_score;

                hard_editor.putInt("chard_best_total_score", chard_best_total_score);

            }
            hard_editor.commit();

            if (mInterstitialAd.isLoaded() && count_status == 1) {
                count_status = 0;
                mInterstitialAd.show();

            } else {
                count_status++;

                //	Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, 1337);
                Intent i = new Intent(getApplicationContext(), ChallengehardScoreboardActivity.class);
                i.putExtra("ncorrect", ncorrect);
                //i.putExtra("nwrong", nwrong);
                //i.putExtra("best", best);
                i.putExtra("chard_best_total_score", chard_best_total_score);
                i.putExtra("total_score", total_score);
                i.putExtra("songs_inbank", songs_inbank);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    //  requestNewInterstitial();
                    Intent i = new Intent(getApplicationContext(), ChallengehardScoreboardActivity.class);
                    i.putExtra("ncorrect", ncorrect);
                    //i.putExtra("nwrong", nwrong);
                    //i.putExtra("best", best);
                    i.putExtra("chard_best_total_score", chard_best_total_score);
                    i.putExtra("total_score", total_score);
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
            //System.out.println(hms);
            //textViewTime.setText(hms);
            textViewTime.setText("" + millisUntilFinished / 1000);

            textViewTime.setTextColor(Color.parseColor("#000000"));
            //Log.v("onTick","");
            //  cuentaRegresiva.setText(""+millisUntilFinished/1000);

        }
    }

    /*	@Override
        public void onSignInFailed() {
            // TODO Auto-generated method stub

        }
        @Override
        public void onSignInSucceeded() {
            // TODO Auto-generated method stub

        }*/
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onStart() {

        //Toast.makeText(MenuSelectionActivity.this, "onstart", Toast.LENGTH_SHORT).show();
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

}  