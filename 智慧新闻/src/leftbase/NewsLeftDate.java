package leftbase;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import been.NewData.newDataTag;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.news.app.MainActivity;
import com.news.app.R;
import com.viewpagerindicator.TabPageIndicator;

public class NewsLeftDate extends BaseLeftDate implements OnPageChangeListener{

	private ViewPager legtPager;
	private List<TabBasePager> tabList;
	ArrayList<newDataTag> mChildren;
	private TabPageIndicator indicator;
	
	public NewsLeftDate(Activity activity, ArrayList<newDataTag> children) {
		super(activity); 
		mChildren=children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.newsleftdate, null);
		legtPager = (ViewPager) view.findViewById(R.id.vp_newsleft);
		
		ViewUtils.inject(this, view);
		
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		indicator.setOnPageChangeListener(this);
        
		return view;
	}
	
	@OnClick(R.id.im_next)
	public void	next(View view){
		int item = legtPager.getCurrentItem();
		legtPager.setCurrentItem(++item);
	}
	
	public void initData() {
		tabList=new ArrayList<TabBasePager>();
		
		for (int i = 0; i < mChildren.size(); i++) {
			TabBasePager tabPager=new TabBasePager(mActivity,mChildren.get(i));
			
			tabList.add(tabPager);
		}
		
		legtPager.setAdapter(new LeftPager());
		indicator.setViewPager(legtPager);
	}

	class LeftPager extends PagerAdapter{
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return mChildren.get(position).title;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tabList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabBasePager tabBasePager = tabList.get(position);
			container.addView(tabBasePager.mRoot);
			tabBasePager.initData();
			return tabBasePager.mRoot;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		System.out.println("select"+arg0);
		MainActivity main=(MainActivity) mActivity;
		SlidingMenu slidingMenu = main.getSlidingMenu();
		if (arg0==0) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
}
