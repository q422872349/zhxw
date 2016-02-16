package com.news.app;

import utils.SharePreferenceUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    private RelativeLayout splash;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        splash = (RelativeLayout) findViewById(R.id.rl_splash);
        animition();
    }

	public void animition() {
		AnimationSet set=new AnimationSet(false);
		RotateAnimation rotate=new RotateAnimation(0, 360, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);
		rotate.setFillAfter(true);
		
		ScaleAnimation scale=new ScaleAnimation(0, 1, 0, 1, 
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(1000);
		scale.setFillAfter(true);
		
		
		set.addAnimation(scale);
		set.addAnimation(rotate);
		
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				firstShow();
			}
		});
		
		splash.startAnimation(set);
		
		
	}
	
	public void firstShow() {
//		SharedPreferences spf = getSharedPreferences("config", MODE_PRIVATE);
		boolean firstShow = SharePreferenceUtils.getPreference(SplashActivity.this, "firstShow", false);
		
		if (!firstShow) {
			
			startActivity(new Intent(SplashActivity.this,ViewPagerActivity.class));
			finish();
		}else {
			startActivity(new Intent(SplashActivity.this,MainActivity.class));
			finish();
		}
		
		
	}
 
}
