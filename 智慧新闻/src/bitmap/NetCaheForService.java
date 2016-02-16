package bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCaheForService {

	private HttpURLConnection conn;
	LocalCacheUtils mLocatCacheUtils;
	MemoryCacheUtils memoryCacheUtils;
	public NetCaheForService(LocalCacheUtils locatCacheUtils, MemoryCacheUtils MemoryCacheUtils) {
		mLocatCacheUtils=locatCacheUtils;
		memoryCacheUtils=MemoryCacheUtils;
	}

	public void getPhotoForService(ImageView image, String url) {
		new bitmapTask().execute(image,url);
		
	}
	
	class bitmapTask extends AsyncTask<Object, Void, Bitmap>{

		private ImageView image;
		private String url;

		@Override
		protected Bitmap doInBackground(Object... params) {
			image = (ImageView) params[0];
			url = (String) params[1];
			Bitmap downloadForService = downloadForService(url);
			image.setTag(url);
			
			return downloadForService;
		}
		
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result!=null) {
				
				String tag = (String) image.getTag();
				if (url.equals(tag)) {
					mLocatCacheUtils.setBitmapFromLocal(url, result);
					memoryCacheUtils.setMemory(url, result);
					image.setImageBitmap(result);
				}
			}
		}
		
	}

	public Bitmap downloadForService(String url) {
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(3000);
			conn.setReadTimeout(5000);
			
			if (conn.getResponseCode()==200) {
				InputStream inputStream = conn.getInputStream();
				
				//Í¼Æ¬Ñ¹Ëõ
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inSampleSize=2;
				options.inPreferredConfig=Bitmap.Config.RGB_565;
				
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (conn!=null) {
				conn.disconnect();
				
			}
		}
		return null;
	}

}
