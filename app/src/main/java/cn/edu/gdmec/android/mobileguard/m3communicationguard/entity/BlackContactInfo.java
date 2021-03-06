package cn.edu.gdmec.android.mobileguard.m3communicationguard.entity;

/**
 * Created by Dell on 2017/10/30.
 */

public class BlackContactInfo {
    /*黑名单号码*/
    public String phoneNumber;
    /*黑名单联系人*/
    public  String contactName;
    public String style;
    /*黑名单拦截模式,1为电话拦截 2为短信拦截 3微电话短信都拦截*/
    public int mode;
    public String getModeString(int mode){
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话、短信拦截";
        }
        return "";
    }
}
