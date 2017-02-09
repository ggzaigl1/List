package Application.ProgressWeb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gab.myapplication.R;

/**
 * Created by Administrator on 2016/11/14 0014.
 */
public class ProgressWebViewActivity extends AppCompatActivity {

    private ProgressWebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressweb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ProgressWebView");
        }
        mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        String url ="http://mp.weixin.qq.com/s?__biz=MzAwODQ5MTA2NQ==&mid=402389923&idx=1&sn=3c89c329e7bf83ce8ff2364726ebd6a7#rd";
        mWebView.loadUrl(url);
    }
}
