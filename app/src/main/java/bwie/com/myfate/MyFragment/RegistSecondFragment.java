package bwie.com.myfate.MyFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import bwie.com.myfate.MyActivity.PhotoActivity;
import bwie.com.myfate.MyPresenter.RegistSecondFragmentPresenter;
import bwie.com.myfate.MyView.RegistSecondFragmentView;
import bwie.com.myfate.R;
import bwie.com.myfate.city.CityNameInterface;
import bwie.com.myfate.city.CityUtils;


public class RegistSecondFragment extends Fragment implements View.OnClickListener, RegistSecondFragmentView {

    private TextView sex;
    private TextView age;
    private TextView city;
    private RegistSecondFragmentPresenter presenter;

    public RegistSecondFragment(String phone) {
        this.phone = phone;
    }

    String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new RegistSecondFragmentPresenter(this);
//        Bundle arguments = getArguments();
//       phone= arguments.getString("phone");
        System.out.println("phone = " + phone);
        return inflater.inflate(R.layout.regist_second, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sex = (TextView) view.findViewById(R.id.sex);
        age = (TextView) view.findViewById(R.id.age);
        city = (TextView) view.findViewById(R.id.city);
        view.findViewById(R.id.regist_second_nxt).setOnClickListener(this);
        sex.setOnClickListener(this);
        age.setOnClickListener(this);
        city.setOnClickListener(this);


    }

    private EditText getNickname() {
        return (EditText) getView().findViewById(R.id.nickname);
    }

    private EditText getDescription() {
        return (EditText) getView().findViewById(R.id.description);
    }

    private EditText getRegistPwd1() {
        return (EditText) getView().findViewById(R.id.regist_pwd1);
    }

    private EditText getRegistPwd2() {
        return (EditText) getView().findViewById(R.id.regist_pwd2);
    }


    @Override
    public void onClick(View view) {
        View view1 = getActivity().getWindow().peekDecorView();
        if (view1 != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        switch (view.getId()) {
            case R.id.regist_second_nxt:
                //TODO implement
                presenter.next(getNickname().getText().toString().trim(),
                        age.getText().toString().trim(),
                        sex.getText().toString().trim(),
                        city.getText().toString().trim(),
                        getDescription().getText().toString().trim()
                        , getRegistPwd1().getText().toString().trim()
                        , getRegistPwd2().getText().toString().trim(), phone);

                break;
            case R.id.age:
                showAgeDialog();
                break;
            case R.id.sex:
                showSexChooseDialog();
                break;
            case R.id.city:
                showLocal();
                break;
        }
    }

    private void showLocal() {
        CityUtils.getInstance().getAllCity(getActivity(), new CityNameInterface() {
            @Override
            public void getCityName(String area) {
                city.setText(area);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void nickEmpty() {
        Toast.makeText(getActivity(), "请输入昵称", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sexEmpty() {
        Toast.makeText(getActivity(), "请选择性别", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void ageEmpty() {
        Toast.makeText(getActivity(), "请选择年龄", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void cityEmpty() {
        Toast.makeText(getActivity(), "请选择所在城市", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void describtionEmpty() {
        Toast.makeText(getActivity(), "请输入自我描述", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void pwd1Empty() {
        Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void pwd2Empty() {
        Toast.makeText(getActivity(), "请重新输入密码", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void isIdentical() {
        Toast.makeText(getActivity(), "您两次输入的密码不一致", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void next() {
        startActivity(new Intent(getActivity(), PhotoActivity.class));

    }


    //性别的选择
    private String[] sexArry = new String[]{"女", "男"};

    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sex.setText(sexArry[which]);
            }
        });
        builder.show();
    }

    /**
     * 年龄的选择
     */
    private void showAgeDialog() {
        final String[] ages = getResources().getStringArray(R.array.age);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择年龄");
        builder.setItems(ages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                age.setText(ages[which]);
            }
        });
        builder.show();

    }


}
