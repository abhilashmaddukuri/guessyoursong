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
import android.graphics.Typeface;
import android.media.MediaPlayer;
 import android.os.Bundle;
import android.os.CountDownTimer;
 import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

 import android.widget.TextView;

 import com.example.circleseek.SongsManager;
 import com.guessursongs.R;


public class MemoryInsaneOptionsActivity extends Activity
{

	private MyCountDownTimer_minsane countDownTimer_minsane;
	
	public ArrayList<HashMap<String, String>> memory_ans = new ArrayList<HashMap<String, String>>();
	SharedPreferences minsane_preference;	
	public long minsane_best_total_score;
	public int minsane_songs_inbank = 0;
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
	private static long startTime = 12000;
	private static long interval = 1000;
	public CountDownTimer cntr_aCounter;
	private TextView textViewTime;
	public int ncorrect = 0, nwrong = 0;
	
  private static int memory_flag_insane = 0; // 0 if user gives correct answer
	 
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
		setContentView(R.layout.memory_insane_options);

		
			
		
		// Font path
        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";
 
 
        // Loading Font Face
        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
       
		
		
        TextView sampletext= ((TextView) findViewById(R.id.sampletext));
		sampletext.setTypeface(tf_bold);		
		
		mem = new MemoryNormalActivity();
		songManager = new SongsManager();
        countDownTimer_minsane = new MyCountDownTimer_minsane(startTime, interval);
		
        textViewTime = (TextView)findViewById(R.id.textView1); 
		countDownTimer_minsane.start();
		songsList = songManager.getPlayList();
		
	//	 memory_highscore = this.getSharedPreferences("best_time", Context.MODE_PRIVATE);
	//	 memory_best = memory_highscore.getLong("memory_highscore", 10000); //10000 is the default value
		 
		minsane_preference = this.getSharedPreferences("minsane_mypref", Context.MODE_PRIVATE);    
		 minsane_best_total_score = minsane_preference.getLong("minsane_best_total_score", 10000); //0 is the default value.
		 
		 
		 
		textViewTime.setText("raavatledhu ra..:("); 
		//memory_ans = new ArrayList<HashMap<String, String>>(memory_answers);
		Intent intent = getIntent();
		final ArrayList<HashMap<String,String>> ruthugod = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("memory_answers"); 
		Bundle extras = getIntent().getExtras();
		minsane_songs_inbank = extras.getInt("insane_songs_inbank");
		
		Log.v("memory_options","Please come here:::"+ruthugod.size());
		for(int j=0; j<ruthugod.size(); j++)
		{
			Log.v("Memory answers","Memory answers"+ruthugod.get(j));
				pattern_one = ruthugod.get(0).get("songtitle");
				pattern_two = ruthugod.get(1).get("songtitle");
				pattern_three = ruthugod.get(2).get("songtitle");
				pattern_four = ruthugod.get(3).get("songtitle");
			
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
		
		currentSongIndex = randd.nextInt(ruthugod.size());
		final String option3 = ruthugod.get(currentSongIndex).get("songtitle").toUpperCase();
		Log.v("Option three is ",""+option3);
		Log.v("patter three is ",""+pattern_three);
		ruthugod.remove(currentSongIndex);
		
		currentSongIndex = randd.nextInt(ruthugod.size());
		final String option4 = ruthugod.get(currentSongIndex).get("songtitle").toUpperCase();
		Log.v("Option four is ",""+option4);
		Log.v("patter four is ",""+pattern_four);
		ruthugod.remove(currentSongIndex);
		
		
		
		Log.v("size","size of answer_topass: "+answer_topass.size());
		for(int k=0; k<answer_topass.size(); k++)
		{
			Log.v("answer_topass","answer_topass"+answer_topass.get(k));
			
		}
		
		final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.left_in);
		
		final Button mButton1=(Button)findViewById(R.id.ib1);
		mButton1.startAnimation(animAnticipateOvershoot_one);
        mButton1.setText(option1);
        
        
        final Button mButton2=(Button)findViewById(R.id.ib2);
        mButton2.startAnimation(animAnticipateOvershoot_one);
        mButton2.setText(option2);
        
        final Button mButton3=(Button)findViewById(R.id.ib3);
        mButton3.startAnimation(animAnticipateOvershoot_one);
        mButton3.setText(option3);
        
        final Button mButton4=(Button)findViewById(R.id.ib4);
        mButton4.startAnimation(animAnticipateOvershoot_one);
        mButton4.setText(option4);

        
        
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
            		mButton1.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
        			Log.v("Awesome dude","Awesome dude"+button_position);	
             	}
            	else
            	{
            		Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        			
        			v1.vibrate(150);
            		// Wrong answer fuck away dude..
            		countDownTimer_minsane.cancel();
            		memory_flag_insane = 1; // user fails to give correct answer.
            		mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                    i.putExtra("minsane_time", time_completed);
                    i.putExtra("memory_flag_insane", memory_flag_insane);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("minsane_best_total_score", minsane_best_total_score);
                    i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		if(option1.equalsIgnoreCase(pattern_two))
            		{
            			button_position++;
            			mButton1.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
            			memory_flag_insane = 1; // user fails to give correct answer.
            			mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 2 && check == 0)
            	{
            		check++;
            		if(option1.equalsIgnoreCase(pattern_three))
            		{
            			button_position++;
            			mButton1.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background

            			}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 3 && check == 0)
            	{
            		check++;
            		if(option1.equalsIgnoreCase(pattern_four))
            		{
            			countDownTimer_minsane.cancel();
            			button_position++;
            			mButton1.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                		memory_flag_insane = 0;
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		
                		editor = minsane_preference.edit();
                  		 
                		if(minsane_best_total_score > time_countDownTimer)
                		{
                			minsane_best_total_score = time_countDownTimer;
                			editor.putLong("minsane_best_total_score", minsane_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
               		 
               		 editor.commit();
            			
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton1.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
            		
            		if(option2.equalsIgnoreCase(pattern_one))
            	{
            			button_position++;
            			mButton2.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            		Log.v("Awesome dude","Awesome dude"+button_position);
             	}
            	else
            	{
            		Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        			
        			v1.vibrate(150);
            		// // Wrong answer fuck away dude..
            		countDownTimer_minsane.cancel();
            		memory_flag_insane = 1; // user fails to give correct answer.
            		mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                    i.putExtra("minsane_time", time_completed);
                    i.putExtra("memory_flag_insane", memory_flag_insane);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("minsane_best_total_score", minsane_best_total_score);
                    i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		
            		if(option2.equalsIgnoreCase(pattern_two))
            		{
            			button_position++;
            			mButton2.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 2 && check == 0)
            	{
            		check++;
            		
            		if(option2.equalsIgnoreCase(pattern_three))
            		{
            			button_position++;
            			mButton2.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	
            	if(button_position == 3 && check == 0)
            	{
            		check++;
            		
            		if(option2.equalsIgnoreCase(pattern_four))
            		{
            			countDownTimer_minsane.cancel();
            			button_position++;
            			mButton2.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                		memory_flag_insane = 0;
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		
                		editor = minsane_preference.edit();
                 		 
                		if(minsane_best_total_score > time_countDownTimer)
                		{
                			minsane_best_total_score = time_countDownTimer;
                			editor.putLong("minsane_best_total_score", minsane_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
               		 
               		 editor.commit();
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton2.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	
            }
           
        });



mButton3.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
            	int check = 0;
              
				// Perform action on click
            	if(button_position == 0 && check == 0)
            	{
            		 check++;
            		 
            	if(option3.equalsIgnoreCase(pattern_one))
            	{
            		button_position++;
            		mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            		Log.v("Awesome dude","Awesome dude"+button_position);
             	}
            	else
            	{
            		Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        			
        			v1.vibrate(150);
            		// Wrong answer fuck away dude..
            		countDownTimer_minsane.cancel();
            		memory_flag_insane = 1; // user fails to give correct answer.
            		mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                    i.putExtra("minsane_time", time_completed);
                    i.putExtra("memory_flag_insane", memory_flag_insane);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("minsane_best_total_score", minsane_best_total_score);
                    i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		
            		if(option3.equalsIgnoreCase(pattern_two))
            		{
            			button_position++;
            			mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 2 && check == 0)
            	{
            		check++;
            		
            		if(option3.equalsIgnoreCase(pattern_three))
            		{
            			button_position++;
            			mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            			
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 3 && check == 0)
            	{
            		check++;
            		
            		if(option3.equalsIgnoreCase(pattern_four))
            		{
            			
            			countDownTimer_minsane.cancel();
            			button_position++;
                		memory_flag_insane = 0;
                		mButton3.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		
                		editor = minsane_preference.edit();
                 		 
                		if(minsane_best_total_score > time_countDownTimer)
                		{
                			minsane_best_total_score = time_countDownTimer;
                			editor.putLong("minsane_best_total_score", minsane_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
               		 
               		 editor.commit();
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton3.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	
            	}
            	
            
            
        });
		


mButton4.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View v) {
              int check = 0;
				// Perform action on click
            	if(button_position == 0 && check == 0)
            	{
            		check++;
            		
            	if(option4.equalsIgnoreCase(pattern_one))
            	{
            		button_position++;
            		mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            		Log.v("Awesome dude","Awesome dude"+button_position);
             	}
            	else
            	{
            		Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        			
        			v1.vibrate(150);
            		// Wrong answer fuck away dude..
            		countDownTimer_minsane.cancel();
            		memory_flag_insane = 1; // user fails to give correct answer.
            		mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
            		Log.v("Wrong answer dude","Wrong");
            		Log.v("time_left_countDownTimer","time_completed"+time_completed);
            		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                    i.putExtra("minsane_time", time_completed);
                    i.putExtra("memory_flag_insane", memory_flag_insane);
                    i.putExtra("answer_topass", answer_topass);
                    i.putExtra("minsane_best_total_score", minsane_best_total_score);
                    i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
            	}
            	}
            	if(button_position == 1 && check == 0)
            	{
            		check++;
            		if(option4.equalsIgnoreCase(pattern_two))
            		{
            			button_position++;
            			mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 2 && check == 0)
            	{
            		check++;
            		
            		if(option4.equalsIgnoreCase(pattern_three))
            		{
            			button_position++;
            			mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
            			Log.v("Awesome dude","Awesome dude"+button_position);
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	if(button_position == 3 && check == 0)
            	{
            		check++;
            		
            		if(option4.equalsIgnoreCase(pattern_four))
            		{
            			
            			countDownTimer_minsane.cancel();
            			button_position++;
                		memory_flag_insane = 0;
                		Log.v("Awesome dude","Awesome dude"+button_position);
                		mButton4.setBackgroundResource(R.drawable.buttonshape_green);   // set background color for green background
                		editor = minsane_preference.edit();
                 		 
                		if(minsane_best_total_score > time_countDownTimer)
                		{
                			minsane_best_total_score = time_countDownTimer;
                			editor.putLong("minsane_best_total_score", minsane_best_total_score);
                		}
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("minsane_flag", memory_flag_insane);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
               		 
               		 editor.commit();
            		}
            		else
            		{
            			Vibrator v1 = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            			
            			v1.vibrate(150);
            			// Wrong answer fuck away dude..
            			countDownTimer_minsane.cancel();
                		memory_flag_insane = 1; // user fails to give correct answer.
                		mButton4.setBackgroundResource(R.drawable.buttonshape_red);   // set background color for red background
                		Log.v("Wrong answer dude","Wrong");
                		Log.v("time_left_countDownTimer","time_completed"+time_completed);
                		Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
                        i.putExtra("minsane_time", time_completed);
                        i.putExtra("memory_flag_insane", memory_flag_insane);
                        i.putExtra("answer_topass", answer_topass);
                        i.putExtra("minsane_best_total_score", minsane_best_total_score);
                        i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
            		}
            	}
            	
            }
           
        });
	



	}
	
	@Override
	public void onBackPressed()
	{
		// check for already playing
		//countDownTimer.cancel();
		//cntr_aCounter.cancel();	
		countDownTimer_minsane.cancel();
		// media.stop();
		// mp.release();
		// mp = null;
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
	    mp.start();
		 Intent i = new Intent(getApplicationContext(), MemoryLevelActivity.class);

           startActivity(i);
           overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
		
	public class MyCountDownTimer_minsane extends CountDownTimer
	{
		//public long time_left_countDownTimer;
        public MyCountDownTimer_minsane(long startTime, long interval) 
        {
        	super(startTime, interval);
        }
        @Override
        public void onFinish() 
        {
        	Log.v("memory insane onfinish","memory on finish insane");
       
        	
        	memory_flag_insane = 2;
        	Intent i = new Intent(getApplicationContext(), MemoryInsaneScoreActivity.class);
        	//i.putExtra("memory_answers",memory_answers);
        	i.putExtra("minsane_time", time_completed);
        	i.putExtra("memory_flag_insane", memory_flag_insane);
            i.putExtra("answer_topass", answer_topass_ontimeup);
            i.putExtra("minsane_best_total_score", minsane_best_total_score);
            i.putExtra("minsane_songs_inbank", minsane_songs_inbank);
               startActivity(i);
               overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

        @Override
        public void onTick(long millisUntilFinished) 
        {
        	Log.v("memory insane ontick","memory insane ontick");
        	time_left_countDownTimer = millisUntilFinished;
        	//time_countDownTimer = (startTime + 1000) - time_left_countDownTimer;
        	long millis = millisUntilFinished; 
        	
        	
        	
        	int minutes = (int) ((millis / (1000*60)) % 60);
        	
        	String min = String.valueOf(minutes);
        	
        	//String test = String.format("%d", time_countDownTimer);
        	//time_completed = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time_countDownTimer) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time_countDownTimer)), TimeUnit.MILLISECONDS.toSeconds(time_countDownTimer) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time_countDownTimer)));
        	String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        	System.out.println(hms); 
        	//textViewTime.setText(hms);
        	//textViewTime.setText(min);
        	//textViewTime.setText("" + millisUntilFinished / 1000);
        	//Log.v("onTick","");
        	

        	time_countDownTimer = (((startTime)/1000) - (millisUntilFinished / 1000));
        	
        	time_completed = String.format(""+time_countDownTimer);
        	textViewTime.setText("" + millisUntilFinished / 1000);
        	Log.v("onTick","");
        	
        	//  cuentaRegresiva.setText(""+millisUntilFinished/1000);
                              
        }
    }
	
}  