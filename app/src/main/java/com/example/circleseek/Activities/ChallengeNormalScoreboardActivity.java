

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

import com.google.android.gms.common.api.GoogleApiClient;
import com.guessursongs.R;

//import com.google.example.games.basegameutils.BaseGameActivity;
//import com.google.example.games.basegameutils.BaseGameUtils;

public class ChallengeNormalScoreboardActivity extends Activity {
    //InterstitialAd mInterstitialAd;
    private static final String tag = "Main";
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;

    private long timeElapsed;
    private boolean timerHasStarted = false;
    private Button startB;
    private TextView text;
    private TextView timeElapsedView;
    public int ncorrect_normal_scoreboard = 0;
    public int nwrong = 0;
    public int scoreboard_best = 0;
    private int nchallenge_scoreboard_best_total_score = 0;
    private int nchallenge_scoreboard_total_score = 0;
    public int cnormal_songs_inbank_scoreboard = 0;
    //private final long startTime = 5000;
    //private final long interval = 5000;

    /**
     * Called when the activity is first created.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_layout);

				
				
				
		/*		// Create the Google Api Client with access to the Play Game services
                mGoogleApiClient = new GoogleApiClient.Builder(this)
			            .addConnectionCallbacks(this)
			            .addOnConnectionFailedListener(this)
			            .addApi(Games.API).addScope(Games.SCOPE_GAMES)
			            // add other APIs and scopes here as needed
			            .build();
			*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ncorrect_normal_scoreboard = extras.getInt("ncorrect_normal");
            //nwrong = extras.getInt("nwrong");
            //scoreboard_best = extras.getInt("best");
            nchallenge_scoreboard_best_total_score = extras.getInt("cnormal_best_total_score");
            nchallenge_scoreboard_total_score = extras.getInt("cnormal_total_score");
            cnormal_songs_inbank_scoreboard = extras.getInt("songs_inbank");
            //Log.v("ScoreBoardActivity","correct:"+ncorrect);
            //Log.v("ScoreBoardActivity","wrong:"+nwrong);
            //Log.v("ScoreBoardActivity","best:"+scoreboard_best);
            Log.v("ScoreBoardActivity", "total_score:" + nchallenge_scoreboard_total_score);
            Log.v("ScoreBoardActivity", "best_total_score:" + nchallenge_scoreboard_best_total_score);
        }

        // globally
        //TextView best = (TextView)findViewById(R.id.best);
        //best.setText("Overall best:"+scoreboard_best);

        //TextView correct = (TextView)findViewById(R.id.correct);
        //correct.setText("No. of correct answers:"+ncorrect);

        //	TextView wrong = (TextView)findViewById(R.id.wrong);
        //wrong.setText("No. of wrong answers:"+nwrong);

        //ImageView challenge_text = (ImageView)findViewById(R.id.challenge_text);
        Drawable challenge_easy = getResources().getDrawable(R.drawable.challenge_easy);
        Drawable challenge_hard = getResources().getDrawable(R.drawable.challenge_hard);
        Drawable challenge_insane = getResources().getDrawable(R.drawable.challenge_insane);
        //			 challenge_text.setBackgroundDrawable(challenge_easy);

        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);
        LinearLayout linear_lineone = (LinearLayout) findViewById(R.id.line_one);
        linear_lineone.startAnimation(animAnticipateOvershoot_one);

        LinearLayout linear_linetwo = (LinearLayout) findViewById(R.id.line_two);
        linear_linetwo.startAnimation(animAnticipateOvershoot_one);

        //	LinearLayout linear_linethree = (LinearLayout)findViewById(R.id.line_three);
        //	linear_linethree.startAnimation(animAnticipateOvershoot_one);


        TextView best_total_score = (TextView) findViewById(R.id.best_total_score);
        best_total_score.setText("    " + nchallenge_scoreboard_best_total_score);

        TextView total_score = (TextView) findViewById(R.id.total_score);
        total_score.setText("" + nchallenge_scoreboard_total_score);

        TextView cnormal_songs_inbank = (TextView) findViewById(R.id.songs_inbank);
        cnormal_songs_inbank.setText("" + cnormal_songs_inbank_scoreboard);

        TextView ncorrect_normal = (TextView) findViewById(R.id.ncorrect);
        ncorrect_normal.setText("" + ncorrect_normal_scoreboard);


        //mGoogleApiClient
            /*	getGamesClient().loadTopScores(new OnLeaderboardScoresLoadedListener() {

					   public void onLeaderboardScoresLoaded(int arg0, LeaderboardBuffer arg1, LeaderboardScoreBuffer arg2) {

					      // iterate through the list of returned scores for the leaderboard
					      int size = arg2.getCount();
					      for ( int i = 0; i < size; i++ )  {
					         LeaderboardScore lbs = arg2.get( i );

					         // access the leaderboard data
					         int rank = i + 1;         // Rank/Position (#1..#2...#n)
					         String name = lbs.getScoreHolderDisplayName();
					         String scoreStr = lbs.getDisplayScore();
					         long score = lbs.getRawScore();

					         // now display or cache these values, or do whatever with them :)
					      }

					      arg2.close();
					      arg1.close();
					   }
					}, getString(R.string.leaderboard_id),LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC, 5, true);
				
				*/


        //Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, 1337);

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
                //Intent i = new Intent(getApplicationContext(), MenuSelectionActivity.class);
                // startActivity(i);


            }
        });

        Button replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(ChallengeNormalScoreboardActivity.this, "", "Getting songs...", true);
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
                Intent i = new Intent(getApplicationContext(), ChallengeNormalActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }
        });

    }

    @Override
    public void onBackPressed() {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        Intent i = new Intent(getApplicationContext(), TimedActivity.class);

        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }


}