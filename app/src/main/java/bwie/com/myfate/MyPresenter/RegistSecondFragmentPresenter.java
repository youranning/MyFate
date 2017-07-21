package bwie.com.myfate.MyPresenter;

import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import bwie.com.myfate.MyFragment.RegistSecondFragment;
import bwie.com.myfate.MyMoudle.RegistSecondFragmentModle;
import bwie.com.myfate.MyMoudle.RegistSecondFragmentModleImpl;
import bwie.com.myfate.MyView.RegistSecondFragmentView;
import bwie.com.myfate.bean.RegisterBean;

/**
 * Created by $USER_NAME on 2017/7/5.
 */

public class RegistSecondFragmentPresenter implements RegistSecondFragmentModle.RegisterInforFragmentDataListener {
    RegistSecondFragmentView registSecondFragmentView;
    private final RegistSecondFragmentModleImpl registSecondFragmentModle;

    public RegistSecondFragmentPresenter(RegistSecondFragmentView registSecondFragmentView) {
        this.registSecondFragmentView = registSecondFragmentView;
        registSecondFragmentModle = new RegistSecondFragmentModleImpl();
    }

    public void next(String pickname, String age, String sex, String city, String describ, String pwd1, String pwd2, String phone) {
        if (TextUtils.isEmpty(pickname)) {
            registSecondFragmentView.nickEmpty();
            return;
        }
        if (age.equals(">")) {
            registSecondFragmentView.ageEmpty();
            return;
        }
        if (sex.equals(">")) {
            registSecondFragmentView.sexEmpty();
            return;
        }
        if (city.equals(">")) {
            registSecondFragmentView.cityEmpty();
            return;
        }
        if (TextUtils.isEmpty(describ)) {
            registSecondFragmentView.describtionEmpty();
            return;
        }
        if (TextUtils.isEmpty(pwd1)) {
            registSecondFragmentView.pwd1Empty();
            return;
        }
        if (TextUtils.isEmpty(pwd2)) {
            registSecondFragmentView.pwd2Empty();
            return;
        }
        if (!pwd1.equals(pwd2)) {
            registSecondFragmentView.isIdentical();
            return;
        }
        registSecondFragmentView.next();

        registSecondFragmentModle.getData(phone, pickname, sex, age, city, describ, pwd1, this);
    }

    @Override
    public void onSuccess(RegisterBean registerBean) {

        System.out.println("registerBean.getResult_code()= " + registerBean.getResult_code());
        if (registerBean.getResult_code() == 200) {
loginHuanxin(registerBean.getData().getUserId()+"",registerBean.getData().getYxpassword());
        }
        System.out.println(registerBean.toString());
    }

    @Override
    public void onFailed(int code) {
        System.out.println("code = " + code);
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
}
