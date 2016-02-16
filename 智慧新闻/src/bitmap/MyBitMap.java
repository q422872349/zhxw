package bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitMap {
	MemoryCacheUtils memoryCacheUtils;
	 NetCaheForService netcahe;
	LocalCacheUtils locatCacheUtils;
	private Bitmap bitmap;
	public MyBitMap() {
		memoryCacheUtils=new MemoryCacheUtils();
		locatCacheUtils=new LocalCacheUtils();
		netcahe =new NetCaheForService(locatCacheUtils,memoryCacheUtils);
		
	}
	
	public void display(ImageView image, String url) {
		
		 bitmap = memoryCacheUtils.getMemoryCache(url);
		 if (bitmap!=null) {
			 image.setImageBitmap(bitmap);
			 return;
		}
		 
		
		bitmap = locatCacheUtils.getBitmapFromLocal(url);
		if (bitmap!=null) {
			image.setImageBitmap(bitmap);
			memoryCacheUtils.setMemory(url, bitmap);
			return;
		}
		
		netcahe.getPhotoForService(image,url);
	}

}
