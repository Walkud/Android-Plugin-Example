package plugin.example.commonlib.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * 一个自定义请求，可以根据GSON Type转换成对象的对象列表。
 */
public class CsocRequest<T> extends Request<T> {
    public static final int GET = Method.GET;
    public static final int POST = Method.POST;
    private static Gson gson = new Gson();
    private Type mType;
    private final ResponeListener<T> mlistener;
    private Map<String, String> mParams;

    //自定义回调，方便使用
    public interface ResponeListener<T> extends Response.Listener<T>, Response.ErrorListener {
    }

    /**
     * 增加method参数选择get或post方式提交http请求
     *
     * @param method
     * @param url
     * @param params
     * @param type
     * @param listener
     */
    public CsocRequest(int method, String url,
                       Map<String, String> params, Type type, ResponeListener<T> listener) {
        super(method, method == POST ? url : getUrl(url, params), listener);
        this.mlistener = listener;
        this.mType = type;
        if (method == POST) {
            mParams = params;
        }
        setShouldCache(false);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null) {
            return mParams;
        } else {
            return super.getParams();
        }
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            String jsonStr = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if (mType != null) {
                result = gson.fromJson(jsonStr, mType);
            } else {
                result = (T) jsonStr;
            }
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mlistener.onResponse(response);
    }


    //构造url，get方式请求也可直接传参数
    public static String getUrl(String url, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {

            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = new StringBuffer(url + "?");
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(params.get(key));
                //去掉null
                value = value == null ? "" : value;
                sb.append(key + "=" + value + "&");
            }

            return sb.toString();
        }
        return url;
    }
}
