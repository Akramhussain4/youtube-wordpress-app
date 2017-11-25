package com.tbv.akramhussain.thebaiganvines.Web;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tbv.akramhussain.thebaiganvines.R;

public class Twitter extends AppCompatActivity {
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        WebView wview = (WebView) findViewById(R.id.webView);
        bar = (ProgressBar) findViewById(R.id.progressBar2);
        wview.getSettings().setJavaScriptEnabled(true);
        wview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wview.getSettings().setSupportMultipleWindows(true);
        wview.setWebViewClient(new myWebclient());
        wview.setWebChromeClient(new WebChromeClient());
        wview.loadUrl("https://twitter.com/thebaiganvines");
        wview.setHorizontalScrollBarEnabled(false);
        wview.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            bar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

    }

}
