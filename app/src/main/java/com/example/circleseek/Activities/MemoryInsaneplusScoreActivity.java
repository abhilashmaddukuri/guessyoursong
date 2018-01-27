package com.example.circleseek.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.guessursongs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryInsaneplusScoreActivity extends Activity
	{
//private long memory_score_timeleft = 0;
	InterstitialAd mInterstitialAd;
private String memory_score;
private static ArrayList<HashMap<String, String>> memory_score_answer = new ArrayList<HashMap<String, String>>();
	public int ncorrect = 0;
		public int nwrong = 0;
		public int best = 0;
		private int memory_check;
		public int minsaneplus_score_songs_inbank = 0;
		public long mhigh = 0;
		public static int count_status = 0;
		//private final long startTime = 5000;
		//private final long interval = 5000;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				//Intent intent = getIntent();
				
				
				
				// Font path
		        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
		        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";
		 
		      //interstitial ads 
				mInterstitialAd = new InterstitialAd(this);
		        mInterstitialAd.setAdUnitId("ca-app-pub-2293516715213176/5262717248");
		        requestNewInterstitial();
				// end of enterstitial ads
		 
		        // Loading Font Face
		        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					memory_score = extras.getString("minsaneplus_time");
					memory_check = extras.getInt("memory_flag_insaneplus");
					minsaneplus_score_songs_inbank = extras.getInt("minsaneplus_songs_inbank");
					mhigh = extras.getLong("minsaneplus_best_total_score");
					
				}	
					
					
					Log.v("Memory ScoreBoardActivity","Memory time:"+memory_score);
					Log.v("insaneplus memory","memory check value is: "+memory_check);
				if(memory_check == 0) // User gives correct input
				{
					String mnormal_best = String.format(""+mhigh);
					/*String mnormal_best = String.format("%02d:%02d", 
						    TimeUnit.MILLISECONDS.toMinutes(mhigh),
						    TimeUnit.MILLISECONDS.toSeconds(mhigh) - 
						    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mhigh))
						);*/
					setContentView(R.layout.memory_score_layout);
					
					// advertisement ki code here.......	
					AdView mAdView = (AdView) findViewById(R.id.adView);
			        AdRequest adRequest = new AdRequest.Builder().build();
			        mAdView.loadAd(adRequest);
			// End of advertisements ki code here....
					
					TextView minsaneplus_score_songs_inbank_textview = (TextView)findViewById(R.id.songs_inbank_memory);
					minsaneplus_score_songs_inbank_textview.setText(""+minsaneplus_score_songs_inbank);
					
					TextView time_taken = (TextView)findViewById(R.id.time_taken);
					
					time_taken.setText(""+memory_score+" sec");
					TextView best = (TextView)findViewById(R.id.best);
					best.setText(""+mnormal_best+" sec");
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
				        	final ProgressDialog ringProgressDialog = ProgressDialog.show(MemoryInsaneplusScoreActivity.this, "",	"Getting songs...", true);
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
				        	Intent i = new Intent(getApplicationContext(), MemoryInsaneplusActivity.class);
				               startActivity(i);
				               overridePendingTransition(R.anim.left_in, R.anim.right_out);

				        }
				    });
					
					
				}

				else 
				{
					setContentView(R.layout.memory_insaneplus_fail);
					
					// advertisement ki code here.......	
					AdView mAdView = (AdView) findViewById(R.id.adView);
			        AdRequest adRequest = new AdRequest.Builder().build();
			        mAdView.loadAd(adRequest);
			// End of advertisements ki code here....
					
			        
			        TextView correct_order = (TextView)findViewById(R.id.correct_order);
			        correct_order.setTypeface(tf_bold);
					if(memory_check == 2)	// user doesn't give any input
					{
						TextView heading = (TextView)findViewById(R.id.heading);
						heading.setText("Time up");
						heading.setTypeface(tf_bold);
					}
					else if(memory_check == 1)	// User failed to give correct input
					{
						TextView heading = (TextView)findViewById(R.id.heading);
						heading.setText("Wrong sequence");
						 heading.setTypeface(tf_bold);
						 
					}
					 memory_score_answer = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("answer_topass");
					 Log.v("Memory score","Memory score"+memory_score_answer.size());
						for(int j=0; j<memory_score_answer.size(); j++)
						{
							Log.v("Memory score","Memory score"+memory_score_answer.get(j));	
						}
					 
					//TextView answer_text = (TextView)findViewById(R.id.answers);
					 //String option1;
						
						final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.left_in);
						
					 String[] ans = new String[5];
					 final TextView option1=(TextView)findViewById(R.id.option1);
					 final TextView option2=(TextView)findViewById(R.id.option2);
					 final TextView option3=(TextView)findViewById(R.id.option3);
					 final TextView option4=(TextView)findViewById(R.id.option4);
					 final TextView option5=(TextView)findViewById(R.id.option5);
				      
					 option1.startAnimation(animAnticipateOvershoot_one);
					 option2.startAnimation(animAnticipateOvershoot_one);
					 option3.startAnimation(animAnticipateOvershoot_one);
					 option4.startAnimation(animAnticipateOvershoot_one);
					 option5.startAnimation(animAnticipateOvershoot_one);
				
					
					for(int j=0; j<memory_score_answer.size(); j++)
					{
						ans[j] = memory_score_answer.get(j).get("songtitle");;
							
					}
					  option1.setText(" "+ans[0]);
					  option2.setText(" "+ans[1]);
					  option3.setText(" "+ans[2]);
					  option4.setText(" "+ans[3]);
					  option5.setText(" "+ans[4]);
					  
					  option1.setTypeface(tf_bold);
						 option2.setTypeface(tf_bold);
						 option3.setTypeface(tf_bold);
						 option4.setTypeface(tf_bold);
						 option5.setTypeface(tf_bold);
					  
					  Button next = (Button) this.findViewById(R.id.next);
						next.setOnClickListener(new OnClickListener() 
					     {
				             @Override
				             public void onClick(View arg0)
				             {
				            	 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
				            	    mp.start();
				            	    if (mInterstitialAd.isLoaded() && count_status == 1) 
				                	{
				                        count_status = 0;
				                		mInterstitialAd.show();
				                        
				                    }
				                	else
				                	{
				                		count_status++;
				                	
				                		 Intent i = new Intent(getApplicationContext(), MemoryInsaneplusAfterNextActivity.class);
						            	 i.putExtra("mhigh",mhigh);
							             	i.putExtra("minsaneplus_score_songs_inbank",minsaneplus_score_songs_inbank);
						                     startActivity(i);
						                     overridePendingTransition(R.anim.right_in, R.anim.left_out);
				                	}
				                	 mInterstitialAd.setAdListener(new AdListener() {
				                         @Override
				                         public void onAdClosed() {
				                        	 Intent i = new Intent(getApplicationContext(), MemoryInsaneplusAfterNextActivity.class);
							            	 i.putExtra("mhigh",mhigh);
								             	i.putExtra("minsaneplus_score_songs_inbank",minsaneplus_score_songs_inbank);
							                     startActivity(i);
							                     overridePendingTransition(R.anim.right_in, R.anim.left_out);
				                         }
				                     });
				            	    
				            	    
				            	
				             }
					     });
					
				}
				
				
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
	
		private void requestNewInterstitial() {
	        AdRequest adRequest = new AdRequest.Builder()
	                  .addTestDevice("YOUR_DEVICE_HASH")
	                  .build();

	        mInterstitialAd.loadAd(adRequest);
	    }
		
	}