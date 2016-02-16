package leftbase;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TogetherLeftDate extends BaseLeftDate{

	public TogetherLeftDate(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView tvView=new TextView(mActivity);
		tvView.setText("≤Àµ•œÍ«È“≥-ª•∂Ø");
		tvView.setTextColor(Color.RED);
		tvView.setTextSize(25);
		tvView.setGravity(Gravity.CENTER);
		return tvView;
	}

}
