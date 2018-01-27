package com.example.circleseek.Activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;

import com.example.circleseek.SongsManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guessursongs.R;


public class MemoryNormalOptionsActivity extends Activity
{

	public MyCountDownTimer countDownTimer;
	
	public ArrayList<HashMap<String, String>> memory_ans = new ArrayList<HashMap<String, String>>();
	SharedPreferences mnormal_preference;
	public long mnormal_best_total_score;
	public int mnormal_songs_inbank = 0;
	public int best = 0;
	public ImageButton btnPlay;

	// Media Player
	public  MediaPlayer mp;
	private MemoryNormalActivity mem;
	// Handler to update UI timer, progress bar etc,.
	
	private SongsManager songManager;
	 
	public int x=0;
	private static long time_left_countDownTimer = 0;
	private static long time_countDownTimer = 0;
	private static String time_completed;
	
	String variable = "ruthvik";
	private static long startTime = 8000;
	
	private static long interval = 1000;
	public CountDownTimer cntr_aCounter;
	public TextView textViewTime;
	public int ncorrect = 0, nwrong = 0;
	
  private static int memory_flag = 0; // 0 if user gives correct answer
	 
	public final String option1= null, option2= null, option3= null, option4= null, answer= null;
	private String pattern_one = null;

	private String pattern_two = null;

	private String pattern_three = null;

	private String pattern_four = null;
	
	private int button_position = 0;
	
	private Editor editor;
	
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	//private ArrayList<HashMap<String, String>> ruthugod = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> answer_topass_ontimeup = new ArrayList<HashMap<String, String>>();
	public HashMap<String, String> map = new HashMap<String, String>();
	public HashMap<String, String> mapp = new HashMap<String, String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory_normal_options);
		
		// advertisement ki code here.......	
				AdView mAdView = (AdView) findViewById(R.id.adView);
		        AdRequest adRequest = new AdRequest.Builder().build();
		        mAdView.loadAd(adRequest);
		// End of advertisements ki code here....	
		
		
		
		mem = new MemoryNormalActivity();
		songManager = new SongsManager();
        countDownTimer = new MyCountDownTimer(startTime, interval);
		
        textViewTime = (TextView)findViewById(R.id.textView1); 
		countDownTimer.start();
		songsList = songManager.getPlayList();
		
		// memory_highscore = this.getSharedPreferences("best_time", Context.MODE_PRIVATE);
		// memory_best = memory_highscore.getLong("memory_highscore", 10000); //10000 is the default value
		 
		 mnormal_preference = this.getSharedPreferences("mnormal_mypref", Context.MODE_PRIVATE);    
		 mnormal_best_total_score = mnormal_preference.getLong("mnormal_best_total_score", 10000); //0 is the default value.
		 
		 		 
		textViewTime.setText("raavatledhu ra..:("); 
		//memory_ans = new ArrayList<HashMap<String, String>>(memory_answers);
		Intent intent = getIntent();
		final ArrayList<HashMap<String,String>> ruthugod = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("memory_answers"); 
		Bundle extras = getIntent().getExtras();
		mnormal_songs_inbank = extras.getInt("normal_songs_inbank");
		
		Log.v("memory_options","Please come here:::"+ruthugod.size());
		for(int j=0; j<ruthugod.size(); j++)
		{
			Log.v("Memory answers","Memory answers"+ruthugod.get(j));
				pattern_one = ruthugod.get(0).get("songtitle");
				pattern_two = ruthugod.get(1).get("songtitle");
				//pattern_three = ruthugod.get(2).get("songtitle");
				
			
		}
		final ArrayList<HashMap<String, String>> answer_topass = new ArrayList<HashMap<String, String>>(ruthugod);
		
		
		answer_topass_ontimeup = new ArrayList<HashMap<String, String>>(answer_topass);
		
		Random randd = new Random();
		
		int currentSongIndex = randd.nextInt(ruthugod.size());
		final String option1 = ruthugod.get(currentSongIndex).get("songtitle").toUpperCase();
		
		Log.v("Option one is ",""+option1);
		Log.v("patter one is ",""+pattern_one);
		
		ruthugod.remove(currentSongIndex);
		currentSongIndex = randd.nextInt(ruthugod.size());
		final String option2 = ruthugod.get(currentSongIndex).get("songtitle").toUpperCase();
		Log.v("Option two is ",""+option2);
		Log.v("patter two is ",""+pattern_two);
		ruthugod.remove(currentSongIndex);
		
		
		
		
		Log.v("size","size of answer_topass: "+answer_topass.size());
		for(int k=0; k<answer_topass.size(); k++)
		{
			Log.v("answer_topass","answer_topass"+answer_topass.get(k));
			
		}
		final Button mButton1=(Button)findViewById(R.id.ib1);
        mButton1.setText(option1);
        
        
        final Button mButton2=(Button)findViewById(R.id.ib2);
        mButton2.setText(option2);
        
        
      

        
        
mButton1.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
            	int check = 0;
            	Log.v("mbutton1","onclic button one:::");
            	for(int k=0; k<answer_topass.size(); k++)
        		{
        			Log.v("Memory options","Memory options"+answer_topass.get(k));
        			
        		}
				// Perform action on click
            	if(button_position == 0 && check == 0)
            	{
            		check++;
            	if(option1.equalsIgnoreCase(pattern_one))
            	{
            		button_position++;
            		mButton1.setBackgroundResource(R.drawable.btn_clicked_right);   // set background color for green background
        			Log.v("Awesome dude","Awesome dude"+button_position);	
             	}
            	else
            	{
            		// Wrong answer fuck away dude..
            		countDownTimer.cancel();
            		memory_flag = 1; // user fails to give correct answer.
            		mButton1.setBackgroundResource(R.drawable.btn_clicked_wrong);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                    i.putExtra("mnormal_time", time_completed);
                    i.putExtra("mnormal_flag", memory_flag);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                    i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		if(option1.equalsIgnoreCase(pattern_two))
            		{

            			countDownTimer.cancel();
            			button_position++;
                		memory_flag = 0;
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		mButton1.setBackgroundResource(R.drawable.btn_clicked_right);   // set background color for green background
                		
                		editor = mnormal_preference.edit();
                  		 
                		if(mnormal_best_total_score > time_countDownTimer)
                		{
                			mnormal_best_total_score = time_countDownTimer;
                			editor.putLong("mnormal_best_total_score", mnormal_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                        i.putExtra("mnormal_time", time_completed);
                        i.putExtra("mnormal_flag", memory_flag);
                        i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                        i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                        
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               		 
               		 editor.commit();
            		}
            		else
            		{
            			// Wrong answer fuck away dude..
            			countDownTimer.cancel();
            			memory_flag = 1; // user fails to give correct answer.
            			mButton1.setBackgroundResource(R.drawable.btn_clicked_wrong);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                        i.putExtra("mnormal_time", time_completed);
                        i.putExtra("mnormal_flag", memory_flag);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                        i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            		}
            	}
            	
            }
           
        });
		


mButton2.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
              int check = 0;
				// Perform action on click
            	if(button_position == 0 && check == 0)
            	{
            		check++;
            		button_position++;
            		if(option2.equalsIgnoreCase(pattern_one))
            	{
            			mButton2.setBackgroundResource(R.drawable.btn_clicked_right);   // set background color for green background
            		Log.v("Awesome dude","Awesome dude"+button_position);
             	}
            	else
            	{
            		// // Wrong answer fuck away dude..
            		countDownTimer.cancel();
            		memory_flag = 1; // user fails to give correct answer.
            		mButton1.setBackgroundResource(R.drawable.btn_clicked_wrong);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                    i.putExtra("mnormal_time", time_completed);
                    i.putExtra("mnormal_flag", memory_flag);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                    i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		button_position++;
            		if(option2.equalsIgnoreCase(pattern_two))
            		{
            			
            			countDownTimer.cancel();
            			button_position++;
            			mButton2.setBackgroundResource(R.drawable.btn_clicked_right);   // set background color for green background
                		memory_flag = 0;
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		
                		editor = mnormal_preference.edit();
                 		 
                		if(mnormal_best_total_score > time_countDownTimer)
                		{
                			mnormal_best_total_score = time_countDownTimer;
                			editor.putLong("mnormal_best_total_score", mnormal_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                        i.putExtra("mnormal_time", time_completed);
                        i.putExtra("mnormal_flag", memory_flag);
                        i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                        i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               		 
               		 editor.commit();
            		}
            		else
            		{
            			// Wrong answer fuck away dude..
            			countDownTimer.cancel();
                		memory_flag = 1; // user fails to give correct answer.
                		mButton2.setBackgroundResource(R.drawable.btn_clicked_wrong);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
                        i.putExtra("mnormal_time", time_completed);
                        i.putExtra("mnormal_flag", memory_flag);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
                        i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            		}
            	}
            	
            	
            	
            }
           
        });
	}
	
		
	public class MyCountDownTimer extends CountDownTimer
	{
		//public long time_left_countDownTimer;
        public MyCountDownTimer(long startTime, long interval) 
        {
        	super(startTime, interval);
        }
        @Override
        public void onFinish() 
        {
        	memory_flag = 2;
        	Intent i = new Intent(getApplicationContext(), MemoryNormalScoreActivity.class);
        	//i.putExtra("memory_answers",memory_answers);
        	i.putExtra("mnormal_time", time_completed);
        	i.putExtra("mnormal_flag", memory_flag);
            i.putExtra("answer_topass", answer_topass_ontimeup);
            i.putExtra("mnormal_best_total_score", mnormal_best_total_score);
            i.putExtra("mnormal_songs_inbank", mnormal_songs_inbank);
               startActivity(i);
               overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }

        @Override
        public void onTick(long millisUntilFinished) 
        {
        	time_left_countDownTimer = millisUntilFinished;
        	time_countDownTimer = (startTime + 1000) - time_left_countDownTimer;
        	long millis = millisUntilFinished; 
        	//String test = String.format("%d", time_countDownTimer);
        	time_completed = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time_countDownTimer) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time_countDownTimer)), TimeUnit.MILLISECONDS.toSeconds(time_countDownTimer) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time_countDownTimer)));
        	String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        	System.out.println(hms); 
        	textViewTime.setText(hms); 
        	Log.v("onTick","");
        	
        	//  cuentaRegresiva.setText(""+millisUntilFinished/1000);
                              
        }
    }
	
}  