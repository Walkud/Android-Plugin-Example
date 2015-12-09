package plugin.example.commonlib.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;

import plugin.example.commonlib.bean.BaseRequestBean;
import plugin.example.commonlib.network.request.CsocRequest;

;

/**
 * HTTP请求封装
 *
 * @author wyg
 */
public class HttpTools {
    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {

        if (mRequestQueue == null) {
            //因为我们使用OkHttp作为Volley的传输层，所以增加一个HttpStack参数
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    /**
     * 取消所有Request
     */
    public static void cancelAllRequest(Context context) {
        RequestQueue localRequestQueue = getRequestQueue(context);
        if (localRequestQueue != null) {
            localRequestQueue.cancelAll(new RequestQueue.RequestFilter() {

                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
            localRequestQueue.stop();
        }
    }

    /**
     * 根据标签取消指定Request
     *
     * @param tag
     */
    public static void cancelWithTag(Context context, String tag) {
        if (!TextUtils.isEmpty(tag)) {
            if (getRequestQueue(context) != null) {
                getRequestQueue(context).cancelAll(tag);
            }
        }
    }

    /**
     * @param context
     * @param requestBean 请求参数
     * @param listener    监听
     * @return
     */
    public static CsocRequest addRequest(final Context context,
                                         BaseRequestBean requestBean,
                                         final CsocRequest.ResponeListener listener) {
        return addRequest(context, Method.GET, requestBean, null, listener);
    }

    /**
     * @param context
     * @param requestBean 请求参数
     * @param type        返回实体类型
     * @param listener    监听
     * @return
     */
    public static CsocRequest addRequest(final Context context,
                                         BaseRequestBean requestBean, Type type,
                                         final CsocRequest.ResponeListener listener) {
        return addRequest(context, Method.GET, requestBean, type, listener);
    }

    /**
     * @param context
     * @param requestBean 请求参数
     * @param type        返回实体类型
     * @param listener    监听
     * @return
     */
    private static CsocRequest addRequest(final Context context, int method,
                                          BaseRequestBean requestBean, Type type,
                                          final CsocRequest.ResponeListener listener) {
        RequestQueue requestQueue = getRequestQueue(context);
        requestBean.putMustParams(context);
        CsocRequest request = new CsocRequest(method, requestBean.getApi(), requestBean, type, new CsocRequest.ResponeListener<Object>() {

            @Override
            public void onResponse(Object response) {
                listener.onResponse(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });

        requestQueue.add(request);
        return request;
    }
}
