package cn.edu.gdmec.android.mobileguard.m5virusscan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.utils.MyUtils;

/**
 * Created by Dell on 2017/11/10.
 */

public class VirusScanActivity extends Activity implements View.OnClickListener{
    private TextView mLastTimeTV;
    private SharedPreferences mSP;
    private String mVersion;
    private TextView mTvVision;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_virus_scan);
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        copyDB("antivirus.db");
        initview();
    }
    @Override
    protected void onResume(){
        String string=mSP.getString("lastVirusScan","您还没有查杀病毒！");
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTvVision =(TextView)findViewById(R.id.tv_db);
        mLastTimeTV.setText(string);
        mTvVision.setText("病毒数据库版本:"+mVersion);
        super.onResume();
    }
    /**
     * 复制病毒数据库
     */
    private void copyDB(final String dbname){
        new Thread(){
            public void run(){
                try{
                    File file=new File(getFilesDir(),dbname);
                    if(file.exists() && file.length()>0){
                        Log.i("VirusScanActivity","数据库已存在!");
                        return;
                    }
                    InputStream is=getAssets().open(dbname);
                    FileOutputStream fos=openFileOutput(dbname,MODE_PRIVATE);
                    byte[] buffer=new byte[1024];
                    int len=0;
                    while((len=is.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                    is.close();
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
        }.start();
    }
    private void initview(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.light_blue)
        );
        ImageView mLeftImgev=(ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("病毒查杀");
        mLeftImgev.setOnClickListener(this);
        mLeftImgev.setImageResource(R.drawable.back);
        mLastTimeTV = (TextView) findViewById(R.id.tv_lastscantime);
        findViewById(R.id.rl_allscanvirus).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.rl_allscanvirus:
                startActivity(new Intent(this,VirusScanSpeedActivity.class));
                break;
        }
    }
}
