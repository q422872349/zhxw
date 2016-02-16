package been;

import java.util.ArrayList;

public class TabDetail {
	public String retcode;
	public newTabDetail data;
	
	public class newTabDetail{
		public String title;
		public String more;
		public ArrayList<newsTab> news;
		public ArrayList<newsTop> topnews;
		@Override
		public String toString() {
			return "newTabDetail [title=" + title + ", news=" + news
					+ ", topnews=" + topnews + "]";
		}
		
		
	}
	
	public class newsTab{
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "newsTab [title=" + title + "]";
		}
		
		
	}
	
	public class newsTop{
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "newsTop [title=" + title + "]";
		}
		
	}

	@Override
	public String toString() {
		return "TabDetail [data=" + data + "]";
	}
	
	
}
