/*
 * 文 件 名:  ViewFactory.java
 * 描    述:  <描述>
 * 修 改 人:  杨强
 * 修改时间:  2013-8-6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package plugin.example.commonlib.common.lib.viewioc;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Field;


public class ViewFactory {
    
    /**
     * 初始化activity
     * 
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public static void InitActivity(Activity activity) {
        InitContent(activity);
        
        InitView(activity, activity.getWindow().getDecorView());
        
    }
    
    /**
     * 初始化content
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public static void InitContent(Activity activity) {
        ContentViewById contentViewById = activity.getClass().getAnnotation(ContentViewById.class);
        if (contentViewById != null) {
            int viewId = contentViewById.value();
            activity.setContentView(viewId);
        }
    }
    
    /**
     * 初始化view
     * 
     * @param injectedSource
     * @param sourceView
     * @see [类、类#方法、类#成员]
     */
    public static void InitView(Object injectedSource, View sourceView) {
        
        Field[] fields = injectedSource.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                ViewById viewInObject = field.getAnnotation(ViewById.class);
                if (viewInObject != null) {
                    int viewId = viewInObject.value();
                    try {
                        field.setAccessible(true);
                        field.set(injectedSource, sourceView.findViewById(viewId));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    // 设置clicklistener
                    String clickMethod = viewInObject.click();
                    if (!TextUtils.isEmpty(clickMethod))
                        setViewClickListener(injectedSource, field, clickMethod);
                    
                    // 设置text
                    String textValue = viewInObject.text();
                    if (!TextUtils.isEmpty(textValue)) {
                        setTextValue(injectedSource, field, textValue);
                    }
                }
            }
        }
    }
    
    /**
     * 设置点击事件
     * 
     * @param injectedSource
     * @param field
     * @param clickMethod
     */
    private static void setViewClickListener(Object injectedSource, Field field, String clickMethod) {
        try {
            field.setAccessible(true);
            Object obj = field.get(injectedSource);
            if (obj instanceof View) {
                ((View)obj).setOnClickListener(new EventListener(injectedSource).click(clickMethod));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 设置text值
     * 
     * @param injectedSource
     * @param field
     * @param textValue
     */
    private static void setTextValue(Object injectedSource, Field field, String textValue) {
        
        try {
            Object obj = field.get(injectedSource);
            if (obj instanceof TextView) {
                ((TextView)obj).setText(textValue);
            }
            else if (obj instanceof Button) {
                ((Button)obj).setText(textValue);
            }
            else if (obj instanceof RadioButton) {
                ((RadioButton)obj).setText(textValue);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
