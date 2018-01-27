

package com.example.circleseek.Activities;
//package com.google.android.gms:play-services:6.5.87;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guessursongs.R;

public class ErrorCatchingActivity extends Activity
	{
		

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.error_catching);
				
				TextView heading= ((TextView) findViewById(R.id.heading));
				
				// Font path
		        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
		        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";
		 
		 
		        // Loading Font Face
		        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
		 
		        //TextView heading= ((TextView) findViewById(R.id.heading));
		        heading.setTypeface(tf_bold);
				
	 				//String desc ="<br/>&#8226;Guess the song played<br/>&#8226;Faster you choose the correct answer, more you get the points<br/>&#8226;Sample txt all the best dude";
		        String desc ="<br/>-Your playlist doesn't contain songs. (or)<br/>-Songs are not present in correct directory (in 'Music'/'Bluetooth' folder). (or)<br/>-Some unknown issue.";
		        TextView description= ((TextView) findViewById(R.id.description));
	 				
	 				//    TextView description= ((TextView).findViewById(R.id.description));
	                description.setText(Html.fromHtml(desc));
	             // Applying font
			        description.setTypeface(tf_bold);    
			        
			        
			        Button back = (Button) findViewById(R.id.back);
				    back.setOnClickListener(new View.OnClickListener() {
				        @Override
				        public void onClick(View v) {
				        	MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
		            	    mp.start();
				        	 Intent i = new Intent(getApplicationContext(), MenuSelectionActivity.class);
			                 //   f i.putExtra("id", position);
			                     startActivity(i);
			                     overridePendingTransition(R.anim.left_in, R.anim.right_out);
			            	 
				        }
				    });
				
				
			}
		@Override
		public void onBackPressed()
		{
			MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
    	    mp.start();
			 Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

	           startActivity(i);
	           overridePendingTransition(R.anim.left_in, R.anim.right_out);
		}
	}