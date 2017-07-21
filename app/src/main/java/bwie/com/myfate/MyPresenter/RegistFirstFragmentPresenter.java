package bwie.com.myfate.MyPresenter;

import android.text.TextUtils;

import bwie.com.myfate.MyView.RegistFirstFragmentView;
import bwie.com.myfate.utils.PhoneCheckUtils;
import cn.smssdk.SMSSDK;

/**
 * Created by $USER_NAME on 2017/7/5.
 */

public class RegistFirstFragmentPresenter {

    RegistFirstFragmentView registFirstFragmentView;

    public RegistFirstFragmentPresenter(RegistFirstFragmentView registFirstFragmentView) {
        this.registFirstFragmentView = registFirstFragmentView;
    }

    public void sendVerification(String phone) {
        if (TextUtils.isEmpty(phone)) {
            registFirstFragmentView.userError(1);
            return;
        }
        if (!PhoneCheckUtils.isChinaPhoneLegal(phone)) {
            registFirstFragmentView.userError(2);
            return;
        }
        registFirstFragmentView.sendVerification();
        SMSSDK.getVerificationCode("86", phone);
         registFirstFragmentView.showTimer();
    }


    public void next(String verification, String phone) {
        if (TextUtils.isEmpty(phone)) {
            sendVerification(phone);
        }
        if (TextUtils.isEmpty(verification)) {
            registFirstFragmentView.pwdErry(1);
            return;
        }
        if (!PhoneCheckUtils.isVerification(verification)) {
            registFirstFragmentView.pwdErry(2);
            return;
        }
        registFirstFragmentView.next();

    }
}
