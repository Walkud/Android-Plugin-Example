/*
 * 文 件 名:  ContentView.java
 * 版    权:   Copyright 2014,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  
 * 修改时间:  2014-2-8
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package plugin.example.commonlib.common.lib.viewioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.TYPE})
public @interface ContentViewById {
    int value();
}
