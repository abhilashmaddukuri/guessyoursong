
package com.example.circleseek.Activities;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guessursongs.R;

public class MemoryLevelActivity extends Activity
	{
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.memory_level_layout);
				
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
		        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);
				
				
		        TextView heading= ((TextView) findViewById(R.id.heading));
				heading.setTypeface(tf_bold);
				
				TextView memory_normal_text= ((TextView) findViewById(R.id.memory_normal_text));
				memory_normal_text.setTypeface(tf_bold);
				
				TextView memory_hard_text= ((TextView) findViewById(R.id.memory_hard_text));
				memory_hard_text.setTypeface(tf_bold);
				
				TextView memory_insane_text= ((TextView) findViewById(R.id.memory_insane_text));
				memory_insane_text.setTypeface(tf_bold);
		        
			//	String desc ="<br/>&#8226;Guess the song played<br/>&#8226;Faster you choose the correct answer, more you get the points<br/>&#8226;Sample txt all the best dude";
			//	TextView description= ((TextView) findViewById(R.id.description));
			//	description.setText(Html.fromHtml(desc));
				
			//	memory_button.setOnClickListener(this);
				//countDownTimer = new MalibuCountDownTimer(startTime, interval);
				//text.setText(text.getText() + String.valueOf(startTime));
				ImageButton memory_normal = (ImageButton) this.findViewById(R.id.memory_normal);
				//challenge_button.setOnClickListener(this);
				ImageButton memory_hard = (ImageButton) this.findViewById(R.id.memory_hard);
				ImageButton memory_insane = (ImageButton) this.findViewById(R.id.memory_insane);
				
				RelativeLayout relative_normal =(RelativeLayout)findViewById(R.id.relative_one);
				
				relative_normal.setOnClickListener(new OnClickListener() 
			     {
		             @Override
		             public void onClick(View arg0)
		             {
		            	 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
		            	    mp.start();
		            	 final ProgressDialog ringProgressDialog = ProgressDialog.show(MemoryLevelActivity.this, "",	"Getting songs...", true);
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
			            	 
		            	 Intent i = new Intent(getApplicationContext(), MemoryHardActivity.class);  // normal level
		                 //    i.putExtra("id", position);
		                     startActivity(i);
		                     overridePendingTransition(R.anim.right_in, R.anim.left_out);

		             }
			     });
				
				
				RelativeLayout relative_hard =(RelativeLayout)findViewById(R.id.relative_two);
				relative_hard.setOnClickListener(new OnClickListener() 
			     {
		             @Override
		             public void onClick(View arg0)
		             {
		            	 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
		            	    mp.start();
		            	 final ProgressDialog ringProgressDialog = ProgressDialog.show(MemoryLevelActivity.this, "",	"Getting songs...", true);
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
			            	 
		            	 Intent i = new Intent(getApplicationContext(), MemoryInsaneActivity.class);  // hard level
		                 //    i.putExtra("id", position);
		                     startActivity(i);
		                     overridePendingTransition(R.anim.right_in, R.anim.left_out);

		             }
			     });
				
				RelativeLayout relative_insane =(RelativeLayout)findViewById(R.id.relative_three);
				relative_insane.setOnClickListener(new OnClickListener() 
			     {
		             @Override
		             public void onClick(View arg0)
		             {
		            	 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
		            	    mp.start();
		            	 final ProgressDialog ringProgressDialog = ProgressDialog.show(MemoryLevelActivity.this, "",	"Getting songs...", true);
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
			            	 
		            	 Intent i = new Intent(getApplicationContext(), MemoryInsaneplusActivity.class);  // insane level
		                 //    i.putExtra("id", position);
		                     startActivity(i);
		                     overridePendingTransition(R.anim.right_in, R.anim.left_out);
		             }
			     });

				
				 Button guide= (Button)findViewById(R.id.guide);
		         guide.setOnClickListener(new OnClickListener(){
		             @Override
		             public void onClick(View v)
		             {
		            	 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
		            	    mp.start();
		            	 Intent i = new Intent(getApplicationContext(), MemoryGuideActivity.class);
		                 //   f i.putExtra("id", position);
		                     startActivity(i);
		                     overridePendingTransition(R.anim.right_in, R.anim.left_out);
		            	 
		            	// create a Dialog component
		 				//final Dialog dialog = new Dialog(ChallengeLevelActivity.this,R.style.myBackgroundStyle);
		 				
		            	// final Dialog dialog = new Dialog(ChallengeLevelActivity.this);
		            	 
		            	// final Dialog dialog = new Dialog(
		 				//		  new ContextThemeWrapper(ChallengeLevelActivity.this, android.R.style.AppTheme));
		 				

		 				//tell the Dialog to use the dialog.xml as it's layout description
		 				
		 				
	//	 				dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 			//	dialog.setContentView(R.layout.dialog);
	//	 			    dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.dialog_heading);
		 				
		 				
		 			//	dialog.setTitle("Android");
		 				
		 				
		 			//	String desc ="<br/>&#8226;Guess the song played<br/>&#8226;Faster you choose the correct answer, more you get the points<br/>&#8226;Sample txt all the best dude";
		               //  TextView description= ((TextView) dialog.findViewById(R.id.sample));
		             //	 description.setText(Html.fromHtml(desc));
		                // description.setText("pLEASE COME PLEASE COME");

		 			//	dialog.show();
		 				
		 				//Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);

		 			/*	dialogButton.setOnClickListener(new OnClickListener() {
		 					@Override
		 					public void onClick(View v) {
		 						dialog.dismiss();
		 					}
		 				});

		 				
		            	 
		            	 
		            	 
		            	 
		            	 
		            	 final Dialog d = new Dialog(ChallengeLevelActivity.this);
		                 d.setContentView(R.layout.dialog);
		                 d.setTitle("This is custom dialog box");
		                
		                 String desc ="<br/>&#8226;Guess the song played<br/>&#8226;Faster you choose the correct answer, more you get the points<br/>&#8226;Sample txt all the best dude";
		                 TextView description= ((TextView) d.findViewById(R.id.sample));
		             	 description.setText(Html.fromHtml(desc));
		                 description.setText("pLEASE COME PLEASE COME");
		                 d.show();  */
		             }
		         });



	}

		@Override
		public void onBackPressed()
		{
			// check for already playing
		//	countDownTimer.cancel();
		//	cntr_aCounter.cancel();	
		//	 mp.stop();
			// mp.release();
			// mp = null;
			MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
    	    mp.start();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);

	           startActivity(i);
	           overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		}
	}