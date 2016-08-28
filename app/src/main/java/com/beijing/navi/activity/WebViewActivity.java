package com.beijing.navi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 加载网页
 */
public class WebViewActivity extends BaseActivity {

    private TextView tv_title;

    private TextView tv_commit;

    private WebView webview;

    private String title = "使用说明";

    private String url = "http://wetest.zhid58.com/html/coupon_introduction.html";

    private String content;

    private String channel;

    private String imageUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getIntents();
        findView();
    }

    private void getIntents() {
        Intent intent = this.getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            url = intent.getStringExtra("url");
            content = intent.getStringExtra("content");
            channel = intent.getStringExtra("channel");
            imageUrl = intent.getStringExtra("imageUrl");
        }
    }

    private void findView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setVisibility(View.VISIBLE);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setVisibility(View.GONE);
        webview = (WebView) findViewById(R.id.webview);
        tv_title.setText(title);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if(TextUtils.isEmpty(content)){
//            img_share.setVisibility(View.INVISIBLE);
//        }
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                tv_title.setText("正在加载...");
                showLoadingDialog(true,getString(R.string.loading),true);
                WebViewActivity.this.setProgress(progress * 100);
                // Return the app name after finish
                // loading
                if (progress == 100) {
                    cancelLoadingDialog();
                    tv_title.setText(title);
                }
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                webview.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl(url);
    }

    public static void startAc(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void startAc(BaseActivity context,String title,String url, String channel, String content, String imageUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        bundle.putString("channel", channel);
        bundle.putString("content", content);
        bundle.putString("imageUrl", imageUrl);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void back(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
        MobclickAgent.onPause(this);
    }
}
