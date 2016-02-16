package base;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class SmartServiceBase extends BasePager{

	public SmartServiceBase(Activity activity) {
		super(activity);
		
	}
	
	@Override
	public void initData() {
		tv_title.setText("����");
		TextView tvView=new TextView(mActivity);
		tvView.setText("�ǻ۷���");
		tvView.setTextColor(Color.RED);
		tvView.setTextSize(25);
		tvView.setGravity(Gravity.CENTER);
		noSlide(true);
		
		flcontent.addView(tvView);
	}

}
