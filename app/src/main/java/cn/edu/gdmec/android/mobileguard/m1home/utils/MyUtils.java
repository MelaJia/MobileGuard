package cn.edu.gdmec.android.mobileguard.m1home.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by HP on 2017/9/13.
 */

public class MyUtils {
    public static String getVersion(Context context) {
        //可以获取清单文件中的所有内容
        PackageManager packageManager = context.getPackageManager();
        //PackageInfo packageInfo = null;
        try {
            //getPackageName（）获取当前文件的包名，获取本地版本号
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void installApk(Activity activity,String apkFile) {
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType( Uri.fromFile(
                new File( Environment.getExternalStoragePublicDirectory("/download/").getPath()+"/"+apkFile)),"application/vnd.android.package-archive");
        activity.startActivityForResult(intent,0);


    }
}
