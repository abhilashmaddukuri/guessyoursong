

package com.example.circleseek.Activities;
//package com.google.android.gms:play-services:6.5.87;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guessursongs.R;

public class MemoryNormalAfterNextActivity extends Activity
	{
	//public int mnormal_score_songs_inbank_normal_an = 0;
	//public long mhigh_after_next = 0;
	
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.memory_after_next);
				int mnormal_score_songs_inbank_normal_an = 0;
				long mhigh_after_next = 0;
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					mnormal_score_songs_inbank_normal_an= extras.getInt("mnormal_score_songs_inbank");
					mhigh_after_next= extras.getLong("mhigh");
				}
				
				
				TextView mnormal_score_songs_inbank = (TextView)findViewById(R.id.songs_inbank_memory);
				mnormal_score_songs_inbank.setText(""+mnormal_score_songs_inbank_normal_an);
				
				
				TextView time_taken = (TextView)findViewById(R.id.time_taken);
				
				//time_taken.setText(""+memory_score);
				TextView best = (TextView)findViewById(R.id.best);
				best.setText(""+mhigh_after_next);

				Button share = (Button) findViewById(R.id.share);
			    share.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        
			        	String message = "Text I want to share.";
			        			Intent share = new Intent(Intent.ACTION_SEND);
			        			share.setType("text/plain");
			        			share.putExtra(Intent.EXTRA_TEXT, message);

			        			startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
			        	

			        }
			    });
			    
			    Button replay = (Button) findViewById(R.id.replay);
			    replay.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			        	Intent i = new Intent(getApplicationContext(), MemoryNormalActivity.class);
			               startActivity(i);

			        }
			    });
	}


	}