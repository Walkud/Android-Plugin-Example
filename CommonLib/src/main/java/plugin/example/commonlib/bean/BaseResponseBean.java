package plugin.example.commonlib.bean;

import android.util.Log;

import org.json.JSONObject;

public class BaseResponseBean {
    public static final String TAG = "BaseResponseBean";
    private boolean isSuccess;
    private String message;
    private JSONObject json;

    public BaseResponseBean() {
        super();
    }

    public BaseResponseBean(String jsonStr) {
        try {
            json = new JSONObject(jsonStr);
            isSuccess = json.optBoolean("success");
            message = json.optString("msg");
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getJson() {
        // 解决因json为null子类解析程序崩溃
        if (json == null) {
            return new JSONObject();
        }
        return json;
    }

}
