package cn.edu.gdmec.android.mobileguard.m5virusscan.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Dell on 2017/11/10.
 */

public class ScanAppInfo {
    public String appName;
    public boolean isVirus;
    public String packagename;
    public String description;
    public Drawable appicon;
    //云查杀部分
    public String virusScanUrl;
    public String md5info;
}
