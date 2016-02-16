package com.news.app;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import frament.ContentFragment;
import frament.LeftFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity {

	private static final String FRAGMENT_LEFTFRAGMENT = "fragment_leftFragment";
	private static final String FRAGMENT_COTENT = "fragment_content";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		setBehindContentView(R.layout.left_slide);
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		int width = getWindowManager().getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(width*200/320);
		initFragment();
		// ////
	}

	public void initFragment() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fl_left, new LeftFragment(),
				FRAGMENT_LEFTFRAGMENT);
		transaction.replace(R.id.fl_content, new ContentFragment(),
				FRAGMENT_COTENT);
		transaction.commit();

	}

	public LeftFragment getLeftFragment() {
		FragmentManager manager = getSupportFragmentManager();
		LeftFragment leftFragment = (LeftFragment) manager
				.findFragmentByTag(FRAGMENT_LEFTFRAGMENT);

		return leftFragment;
	}

	public ContentFragment getLeftContent() {
		FragmentManager manager = getSupportFragmentManager();
		ContentFragment leftContent = (ContentFragment) manager
				.findFragmentByTag(FRAGMENT_COTENT);

		return leftContent;
	}

}
