package leftbase;

import java.util.ArrayList;

import utils.Cahedata;

import been.HttpService;
import been.PhotoData;
import been.PhotoData.PhotoInfo;
import bitmap.MyBitMap;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.news.app.R;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoLeftDate extends BaseLeftDate implements OnClickListener{

	private ListView lvPhoto;
	private GridView gvPhoto;
	private ArrayList<PhotoInfo> newsInfo;
	private PhotoData photoData;
	private PhotoListView adapter;
	private ImageButton ibGrid;

	public PhotoLeftDate(Activity activity, ImageButton ibGrid) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.ibGrid=ibGrid;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.photo_listview, null);
		lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
		gvPhoto = (GridView) view.findViewById(R.id.gv_photo);
		return view;
	}
	
	public void initData() {
		String caheData = Cahedata.getCaheData(mActivity, HttpService.PHOTO);
		if (!TextUtils.isEmpty(caheData)) {
			parseData(caheData);
		}
		getJsonForServer();
		
		
	}
	
	private void getJsonForServer() {
		HttpUtils httpUtils=new HttpUtils();
		httpUtils.send(HttpRequest.HttpMethod.GET, HttpService.PHOTO, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				
				Cahedata.setCaheData(mActivity, HttpService.PHOTO, result);
				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
			}
		});
	
	}

	private void parseData(String result) {
		Gson gson=new Gson();
		photoData = gson.fromJson(result, PhotoData.class);
		
		newsInfo = photoData.data.news;
		if (newsInfo!=null) {
			
			adapter = new PhotoListView();
			lvPhoto.setAdapter(adapter);
			gvPhoto.setAdapter(adapter);
		}
		
		ibGrid.setOnClickListener(this);
	}

	class PhotoListView extends BaseAdapter{

//		private BitmapUtils utils;
		private MyBitMap utils;
		public PhotoListView(){
//			utils = new BitmapUtils(mActivity);
			utils=new MyBitMap();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newsInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder Holder ;
			if (convertView==null) {
				Holder=new ViewHolder();
				convertView=View.inflate(mActivity, R.layout.photo_leftdata, null);
				Holder.image=(ImageView) convertView.findViewById(R.id.iv_photo);
				Holder.tvTitle=(TextView) convertView.findViewById(R.id.tv_photo);
				convertView.setTag(Holder);
			}else {
				Holder=(ViewHolder) convertView.getTag();
			}
			
			String title = newsInfo.get(position).title;
			String listimage = newsInfo.get(position).listimage;
			Holder.tvTitle.setText(title);
			utils.display(Holder.image, listimage);
			
			return convertView;
		}
		
	}
	
	class ViewHolder{
		public ImageView image;
		public TextView tvTitle;
	}

	public boolean isDisplay=false;
	
	@Override
	public void onClick(View v) {
		if (isDisplay) {
			lvPhoto.setVisibility(View.VISIBLE);
			gvPhoto.setVisibility(View.GONE);
			ibGrid.setBackgroundResource(R.drawable.icon_pic_grid_type);
			isDisplay=false;
		}else {
			lvPhoto.setVisibility(View.GONE);
			gvPhoto.setVisibility(View.VISIBLE);
			isDisplay=true;
			ibGrid.setBackgroundResource(R.drawable.icon_pic_list_type);
		}
	}

}
