package plugin.example.commonlib.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.ProgressBar;

import plugin.example.commonlib.R;
import plugin.example.commonlib.view.webview.jsinterface.CommInterface;

/**
 * 带进度条的WebView
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_horizontal_color));
        addView(progressbar, new LayoutParams(LayoutParams.MATCH_PARENT,
                5, 0, 0));
        setWebChromeClient(new WebChromeClient());
        addJavascriptInterface(new CommInterface(getContext()), "android");

        setting();
    }

    /**
     * 设置
     */
    private void setting() {
        setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);// 滚动条样式

        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);//支持Js
        webSettings.setBuiltInZoomControls(false); // 显示放大缩小
        webSettings.setSupportZoom(true); // 可以缩放
        webSettings.setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUserAgentString(webSettings.getUserAgentString());
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);// 影响默认满屏和双击缩放
        webSettings.setLoadWithOverviewMode(true);// 影响默认满屏和手势缩放
        webSettings.setDomStorageEnabled(true); // 开启本地存储
        webSettings.setDatabaseEnabled(true);// 启用数据库
        // 设置数据库及缓存路径
        String path = getContext().getCacheDir().getAbsolutePath();
        webSettings.setDatabasePath(path + "/webViewDatabase");
        webSettings.setAppCachePath(path + "/webViewCache");
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //设置进度
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}