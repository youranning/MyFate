package bwie.com.myfate.MyActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import bwie.com.myfate.R;

public class InitActivityActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        }
        setContentView(R.layout.init_activity);

        findViewById(R.id.init_login).setOnClickListener(this);
        findViewById(R.id.regist).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.init_login:
                //TODO implement
                startActivity(new Intent(getApplication(), LoginActivity.class));
                overridePendingTransition(R.anim.come,R.anim.out);
                break;
            case R.id.regist:
                //TODO implement
                startActivity(new Intent(getApplication(), RegistActivityActivity.class));
                overridePendingTransition(R.anim.come,R.anim.out);
                break;
        }
    }
}
