package cn.edu.gdmec.android.mobileguard.m1home;
import cn.edu.gdmec.android.mobileguard.R;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.SplashActivity;
import cn.edu.gdmec.android.mobileguard.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.android.mobileguard.m2theftguard.LostFindActivity;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.SetUpPasswordDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.receiver.MyDeviceAdminReceiver;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.MD5Utils;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.SecurityPhoneActivity;
import cn.edu.gdmec.android.mobileguard.m4appmanager.AppManagerActivity;
import cn.edu.gdmec.android.mobileguard.m5virusscan.VirusScanActivity;

/**
 * Created by HP on 2017/9/24.-+--+
 */

public class HomeActivity extends Activity {
    private GridView gv_home;
    private long mExitTime;
    /**
     * 存储手机防盗密码的sp
     */
    private SharedPreferences msharedPreferences;
    //设置管理员
    private DevicePolicyManager policyManager;
    //申请权限
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
       //  getSupportActionBar().hide();
        msharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        //初始化GridView
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        //设置条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.print(i);
                switch (i) {
                    case 0: //点击手机防盗
                        if (isSetUpPassword()) {
                            //弹出输入密码对话框
                            showInterPswdDialog();
                        } else {
                            //弹出设置密码对话框
                            showSetUpPswdDialog();
                        }
                        break;
                    case 1:
                        startActivity(SecurityPhoneActivity.class);
                        break;//通讯卫士
                    case 2:
                        startActivity(AppManagerActivity.class);
                        break;
                    case 3:
                        startActivity(VirusScanActivity.class);
                        break;//手机杀毒
                }
            }
        });
       //1.获取设备管理员
        policyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        //2.申请权限
        componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        //3.判断，如果没有权限则申请权限
        boolean active = policyManager.isAdminActive(componentName);
        if (!active){
            //没有管理员权限，则获取管理员权限
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"获取超级管理员权限，用于远程锁屏和清除数据");
            startActivity(intent);

        }
    }
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }
    /**
     * 弹出设置密码对话框 本方法需要完成“手机防盗模块"之后才能启用
     */
    private void showSetUpPswdDialog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)) {
                    if (firstPwsd.equals(affirmPwsd)) {
                        //两次密码一致，存储密码
                        savePwsd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        //显示输入密码对话框
                        showInterPswdDialog();

                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void cancel() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    /**
     * t弹出输入密码对话框  本方法需要在完成“手机防盗模块“之后才能启用
     */
    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog mInPswdDialog = new InterPasswordDialog(HomeActivity.this);
        mInPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(mInPswdDialog.getPassword())) {
                    Toast.makeText(HomeActivity.this, "密码不能为空！",0).show();
                } else if (password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))) {
                    //进入防盗主界面
                    mInPswdDialog.dismiss();
                    startActivity(LostFindActivity.class);
                   // Toast.makeText(HomeActivity.this, "可以进入手机防盗模块", Toast.LENGTH_LONG).show();
                } else {
                    //对话框消失，弹出土司
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入",0).show();
                }
            }

            @Override
            public void cancel() {
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        //让对话框显示
        mInPswdDialog.show();
    }

    /**
     * 保存密码 本方法需要在完成：手机防盗模块后使用;
     *
     *
     */
    private void savePwsd(String affirmPwsd) {
        SharedPreferences.Editor edit = msharedPreferences.edit();
        //防止用户隐私信息被泄露，需要加密密码
        edit.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        edit.commit();
    }

    /**
     * 获取密码
     *
     * @return sp存储的密码
     */
    private String getPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        return password;
    }

    /**
     * 判断用户是否设置过手机防盗密ma
     */
    private boolean isSetUpPassword() {
    String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if(TextUtils.isEmpty(password)){
             return false;
        }
        return true;
    }
    /**
     * 开启新的activity不关闭自己
     *
     * @param cls
     *              新的activity字节码
     * */

    /**
     * 按两次返回键退出程序
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
