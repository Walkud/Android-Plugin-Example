package plugin.example.commonlib.common.lib.viewioc;

import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Method;

public class EventListener implements OnClickListener {
    
    private Object handler;
    
    private String clickMethod;
    
    public EventListener(Object handler) {
        this.handler = handler;
    }
    
    public EventListener click(String method) {
        this.clickMethod = method;
        return this;
    }
    
    @Override
	public void onClick(View v) {
        
        invokeClickMethod(handler, clickMethod, v);
    }
    
    private static Object invokeClickMethod(Object handler, String methodName, Object... params) {
        if (handler == null)
            return null;
        Method method = null;
        try {
            method = handler.getClass().getDeclaredMethod(methodName, View.class);
            if (method != null) {
                return method.invoke(handler, params);
            }
            else {
                throw new Exception("方法名不存在" + methodName);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
    }
}
