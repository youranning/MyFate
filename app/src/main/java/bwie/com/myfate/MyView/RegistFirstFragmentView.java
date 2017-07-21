package bwie.com.myfate.MyView;

/**
 * Created by $USER_NAME on 2017/7/5.
 */

public interface RegistFirstFragmentView {
    void userError(int typeError);

    void pwdErry(int typError);

    void sendVerification();

    void next();

    // 显示倒计时
     void showTimer();
}
