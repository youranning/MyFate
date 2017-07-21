package bwie.com.myfate.MyMoudle;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import bwie.com.myfate.IApplication;
import bwie.com.myfate.bean.RegisterBean;
import bwie.com.myfate.md5.cipher.Md5Utils;
import bwie.com.myfate.md5.cipher.aes.JNCryptorUtils;
import bwie.com.myfate.md5.cipher.rsa.RsaUtils;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;
import bwie.com.myfate.utils.PreferencesUtils;

/**
 * Created by $USER_NAME on 2017/7/6.
 */

public class RegistSecondFragmentModleImpl implements RegistSecondFragmentModle {
    @Override
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener) {
        Map<String,String> map = new HashMap<String,String>();
    /*    map.put("user.nickname",nickname);
        //生成随机16位字符数
        String randomKey =   RsaUtils.getStringRandom(16);

        //16位随机数和 rsa 加密
        String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);

        //给电话号码  和 aes 加密
        String aesPhone =   JNCryptorUtils.getInstance().encryptData(phone,IApplication.getApplication(),randomKey);

        map.put("user.phone",aesPhone);
        map.put("user.password", Md5Utils.getMD5(password));*/

        map.put("user.nickname",nickname);
        map.put("user.gender",sex);
        map.put("user.area",area);
        map.put("user.age",age);
        map.put("user.introduce",introduce);
        map.put("user.phone",phone);
        map.put("user.password", Md5Utils.getMD5(password));
        String latitude = PreferencesUtils.getValueByKey(IApplication.getApplication(), "latitude", null);
        String longitude = PreferencesUtils.getValueByKey(IApplication.getApplication(), "longitude", null);
        System.out.println("longitude = " + longitude);
        System.out.println("latitude = " + latitude);
//        map.put("user.lat",latitude+"");
//        map.put("user.lng",longitude+"");
//        map.put("user.secretkey",rsaRandomKey);

        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);
       System.out.println(sign);
        System.out.println(Md5Utils.getMD5(password));
        System.out.println("phone = " + phone);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {


                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

/*
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"phone",registerBean.getData().getPhone());
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"password",registerBean.getData().getPassword());
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",registerBean.getData().getUserId());
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"nickname",registerBean.getData().getNickname());*/

                listener.onSuccess(registerBean);

            }

            @Override
            public void onFailed(int code) {
                listener.onFailed(code);
            }
        });

    }
}
