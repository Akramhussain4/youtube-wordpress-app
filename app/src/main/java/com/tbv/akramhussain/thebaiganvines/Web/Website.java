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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tbv.akramhussain.thebaiganvines.R;

public class Website extends AppCompatActivity {
    ProgressBar bar;
    private WebView wview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);


        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Website");
        //toolbar.setTitleTextColor(Color.WHITE);


        wview = (WebView) findViewById(R.id.webView);
        bar = (ProgressBar) findViewById(R.id.progressBar2);
        wview.getSettings().setJavaScriptEnabled(true);
        wview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wview.getSettings().setSupportMultipleWindows(true);
        wview.setWebViewClient(new myWebclient());
        wview.getSettings().setDomStorageEnabled(true);
        wview.setWebChromeClient(new WebChromeClient());
        wview.loadUrl("http://store.thebaiganvines.com/men/designer-t-shirts%3Fmodel=Unisex");
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
