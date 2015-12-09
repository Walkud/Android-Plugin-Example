/*
 * 文 件 名:  ViewInObject.java
 * 描    述:  <描述>
 * 修 改 人:  杨强
 * 修改时间:  2013-8-6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package plugin.example.commonlib.common.lib.viewioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    public int value();
    
    public String click() default "";
    
    public String text() default "";
}
