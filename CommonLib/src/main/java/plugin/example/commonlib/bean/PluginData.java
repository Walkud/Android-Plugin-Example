package plugin.example.commonlib.bean;

/**
 * Created by Walkud on 15/9/11.
 * 插件数据信息
 */
public class PluginData {

    private boolean isInstalling;//是否在安装中

    private String url;//插件下载地址

    private String path;//插件路径

    private String version;//插件版本

    private boolean isSilent;//是否静默安装

    private String desc;//插件简介

    private String pluginId;//插件ID，为插件APK的包名

    private String classType;//启动的类型，Activity,Service,Receiver,Action,Url

    private String bootClass;//启动的类(全名)

    private String params;//启动Intent带的参数，json格式

    /*******************
     * Get And Set
     **********************/

    public boolean isInstalling() {
        return isInstalling;
    }

    public void setInstalling(boolean installing) {
        isInstalling = installing;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setIsSilent(boolean isSilent) {
        this.isSilent = isSilent;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBootClass() {
        return bootClass;
    }

    public void setBootClass(String bootClass) {
        this.bootClass = bootClass;
    }
}
