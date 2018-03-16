package com.blc.jspot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static boolean DEV = false;
    private static String URL = DEV ? "http://" : "http://jspot.x10.mx/";

    private WebView webView;
    private TextView textView;
    private Button button;
    private View refreshBlock;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.webView = findViewById(R.id.webview);
        this.textView = findViewById(R.id.textView);
        this.button = findViewById(R.id.button);
        this.refreshBlock = findViewById(R.id.refreshBlock);

        flag = View.GONE;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.load();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("uniqx", "2");
                refreshBlock.setVisibility(flag == View.GONE ? View.VISIBLE : flag);
                webView.setVisibility(flag);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                flag = View.GONE;
                Log.d("uniqx", "1");
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        this.load();
    }

    private void load() {
        flag = View.VISIBLE;
        webView.loadUrl(URL);
    }
}
