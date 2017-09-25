package cn.edu.gdmec.android.mobileguade;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguade.m1home.utils.MyUtils;
import cn.edu.gdmec.android.mobileguade.m1home.utils.VersionUpdateUtils;


public class SplashActivity extends AppCompatActivity {
    private TextView mTvVision;
    private String mVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTvVision =(TextView)findViewById(R.id.tv_splash_version);
        mTvVision.setText("版本号"+mVersion);


        final VersionUpdateUtils updateUtils=new VersionUpdateUtils(mVersion, SplashActivity.this);
        new Thread(){
            @Override
            public void run() {

                updateUtils.getCloudVersion();
                super.run();
            };
        }.start();
    }

    }

