package cn.edu.gdmec.android.mobileguard;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.m1home.HomeActivity;
import cn.edu.gdmec.android.mobileguard.m1home.utils.MyUtils;
import cn.edu.gdmec.android.mobileguard.m1home.utils.VersionUpdateUtils;


public class SplashActivity extends AppCompatActivity {
    private TextView mTvVision;
    private String mVersion;
//    onCreate(),activity创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setCotentView()  加载布局
        setContentView(R.layout.activity_splash);
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTvVision =(TextView)findViewById(R.id.tv_splash_version);
        mTvVision.setText("版本号"+mVersion);

//        final VersionUpdateUtils versionUpdateUtils=new VersionUpdateUtils(mVersion, SplashActivity.this);
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                versionUpdateUtils.getCloudVersion();
//            };
//        }.start();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    }

