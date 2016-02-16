package leftbase;

import java.util.ArrayList;

//import utils.RefalshListVew;
import utils.Cahedata;
import utils.RefalshListVew;
import utils.RefalshListVew.onRefreshListener;
import utils.SharePreferenceUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import been.HttpService;
import been.NewData.newDataTag;
import been.TabDetail;
import been.TabDetail.newsTab;
import been.TabDetail.newsTop;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.news.app.NewsDetailActivity;
import com.news.app.R;
import com.viewpagerindicator.CirclePageIndicator;

public class TabBasePager extends BaseLeftDate implements OnPageChangeListener {
	newDataTag mChildren;
	String tabUrl;
	private TabDetail tabDetail;

	@ViewInject(R.id.tab_detail)
	private ViewPager tabPager;

	@ViewInject(R.id.tv_detail)
	private TextView tvView;

	private newsTop newsTop;
	@ViewInject(R.id.indicator)
	private CirclePageIndicator indicator;
	@ViewInject(R.id.lv_detail)
	private RefalshListVew lView;
	private ArrayList<newsTab> newsList;
	private String mMore;
	private MyListDetail listDetail;

	private Handler handler;

	public TabBasePager(Activity activity, newDataTag newDataTag) {
		super(activity);
		mChildren = newDataTag;
		tabUrl = HttpService.SERVICE_URL + mChildren.url;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.listview, null);
		View heardView = View.inflate(mActivity, R.layout.tab_base, null);

		ViewUtils.inject(this, view);
		ViewUtils.inject(this, heardView);
		// lView.addHeaderView(heardView);
		lView.addHeaderView(heardView);

		lView.setOnRefreshListener(new onRefreshListener() {

			@Override
			public void onRefresh() {
				getDataForService();
			}

			@Override
			public void onLoading() {
				if (mMore != null) {
					getMoreDataForService();

				} else {
					Toast.makeText(mActivity, "最后一页", 0).show();
					lView.refreshComplete();
				}
			}
		});
		return view;
	}

	public void initData() {

		String caheData = Cahedata.getCaheData(mActivity, tabUrl);

		if (!TextUtils.isEmpty(caheData)) {
			getJsonResult(caheData, false);
		}

		getDataForService();

		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position);
				String ids = SharePreferenceUtils.getString(mActivity,
						"read_id", "");
				String newsId = tabDetail.data.news.get(position).id;
				if (!ids.contains(newsId)) {

					ids = ids + newsId + ",";
					SharePreferenceUtils.setString(mActivity, "read_id", ids);
				}
				// listDetail.notifyDataSetChanged();
				changItem(view);

				Intent intent = new Intent(mActivity, NewsDetailActivity.class);
				intent.putExtra("url", tabDetail.data.news.get(position).url);
				mActivity.startActivity(intent);
			}
		});
	}

	public void changItem(View view) {
		TextView tv_titleView = (TextView) view.findViewById(R.id.tv_content);
		tv_titleView.setTextColor(Color.GRAY);
	}

	private void getDataForService() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpRequest.HttpMethod.GET, tabUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("页签详情的json" + result);
				getJsonResult(result, false);

				Cahedata.setCaheData(mActivity, tabUrl, result);
				lView.refreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				lView.refreshComplete();
			}
		});

	}

	private void getMoreDataForService() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpRequest.HttpMethod.GET, tabUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("页签详情的json" + result);
				getJsonResult(result, true);
				lView.refreshComplete();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				lView.refreshComplete();
			}
		});
	}

	protected void getJsonResult(String result, boolean isMore) {
		Gson gson = new Gson();
		tabDetail = gson.fromJson(result, TabDetail.class);

		String more = tabDetail.data.more;
		if (!TextUtils.isEmpty(more)) {
			mMore = HttpService.SERVICE_URL + more;

		} else {
			mMore = null;
		}

		if (!isMore) {
			tabPager.setAdapter(new MyTabPager());
			newsList = tabDetail.data.news;

			tvView.setText(tabDetail.data.topnews.get(0).title);
			indicator.setViewPager(tabPager);
			indicator.setSnap(true);
			indicator.setOnPageChangeListener(this);
			indicator.onPageSelected(0);

			if (newsList != null) {

				listDetail = new MyListDetail();
				// lVlViewew.setAdapter(listDetail);
				lView.setAdapter(listDetail);
			}

			if (handler == null) {
				handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);

						int currentItem = tabPager.getCurrentItem();
						if (currentItem < newsList.size() - 1) {
							currentItem++;
						} else {
							currentItem = 0;
						}
						tabPager.setCurrentItem(currentItem);
						handler.sendEmptyMessageDelayed(0, 3000);
					};
				};

				handler.sendEmptyMessageDelayed(0, 3000);
			}

		} else {
			ArrayList<newsTab> news = tabDetail.data.news;
			newsList.addAll(news);
			listDetail.notifyDataSetChanged();
		}

	}

	class MyTabPager extends PagerAdapter {

		private BitmapUtils bitmap;

		public MyTabPager() {
			bitmap = new BitmapUtils(mActivity);
			bitmap.configDefaultLoadFailedImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tabDetail.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			image.setBackgroundResource(R.drawable.news_pic_default);
			image.setScaleType(ScaleType.FIT_XY);

			newsTop newsTop = tabDetail.data.topnews.get(position);
			bitmap.display(image, newsTop.topimage);
			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class MyListDetail extends BaseAdapter {

		private BitmapUtils utils;

		public MyListDetail() {
			utils = new BitmapUtils(mActivity);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.listview_detail,
						null);
				holder = new ViewHolder();
				holder.imageDetail = (ImageView) convertView
						.findViewById(R.id.iv_detail);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_content);
				holder.tvData = (TextView) convertView
						.findViewById(R.id.tv_data);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			newsTab newsTab = newsList.get(position);
			holder.tvTitle.setText(newsTab.title);
			holder.tvData.setText(newsTab.pubdate);

			utils.display(holder.imageDetail, newsTab.listimage);

			String ids = SharePreferenceUtils.getString(mActivity, "read_id",
					"");
			if (ids.contains(newsList.get(position).id)) {
				holder.tvTitle.setTextColor(Color.GRAY);

			} else {
				holder.tvTitle.setTextColor(Color.BLACK);
			}

			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView imageDetail;
		public TextView tvTitle;
		public TextView tvData;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		newsTop = tabDetail.data.topnews.get(arg0);
		tvView.setText(newsTop.title);
	}

}
