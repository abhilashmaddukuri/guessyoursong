

package com.example.circleseek.Activities;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.guessursongs.R;

public class TimedActivity extends Activity {
    ProgressDialog progress;
    private ProgressBar spinner;
    private Context mContext;
    private ImageButton challenge_normal;
    private ImageButton challenge_hard;
    private ImageButton challenge_insane;
    private TextView challenge_normal_text;
    private TextView challenge_hard_text;
    private TextView challenge_insane_text;
    private TextView heading;
    private RelativeLayout relative_normal;
    private RelativeLayout relative_hard;
    private RelativeLayout relative_insane;
    private Button guide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed);
        mContext = TimedActivity.this;
        init();
        setFonts();
        setCustomAnims();
        onClicks();
        setAds();
    }

    private void setAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void onClicks() {
        relative_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(mContext, "", "Getting songs...", true);
                ringProgressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();

                Intent i = new Intent(getApplicationContext(), ChallengeNormalActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });


        relative_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(TimedActivity.this, "", "Getting songs...", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();


                Intent i = new Intent(getApplicationContext(), ChallengeHardActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });


        relative_insane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                final ProgressDialog ringProgressDialog = ProgressDialog.show(TimedActivity.this, "", "Getting songs...", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();


                Intent i = new Intent(getApplicationContext(), ChallengeInsaneActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
                mp.start();
                Intent i = new Intent(getApplicationContext(), ChallengeGuideActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
    }

    private void setCustomAnims() {
        final Animation animAnticipateOvershoot_one = AnimationUtils.loadAnimation(this, R.anim.right_in);
        final Animation animAnticipateOvershoot_two = AnimationUtils.loadAnimation(this, R.anim.right_in);
        final Animation animAnticipateOvershoot_three = AnimationUtils.loadAnimation(this, R.anim.right_in);


        final Animation anim_top_in = AnimationUtils.loadAnimation(this, R.anim.top_in);
    }

    private void setFonts() {
        // Font path
        String fontPath_bold = "fonts/Caviar_Dreams_Bold.ttf";
        String fontPath_black = "fonts/CaviarDreams_Italic.ttf";


        // Loading Font Face
        Typeface tf_bold = Typeface.createFromAsset(getAssets(), fontPath_bold);
        Typeface tf_black = Typeface.createFromAsset(getAssets(), fontPath_black);

        challenge_normal_text.setTypeface(tf_bold);


        challenge_hard_text.setTypeface(tf_bold);


        challenge_insane_text.setTypeface(tf_bold);
        heading.setTypeface(tf_bold);
    }

    private void init() {
        challenge_normal = (ImageButton) findViewById(R.id.challenge_normal);
        challenge_hard = (ImageButton) findViewById(R.id.challenge_hard);
        challenge_insane = (ImageButton) findViewById(R.id.challenge_insane);
        challenge_normal_text = ((TextView) findViewById(R.id.challenge_normal_text));
        challenge_hard_text = ((TextView) findViewById(R.id.challenge_hard_text));
        challenge_insane_text = ((TextView) findViewById(R.id.challenge_insane_text));
        heading = ((TextView) findViewById(R.id.heading));
        relative_normal = (RelativeLayout) findViewById(R.id.relative_one);
        relative_hard = (RelativeLayout) findViewById(R.id.relative_two);
        relative_insane = (RelativeLayout) findViewById(R.id.relative_three);
        guide = (Button) findViewById(R.id.guide);
    }

    @Override
    public void onBackPressed() {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.onclick);
        mp.start();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }
}