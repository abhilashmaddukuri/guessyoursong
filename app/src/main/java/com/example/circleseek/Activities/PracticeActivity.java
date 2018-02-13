package com.example.circleseek.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.example.circleseek.SongsManager;
import com.example.circleseek.Utils.SongsCache;
import com.example.circleseek.Utils.Utilities;
import com.example.circleseek.Views.CircularProgressBar;
import com.example.circleseek.Views.CircularProgressBar.ProgressAnimationListener;
import com.guessursongs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class PracticeActivity extends Activity {

    public MyCountDownTimer countDownTimer;

    //setting preferences

    SharedPreferences bestt;


    SharedPreferences highscore;

    //SharedPreferences best_total_score;

    public int best = 0;


    private ImageButton btnPlay;
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
    public long startTime = 30000;
    public long interval = 1000;
    public CountDownTimer cntr_aCounter;
    private CountDownTimer ruthvik;
    public TextView textViewTime;
    //public TextView score;
    //public TextView score_current;
    public int ncorrect = 0, nwrong = 0;

    private Button mButton1, mButton2, mButton3, mButton4;

    private View button1, button2, button3, button4;


    private int song_time = 10000;
    private int song_interval = 1000;
    // textViewTime.setText("");
    private int individual_score = 0;
    private static int total_score = 0;
    public int best_total_score = 0;

    public String option1 = null;

    public String option2 = null;

    public String option3 = null;

    public String option4 = null;

    public static String answer = null;

    private CircularProgressBar practice_c2;

    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> songsListnew = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> options = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> map = new HashMap<String, String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.practice_layout);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);

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

        //	 bestt = this.getSharedPreferences("highscore", Context.MODE_PRIVATE);
        //	 best = bestt.getInt("highscore", 0); //0 is the default value
        //	 best_total_score = bestt.getInt("best_total_score",0); //best_total_score

        //	 highscore = this.getSharedPreferences("highscore", Context.MODE_PRIVATE);
        // All player buttons
        // textViewTime = (TextView)findViewById(R.id.textView1);
        //	 score = (TextView)findViewById(R.id.score);
        //	 score.setText("0");
        //	 score_current = (TextView)findViewById(R.id.score_current);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        initialize_player();

        //  countDownTimer = new MyCountDownTimer(startTime, interval);

        //	countDownTimer.start();
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
        if (SongsCache.getInstance().getIsSongsServiceCompleted()) {
            songsList = SongsCache.getInstance().getSongsList();
        } else {
            songManager = new SongsManager(PracticeActivity.this);
            songsList = songManager.getPlayList();
            SongsCache.getInstance().setIsSongsServiceCompleted(true);
            SongsCache.getInstance().setSongsList(songsList);
        }

    }

    private void function(long time, long interval, final ImageButton btnPlay) {
        practice_c2 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
        practice_c2.progress_duration = 10000;
        practice_c2.animateProgressTo(0, 100, new ProgressAnimationListener() {
            public void onAnimationStart() {

            }

            public void onAnimationProgress(int progress) {
                practice_c2.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //practice_c2.setSubTitle("done");
            }
        });

        //countDownTimer.cancel();
        Random randobj = new Random();
        //Log.v("function tarwaata","after function");
        for (int i = 0; i < songsList.size(); i++) {
            //Log.v("Data in songsList",""+songsList.get(i));

        }

        songsListnew = new ArrayList<HashMap<String, String>>(songsList);

        long duration = mp.getDuration();

        if (songsListnew != null && songsListnew.size() < 5) {
            force_stop();
        } else {

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
            if (duration >= 30000) {
                mp.seekTo(15000);
            } else {

            }

            final int t = 0;

            cntr_aCounter = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    x = t + 1;
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

            if (songsListnew != null && songsListnew.size() > 1) {
                currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
            }
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
                    // check for already playing

                    cntr_aCounter.cancel();
                    mp.stop();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    // Changing button image to play button
                    //btnPlay.setImageResource(R.drawable.btn_stop);


                }
            });
        }


    }

    public void force_stop() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Intent i = new Intent(getApplicationContext(), ErrorCatchingActivity.class);
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
            if (songsList != null && songsList.size() > 0) {
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
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
            //int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
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
        option1 = options.get(currentSongIndex).get("songtitle").toUpperCase();
        Log.v("Option one is ", "" + option1);

        options.remove(currentSongIndex);


        currentSongIndex = randd.nextInt(options.size());
        option2 = options.get(currentSongIndex).get("songtitle").toUpperCase();
        Log.v("Option two is ", "" + option2);
        options.remove(currentSongIndex);

        currentSongIndex = randd.nextInt(options.size());
        option3 = options.get(currentSongIndex).get("songtitle").toUpperCase();
        Log.v("Option three is ", "" + option3);
        options.remove(currentSongIndex);

        currentSongIndex = randd.nextInt(options.size());
        option4 = options.get(currentSongIndex).get("songtitle").toUpperCase();
        Log.v("Option four is ", "" + option4);
        options.remove(currentSongIndex);
        //button1 = findViewById(R.id.imageButton1);
        //button2 = findViewById(R.id.imageButton2);
        //button3 = findViewById(R.id.imageButton3);
        //button4 = findViewById(R.id.imageButton4);


        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);

        mButton1 = (Button) findViewById(R.id.imageButton1);
        mButton1.startAnimation(animAnticipateOvershoot_one);
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
        //	score_current.setVisibility(View.INVISIBLE);
        mButton1.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton2.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton3.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background
        mButton4.setBackgroundResource(R.drawable.buttonshape_blue);   // set background color for normal background

        // time batti score display avvalii...


        ruthvik = new CountDownTimer(12000, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                long seconds = millis / 1000;

                //Log.v("display","seconds "+seconds);
                //Log.v("millissss","millissss"+millis);
                //
                if (seconds > 8) {
                    button_onclick(seconds);
                } else if (seconds > 4) {
                    button_onclick(seconds);
                } else {
                    button_onclick(seconds);
                }

            }

            public void onFinish() {
                //code fire after finish
                //ViewGroup layout = (ViewGroup) mButton2.getParent();
                //importPanel.setVisibility(View.GONE);

                //button.setVisibility(0);
                //importPanel.setVisibility(View.VISIBLE);
            }
        };
        ruthvik.start();


    }

    public void progressstop() {
        //countDownTimer.pause();
        practice_c2.animateProgressTo(0, 0, new ProgressAnimationListener() {
            public void onAnimationStart() {

            }

            public void onAnimationProgress(int progress) {
                practice_c2.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                practice_c2.setSubTitle("done");
            }
        });
        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                //countDownTimer.resume();
                function(song_time, song_interval, btnPlay);// DO DELAYED STUFF
            }
        }, 1500);
    }


    public void button_onclick(final long seconds) {

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

                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    //Toast.makeText(PracticeActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    mp.stop();
                    cntr_aCounter.cancel();

                    progressstop();


                } else {
                    mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for green background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);


                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    //score.setText(""+total_score);
                    //score_current.setVisibility(View.VISIBLE);
                    //score_current.setText(""+individual_score);
                    //Toast.makeText(PracticeActivity.this, "Wrong Answer", Toast.LENGTH_LONG).show();

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
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //Toast.makeText(PracticeActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    progressstop();
                } else {
                    mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for green background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    mp.stop();
                    cntr_aCounter.cancel();
                    //	score.setText(""+total_score);
                    //	score_current.setVisibility(View.VISIBLE);
                    //	score_current.setText(""+individual_score);
                    //Toast.makeText(PracticeActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                    progressstop();

                }
            }
        });


        mButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option3)) {
                    mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //Toast.makeText(PracticeActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();

                    progressstop();
                } else {
                    mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for green background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);
                    //individual_score = -20;
                    //total_score = total_score + individual_score;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //score.setText(""+total_score);
                    //score_current.setVisibility(View.VISIBLE);
                    //score_current.setText(""+individual_score);
                    //Toast.makeText(PracticeActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();

                    progressstop();

                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   
                if (answer.equalsIgnoreCase(option4)) {
                    mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                    ncorrect = ncorrect + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);

                    mp.stop();
                    cntr_aCounter.cancel();
                    //	Toast.makeText(PracticeActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();

                    progressstop();
                } else {
                    mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for green background
                    Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    v1.vibrate(150);
                    //individual_score = -20;
                    //total_score = total_score + individual_score;
                    nwrong = nwrong + 1;
                    mButton1.setEnabled(false);
                    mButton2.setEnabled(false);
                    mButton3.setEnabled(false);
                    mButton4.setEnabled(false);
                    //score.setText(""+total_score);
                    //score_current.setVisibility(View.VISIBLE);
                    //score_current.setText(""+individual_score);
                    mp.stop();
                    cntr_aCounter.cancel();
                    //Toast.makeText(PracticeActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();


                    progressstop();

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        // check for already playing
        //countDownTimer.cancel();
        cntr_aCounter.cancel();
        mp.stop();
        // mp.release();
        // mp = null;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();

        Intent i = new Intent(getApplicationContext(), HomeActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        Editor editor;

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        public void pause() {
            //countDownTimer.cancel();
            startTime = timeleft_countDownTimer;
            //start_time = timeleft_countDownTimer;
            // TODO Auto-generated method stub

        }

        public void resume() {
            //countDownTimer = new MyCountDownTimer(startTime, interval_time);
            //countDownTimer.start();
        }

        @Override
        public void onFinish() {
            mp.stop();
            cntr_aCounter.cancel();


        }

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            timeleft_countDownTimer = millis;
            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            //System.out.println(hms);
            textViewTime.setText(hms);


            //Log.v("onTick","");
            //  cuentaRegresiva.setText(""+millisUntilFinished/1000);

        }
    }

}  