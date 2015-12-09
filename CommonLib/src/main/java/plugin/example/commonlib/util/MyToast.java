package plugin.example.commonlib.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 解决多个toast显示时，出现的长时间才消失的问题
 * 
 */
public abstract class MyToast {
	private static final String TAG = MyToast.class.getSimpleName();

	private static Toast toast;
	private static Handler handler = new Handler();

	private static Runnable run = new Runnable() {
		public void run() {
			toast.cancel();
		}
	};

	/**
	 * 
	 * @param ctx
	 * @param msg
	 * @param duration
	 */
	private static void toast(Context ctx, CharSequence msg,int duration) {
		if (null == ctx) {
			throw new NullPointerException("The ctx is null!");
		}

		if (msg == null || StringUtils.isEmpty(msg.toString())) {
			MLog.i(TAG, "");
			return;
		}
		handler.removeCallbacks(run);
		// handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
		switch (duration) {
		case Toast.LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
			duration = 3000;
			break;
		default:// 默认为持续时间大概为1.5s
			duration = 1500;
			break;
		}
		if (null != toast) {
			toast.setText(msg);
		} else {
			toast = Toast.makeText(ctx, msg, duration);
		}
		handler.postDelayed(run, duration);
		toast.show();
	}

	/**
	 * 弹出Toast
	 * 
	 * @param ctx
	 *            弹出Toast的上下文
	 * @param msg
	 *            弹出Toast的内容
	 * @param duration
	 *            弹出Toast的持续时间
	 */
	public static void show(Context ctx, CharSequence msg, int duration)
			throws NullPointerException {
		toast(ctx, msg, duration);
	}

	/**
	 * 弹出Toast
	 * 
	 * @param ctx
	 *            弹出Toast的上下文
	 * @param res
	 *            弹出Toast的内容的资源ID
	 * @param duration
	 *            弹出Toast的持续时间
	 */
	public static void show(Context ctx, int res, int duration) {
		toast(ctx, ctx.getResources().getString(res), duration);
	}

	/**
	 * 弹出Toast
	 * 
	 * @param ctx
	 *            context
	 * @param msg
	 *            内容
	 * @throws NullPointerException
	 */
	public static void show(Context ctx, CharSequence msg) {
		toast(ctx, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 弹出Toast
	 * 
	 * @param ctx
	 *            context
	 * @param res
	 *            弹出Toast的内容的资源ID
	 */
	public static void show(Context ctx, int res) {
		toast(ctx, ctx.getResources().getString(res), Toast.LENGTH_SHORT);
	}
}