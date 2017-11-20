package cn.edu.gdmec.android.mobileguard.m6cleancache;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

//import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m6cleancache.adapter.CacheCleanAdapter;
import cn.edu.gdmec.android.mobileguard.m6cleancache.entity.CacheInfo;

public class CacheClearListActivity extends AppCompatActivity {
    protected static final int SCANNING = 100;
    protected static final  int FINISH = 101;
    private AnimationDrawable animation;

    private TextView mRecomandTV;
    private TextView mCanCleanTV;
    private long cacheMemory;
    private List<CacheInfo> cacheInfos = new ArrayList<CacheInfo>();
    private List<CacheInfo> mCacheInfos = new ArrayList<CacheInfo>();
    private PackageManager pm;
    private CacheCleanAdapter adapter;
    private ListView mCacheLV;
    private Button mCacheBtn;
    private Thread thread;
    private Handler handler = new Handler(){
     //   @Override
        public void handlerMessage(Message msg){
            switch (msg.what){
                case SCANNING:
                    PackageInfo info = (PackageInfo)msg.obj;
                    mRecomandTV.setText("正在扫描："+info.packageName);
                    mCanCleanTV.setText("已扫描缓存："+ Formatter.formatFileSize(CacheClearListActivity.this,cacheMemory));/*Formatter.formatFileSize(CacheClearListActivity.this,cacheMemory));*/;
                    mCacheInfos.clear();
                    mCacheInfos.addAll(cacheInfos);
                    adapter.notifyDataSetChanged();
                    mCacheLV.setSelection(mCacheInfos.size());
                    break;

                case FINISH:
                    animation.stop();
                    if (cacheMemory>0){
                        mCacheBtn.setEnabled(true);
                    }else {
                        mCacheBtn.setEnabled(false);
                        Toast.makeText(CacheClearListActivity.this,"您的手机洁净如新",Toast.LENGTH_LONG).show();

                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clear_list);
        pm = getPackageManager();
        initView();
    }
    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.rose_red));
        ImageView mLeftImgv = (ImageView)findViewById(R.id.imgv_leftbtn);
       // mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        ((TextView)findViewById(R.id.tv_title)).setText("缓存扫描");
        mRecomandTV = (TextView)findViewById(R.id.tv_recommend_clean);
        mCanCleanTV = (TextView) findViewById(R.id.tv_can_clean);
        mCacheLV = (ListView) findViewById(R.id.lv_scancache);
        mCacheBtn = (Button)findViewById(R.id.btn_cleanall);
       // mCacheBtn.setOnClickListener(this);
        animation =(AnimationDrawable)findViewById(R.id.imgv_broom).getBackground();
        animation.start();
        adapter = new CacheCleanAdapter(this,mCacheInfos);
        mCacheLV.setAdapter(adapter);
        fillData();
    }
    private void fillData(){

    }

}
