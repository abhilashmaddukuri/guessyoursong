

package com.example.circleseek.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guessursongs.R;

public class ChallengeInsaneScoreboardActivity extends Activity {
    private static final String tag = "Main";

    private long timeElapsed;
    private boolean timerHasStarted = false;
    private Button startB;
    private TextView text;
    private TextView timeElapsedView;
    public int ncorrect_insane_scoreboard = 0;
    public int nwrong = 0;
    public int scoreboard_best = 0;
    private int ichallenge_scoreboard_best_total_score = 0;
    private int ichallenge_scoreboard_total_score = 0;
    public int cinsane_songs_inbank_scoreboard = 0;
    //private final long startTime = 5000;
    //private final long interval = 5000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_layout);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ncorrect_insane_scoreboard = extras.getInt("ncorrect_insane");
            //nwrong = extras.getInt("nwrong");
            //scoreboard_best = extras.getInt("best");
            ichallenge_scoreboard_best_total_score = extras.getInt("cinsane_best_total_score");
            ichallenge_scoreboard_total_score = extras.getInt("total_score");
            cinsane_songs_inbank_scoreboard = extras.getInt("songs_inbank");
            // Log.v("ScoreBoardActivity","correct:"+ncorrect);
            //Log.v("ScoreBoardActivity","wrong:"+nwrong);
            //Log.v("ScoreBoardActivity","best:"+scoreboard_best);
            Log.v("ScoreBoardActivity", "total_score:" + ichallenge_scoreboard_total_score);
            Log.v("ScoreBoardActivity", "best_total_score:" + ichallenge_scoreboard_best_total_score);
        }

        // globally
        //TextView best = (TextView)findViewById(R.id.best);
        //best.setText("Overall best:"+scoreboard_best);

        //TextView correct = (TextView)findViewById(R.id.correct);
        //correct.setText("No. of correct answers:"+ncorrect);

        //	TextView wrong = (TextView)findViewById(R.id.wrong);
        //wrong.setText("No. of wrong answers:"+nwrong);

        //	ImageView challenge_text = (ImageView)findViewById(R.id.challenge_text);
        Drawable challenge_easy = getResources().getDrawable(R.drawable.challenge_easy);
        Drawable challenge_hard = getResources().getDrawable(R.drawable.challenge_hard);
        Drawable challenge_insane = getResources().getDrawable(R.drawable.challenge_insane);
        //	 challenge_text.setBackgroundDrawable(ChallengeInsaneActivity);

        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);

        LinearLayout linear_lineone = (LinearLayout) findViewById(R.id.line_one);
        linear_lineone.startAnimation(animAnticipateOvershoot_one);

        LinearLayout linear_linetwo = (LinearLayout) findViewById(R.id.line_two);
        linear_linetwo.startAnimation(animAnticipateOvershoot_one);

        //LinearLayout linear_linethree = (LinearLayout)findViewById(R.id.line_three);
        //linear_linethree.startAnimation(animAnticipateOvershoot_one);


        TextView cinsane_songs_inbank = (TextView) findViewById(R.id.songs_inbank);
        cinsane_songs_inbank.setText("" + cinsane_songs_inbank_scoreboard);

        TextView best_total_score = (TextView) findViewById(R.id.best_total_score);
        best_total_score.setText("    " + ichallenge_scoreboard_best_total_score);

        TextView ncorrect_insane = (TextView) findViewById(R.id.ncorrect);
        ncorrect_insane.setText("" + ncorrect_insane_scoreboard);

        TextView total_score = (TextView) findViewById(R.id.total_score);
        total_score.setText("" + ichallenge_scoreboard_total_score);

        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = " Guess your song is pretty cool. Check it out on Google play games. See if you can beat my score. http://play.google.com/store/apps/details?id=com.guessursongs";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Check out Guess your song");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Share"));
                //Intent i = new Intent(getApplicationContext(), menu_selection.class);
                // startActivity(i);

            }
        });

        Button replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(ChallengeInsaneScoreboardActivity.this, "", "Getting songs...", true);
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
                        if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
                            ringProgressDialog.dismiss();
                        }
                    }
                }).start();
                Intent i = new Intent(getApplicationContext(), ChallengeInsaneActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // check for already playing
        //countDownTimer.cancel();
        //cntr_aCounter.cancel();
        // media.stop();
        // mp.release();
        // mp = null;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        Intent i = new Intent(getApplicationContext(), TimedActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }


}