package bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCacheUtils {

	
	LruCache<String, Bitmap> lruCache;
	
	public MemoryCacheUtils(){
		long maxMemory = Runtime.getRuntime().maxMemory()/8;
		lruCache=new LruCache<String, Bitmap>((int) maxMemory){
			
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int byteCount = value.getByteCount();
				
				return byteCount;
			}
		};
		
		
	}
	
	public Bitmap getMemoryCache(String url) {
		return lruCache.get(url);
	}
	
	public void setMemory(String url,Bitmap bitmap) {
		lruCache.put(url, bitmap);
	}
	
}
