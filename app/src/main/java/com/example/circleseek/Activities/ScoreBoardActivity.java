package com.example.circleseek.Activities;




import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.circleseek.Activities.ChallengeHardActivity;
import com.guessursongs.R;

public class ScoreBoardActivity extends Activity
	{
		private static final String tag = "Main";
		
		private long timeElapsed;
		private boolean timerHasStarted = false;
		private Button startB;
		private TextView text;
		private TextView timeElapsedView;
		public int ncorrect = 0;
		public int nwrong = 0;
		public int scoreboard_best = 0;
		public int scoreboard_best_total_score = 0;
		public int scoreboard_total_score = 0;
		//private final long startTime = 5000;
		//private final long interval = 5000;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.scoreboard_layout);
				
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
				    ncorrect = extras.getInt("ncorrect");
				    nwrong = extras.getInt("nwrong");
				    scoreboard_best = extras.getInt("best");
				    scoreboard_best_total_score = extras.getInt("best_total_score");
				    scoreboard_total_score = extras.getInt("total_score");
				    Log.v("ScoreBoardActivity","correct:"+ncorrect);
				    Log.v("ScoreBoardActivity","wrong:"+nwrong);
				    Log.v("ScoreBoardActivity","best:"+scoreboard_best);
				    Log.v("ScoreBoardActivity","total_score:"+scoreboard_total_score);
				    Log.v("ScoreBoardActivity","best_total_score:"+scoreboard_best_total_score);
				}

				// globally 
				//TextView best = (TextView)findViewById(R.id.best);
				//best.setText("Overall best:"+scoreboard_best);
				
				//TextView correct = (TextView)findViewById(R.id.correct);
				//correct.setText("No. of correct answers:"+ncorrect);
				
			//	TextView wrong = (TextView)findViewById(R.id.wrong);
				//wrong.setText("No. of wrong answers:"+nwrong);
				
				// Font path
		        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
		        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";
		 
		 
		        // Loading Font Face
		        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
		        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);
		
				
				
				TextView best_total_score = (TextView)findViewById(R.id.best_total_score);
				best_total_score.setTypeface(tf_bold);
				best_total_score.setText("Best: "+scoreboard_best_total_score);
				
				TextView total_score = (TextView)findViewById(R.id.total_score);
				total_score.setTypeface(tf_bold);
				total_score.setText("Score: "+scoreboard_total_score);
					    
			    ImageButton share = (ImageButton) findViewById(R.id.share);
			    share.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        
			        	String message = "Text I want to share.";
			        			Intent share = new Intent(Intent.ACTION_SEND);
			        			share.setType("text/plain");
			        			share.putExtra(Intent.EXTRA_TEXT, message);

			        			startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
			        	
			        	//Intent i = new Intent(getApplicationContext(), menu_selection.class);
			              // startActivity(i);

			        }
			    });
			    
			    ImageButton replay = (ImageButton) findViewById(R.id.replay);
			    replay.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        	Intent i = new Intent(getApplicationContext(), ChallengeHardActivity.class);
			               startActivity(i);

			        }
			    });
				
			}

		

	}