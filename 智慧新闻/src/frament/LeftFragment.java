package frament;

import java.util.ArrayList;

import base.NewsBase;
import been.NewData;
import been.NewData.newDataMenu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.news.app.MainActivity;
import com.news.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftFragment extends BaseFragment {

	private ListView lView;
	private ArrayList<newDataMenu> dataMenu;
	private int mcurrent ;
	private MylistViewAdapter mAdapter;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity,R.layout.left_fragment , null);
		lView = (ListView) view.findViewById(R.id.lv);
		return view;
	}
	
	
	public void initData() {
		lView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mcurrent=position;
				mAdapter.notifyDataSetChanged();
				
				setCurrentPosition(position);
				
				togoleSlideShow();
			}
		});
//		setCurrentPosition(0);
	}
	
	protected void togoleSlideShow() {
		MainActivity maActivity=(MainActivity) mActivity;
		SlidingMenu slidingMenu = maActivity.getSlidingMenu();
		slidingMenu.toggle();
	}


	protected void setCurrentPosition(int position) {
		MainActivity main=(MainActivity) mActivity;
		ContentFragment leftContent = main.getLeftContent();
		NewsBase newsBase = leftContent.getNewsBase();
		newsBase.setCurrentLeftPage(position);
	}


	public void setMenuFragment(NewData data) {
//		System.out.println("²à±ßÀ¸:"+data);
		dataMenu = data.data;
		mAdapter = new MylistViewAdapter();
		lView.setAdapter(mAdapter);
	}

	class MylistViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataMenu.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dataMenu.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View leftView = View.inflate(mActivity, R.layout.listview_item, null);
			TextView tvTitle=(TextView) leftView.findViewById(R.id.tv_leftTitle);
			newDataMenu menu = (newDataMenu) getItem(position);
			
			tvTitle.setText(menu.title);
			if (mcurrent==position) {
				tvTitle.setEnabled(true);
			}else {
				tvTitle.setEnabled(false);
			}
			return leftView;
		}
		
	}
	
}
