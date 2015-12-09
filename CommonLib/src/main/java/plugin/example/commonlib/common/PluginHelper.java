package plugin.example.commonlib.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Walkud on 15/9/14.
 */
public class PluginHelper {

    public static final String EXTRA_PARAMS = "Params";


    public static void startActivity(Context context, String className) {
        startActivity(context, className, null);
    }

    public static void startActivity(Context context, String className, String params) {
        start(context, className, params);
    }

    public static void startService(Context context, String className) {
        startActivity(context, className, null);
    }

    public static void startService(Context context, String className, String params) {
        start(context, className, params);
    }

    public static void startReceiver(Context context, String className) {
        startActivity(context, className, null);
    }

    public static void startReceiver(Context context, String className, String params) {
        start(context, className, params);
    }

    public static void startByAction(Context context, String action, String params) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_PARAMS, params);
        context.startActivity(intent);
    }

    public static void startByUrl(Context context, String uri, String params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(uri));
        intent.putExtra(EXTRA_PARAMS, params);
        context.startActivity(intent);
    }

    private static void start(Context context, String className, String params) {
        Intent intent = new Intent();
        intent.setClassName(context, className);
        intent.putExtra(EXTRA_PARAMS, params);
        context.startActivity(intent);
    }
}
