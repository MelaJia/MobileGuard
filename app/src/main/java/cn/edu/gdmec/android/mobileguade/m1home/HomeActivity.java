package cn.edu.gdmec.android.mobileguade.m1home;
import cn.edu.gdmec.android.mobileguade.R;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.R.attr;

import cn.edu.gdmec.android.mobileguade.m1home.adapter.HomeAdapter;

/**
 * Created by HP on 2017/9/24.
 */

public class HomeActivity extends AppCompatActivity{
    private GridView gv_name;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        gv_name=(GridView) findViewById(R.id.gv_home);
        gv_name.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_name.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int i,long l){
                switch (i){
                    //下节课
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis()-mExitTime)<2000){
                System.exit(0);
            }else{
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG).show();
                mExitTime=System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

}
