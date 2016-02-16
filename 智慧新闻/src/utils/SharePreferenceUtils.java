package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	public static boolean getPreference(Context ctx,String key, boolean value) {
		SharedPreferences spf = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		return spf.getBoolean(key, value);
	}
	
	public static void setPreference(Context ctx,String key, boolean value) {
		SharedPreferences spf = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		spf.edit().putBoolean(key, value).commit();
	}
	
	public static void setString(Context ctx,String key, String value) {
		SharedPreferences spf = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		spf.edit().putString(key, value).commit();
	}
	
	public static String getString(Context ctx,String key, String value) {
		SharedPreferences spf = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		return spf.getString(key, value);
	}
}
