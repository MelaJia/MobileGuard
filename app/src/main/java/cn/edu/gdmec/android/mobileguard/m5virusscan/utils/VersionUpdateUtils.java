package cn.edu.gdmec.android.mobileguard.m5virusscan.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.VirusScanActivity;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.VersionEntity;
import cn.edu.gdmec.android.mobileguard.m5virusscan.utils.DownLoadUtils;

/**
 * Created by HP on 2017/9/18.
 */

public class VersionUpdateUtils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    private static final int MESSAGE_IO_ERROR = 102;
    private static final int MESSAGE_JSON_ERROR = 103;
    private static final int MESSAGE_SHOW_DIALOG = 104;
    private static final int MESSAGE_ENTERHOME = 105;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case MESSAGE_IO_ERROR:
                   Toast.makeText(context,"IO错误",Toast.LENGTH_LONG).show();
                   break;
               case MESSAGE_JSON_ERROR:
                   Toast.makeText(context,"JSON解析错误",Toast.LENGTH_LONG).show();
                   break;
               case MESSAGE_SHOW_DIALOG:
                   showUpdateDialog(versionEntity);

                   break;
               case MESSAGE_ENTERHOME:
                   Intent intent = new Intent(context, VirusScanActivity.class);
                   context.startActivity(intent);
                   context.finish();
                   break;

           }
        }
    };


    public VersionUpdateUtils(String mVersion, Activity context) {
        this.context = context;
        this.mVersion = mVersion;

    }

    public void getCloudVersion() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5000);
            HttpGet httpGet = new HttpGet("http://android2017.duapp.com/virusupdateinfo.html");
            HttpResponse execute = httpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = execute.getEntity();
                String result = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(result);
                versionEntity = new VersionEntity();
                versionEntity.versionCode = jsonObject.getString("code");
                versionEntity.description = jsonObject.getString("des");
                versionEntity.apkurl = jsonObject.getString("apkurl");
                if (!mVersion.equals(versionEntity.versionCode)){
                    handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
                    System.out.println(versionEntity.description);
                    cn.edu.gdmec.android.mobileguard.m1home.utils.DownLoadUtils downloadUtils = new cn.edu.gdmec.android.mobileguard.m1home.utils.DownLoadUtils();
                    downloadUtils.downloadApk(versionEntity.apkurl,"mobileguade.apk",context);
                }
            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        }catch (JSONException e) {
            handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }
    }
    private void showUpdateDialog(final VersionEntity versionEntity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("检测到有新版本："+versionEntity.versionCode);
        builder.setMessage(versionEntity.description);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadNewApk(versionEntity.apkurl);
            }
        });
        builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();
            }
        });
        builder.show();
    }
    private void enterHome(){
        handler.sendEmptyMessage(MESSAGE_ENTERHOME);
    }
    private void downloadNewApk(String apkurl){
        cn.edu.gdmec.android.mobileguard.m5virusscan.utils.DownLoadUtils downLoadUtils = new DownLoadUtils();
        downLoadUtils.downloadApk(apkurl,"mobileguade.apk",context);


    }



}
