package bwie.com.myfate.MyMoudle;

import bwie.com.myfate.bean.RegisterBean;

/**
 * Created by $USER_NAME on 2017/7/6.
 */

public interface RegistSecondFragmentModle {

    void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, RegisterInforFragmentDataListener listener);


    interface RegisterInforFragmentDataListener {


        void onSuccess(RegisterBean registerBean);

        void onFailed(int code);
    }
}