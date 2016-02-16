package base;

import java.util.ArrayList;
import java.util.List;

import utils.Cahedata;

import leftbase.BaseLeftDate;
import leftbase.FocusLeftDate;
import leftbase.NewsLeftDate;
import leftbase.PhotoLeftDate;
import leftbase.TogetherLeftDate;

import been.HttpService;
import been.NewData;
import been.NewData.newDataMenu;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.news.app.MainActivity;

import frament.LeftFragment;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewsBase extends BasePager{

	private List<BaseLeftDate> leftList;
	private NewData gsonData;

	public NewsBase(Activity activity) {
		super(activity);
		
	}
	
	@Override
	public void initData() {
		tv_title.setText("新闻");
		noSlide(true);
		
		String caheData = Cahedata.getCaheData(mActivity, HttpService.CATEGORIES);
		
		if (!TextUtils.isEmpty(caheData)) {
			getJsonResult(caheData);
		}
		
		getHttpService();
	}
	
	public void getHttpService() {
		HttpUtils utils=new HttpUtils();
		utils.send(HttpRequest.HttpMethod.GET, HttpService.CATEGORIES, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo responseInfo) {
				String result = (String) responseInfo.result;
				System.out.println("下载的json"+result);
				
				Cahedata.setCaheData(mActivity, HttpService.CATEGORIES, result);
				getJsonResult(result);
			}

			

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
			}
		});
	}
	
	private void getJsonResult(String result) {
		Gson gson=new Gson();
		gsonData = gson.fromJson(result, NewData.class);
//		System.out.println("解释的json"+data);
		MainActivity main=(MainActivity) mActivity;
		LeftFragment leftFragment = main.getLeftFragment();
		leftFragment.setMenuFragment(gsonData);
		
		leftList = new ArrayList<BaseLeftDate>();
		leftList.add(new NewsLeftDate(mActivity,gsonData.data.get(0).children));
		leftList.add(new FocusLeftDate(mActivity));
		leftList.add(new PhotoLeftDate(mActivity,ibGrid));
		leftList.add(new TogetherLeftDate(mActivity));
		
		setCurrentLeftPage(0);
	}
	
	public void setCurrentLeftPage(int position) {
		BaseLeftDate baseLeftDate = leftList.get(position);
		flcontent.removeAllViews();
		flcontent.addView(baseLeftDate.mRoot);
		
		newDataMenu newDataMenu = gsonData.data.get(position);
		tv_title.setText(newDataMenu.title);
		
		baseLeftDate.initData();
		
		if (baseLeftDate instanceof PhotoLeftDate) {
			ibGrid.setVisibility(View.VISIBLE);
			
		}else {
			ibGrid.setVisibility(View.GONE);
		}
	}

}
