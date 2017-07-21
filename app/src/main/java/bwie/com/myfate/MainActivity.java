package bwie.com.myfate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import bwie.com.myfate.MyActivity.HomepageActivity;
import bwie.com.myfate.MyActivity.InitActivityActivity;
import bwie.com.myfate.MyActivity.LiaotianActivity;
import bwie.com.myfate.MyActivity.LoginActivity;
import bwie.com.myfate.MyActivity.Main;
import bwie.com.myfate.utils.PreferencesUtils;

public class MainActivity extends AppCompatActivity {
    //声明AMapLocationClient类对象
//声明定位回调监听器

    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    double latitude = aMapLocation.getLatitude();//获取纬度
                    PreferencesUtils.addConfigInfo(getApplication(),"latitude",latitude);
                    System.out.println("latitude = " + latitude);
                    double longitude = aMapLocation.getLongitude();//获取经度
                    PreferencesUtils.addConfigInfo(getApplication(),"longitude",longitude);
                    System.out.println("longitude = " + longitude);
                    String country = aMapLocation.getCountry();//国家信息
                    System.out.println("country = " + country);
                    String province = aMapLocation.getProvince();//省信息
                    System.out.println("province = " + province);
                    String city = aMapLocation.getCity();//城市信息
                    System.out.println("city = " + city);
                    Toast.makeText(getApplication(),"当前城市是"+city,Toast.LENGTH_SHORT).show();
                    PreferencesUtils.addConfigInfo(MainActivity.this,"latitude",latitude);
                    PreferencesUtils.addConfigInfo(MainActivity.this,"longitude",longitude);
                    PreferencesUtils.addConfigInfo(MainActivity.this,"city",city);

//可在其中解析amapLocation获取相应内容。
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

//获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();




//        startActivity(new Intent(getApplication(), LiaotianActivity.class));
        startActivity(new Intent(getApplication(), Main.class));
        finish();


    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }
}
