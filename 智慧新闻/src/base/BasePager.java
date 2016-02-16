package base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.news.app.MainActivity;
import com.news.app.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {

	public Activity mActivity;
	public View mRootView;
	public TextView tv_title;
	public FrameLayout flcontent;
	public ImageButton ibtn;
	public ImageButton ibGrid;
	public BasePager(Activity activity){
		mActivity=activity;
		initView();
	}
	
	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.basepager, null);
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		flcontent = (FrameLayout) mRootView.findViewById(R.id.flPagerContent);
		ibtn = (ImageButton) mRootView.findViewById(R.id.ib_btn);
		ibGrid = (ImageButton) mRootView.findViewById(R.id.ib_list);
		
		ibtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				togoleSlideShow();
			}
		});
	}
	
	protected void togoleSlideShow() {
		MainActivity maActivity=(MainActivity) mActivity;
		SlidingMenu slidingMenu = maActivity.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	public void initData() {
		
	}
	
	public void noSlide(boolean enable) {
		MainActivity main=(MainActivity) mActivity;
		SlidingMenu slidingMenu = main.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
}
