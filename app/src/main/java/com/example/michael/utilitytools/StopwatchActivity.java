package com.example.michael.utilitytools;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class StopwatchActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mAd;

    private TextView stopwatchText;
    private Button startBtn;
    private Button pauseBtn;
    private Button resetBtn;

    Handler handler = new Handler();
    long startTime = 0l;
    long millis = 0l;
    long timeSwap = 0l;
    long updateTime = 0l;

    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            millis = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwap + millis;
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (updateTime%1000);
            stopwatchText.setText(mins + ":" + String.format("%2d", secs) + ":" + String.format("%3d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        MobileAds.initialize(this, Integer.toString(R.string.appID));

        AdView adView = (AdView) findViewById(R.id.adView);
//      AdRequest adRequest = new AdRequest.Builder().addTestDevice("A0D2DD44E80EC06C276F4C86AAE5DD68").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        mAd.loadAd("ca-app-pub-7974952675001102/6099882142", new AdRequest.Builder().build());

        stopwatchText = (TextView) findViewById(R.id.stopwatchTextView);
        startBtn = (Button) findViewById(R.id.start);
        pauseBtn = (Button) findViewById(R.id.pause);
        resetBtn = (Button) findViewById(R.id.reset);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimeThread, 0);
                resetBtn.setEnabled(false);
                startBtn.setEnabled(false);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap += millis;
                handler.removeCallbacks(updateTimeThread);
                resetBtn.setEnabled(true);
                startBtn.setEnabled(true);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAd.isLoaded()) {
                    mAd.show();
                }
                startTime = 0l;
                millis = 0l;
                timeSwap = 0l;
                updateTime = 0l;
                stopwatchText.setText(0 + ":" + String.format("%2d", 0) + ":" + String.format("%3d", 0));
            }
        });
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
