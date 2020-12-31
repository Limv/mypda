package loadingpicture.blue.com.mypda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private ImageView imageView;
    private TextView tv_refresh,tv_logout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webview);
        imageView = (ImageView) findViewById(R.id.imgbtn_back);
        tv_refresh = (TextView) findViewById(R.id.tv_refresh );
        tv_logout = (TextView) findViewById(R.id.tv_logout );
        progressBar = (ProgressBar) findViewById(R.id.pb);
        indata();
    }


    public void indata() {
//        String url = "http://192.168.88.64/system/index.php/mob";
        String url = "http://www.baidu.com";
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView加载web资源
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new webViewClient());
        webview.setWebChromeClient(new webChromeClient());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                webview.reload();

            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.goBack();
            }
        });

    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class webChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
    }
}
