package plugin.example.commonlib.module.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plugin.core.PluginLoader;

import java.util.List;

import plugin.example.commonlib.R;
import plugin.example.commonlib.bean.PluginData;

/**
 * Created by Walkud on 15/9/10.
 */
public class PluginGridAdapter extends BaseAdapter {

    private Context mContext;
    List<PluginData> mPluginDatas;

    public PluginGridAdapter(Context context, List<PluginData> pluginDatas) {
        this.mContext = context;
        this.mPluginDatas = pluginDatas;
    }

    @Override
    public int getCount() {
        return mPluginDatas.size();
    }

    @Override
    public PluginData getItem(int position) {
        return mPluginDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.vh_plugin_grid_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        PluginData pluginData = getItem(position);

        vh.itemText.setText(pluginData.getDesc());
        vh.itemVersion.setText(pluginData.getVersion());

        setPluginInstallStatus(pluginData, vh);

        return convertView;
    }

    /**
     * 设置插件安装状态
     */
    private void setPluginInstallStatus(PluginData pluginData, ViewHolder vh) {


        if (pluginData.isInstalling()) {
            vh.itemInstallStatus.setText("安装中");
            vh.itemProgressbar.setVisibility(View.VISIBLE);
        } else if (PluginLoader.isInstallPluginById(pluginData.getPluginId())) {
            String version = PluginLoader.getPluginVersionById(pluginData.getPluginId());
            if (version.startsWith(pluginData.getVersion())) {
                vh.itemInstallStatus.setText("已安装");
            } else {
                vh.itemInstallStatus.setText("更新");
            }
            vh.itemProgressbar.setVisibility(View.GONE);
        } else {
            vh.itemInstallStatus.setText("未安装");
            vh.itemProgressbar.setVisibility(View.GONE);
        }

    }

    static class ViewHolder {
        TextView itemText;
        TextView itemVersion;
        TextView itemInstallStatus;
        ProgressBar itemProgressbar;

        ViewHolder(View view) {
            itemText = (TextView) view.findViewById(R.id.item_text);
            itemVersion = (TextView) view.findViewById(R.id.item_version);
            itemInstallStatus = (TextView) view.findViewById(R.id.item_install_status);
            itemProgressbar = (ProgressBar) view.findViewById(R.id.item_progressbar);


        }
    }
}
