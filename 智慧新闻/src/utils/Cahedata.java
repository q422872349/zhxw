package utils;

import android.content.Context;

public class Cahedata {

	public static void setCaheData(Context ctx,String key,String value) {
		SharePreferenceUtils.setString(ctx, key, value);
		
	}
	
	
	public static String getCaheData(Context ctx,String key) {
		return SharePreferenceUtils.getString(ctx, key, null);
	}
}
