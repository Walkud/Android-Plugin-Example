# Android-Plugin-Example

该示例基于Android-Plugin-Framework框架，只对实现作相应说明，原理请移驾( https://github.com/limpoxe/Android-Plugin-Framework ) 使用的方案为：定制aapt方案，<br>
appt工具来自OpenAtlasExtension https://github.com/bunnyblue/ACDDExtension

---

##Sample

左图为插件在宿主程序中安装并运行效果，右图为插件独立测试运行效果<br>
![](https://github.com/Walkud/Android-Plugin-Example/blob/master/image/PluginGif.gif)
![](https://github.com/Walkud/Android-Plugin-Example/blob/master/image/PluginOne.gif)

##示例说明
###目的
  * 满足插件程序能独立运行测试
  * 减少开发学习成本
  * 尽量支持公共库，避免代码冗余
  * 尽量减小插件包大小
  * 保证插件完整性(插件安全验证)

示例主要体现插件在不修改任何代码支持独立运行和插件运行，其余几点框架本身已经支持，对于插件包大小优化以后再去深入研究。

###项目结构说明
>Android-Plugin-Example<br>
>>CommonLib        公共库<br>
>>PluginCode       插件框架<br>
>>PluginMain       宿主程序<br>
>>PluginOne        插件<br>
>>ProvidedJar      jar目录<br>
>>QrScanCode       插件<br>
>>……<br>

CommonLib:公用的页面、图片库、网络库、自定义控件等等(注意：当公共库有改动时，需要执行task===>copyCommonLibJar,并同时注意改动在插件中是否会有影响)<br>
PluginCode:插件化框架<br>
PluginMain:宿主程序，当前示例中只有资源和配置文件，并无任何代码<br>
PluginOne:测试插件<br>
ProvidedJar:一些公用的jar，执行copyCommonLibJar后会在该目录生成一个CommonLib.jar文件用于插件依赖<br>
QrScanCode:二维码扫描插件

###开发模型
![](https://github.com/Walkud/Android-Plugin-Example/blob/master/image/PluginModel.png)


###开发步骤
示例是基于Android-Plugin-Framework框架的定制aapt方案，所以首先去( https://github.com/bunnyblue/ACDDExtension )下载对应平台的aapt工具,目前我看已经更新至6.0了(赞一个~),在tag中可以下载5.1的aapt工具(buildTools/binary/build.tool.R22/)

1、将下载aapt工具替换掉SDK目录\build-tools\版本\aapt<br>
2、在插件中build.gradle文件<br>
    1）buildToolsVersion 'xx.x.x' 版本配置为aapt替换掉的版本<br>
    2）versionName版本添加后缀（0x[2-6]f)，例如:versionName "1.0.2"  --->  versionName "1.0.20x5f"<br>
3、公共库build.gradle文件添加任务生成插件依赖的Jar包，并执行copyCommonLibJar task(:CommonLib->other->copyCommonLibJar)，生成的jar目录为ProvidedJar
```
//删掉指定目录的Jar包
task clearCommonLibJar(type: Delete) {
    delete '../ProvidedJar/CommonLib.jar'
    println 'clearCommonLibJar'
}

//创建Jar包，生成目录在build/libs
task createCommonLibJar(type: Jar) {
    //指定生成的jar名
    baseName 'CommonLib'
    from 'build/intermediates/classes/release/'
    include '**/*.class'
    println 'createCommonLibJar'
}

//将生成的Jar包复制到指定目录
task copyCommonLibJar(type: Copy) {
    from 'build/libs/CommonLib.jar'
    into '../ProvidedJar/' // 目标位置
    println 'copyCommonLibJar'
}
```
4、在插件build.gradle文件使用provided关键字依赖jar
```
dependencies {
    provided files('../ProvidedJar/CommonLib.jar')
}
```
5、按照正常的方式生成apk，将apk拷贝至宿主程序的assets目录下，将assets下的PluginData.txt内容添加或修改对应的包名、启动Activity等
```

……
{
        "pluginId":"com.example.pluginstudy",//包名，必须唯一
        "desc": "PluginOne",//apk简介
        "version": "1.0.2",//版本
        "url": "http://172.25.105.14:8080/PluginOne-pluginOne-release.apk",//示例支持通过网络下载更新插件
        "path": "PluginOne-pluginOne-release.apk",//apk文件的包名
        "isSilent": false,//是否静默安装
        "classType": "Activity",//启动类型
        "bootClass": "com.example.pluginstudy.MainActivity",//启动Class全名
        "params": "{\"no\":1234567,\"str\":\"abcder\"}"//传入参数
    },
……

```
6、运行PluginMain

###插件独立运行配置
Gralde的灵活强大用起来非常棒(大大的赞一个~)，当然只用最基础的productFlavors和dependencies,在插件build.gradle中使用
```

    productFlavors {
        inside {
            manifestPlaceholders = [ SHARED_USER_ID:""]
        }

        pluginOne {
            manifestPlaceholders = [ SHARED_USER_ID:"plugin.example.pluginmain"]
        }
    }
    
    dependencies {
    ……
    pluginOneProvided files(COMMON_LIB) //COMMON_LIB常量为'../ProvidedJar/CommonLib.jar'
    pluginOneProvided 'com.squareup.picasso:picasso:2.5.2'
    ……

    insideCompile project(':CommonLib')
    }

```
productFlavors用来控制版本、包名、versionName、进程Id等等

dependencies在依赖上进行配置<br>
1、pluginOneProvided 配置用于插件版本时将jar包参与编译，不参与打包<br>
2、insideCompile 配置用于在独立测试时，直接依赖于公共库，在开发的时候，修改了代码直接Run,不需要将模块打包成插件再去测试，提高了开发和测试的效率。

###注意事项

待续...


##感谢
Android-Plugin-Framework框架作者 limpoxe<br>
aapt工具提供者 bunnyblue

##联系
添加时请注明插件开发。 Q群：207397154
