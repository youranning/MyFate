package bwie.com.myfate.MyFragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import bwie.com.myfate.MyPresenter.RegistFirstFragmentPresenter;
import bwie.com.myfate.MyView.RegistFirstFragmentView;
import bwie.com.myfate.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RegistFirstFragment extends Fragment implements View.OnClickListener, RegistFirstFragmentView {


    private RegistFirstFragmentPresenter presenter;
    EventHandler eventHandler;
    private Button regist_verification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new RegistFirstFragmentPresenter(this);

        SMSSDK.initSDK(getActivity(), "1f2aff4e30c90", "bda60010b3500957db3d944413ae1ff4");
        // MobSDK.init(this, "1f2aff4e30c90", "bda60010b3500957db3d944413ae1ff4");
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                System.out.println("result = " + result);
                System.out.println("data = " + data);
            }
        };


        SMSSDK.registerEventHandler(eventHandler);
        return inflater.inflate(R.layout.regist_first, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.regist_loginbtn).setOnClickListener(this);
        regist_verification = (Button) view.findViewById(R.id.regist_verification);
        regist_verification.setOnClickListener(this);
    }

    private EditText getLoginUsername() {

        return (EditText) getView().findViewById(R.id.regist_username);
    }

    private EditText getLoginPassword() {
        return (EditText) getView().findViewById(R.id.regist_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_loginbtn:
                presenter.next(getLoginPassword().getText().toString().trim(), getLoginUsername().getText().toString().trim());
                break;
            case R.id.regist_verification:
                presenter.sendVerification(getLoginUsername().getText().toString().trim());

                break;
        }
    }

    @Override
    public void userError(int typeError) {
        switch (typeError) {
            case 1:
                Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void pwdErry(int typError) {
        switch (typError) {
            case 1:
                Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "请输入正确的验证码", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void sendVerification() {
        getLoginPassword().requestFocus();
    }

    @Override
    public void next() {

//        Bundle bundle=new Bundle();
//        bundle.putString("phone",);
        String phone = getLoginUsername().getText().toString().trim();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.regist_framlayout, new RegistSecondFragment(phone)).addToBackStack(null).commit();
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showTimer() {
        regist_verification.setClickable(false);
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


//                        d.dispose();

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        System.out.println("aLong = " + aLong);
                        regist_verification.setText(aLong + " S ");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        regist_verification.setClickable(true);
                        regist_verification.setText("重新发送验证码");

                    }
                });
    }
}
