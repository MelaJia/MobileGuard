package cn.edu.gdmec.android.mobileguade.m1home.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by HP on 2017/9/13.
 */

public class MyUtils {
    public static String getVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void installApk(Activity activity) {
        Intent intent=new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")));
        activity.startActivityForResult(intent,0);


    }
}
