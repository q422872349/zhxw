package been;

import java.util.ArrayList;

public class PhotoData {

	public int retcode;
	public PhotoDataInfo data;
	
	
	public class PhotoDataInfo{
		public String title;
		public ArrayList<PhotoInfo> news;
	}
	
	public class PhotoInfo{
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
	}
}
