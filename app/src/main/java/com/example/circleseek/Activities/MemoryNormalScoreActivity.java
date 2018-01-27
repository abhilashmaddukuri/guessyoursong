package com.example.circleseek.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.guessursongs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MemoryNormalScoreActivity extends Activity
	{
//private long memory_score_timeleft = 0;
private String memory_score;
private static ArrayList<HashMap<String, String>> memory_score_answer = new ArrayList<HashMap<String, String>>();
	public int ncorrect = 0;
		public int nwrong = 0;
		//public int best = 0;
		private int memory_check;
		private int mnormal_score_songs_inbank_normal;
		private long mhigh;
		private String mnormal_best;
		//private final long startTime = 5000;
		//private final long interval = 5000;

		/** Called when the activity is first created. */
		@SuppressWarnings("unchecked")
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				//Intent intent = getIntent();
				
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					memory_score = extras.getString("mnormal_time");
					memory_check = extras.getInt("mnormal_flag");
					 mhigh = extras.getLong("mnormal_best_total_score");
						mnormal_score_songs_inbank_normal = extras.getInt("mnormal_songs_inbank");
				}	
					
					
					Log.v("Memory ScoreBoardActivity","Memory time:"+memory_score);
				
				if(memory_check == 0) // User gives correct input
				{
					
					mnormal_best = String.format("%02d:%02d", 
						    TimeUnit.MILLISECONDS.toMinutes(mhigh),
						    TimeUnit.MILLISECONDS.toSeconds(mhigh) - 
						    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mhigh))
						);
					setContentView(R.layout.memory_score_layout);
					
					
					TextView mnormal_score_songs_inbank = (TextView)findViewById(R.id.songs_inbank_memory);
					mnormal_score_songs_inbank.setText(""+mnormal_score_songs_inbank_normal);
					
					
					TextView time_taken = (TextView)findViewById(R.id.time_taken);
					
					time_taken.setText(""+memory_score);
					TextView best = (TextView)findViewById(R.id.best);
					best.setText(""+mnormal_best);
				}

				else 
				{
					setContentView(R.layout.memory_normal_fail);
					if(memory_check == 2)	// user doesn't give any input
					{
						TextView heading = (TextView)findViewById(R.id.heading);
						heading.setText("OOPS! Time Up"+mnormal_score_songs_inbank_normal);
					}
					else if(memory_check == 1)	// User failed to give correct input
					{
						TextView heading = (TextView)findViewById(R.id.heading);
						heading.setText("OOPS! Wrong Answer"+mhigh);
					}
					 memory_score_answer = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("answer_topass");
					 Log.v("Memory score","Memory score"+memory_score_answer.size());
						for(int j=0; j<memory_score_answer.size(); j++)
						{
							Log.v("Memory score","Memory score"+memory_score_answer.get(j));	
						}
					 
					//TextView answer_text = (TextView)findViewById(R.id.answers);
					 //String option1;
					 String[] ans = new String[4];
					 final TextView option1=(TextView)findViewById(R.id.option1);
					 final TextView option2=(TextView)findViewById(R.id.option2);
					 final TextView option3=(TextView)findViewById(R.id.option3);
					 final TextView option4=(TextView)findViewById(R.id.option4);
				      
					
					for(int j=0; j<memory_score_answer.size(); j++)
					{
						ans[j] = memory_score_answer.get(j).get("songtitle");;
							
					}
					  option1.setText("1. "+ans[0]);
					  option2.setText("2. "+ans[1]);
				
					  
					  Button next = (Button) this.findViewById(R.id.next);
						next.setOnClickListener(new OnClickListener() 
					     {
				             @Override
				             public void onClick(View arg0)
				             {
				            	 Intent i = new Intent(getApplicationContext(), MemoryNormalAfterNextActivity.class);
				            	 i.putExtra("mhigh",mhigh);
				             	i.putExtra("mnormal_score_songs_inbank",mnormal_score_songs_inbank_normal);
				                     startActivity(i);

				             }
					     });

					
				}
				
				
			}

	}