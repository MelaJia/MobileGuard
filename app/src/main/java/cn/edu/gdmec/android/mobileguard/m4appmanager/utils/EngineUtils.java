package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.BuildConfig;
import cn.edu.gdmec.android.mobileguard.m4appmanager.adapter.AppManagerAdapter;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by HP on 2017/11/5.
 * 分享应用
 */

public class EngineUtils {
    public static void shareApplication(Context context, AppInfo appInfo) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件，名字叫做:" + appInfo.appName + "下载路径:https://play.google.com.store/apps/details?id=" + appInfo.packageName);
        context.startActivity(intent);

    }

    /**
     * 开启应用程序
     */
    public static void startApplication(Context context, AppInfo appInfo) {
        //
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "该应用没有启动界面", 0).show();
        }
    }

    /**
     * 开启应用设置界面
     *
     * @param context
     * @param appInfo
     */
    public static void settingAppDetail(Context context, AppInfo appInfo) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + appInfo.packageName));
        context.startActivity(intent);

    }

    /*卸载应用*/
    public static void utinstallApplication(Context context, AppInfo appInfo) {
        if (appInfo.isUserAPP) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DEFAULT);
            intent.setData(Uri.parse("package:" + appInfo.packageName));
            context.startActivity(intent);
        } else {
            //系统需要root权限，利用Linux命令删除文件
            if (!RootTools.isRootAvailable()) {
                Toast.makeText(context, "卸载系统应用，必须要root权限", 0).show();
                return;
            }
            try {
                if (!RootTools.isAccessGiven()) {
                    Toast.makeText(context, "请授权小熊护卫root权限", 0).show();
                    return;
                }
                RootTools.sendShell("mount -o remount,rw/system", 3000);
                RootTools.sendShell("rm -r" + appInfo.appPath, 30000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void aboutApp(Context context, AppInfo appInfo) {
        try {
            //包管理器
            PackageManager pm = context.getPackageManager();
            //获得包的所有内容信息类
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);

//            String versionName = pi.versionName;
            float versioncode = pi.versionCode;
            long firstInstallTime = pi.firstInstallTime;//获取用户第一次安装时间

            Date d = new Date(firstInstallTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 上午 hh:mm:ss");
            String time = format.format(d);

         /** 通过包管理器获得指定包名包含签名的包信息 **/
         //   List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
//            Iterator<PackageInfo> iter = apps.iterator();
//            PackageInfo packageinfo = iter.next();
//            info.signatures[0].toByteArray();

            //String packageName = packageinfo.packageName;
//            String name= packageinfo.signatures[0].toCharsString();
        /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo pk = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_SIGNATURES);
            /** 签名信息**/
           Signature[] signatures = pk.signatures;
            //证书工厂类，这个类实现了出厂合格证算法的功能
            //X509证书，X.509是一种非常通用的证书格式
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            byte[] cert = signatures[0].toByteArray();
            InputStream input = new ByteArrayInputStream(cert);
            X509Certificate c = (X509Certificate) certFactory.generateCertificate(input);
            String cc="";
            cc+=c.getIssuerDN().toString();
            cc += c.getSubjectDN().toString();
            //将签名转换为字节数组流
//            StringBuilder builder = new StringBuilder();
//            for (Signature signature : signatures) {
//                builder.append(signature.toCharsString());
//            }
//            /************** 得到应用签名 **************/
//            String signature = builder.toString();

//            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            //获得公钥

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(appInfo.appName);
            dialog.setMessage("Version:"+versioncode+"\n"+"Install time:"+time+"\n"+"Certificate issuer:"+cc+"\n\n"+"Permissions:"+"");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            dialog.show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
