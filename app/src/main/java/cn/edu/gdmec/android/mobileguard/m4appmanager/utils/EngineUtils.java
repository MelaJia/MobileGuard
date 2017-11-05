package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by HP on 2017/11/5.
 * 分享应用
 */

public class EngineUtils {
    public static void shareApplication(Context context, AppInfo appInfo){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"推荐您使用一款软件，名字叫做:"+appInfo.appName+"下载路径:https://play.google.com.store/apps/details?id="+appInfo.packageName);
        context.startActivity(intent);

    }
   /**
    * 开启应用程序
    * */
   public static void startApplication(Context context,AppInfo appInfo){
       //
       PackageManager pm = context.getPackageManager();
       Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
       if (intent != null){
           context.startActivity(intent);
       }else {
           Toast.makeText(context,"该应用没有启动界面",0).show();
       }
   }
   /**
    * 开启应用设置界面
    * @param context
    * @param appInfo
    * */
   public static void settingAppDetail(Context context,AppInfo appInfo){
       Intent intent = new Intent();
       intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
       intent.addCategory(Intent.CATEGORY_DEFAULT);
       intent.setData(Uri.parse("package:"+appInfo.packageName));
       context.startActivity(intent);

   }
   /*卸载应用*/
   public static void utinstallApplication(Context context,AppInfo appInfo){
       if (appInfo.isUserAPP){
           Intent intent = new Intent();
           intent.setAction(Intent.ACTION_DEFAULT);
           intent.setData(Uri.parse("package:"+appInfo.packageName));
           context.startActivity(intent);
       }else {
           //系统需要root权限，利用Linux命令删除文件
           if (!RootTools.isRootAvailable()){
               Toast.makeText(context,"卸载系统应用，必须要root权限",0).show();
               return;
           }
           try {
               if (!RootTools.isAccessGiven()){
                   Toast.makeText(context,"请授权小熊护卫root权限",0).show();
                   return;
               }
               RootTools.sendShell("mount -o remount,rw/system",3000);
               RootTools.sendShell("rm -r"+appInfo.appPath,30000);

           }catch (Exception e){
               e.printStackTrace();
           }
       }
   }
}
