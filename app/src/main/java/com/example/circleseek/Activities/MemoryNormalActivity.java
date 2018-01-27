package com.example.circleseek.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.circleseek.SongsManager;
import com.example.circleseek.Utils.Utilities;
import com.example.circleseek.Views.CircularProgressBar;
import com.example.circleseek.Views.CircularProgressBar.ProgressAnimationListener;
import com.guessursongs.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MemoryNormalActivity extends Activity  {

	public MyCountDownTimer countDownTimer;
	public ImageButton btnPlay;
	private CircularProgressBar c_memory;

	public int normal_songs_inbank = 0;
	private  MediaPlayer media;

	private Handler mHandler = new Handler();;
	private SongsManager songManager;
	private Utilities utils;
	private int currentSongIndex = 0; 
	public int x=0;
	String variable = "ruthvik";
	private final long startTime = 24000;
	private final long interval = 1000;
	public CountDownTimer cntr_aCounter;
	public TextView textViewTime;
	public CircularProgressBar c1 ;
	private String songTitle;
	public HashMap<String, String> mapp = new HashMap<String, String>();
	private ArrayList<HashMap<String, String>> memory_answers = new ArrayList<HashMap<String, String>>();
	
	public final String option1= null, option2= null, option3= null, option4= null, answer= null;
	
	
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	public ArrayList<HashMap<String, String>> songsListnew = new ArrayList<HashMap<String, String>>();
	public ArrayList<HashMap<String, String>> options = new ArrayList<HashMap<String, String>>();
	public HashMap<String, String> map = new HashMap<String, String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory_layout);
		
		 // All player buttons
		 textViewTime = (TextView)findViewById(R.id.textView1);
			btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		
			media = new MediaPlayer();
			songManager = new SongsManager();
			utils = new Utilities();
			
		
			songsList = songManager.getPlayList();
		
        countDownTimer = new MyCountDownTimer(startTime, interval);
		
		countDownTimer.start();
		
		//Stop background playing songs and play current songs
				AudioManager am =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
				OnAudioFocusChangeListener afChangeListener = null;
				
			     // Request audio focus for playback
			     int result = am.requestAudioFocus(afChangeListener,
			     // Use the music stream.
			     AudioManager.STREAM_MUSIC,
			     // Request permanent focus.
			     AudioManager.AUDIOFOCUS_GAIN);

			     
			     if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			     // other app had stopped playing song now , so u can do u stuff now .
			     }
		
		function();
			
	}
	
	public void  function()
	{
	// media.stop(); 
		c_memory = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
		c_memory.progress_duration(12000);
		c_memory.animateProgressTo(0,100, new ProgressAnimationListener(){
			public void onAnimationStart(){
				
			}
			
			public void onAnimationProgress(int progress) {
				c_memory.setTitle(progress + "%");
			}
			
			@Override
			public void onAnimationFinish() {
				c_memory.setSubTitle("done");
			}
		});
		
		Log.v("Function ki vachindii","came to function");
		 c1 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
			c1.animateProgressTo(0,100, new ProgressAnimationListener(){
				public void onAnimationStart(){
					
				}
				
				public void onAnimationProgress(int progress) {
					c1.setTitle(progress + "%");
				}
				
				@Override
				public void onAnimationFinish() {
					c1.setSubTitle("done");
				}
			});
		
		Random randobj = new Random();
		
		for(int i=0; i< songsList.size();i++)
		{
			Log.v("Data in songsList",""+songsList.get(i));
			
		}
		
		songsListnew = new ArrayList<HashMap<String, String>>(songsList);
		
		normal_songs_inbank = songsListnew.size();
		currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
		int FirstsongIndex=currentSongIndex;
		songTitle = songsListnew.get(currentSongIndex).get("songTitle");
		songsListnew.remove(currentSongIndex);
		mapp = new HashMap<String, String>();
		mapp.put("constant", variable);
		mapp.put("songtitle", songTitle);
		memory_answers.add(mapp);
		
		
		Log.v("dude check here.memory","Answers array");
		for(int i=0; i< memory_answers.size();i++)
		{
			Log.v("dude check here.memory","Answers array"+memory_answers.get(i));
			
		}
		
		long duration = media.getDuration();

		map = new HashMap<String, String>();
        map.put("constant", variable);
        map.put("songtitle", songTitle);
        options.add(map);
        memory_song(FirstsongIndex);
        if(duration >= 30000)
        {
        	media.seekTo(15000);
        }
        else
        {
        	
        }
        
        final int t=0;
        		
        cntr_aCounter = new CountDownTimer(12000, 1000) {
            public void onTick(long millisUntilFinished) {
            	x = t+1;             
            }

            public void onFinish() {
                //code fire after finish
             Log.v("Verify","Number of onTick: "+x);
            	  
                   function();
            }
            };
            cntr_aCounter.start();
        
        
        //mp.setProgress(currentPosition);
        // mHandler.removeCallbacks(mUpdateTimeTask);
        Log.v("After first","First song play"+FirstsongIndex);
		
		currentSongIndex = randobj.nextInt((songsListnew.size() - 1) - 0 + 1) + 0;
		String songTitle1 = songsListnew.get(currentSongIndex).get("songTitle");
		songsListnew.remove(currentSongIndex);
		map = new HashMap<String, String>();
        map.put("constant", variable);
        map.put("songtitle", songTitle1);
        options.add(map);
        Log.v("After second","Second song option"+currentSongIndex);
        
		for(int i=0; i< songsListnew.size();i++)
		{
			Log.v("Data in songsListnew",""+songsListnew.get(i));
			
		}
		
        
        
        
        
        
       for(int i=0; i< options.size();i++)
		{
			Log.v("Data in options",""+options.get(i));
			
		}
        
     
        		
       
        
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				countDownTimer.start();
				cntr_aCounter.cancel();	
				 media.stop();
				 Intent i = new Intent(getApplicationContext(), MenuSelectionActivity.class);
		
		           startActivity(i);
		           overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						// Changing button image to play button
						//btnPlay.setImageResource(R.drawable.btn_stop);
					
				
			}
		});
		
	
		
		
		
	}
	
	
	
	
	/**
	 * Receiving song index from playlist view
	 * and play the song
	 * */
	@Override
    protected void onActivityResult(int requestCode,
                                     int resultCode, Intent data) {
       
		
 
    }
	
	/**
	 * Function to play a song
	 * @param songIndex - index of song
	 * */
	public void  memory_song(int songIndex){
		// Play song
		try {
        	media.reset();
			media.setDataSource(songsList.get(songIndex).get("songPath"));
			media.prepare();
			media.start();
			// Displaying Song title
		//	String songTitle = songsList.get(songIndex).get("songTitle");
        //	songTitleLabel.setText(songTitle);
			
        	// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.btn_stop);
			
		
			
			// Updating progress bar
			updateProgressBar();			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update timer on seekbar
	 * */
	public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 10);        
    }	
	
	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   long totalDuration = media.getDuration();
			   long currentDuration = media.getCurrentPosition();
			  
			   // Displaying Total Duration time
			//   songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
			   // Displaying time completed playing
			//   songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
			   
			   // Updating progress bar
			   int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
			   //Log.d("Progress", ""+progress);
			
			   
			   // Running this thread after 100 milliseconds
		       mHandler.postDelayed(this, 100);
		   }
		};
		
	/**
	 * 
	 * */
	

	/**
	 * When user starts moving the progress handler
	 * */
	
	
	
	@Override
	 public void onDestroy(){
	 super.onDestroy();
	    media.release();
	 }
		
	public class MyCountDownTimer extends CountDownTimer
	{
        public MyCountDownTimer(long startTime, long interval) 
        {
        	super(startTime, interval);
        }
        @Override
        public void onFinish() 
        {
        	media.stop();
        	cntr_aCounter.cancel();
        	
        	
        	
        	
        	Intent i = new Intent(getApplicationContext(), MemoryNormalOptionsActivity.class);
        	i.putExtra("memory_answers",memory_answers);
        	i.putExtra("normal_songs_inbank",normal_songs_inbank);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        	// cuentaRegresiva.setText("");        
        }

        @Override
        public void onTick(long millisUntilFinished) 
        {
        	long millis = millisUntilFinished; 
        	String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        	System.out.println(hms); 
        	textViewTime.setText(hms); 

 
        	
        	Log.v("onTick","");
        	//  cuentaRegresiva.setText(""+millisUntilFinished/1000);
                              
        }
    }
	
}  