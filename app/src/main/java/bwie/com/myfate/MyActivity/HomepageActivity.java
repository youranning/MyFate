package bwie.com.myfate.MyActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myfate.MyFragment.MsgFragment;
import bwie.com.myfate.R;
import bwie.com.myfate.adapter.HomePagePageAdapter;
import bwie.com.myfate.bean.AddFriend;
import bwie.com.myfate.bean.HomePage;
import bwie.com.myfate.bean.MyEvENT;
import bwie.com.myfate.bean.PhotolistBean;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;
import bwie.com.myfate.recyle.HomePage_RecyleAdapter;
import bwie.com.myfate.recyle.MyAdapter;

public class HomepageActivity extends Activity implements View.OnClickListener, MyAdapter.OnItemClickListener {

    private ViewPager homepagePic;
    private TextView homepageName;
    private TextView homepageOther;
    private RecyclerView homepageRecyPic;
    List<PhotolistBean> list = new ArrayList<>();
    private HomePage_RecyleAdapter adapter;
    private HomePagePageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        requestData();

        homepagePic = (ViewPager) findViewById(R.id.homepage_pic);
        findViewById(R.id.homepage_back).setOnClickListener(this);
        findViewById(R.id.homepage_sayhello).setOnClickListener(this);
        findViewById(R.id.homepage_select).setOnClickListener(this);
        findViewById(R.id.homepage_add_pic).setOnClickListener(this);
        homepageName = (TextView) findViewById(R.id.homepage_name);
        homepageOther = (TextView) findViewById(R.id.homepage_other);
        homepageRecyPic = (RecyclerView) findViewById(R.id.homepage_recy_pic);

        adapter = new HomePage_RecyleAdapter(getApplication(), list);
        homepageRecyPic.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false);
//        GridLayoutManager manager1=new GridLayoutManager(getApplication(),1,GridLayoutManager.HORIZONTAL,false);
        homepageRecyPic.setLayoutManager(manager);
        String imagepath = getIntent().getStringExtra("imagepath");
        pageAdapter = new HomePagePageAdapter(list, getApplication(), imagepath);
        homepagePic.setAdapter(pageAdapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homepage_back:
                finish();
                //TODO implement
                break;
            case R.id.homepage_select:
                pop();
                pw.showAsDropDown(view);
                break;
            case R.id.homepage_add_pic:
                Toast.makeText(getApplication(), "索要成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage_sayhello:

                addFriend();
                break;
        }
    }



    private void requestData() {
        ///   user.userId,user.sign
        int userid =  getIntent().getIntExtra("userid", 0);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.userId", userid + "");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson = new Gson();
                HomePage homePage = gson.fromJson(result, HomePage.class);
                homepageName.setText(homePage.getData().getNickname());
                homepageOther.setText(homePage.getData().getGender());
                List<PhotolistBean> photolist = homePage.getData().getPhotolist();
                if (list != null) {
                    list.addAll(photolist);

                }
                adapter.notifyDataSetChanged();
                pageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code) {
                System.out.println("code = " + code);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        homepagePic.setCurrentItem(position);
    }

    private PopupWindow pw;

    private void pop() {
        View view = View.inflate(getApplication(), R.layout.homepage_pop, null);

        pw = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }
    private void addFriend() {


        Map<String, String> map = new HashMap<String, String>();
        map.put("relationship.friendId",getIntent().getIntExtra("userid", 0)+"");
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));

        map.put("user.sign", sign);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_addFriends.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson=new Gson();
                AddFriend addFriend = gson.fromJson(result, AddFriend.class);
                if (addFriend.getResult_code()==200){
                    Toast.makeText(getApplication(),addFriend.getResult_message(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), LiaotianActivity.class);
                    intent.putExtra("pickname",addFriend.getAddUser().getNickname());
                    intent.putExtra("userid",addFriend.getAddUser().getUserId()+"");
                    intent.putExtra("otherimage",addFriend.getAddUser().getImagePath());
                    startActivity(intent);
                    EventBus.getDefault().post(new MyEvENT(true));
                    finish();


                }else {
                    Toast.makeText(getApplication(),addFriend.getResult_message(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplication(),LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });


    }
}
