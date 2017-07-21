package bwie.com.myfate.MyFragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import bwie.com.myfate.IApplication;
import bwie.com.myfate.MyActivity.LoginActivity;
import bwie.com.myfate.R;
import bwie.com.myfate.network.cookie.CookiesManager;


public class MyFragment extends Fragment implements View.OnClickListener {


    private ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.my, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv = (ImageView) view.findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new CookiesManager(IApplication.application).removeAllCookie();

        startActivity(new Intent(getActivity(),LoginActivity.class));

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                System.out.println("退出成功");

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                System.out.println("message = " + message);

            }
        });


        getActivity().finish();

    }
}
