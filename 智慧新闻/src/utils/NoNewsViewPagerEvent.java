package utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoNewsViewPagerEvent extends ViewPager{

	public NoNewsViewPagerEvent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NoNewsViewPagerEvent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	 @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
		 if (getCurrentItem()!=0) {
			
			 getParent().requestDisallowInterceptTouchEvent(true);
		}else {
			getParent().requestDisallowInterceptTouchEvent(false);
		}
	    	return super.dispatchTouchEvent(ev);
	    }
	
}
