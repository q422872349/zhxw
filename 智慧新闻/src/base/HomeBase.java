package base;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HomeBase extends BasePager{

	public HomeBase(Activity activity) {
		super(activity);
		
	}
	
	@Override
	public void initData() {
		tv_title.setText("今日看点");
		ibtn.setVisibility(View.GONE);
		TextView tvView=new TextView(mActivity);
		tvView.setText("首页");
		tvView.setTextColor(Color.RED);
		tvView.setTextSize(25);
		tvView.setGravity(Gravity.CENTER);
		noSlide(false);
		
		flcontent.addView(tvView);
	}

}
