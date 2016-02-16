package base;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class GovBase extends BasePager{

	public GovBase(Activity activity) {
		super(activity);
		
	}
	
	@Override
	public void initData() {
		tv_title.setText("公共服务");
		TextView tvView=new TextView(mActivity);
		
		tvView.setText("政务");
		tvView.setTextColor(Color.RED);
		tvView.setTextSize(25);
		tvView.setGravity(Gravity.CENTER);
		noSlide(true);
		
		flcontent.addView(tvView);
	}

}
