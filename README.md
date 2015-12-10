# Android-Plugin-Example

该示例基于Android-Plugin-Framework框架，只对实现作相应说明，原理请移驾( https://github.com/limpoxe/Android-Plugin-Framework ) 使用的方案为：定制aapt方案，<br>
appt工具来自OpenAtlasExtension https://github.com/bunnyblue/ACDDExtension

---

##Sample

左图为插件在宿主程序中安装并运行效果，右图为插件独立测试运行效果
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

CommonLib:公用的页面、图片库、网络库、自定义控件等等(注意：当公共库有改动时，需要执行task===>copyCommonLibJar,并同时注意改动在插件中是否会有影响)
PluginCode:插件化框架
PluginMain:宿主程序，当前示例中只有资源和配置文件，并无任何代码
PluginOne:测试插件
ProvidedJar:一些公用的jar，执行copyCommonLibJar后会在该目录生成一个CommonLib.jar文件用于插件依赖
QrScanCode:二维码扫描插件

###开发模型
![](https://github.com/Walkud/Android-Plugin-Example/blob/master/image/PluginModel.png)


###开发步骤




