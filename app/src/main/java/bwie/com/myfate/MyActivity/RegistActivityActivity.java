package bwie.com.myfate.MyActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import bwie.com.myfate.MyFragment.RegistFirstFragment;
import bwie.com.myfate.R;


public class RegistActivityActivity extends FragmentActivity implements View.OnClickListener {


    private TextView title_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setContentView(R.layout.regist_activity);


        initView();
        getSupportFragmentManager().beginTransaction().replace(R.id.regist_framlayout,new RegistFirstFragment()).commit();
    }


    private void initView() {
        title_regist = (TextView) findViewById(R.id.title_regist);
        title_regist.setText("登录");
        findViewById(R.id.goback).setOnClickListener(this);

        title_regist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                finish();
                overridePendingTransition(R.anim.come,R.anim.out);
                break;
            case R.id.title_regist:
                startActivity(new Intent(getApplication(),LoginActivity.class));
                overridePendingTransition(R.anim.come,R.anim.out);
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.come,R.anim.out);
    }
}
