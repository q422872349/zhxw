package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.AbsListView.OnScrollListener;
import been.NewData.newDataTag;

import com.news.app.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefalshListVew extends ListView implements OnScrollListener,android.widget.AdapterView.OnItemClickListener{

	private int startY = -1;
	private View view;
	private int heardMeasure;

	public static final int PULL = 0;
	public static final int REALSHING = 1;
	public static final int RESHURING = 2;

	int currentState = PULL;
	private ImageView image;
	private ProgressBar pBar;
	private TextView tv_state;
	private TextView tv_time;
	private RotateAnimation upAnimation;
	private RotateAnimation downAnimation;

	public RefalshListVew(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView();
		initLoad();
	}

	public RefalshListVew(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initLoad();
	}

	public RefalshListVew(Context context) {
		super(context);
		initView();
		initLoad();
	}

	private void initView() {
		view = View.inflate(getContext(), R.layout.refalsh_listview, null);
		image = (ImageView) view.findViewById(R.id.iv_arrow);
		pBar = (ProgressBar) view.findViewById(R.id.pb_wait);
		tv_state = (TextView) view.findViewById(R.id.tv_refalsh);
		tv_time = (TextView) view.findViewById(R.id.tv_refalsh_data);

		view.measure(0, 0);
		heardMeasure = view.getMeasuredHeight();
		view.setPadding(0, -heardMeasure, 0, 0);

		this.addHeaderView(view);
		animition();
	}

	public void initLoad() {
		loadView = View.inflate(getContext(), R.layout.load_lisview, null);
		
		loadView.measure(0, 0);
		footMeasure = loadView.getMeasuredHeight();
		loadView.setPadding(0, -footMeasure, 0, 0);
		
		this.addFooterView(loadView);
		this.setOnScrollListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			if (startY == -1) {
				startY = (int) ev.getRawY();
			}
			if (currentState == RESHURING) {
				break;

			}

			int endY = (int) ev.getRawY();
			int dy = endY - startY;
			if (dy > 0 && getFirstVisiblePosition() == 0) {
				int padding = dy - heardMeasure;
				view.setPadding(0, padding, 0, 0);

				if (padding > 0 && currentState != REALSHING) {
					currentState = REALSHING;
					realshingUI();

				} else if (padding < 0 && currentState != PULL) {
					currentState = PULL;
					realshingUI();

				}

				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if (currentState == PULL) {
				view.setPadding(0, -heardMeasure, 0, 0);
			} else if (currentState == REALSHING) {
				currentState = RESHURING;
				view.setPadding(0, 0, 0, 0);
				realshingUI();
			}
			break;
		}

		return super.onTouchEvent(ev);
	}

	private void realshingUI() {
		switch (currentState) {
		case PULL:
			tv_state.setText("下拉刷新");
			image.setVisibility(View.VISIBLE);
			pBar.setVisibility(View.INVISIBLE);
			image.startAnimation(downAnimation);
			break;
		case REALSHING:
			tv_state.setText("松开刷新");
			image.setVisibility(View.VISIBLE);
			pBar.setVisibility(View.INVISIBLE);
			image.startAnimation(upAnimation);
			break;
		case RESHURING:
			image.clearAnimation();
			tv_state.setText("正在刷新");
			image.setVisibility(View.INVISIBLE);
			pBar.setVisibility(View.VISIBLE);

			if (mListener != null) {
				mListener.onRefresh();
			}
			break;

		}
	}

	public void animition() {
		upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

		upAnimation.setDuration(200);
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);

		downAnimation.setDuration(200);
		downAnimation.setFillAfter(true);

	}

	onRefreshListener mListener;
	private View loadView;

	public void setOnRefreshListener(onRefreshListener listener) {
		mListener = listener;
	}

	public interface onRefreshListener {
		public void onRefresh();
		
		public void	onLoading();
	}

	public void refreshComplete() {
		if (isMore) {
			
			loadView.setPadding(0, -footMeasure, 0, 0);
			isMore=false;
		}else {
			
			currentState = PULL;
			tv_state.setText("下拉刷新");
			image.setVisibility(View.VISIBLE);
			pBar.setVisibility(View.INVISIBLE);
			image.startAnimation(downAnimation);
			view.setPadding(0, -heardMeasure, 0, 0);
			tv_time.setText("最近刷新时间:" + getCurrentTime());
		}
	}

	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(new Date());
	}
	
	public boolean isMore;
	private int footMeasure;
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING ) {
			if (getLastVisiblePosition()==getCount()-1&& !isMore) {
				loadView.setPadding(0, 0, 0, 0);
				setSelection(getCount());
				isMore=true;
				
				if (mListener!=null) {
					mListener.onLoading();
					
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}

	OnItemClickListener mItemListener;
	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		super.setOnItemClickListener(this);
		mItemListener=listener;
	};
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mItemListener!=null) {
			mItemListener.onItemClick(parent, view, position-getHeaderViewsCount(), id);
			
		}
	}

}
