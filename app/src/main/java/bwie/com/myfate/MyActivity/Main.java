package bwie.com.myfate.MyActivity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bwie.com.myfate.MyFragment.AnimFragment;
import bwie.com.myfate.MyFragment.FindfragmentFragment;
import bwie.com.myfate.MyFragment.MsgFragment;
import bwie.com.myfate.MyFragment.MyFragment;
import bwie.com.myfate.R;
import bwie.com.myfate.bean.MyEvENT;

/**
 * Created by $USER_NAME on 2017/7/10.
 */

public class Main extends FragmentActivity implements View.OnClickListener {
    private FragmentManager manager;
    private MyFragment my;
    private MsgFragment msg;
    private FindfragmentFragment find;
    private AnimFragment animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.main);
        initView();

        manager = getSupportFragmentManager();
        find = (FindfragmentFragment) manager.findFragmentById(R.id.main_find);
        animation = (AnimFragment) manager.findFragmentById(R.id.main_anim);
        msg = (MsgFragment) manager.findFragmentById(R.id.main_msg);
        my = (MyFragment) manager.findFragmentById(R.id.main_my);
        manager.beginTransaction().hide(animation).hide(msg).hide(my).show(find).commit();
    }


    private void initView() {

        findViewById(R.id.rg1).setOnClickListener(this);
        findViewById(R.id.rg2).setOnClickListener(this);
        findViewById(R.id.rg3).setOnClickListener(this);
        findViewById(R.id.rg4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rg2:
                Log.e("", "-----------------------");
                manager.beginTransaction().hide(find).hide(msg).hide(my).show(animation).commit();
                break;
            case R.id.rg1:
                manager.beginTransaction().hide(animation).hide(msg).hide(my).show(find).commit();

                break;
            case R.id.rg3:
                manager.beginTransaction().hide(animation).hide(find).hide(my).show(msg).commit();

                break;
            case R.id.rg4:
                manager.beginTransaction().hide(animation).hide(msg).hide(find).show(my).commit();

                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(MyEvENT event) {
        manager.beginTransaction().hide(animation).hide(find).hide(my).show(msg).commit();

    }
}
