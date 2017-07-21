package bwie.com.myfate.MyPresenter;

import android.text.TextUtils;

import bwie.com.myfate.MyView.LoginView;

/**
 * Created by $USER_NAME on 2017/7/4.
 */

public class LoginPresenter {

    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }


    public void login(String username, String pwd) {
        if (TextUtils.isEmpty(username)) {
            loginView.userEmpty();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            loginView.pwdEmpty();
            return;
        }
        loginView.login();

    }


}
