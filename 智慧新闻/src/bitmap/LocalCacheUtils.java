package bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import been.MD5Encoder;
import been.NewData.newDataMenu;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {

	public static final String CACHE_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"xinwen";
	
	public Bitmap getBitmapFromLocal(String url) {
		try {
			String fileName = MD5Encoder.encode(url);
			File file =new File(CACHE_PATH, fileName);
			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				return bitmap;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void setBitmapFromLocal(String url,Bitmap bitmap) {
		try {
			String fileName = MD5Encoder.encode(url);
			
			File file =new File(CACHE_PATH, fileName);
			File parentFile = file.getParentFile();
			
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file)  );
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
