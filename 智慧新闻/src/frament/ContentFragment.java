package frament;

import java.util.ArrayList;
import java.util.List;

import base.BasePager;
import base.GovBase;
import base.HomeBase;
import base.NewsBase;
import base.SetttingBase;
import base.SmartServiceBase;

import com.news.app.R;

import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	private RadioGroup rGroup;
	private ViewPager vPager;

	private ArrayList<BasePager> listPager;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.content_fragment, null);
		rGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		vPager = (ViewPager) view.findViewById(R.id.pager_content);
		return view;
	}

	@Override
	public void initData() {
		listPager = new ArrayList<BasePager>();
		rGroup.check(R.id.rb_home);
		listPager.add(new HomeBase(mActivity));
		listPager.add(new NewsBase(mActivity));
		listPager.add(new SmartServiceBase(mActivity));
		listPager.add(new GovBase(mActivity));
		listPager.add(new SetttingBase(mActivity));

		vPager.setAdapter(new MyPagerContent());
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					vPager.setCurrentItem(0,false);
					break;
				case R.id.rb_news:
					vPager.setCurrentItem(1,false);
					break;
				case R.id.rb_service:
					vPager.setCurrentItem(2,false);
					break;
				case R.id.rb_gov:
					vPager.setCurrentItem(3,false);
					break;
				case R.id.rb_settting:
					vPager.setCurrentItem(4,false);
					break;

				
				}
			}
		});
		
		vPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				listPager.get(arg0).initData();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		listPager.get(0).initData();
	}

	class MyPagerContent extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listPager.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = listPager.get(position);
			container.addView(pager.mRootView);
//			pager.initData();
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	public NewsBase getNewsBase() {
		return (NewsBase) listPager.get(1);
	}

}
