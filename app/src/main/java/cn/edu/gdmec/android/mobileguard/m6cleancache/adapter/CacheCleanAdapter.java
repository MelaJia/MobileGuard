package cn.edu.gdmec.android.mobileguard.m6cleancache.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Formatter;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m6cleancache.entity.CacheInfo;

/**
 * Created by student on 17/11/13.
 * 数据适配器
 */

public class CacheCleanAdapter extends BaseAdapter {
    private Context context;
    private List<CacheInfo> cacheInfos;
    public  CacheCleanAdapter(Context context,List<CacheInfo> cacheInfos){
        super();
        this.context=context;
        this.cacheInfos=cacheInfos;
    }
    @Override
    public int getCount() {
        return cacheInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return cacheInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_cacheclean_list, null);

        }
        CacheInfo cacheInfo = cacheInfos.get(i);
        holder.mAppIconImgv.setImageDrawable(cacheInfo.appIcon);
        holder.mAppNameTV.setText(cacheInfo.appName);
        holder.mCacheSizeTV.setText(android.text.format.Formatter.formatFileSize(context,cacheInfo.cacgeSize));
        return view;
    }
    static class ViewHolder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        TextView mCacheSizeTV;

    }
}
