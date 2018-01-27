package com.example.circleseek.Activities;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;


public class AudioRequestActivity extends Activity {
    public AudioRequestActivity() {

    }

    public void audiomanager() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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

    }


}