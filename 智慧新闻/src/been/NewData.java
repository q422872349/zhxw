package been;

import java.util.ArrayList;

public class NewData {
	public int retcode;
	public ArrayList<newDataMenu> data;
	
	public class newDataMenu{
		public String id;
		public String title;
		public int type;
		public String url;
		
		public ArrayList<newDataTag> children;

		@Override
		public String toString() {
			return "newDataMenu [title=" + title + ", children=" + children
					+ "]";
		}

		
		
		
	}
	
	public class newDataTag{
		public String id;
		public String title;
		public int type;
		public String url;
		
		
		
	}

	@Override
	public String toString() {
		return "NewData [data=" + data + "]";
	}
	
}
