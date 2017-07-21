package bwie.com.myfate.MyPresenter;

import bwie.com.myfate.MyView.RegistThirdFragmentView;

/**
 * Created by $USER_NAME on 2017/7/7.
 */

public class RegistThirdFragmentPreaenter {
    RegistThirdFragmentView registThirdFragmentView;

    public RegistThirdFragmentPreaenter(RegistThirdFragmentView registThirdFragmentView) {
        this.registThirdFragmentView = registThirdFragmentView;
    }



    public void  camera(){
        registThirdFragmentView.camera();
    }

    public  void pick(){
        registThirdFragmentView.pick();
    }
}
