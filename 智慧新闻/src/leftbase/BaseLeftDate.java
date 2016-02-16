package leftbase;

import android.app.Activity;
import android.view.View;

public abstract class BaseLeftDate {
	
	Activity mActivity;
	public View mRoot;
	public  BaseLeftDate(Activity activity) {
		mActivity=activity;
		mRoot=initView();
	}
	
	public abstract View initView();
	
	public void initData() {
		
	}
	
}
