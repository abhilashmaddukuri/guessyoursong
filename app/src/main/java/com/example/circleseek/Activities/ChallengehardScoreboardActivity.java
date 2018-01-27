
package com.example.circleseek.Activities;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.guessursongs.R;

public class ChallengehardScoreboardActivity extends Activity
	{
		private static final String tag = "Main";
		
		private InterstitialAd interstitial;
		
		private long timeElapsed;
		private boolean timerHasStarted = false;
		private Button startB;
		private TextView text;
		private TextView timeElapsedView;
		private int ncorrect_hard_scoreboard = 0;
		public int nwrong = 0;
		public int scoreboard_best = 0;
		public int chard_scoreboard_best_total_score = 0;
		public int chard_scoreboard_total_score = 0;
		public int chard_songs_inbank_scoreboard = 0;
		//private final long startTime = 5000;
		//private final long interval = 5000;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.scoreboard_layout);
				
				// Create the interstitial.
			    interstitial = new InterstitialAd(this);
			    interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

			    // Create ad request.
			    AdRequest adRequest = new AdRequest.Builder().build();

			    // Begin loading your interstitial.
			    interstitial.loadAd(adRequest);
				
			    displayInterstitial();
			    
				// Font path
		        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
		        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";
		 
		 
		        // Loading Font Face
		        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
		        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);
		        
		        
		        
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
				    ncorrect_hard_scoreboard = extras.getInt("ncorrect");
				    //nwrong = extras.getInt("nwrong");
				    //scoreboard_best = extras.getInt("best");
				    chard_scoreboard_best_total_score = extras.getInt("chard_best_total_score");
				    chard_scoreboard_total_score = extras.getInt("total_score");
				    chard_songs_inbank_scoreboard = extras.getInt("songs_inbank");
				    //Log.v("ScoreBoardActivity","correct:"+ncorrect);
				    //Log.v("ScoreBoardActivity","wrong:"+nwrong);
				    //Log.v("ScoreBoardActivity","best:"+scoreboard_best);
				    Log.v("ScoreBoardActivity","total_score:"+chard_scoreboard_total_score);
				    Log.v("ScoreBoardActivity","best_total_score:"+chard_scoreboard_best_total_score);
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
			//	 challenge_text.setBackgroundDrawable(ChallengeHardActivity);
				
				 final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);
				 
				TextView best_total_score = (TextView)findViewById(R.id.best_total_score);
				//best_total_score.setTypeface(tf_bold);
				best_total_score.setText("    "+chard_scoreboard_best_total_score);
				//best_total_score.startAnimation(animAnticipateOvershoot_one);
				
				LinearLayout linear_lineone = (LinearLayout)findViewById(R.id.line_one);
				linear_lineone.startAnimation(animAnticipateOvershoot_one);
				
				LinearLayout linear_linetwo = (LinearLayout)findViewById(R.id.line_two);
				linear_linetwo.startAnimation(animAnticipateOvershoot_one);
				
				//LinearLayout linear_linethree = (LinearLayout)findViewById(R.id.line_three);
				//linear_linethree.startAnimation(animAnticipateOvershoot_one);
				
				TextView heading= (TextView)findViewById(R.id.heading);
				//heading.setTypeface(tf_bold);
				
				
				TextView songs_inbank = (TextView)findViewById(R.id.songs_inbank);
				//songs_inbank.setTypeface(tf_bold);
				songs_inbank.setText(""+chard_songs_inbank_scoreboard);
				//songs_inbank.startAnimation(animAnticipateOvershoot_one);
				
				TextView ncorrect = (TextView)findViewById(R.id.ncorrect);
				 //ncorrect.setTypeface(tf_bold);
				ncorrect.setText(""+ncorrect_hard_scoreboard);
				//ncorrect.startAnimation(animAnticipateOvershoot_one);
				
				
				TextView total_score = (TextView)findViewById(R.id.total_score);
				 //total_score.setTypeface(tf_bold);
				total_score.setText(""+chard_scoreboard_total_score);
				//total_score.startAnimation(animAnticipateOvershoot_one);
				//ImageButton menu = (ImageButton) findViewById(R.id.menu);
			//    menu.setOnClickListener(new View.OnClickListener() {
			 //       @Override
			  //      public void onClick(View v) {
			   //     	Intent i = new Intent(getApplicationContext(), menu_selection.class);
			    //           startActivity(i);

			       // }
			   // });
			    
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
			        	final ProgressDialog ringProgressDialog = ProgressDialog.show(ChallengehardScoreboardActivity.this, "",	"Getting songs...", true);
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
			        	
			        	Intent i = new Intent(getApplicationContext(), ChallengeHardActivity.class);
			               startActivity(i);
			               overridePendingTransition(R.anim.left_in, R.anim.right_out);

			        }
			    });
				
			}

		@Override
		public void onBackPressed()
		{
			// check for already playing
			//countDownTimer.cancel();
			//cntr_aCounter.cancel();	
			// media.stop();
			// mp.release();
			// mp = null;
			MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
    	    mp.start();
			 Intent i = new Intent(getApplicationContext(), ChallengeLevelActivity.class);

	           startActivity(i);
	           overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		}

		
		// Invoke displayInterstitial() when you are ready to display an interstitial.
		  public void displayInterstitial() {
		    if (interstitial.isLoaded()) {
		      interstitial.show();
		    }
		  }
	}