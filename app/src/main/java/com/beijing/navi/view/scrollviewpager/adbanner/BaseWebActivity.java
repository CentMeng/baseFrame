package com.beijing.navi.view.scrollviewpager.adbanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.core.constant.LogConstant;
import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;


/**
 * @Description:WebView界面，带自定义进度条显示
 */
public class BaseWebActivity extends BaseActivity {

    protected WebView mWebView;
    private TextView title;
    private TextView tv_commit;
    private String title_str;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseweb);
        getIntents();
        findView();
        initData();
    }

    private void findView() {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(title_str);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setVisibility(View.GONE);
        mWebView = (WebView) findViewById(R.id.baseweb_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        //单列显示
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                showLoadingDialog(true, "加载页面", true);
                BaseWebActivity.this.setProgress(progress * 100);
                // Return the app name after finish
                // loading
                if (progress == 100)
                    cancelLoadingDialog();
            }
        });

    }



    private void getIntents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            title_str = bundle.getString("title");
        }
        if (TextUtils.isEmpty(title_str)) {
            title_str = "公告";
        }
        url = bundle.getString("url");
    }

    protected void initData() {
        Log.e(LogConstant.DATA_OUTPUT, "打开网页地址" + url);
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView = null;

    }

    public void back(View view) {
        finish();
    }


    public static void startAc(Context context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        Intent intent = new Intent(context, BaseWebActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
