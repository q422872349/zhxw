package com.news.app;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class NewsDetailActivity extends Activity implements OnClickListener {

	private ProgressBar pBar;
	private WebView wbTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetail);

		wbTextView = (WebView) findViewById(R.id.wv_text);
		ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
		ImageButton btnShare = (ImageButton) findViewById(R.id.btn_share);
		ImageButton btnText = (ImageButton) findViewById(R.id.btn_textsize);
		pBar = (ProgressBar) findViewById(R.id.pb_detail_wait);

		String url = getIntent().getStringExtra("url");
		WebSettings settings = wbTextView.getSettings();
		settings.setJavaScriptEnabled(true);

		btnBack.setOnClickListener(this);
		btnText.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		wbTextView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				// pBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				pBar.setVisibility(View.INVISIBLE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		wbTextView.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_textsize:
			showTextDialog();
			break;
		case R.id.btn_share:
			showShare();
			break;

		default:
			break;
		}
	}

	int mCurrentChoseItem;
	int mCurrentItem = 2;

	private void showTextDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final WebSettings settings = wbTextView.getSettings();
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };
		builder.setTitle("字体选择");
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCurrentChoseItem = which;
					}
				});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (mCurrentChoseItem) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;

				}
				mCurrentItem = mCurrentChoseItem;
			}

		});

		builder.setNegativeButton("取消", null);

		builder.show();
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		oks.setText("我是分享文本");
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
		// 启动分享GUI
		oks.show(this);
	}

}
