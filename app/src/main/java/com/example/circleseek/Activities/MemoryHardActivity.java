package com.example.circleseek.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.circleseek.SongsManager;
import com.example.circleseek.Utils.Utilities;
import com.example.circleseek.Views.CircularProgressBar;
import com.example.circleseek.Views.CircularProgressBar.ProgressAnimationListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guessursongs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MemoryHardActivity extends Activity {

    public MyCountDownTimer countDownTimer;
    public ImageButton btnPlay;
    private CircularProgressBar c_memory;

    private int hard_songs_inbank = 0;

    private MediaPlayer media;

    private Handler mHandler = new Handler();
    ;
    private SongsManager songManager;
    private Utilities utils;
    private int currentSongIndex = 0;
    public int x = 0;
    String variable = "ruthvik";
    private final long startTime = 24000;
    private final long interval = 1000;
    public CountDownTimer cntr_aCounter;
    public TextView textViewTime;

    private String songTitle;
    public HashMap<String, String> mapp = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> memory_answers = new ArrayList<HashMap<String, String>>();

    public final String option1 = null, option2 = null, option3 = null, option4 = null, answer = null;


    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> songsListnew = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> options = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> map = new HashMap<String, String>();

    public void force_stop() {

        countDownTimer.cancel();
        //cntr_aCounter.cancel();

        Intent i = new Intent(getApplicationContext(), ErrorCatchingActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_layout);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    media.stop();
                    Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    media.stop();
                    Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        // advertisement ki code here.......
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // End of advertisements ki code here....

        // Font path
        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";


        // Loading Font Face
        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);


        TextView sampletext_one = ((TextView) findViewById(R.id.sampletext1));
        sampletext_one.setTypeface(tf_bold);

        TextView sampletext_two = ((TextView) findViewById(R.id.sampletext2));
        sampletext_two.setTypeface(tf_bold);


        // All player buttons
        textViewTime = (TextView) findViewById(R.id.textView1);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        media = new MediaPlayer();
        songManager = new SongsManager(MemoryHardActivity.this);
        utils = new Utilities();


        songsList = songManager.getPlayList();

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

        function();

    }

    @Override
    public void onBackPressed() {
        // check for already playing
        countDownTimer.cancel();
        cntr_aCounter.cancel();
        media.stop();
        // mp.release();
        // mp = null;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    public void function() {
        // media.stop();
        c_memory = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
        c_memory.progress_duration(8000);
        c_memory.animateProgressTo(0, 100, new ProgressAnimationListener() {
            public void onAnimationStart() {

            }

            public void onAnimationProgress(int progress) {
                c_memory.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //c_memory.setSubTitle("done");
            }
        });

        Log.v("Function ki vachindii", "came to function");
        Random randobj = new Random();


        songsListnew = new ArrayList<HashMap<String, String>>(songsList);
        if (songsListnew != null & songsListnew.size() > 0) {
            hard_songs_inbank = songsListnew.size();
        } else {
            force_stop();
        }
        if (hard_songs_inbank < 5) {
            force_stop();
        } else {

            currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
            int FirstsongIndex = currentSongIndex;
            songTitle = songsListnew.get(currentSongIndex).get("songTitle");
            songsListnew.remove(currentSongIndex);
            mapp = new HashMap<String, String>();
            mapp.put("constant", variable);
            mapp.put("songtitle", songTitle);
            memory_answers.add(mapp);


            Log.v("dude check here.memory", "Answers array");
            for (int i = 0; i < memory_answers.size(); i++) {
                Log.v("dude check here.memory", "Answers array" + memory_answers.get(i));

            }

            long duration = media.getDuration();

            map = new HashMap<String, String>();
            map.put("constant", variable);
            map.put("songtitle", songTitle);
            options.add(map);
            memory_song(FirstsongIndex);
            if (duration >= 30000) {
                media.seekTo(45000);
            } else {

            }
            final int t = 0;

            cntr_aCounter = new CountDownTimer(8000, 1000) {
                public void onTick(long millisUntilFinished) {
                    x = t + 1;
                }

                public void onFinish() {
                    //code fire after finish
                    Log.v("Verify", "Number of onTick: " + x);

                    function();
                }
            };
            cntr_aCounter.start();


            //mp.setProgress(currentPosition);
            // mHandler.removeCallbacks(mUpdateTimeTask);
            Log.v("After first", "First song play" + FirstsongIndex);

            currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
            String songTitle1 = songsListnew.get(currentSongIndex).get("songTitle");
            songsListnew.remove(currentSongIndex);
            map = new HashMap<String, String>();
            map.put("constant", variable);
            map.put("songtitle", songTitle1);
            options.add(map);
            Log.v("After second", "Second song option" + currentSongIndex);

            for (int i = 0; i < songsListnew.size(); i++) {
                Log.v("Data in songsListnew", "" + songsListnew.get(i));

            }

            currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
            String songTitle2 = songsListnew.get(currentSongIndex).get("songTitle");
            songsListnew.remove(currentSongIndex);
            map = new HashMap<String, String>();
            map.put("constant", variable);
            map.put("songtitle", songTitle2);
            options.add(map);


            Log.v(" name is ", "jgjgjhgjg" + songTitle);
            Log.v("song name is1 ", "jgjgjhgjg" + songTitle1);


            Log.v("song name is2 ", "jgjgjhgjg" + songTitle2);


            for (int i = 0; i < options.size(); i++) {
                Log.v("Data in options", "" + options.get(i));

            }


            // Log.v("song 3 ","jgjgjhgjg"+songTitle3);

            btnPlay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    countDownTimer.cancel();
                    cntr_aCounter.cancel();
                    media.stop();
                    Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    // Changing button image to play button
                    //btnPlay.setImageResource(R.drawable.btn_stop);


                }
            });


        }


    }


    /**
     * Receiving song index from playlist view
     * and play the song
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {


    }

    /**
     * Function to play a song
     *
     * @param songIndex - index of song
     */
    public void memory_song(int songIndex) {
        // Play song
        try {
            media.reset();
            media.setDataSource(songsList.get(songIndex).get("songPath"));
            media.prepare();
            media.start();
            // Displaying Song title
            //	String songTitle = songsList.get(songIndex).get("songTitle");
            //	songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_stop);


            // Updating progress bar
            updateProgressBar();
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
            long totalDuration = media.getDuration();
            long currentDuration = media.getCurrentPosition();

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
     */


    @Override
    public void onDestroy() {
        super.onDestroy();
        media.release();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            media.stop();
            cntr_aCounter.cancel();


            Intent i = new Intent(getApplicationContext(), MemoryHardOptionsActivity.class);
            i.putExtra("memory_answers", memory_answers);
            i.putExtra("hard_songs_inbank", hard_songs_inbank);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

            // cuentaRegresiva.setText("");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            //textViewTime.setText(hms);
            textViewTime.setText("" + millisUntilFinished / 1000);


            Log.v("onTick", "");
            //  cuentaRegresiva.setText(""+millisUntilFinished/1000);

        }
    }

}  