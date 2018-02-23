package com.example.circleseek.Activities;
//package com.google.android.gms:play-services:6.5.87;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guessursongs.R;

public class MemoryInsaneplusAfterNextActivity extends Activity
	{
		

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.memory_after_next);
				
				int minsaneplus_score_songs_inbank = 0;
				long mhigh_after_next = 0;
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					minsaneplus_score_songs_inbank= extras.getInt("minsaneplus_score_songs_inbank");
					mhigh_after_next= extras.getLong("mhigh");
				}
				
				
				TextView mnormal_score_songs_inbank = (TextView)findViewById(R.id.songs_inbank_memory);
				mnormal_score_songs_inbank.setText(""+minsaneplus_score_songs_inbank);
				
				
				TextView time_taken = (TextView)findViewById(R.id.time_taken);
				
				//time_taken.setText(""+memory_score);
				TextView best = (TextView)findViewById(R.id.best);
				//best.setText(""+mhigh_after_next);

				if(mhigh_after_next == 10000)
				{
					best.setText("---");
				}
				else
				{
					best.setText(""+mhigh_after_next+" sec");
				}
				
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
			        	final ProgressDialog ringProgressDialog = ProgressDialog.show(MemoryInsaneplusAfterNextActivity.this, "",	"Getting songs...", true);
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
			        	Intent i = new Intent(getApplicationContext(), MemoryInsaneplusActivity.class);
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
			 //media.stop();
			// mp.release();
			// mp = null;
			MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
    	    mp.start();
			 Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

	           startActivity(i);
	           overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		}
	}