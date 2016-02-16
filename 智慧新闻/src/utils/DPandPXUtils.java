package utils;

import android.content.Context;

public class DPandPXUtils {

	public static int dp2px(Context ctx,float dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		int px=(int) (dp*density+0.5);
		return px;
	}
	
	public static float px2dp(Context ctx,int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp=px/density;
		return dp;
	}
	
}
