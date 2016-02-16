package com.news.app;

import java.util.ArrayList;
import java.util.List;

import utils.DPandPXUtils;
import utils.SharePreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewPagerActivity extends Activity implements OnClickListener{

	private ViewPager vPager;
	private int length;

	int[] psic=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

	private List<ImageView> mGuide;

	private LinearLayout mPoint;
	private View red_point;
	private Button btn_use;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slide_activity);
		
		vPager = (ViewPager) findViewById(R.id.Vpager);
		mPoint = (LinearLayout) findViewById(R.id.ll_point);
		red_point = findViewById(R.id.red_point);
		btn_use = (Button) findViewById(R.id.btn_use);
		
		btn_use.setOnClickListener(this);
		initValue();
		vPager.setAdapter(new MyViewPager());
		vPager.setOnPageChangeListener(new MyPagerListener());
	}
	
	class MyPagerListener implements OnPageChangeListener{

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			int moveLength=(int) (length*positionOffset)+position*length;
			RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) red_point.getLayoutParams();
			layoutParams.leftMargin=moveLength;
			
			red_point.setLayoutParams(layoutParams);
		}

		@Override
		public void onPageSelected(int position) {
			if (position==psic.length-1) {
				btn_use.setVisibility(View.VISIBLE);
			}else {
				btn_use.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public void initValue() {
		mGuide = new ArrayList<ImageView>();
		for (int i = 0; i < psic.length; i++) {
			ImageView image=new ImageView(this);
			image.setBackgroundResource(psic[i]);
			
			mGuide.add(image);
//			if (i==psic.length-1) {
//				btn_use.setVisibility(View.VISIBLE);
//			}
			
		}
		for (int i = 0; i < psic.length; i++) {
			View point=new View(this);
			point.setBackgroundResource(R.drawable.shape_point_drak);
			
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DPandPXUtils.dp2px(this, 10), DPandPXUtils.dp2px(this, 10));
			if (i>0) {
				params.leftMargin=10;
				
			}
			point.setLayoutParams(params);
			mPoint.addView(point);
		}
		
		mPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			

			@Override
			public void onGlobalLayout() {
				mPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				length = mPoint.getChildAt(1).getLeft()-mPoint.getChildAt(0).getLeft();
			}
		});
	}
	
	
	class MyViewPager extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return psic.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mGuide.get(position));
			return mGuide.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}


	@Override
	public void onClick(View v) {
//		SharedPreferences spf = getSharedPreferences("config", MODE_PRIVATE);
//		spf.edit().putBoolean("firstShow", true).commit();
		SharePreferenceUtils.setPreference(ViewPagerActivity.this, "firstShow", true);
		startActivity(new Intent(ViewPagerActivity.this,MainActivity.class));
		finish();
	}
	
}
