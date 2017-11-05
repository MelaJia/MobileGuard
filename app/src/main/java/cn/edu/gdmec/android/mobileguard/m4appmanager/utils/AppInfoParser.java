package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by HP on 2017/11/5.

 */

public class AppInfoParser {
    /**
     *  获取手机里面的所有应用程序
     * @param context 上下文
     * @return
     */
    public static List<AppInfo> getAppInfos(Context context){

        //得到一个包管理器
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);

        //定义 List<AppInfo>集合，该集合用于存储获取到的应用程序信息
        List<AppInfo> appinfos = new ArrayList<>();

        //for循环用于历遍packageInfos对象，并将每个应用程序包名、图像、应用程序名称存储到AppInfo对象中
        for (PackageInfo packageInfo:packageInfos){
            AppInfo appinfo = new AppInfo();
            String packagename = packageInfo.packageName;
            appinfo.packageName = packagename;
            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
            appinfo.icon=icon;
            String appname=packageInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName=appname;

            //应用程序apk包的路径
            String apppath=packageInfo.applicationInfo.sourceDir;
            appinfo.appPath=apppath;
            File file=new File(apppath);
            long appSize=file.length();
            appinfo.appSize=appSize;
            //应用程序安装位置
            int flags=packageInfo.applicationInfo.flags;//二进制映射
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags) != 0){
                //外部存储
                appinfo.isInRoom=false;
            }else {
                //内部存储
                appinfo.isInRoom=true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM & flags) != 0){
                //系统应用
                appinfo.isUserAPP=false;
            }else {
                //用户应用
                appinfo.isUserAPP=true;
            }

            //将AppInnfo对象添加到List<AppInfo> appinfos集合中
            appinfos.add(appinfo);
            appinfo = null;

        }
        return appinfos;
    }

}
