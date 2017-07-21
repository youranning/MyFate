package bwie.com.myfate.MyActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.mob.MobSDK;

import java.util.HashMap;
import java.util.Map;

import bwie.com.myfate.IApplication;
import bwie.com.myfate.MyPresenter.LoginPresenter;
import bwie.com.myfate.MyView.LoginView;
import bwie.com.myfate.R;
import bwie.com.myfate.bean.Success;
import bwie.com.myfate.md5.cipher.Md5Utils;
import bwie.com.myfate.md5.cipher.aes.JNCryptorUtils;
import bwie.com.myfate.md5.cipher.rsa.RsaUtils;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;
import bwie.com.myfate.utils.KeyBoardHelper;
import bwie.com.myfate.utils.PreferencesUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends Activity implements View.OnClickListener, LoginView, KeyBoardHelper.OnKeyBoardStatusChangeListener {


    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.login);


        findViewById(R.id.login_loginbtn).setOnClickListener(this);
        findViewById(R.id.login_phone).setOnClickListener(this);
        findViewById(R.id.title_regist).setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(LoginActivity.this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(LoginActivity.this);
    }

    private EditText getLoginUsername() {
        return (EditText) findViewById(R.id.login_username);
    }

    private EditText getLoginPassword() {
        return (EditText) findViewById(R.id.login_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_loginbtn:
                loginPresenter.login(getLoginUsername().getText().toString().trim(), getLoginPassword().getText().toString()
                        .trim());
                break;
            case R.id.login_phone:
//                SMSSDK.getVerificationCode("+86", "15035828759");
                break;
            case R.id.title_regist:
                startActivity(new Intent(getApplication(), RegistActivityActivity.class));
                finish();
                overridePendingTransition(R.anim.come, R.anim.out);
                break;
            case R.id.goback:
                finish();
                overridePendingTransition(R.anim.come, R.anim.out);
                break;
        }
    }

    @Override
    public void login() {
        String password = getLoginPassword().getText().toString().trim();
        String phone = getLoginUsername().getText().toString().trim();
        String randomKey = RsaUtils.getStringRandom(16);


        String rsaRandomKey = RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(), randomKey);


        String cipherPhone = JNCryptorUtils.getInstance().encryptData(phone, IApplication.getApplication(), randomKey);


        Map map = new HashMap<String, String>();
        map.put("user.phone", cipherPhone);
        map.put("user.password", Md5Utils.getMD5(password));
        map.put("user.secretkey", rsaRandomKey);
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_login.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);

                Gson gson = new Gson();
                Success success = gson.fromJson(result, Success.class);



                if (success.getResult_code() == 200) {
                    PreferencesUtils.addConfigInfo(LoginActivity.this, "liaotian_head", success.getData().getImagePath());

                    loginHuanxin(success.getData().getUserId() + "", success.getData().getYxpassword());


                    startActivity(new Intent(getApplication(), Main.class));
                    finish();
                } else {
                    Toast.makeText(getApplication(), "密码和账号不匹配", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });

    }


    @Override
    public void userEmpty() {
        Toast.makeText(getApplication(), "请输入账号", Toast.LENGTH_LONG).show();
    }

    @Override
    public void pwdEmpty() {
        Toast.makeText(getApplication(), "请输入密码", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.come, R.anim.out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterEventHandler(eventHandler);
    }

    void loginHuanxin(String userName, String password) {
        System.out.println("userName = " + userName);
        System.out.println("password = " + password);

      /*  try {
            EMClient.getInstance().createAccount(userName, password);//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
        }*/
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        PreferencesUtils.addConfigInfo(IApplication.getApplication(), "kh", keyBoardheight);

    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }
}
