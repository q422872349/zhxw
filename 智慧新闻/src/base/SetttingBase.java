package base;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class SetttingBase extends BasePager{

	public SetttingBase(Activity activity) {
		super(activity);
		
	}
	
	@Override
	public void initData() {
		tv_title.setText("设置");
		ibtn.setVisibility(View.GONE);
		TextView tvView=new TextView(mActivity);
		tvView.setText("设置中心");
		tvView.setTextColor(Color.RED);
		tvView.setTextSize(25);
		tvView.setGravity(Gravity.CENTER);
		noSlide(false);
		
		flcontent.addView(tvView);
	}

}
