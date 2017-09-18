package cn.edu.gdmec.android.mobileguade.m1home.utils;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.edu.gdmec.android.mobileguade.m1home.entity.VersionEntity;

/**
 * Created by HP on 2017/9/18.
 */

public class VersionUpdateUtils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    public VersionUpdateUtils(String mVersion, Activity context) {
        this.context = context;
        this.mVersion = mVersion;

    }

    public void getCloudVersion() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5000);
            HttpGet httpGet = new HttpGet("http://android2017.duapp.com/updateinfo.html");
           HttpResponse execute = null;
            execute = httpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = execute.getEntity();
                String result = EntityUtils.toString(httpEntity,"utf-8");
                    JSONObject jsonObject = new JSONObject(result);
                versionEntity = new VersionEntity();
                versionEntity.versionCode = jsonObject.getString("code");
                versionEntity.description = jsonObject.getString("des");
                versionEntity.apkurl = jsonObject.getString("apkurl");
                if (!mVersion.equals(versionEntity.versionCode)){
                    System.out.println(versionEntity.description);
                    DownLoadUtils downloadUtils = new DownLoadUtils();
                    downloadUtils.downloadApk(versionEntity.apkurl,"mobileguade.apk",context);

                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
