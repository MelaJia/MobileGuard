package cn.edu.gdmec.android.mobileguard.m2theftguard.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

/**
 * Created by student on 17/10/23.
 */

public class GPSLocationService extends Service {
    private LocationManager lm;
    private MyListener listener;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        listener = new MyListener();
        //
        //
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        String name = lm.getBestProvider(criteria,true);
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        lm.requestLocationUpdates(name, 0, 0, listener);

    }
    private class MyListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            StringBuilder sb = new StringBuilder();
            sb.append("accuracy:"+location.getAccuracy()+"\n");
            sb.append("speed:"+location.getSpeed()+"\n");
            sb.append("Longitude:"+location.getLongitude()+"\n");
            sb.append("Latitude:"+location.getLatitude()+"\n");
            String result = sb.toString();
            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            String safenumber = sp.getString("safephone","");
            SmsManager.getDefault().sendTextMessage(safenumber,null,result,null,null);
            stopSelf();

        }
        //当位置提供者 状态发生改变时调用的方法。
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
        //当某个位置提供者 可用的时候调用的方法
        @Override
        public void onProviderEnabled(String s) {

        }
        //当某个位置提供者 不可用时调用的方法
        @Override
        public void onProviderDisabled(String s) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(listener);
        listener = null;
    }
}
