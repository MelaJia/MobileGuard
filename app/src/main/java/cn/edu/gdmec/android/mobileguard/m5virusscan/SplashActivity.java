package cn.edu.gdmec.android.mobileguard.m5virusscan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.utils.MyUtils;
import cn.edu.gdmec.android.mobileguard.m5virusscan.utils.VersionUpdateUtils;


public class SplashActivity extends AppCompatActivity {
    private TextView mTvVision;
    private String mVersion;
//    onCreate(),activity创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setCotentView()  加载布局
        setContentView(R.layout.activity_virus_scan);
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTvVision =(TextView)findViewById(R.id.tv_db_version);
        mTvVision.setText("病毒数据库版本:"+mVersion);

        final VersionUpdateUtils versionUpdateUtils=new VersionUpdateUtils(mVersion, SplashActivity.this);
        new Thread(){
            @Override
            public void run() {
                super.run();
                versionUpdateUtils.getCloudVersion();
            };
        }.start();
    }

    }

