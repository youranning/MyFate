package bwie.com.myfate.MyFragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myfate.MyActivity.HomepageActivity;
import bwie.com.myfate.R;
import bwie.com.myfate.bean.NearbyDataBean;
import bwie.com.myfate.bean.NearbyPeople;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;
import bwie.com.myfate.recyle.MyAdapter;

import static bwie.com.myfate.IApplication.daoSession;

public class FindfragmentFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, CompoundButton.OnCheckedChangeListener, MyAdapter.OnItemClickListener {

    private IRecyclerView findRecycle;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                findRecycle.setRefreshing(false);
            }
        }
    };
    List<NearbyDataBean> list = new ArrayList<>();
    private MyAdapter adapter;
    private CheckBox find_change;
    private LinearLayoutManager manager;
    private long lasttime;
    private long l;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        l = System.currentTimeMillis();

        requestData(l + "");
        return inflater.inflate(R.layout.findfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findRecycle = (IRecyclerView) view.findViewById(R.id.find_recycle);
        find_change = (CheckBox) view.findViewById(R.id.find_change);
        find_change.setOnCheckedChangeListener(this);
        list = daoSession.getNearbyDataBeanDao().loadAll();
        adapter = new MyAdapter(getActivity(), list, 1);
        findRecycle.setIAdapter(adapter);

        manager = new LinearLayoutManager(getActivity());

        findRecycle.setLayoutManager(manager);

        findRecycle.setLoadMoreEnabled(true);
        findRecycle.setRefreshEnabled(true);
        findRecycle.setOnRefreshListener(this);
        findRecycle.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(this);
    }


    void requestData(final String time) {
        Map map = new HashMap<String, String>();
        map.put("user.currenttimer", time);
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson = new Gson();
                NearbyPeople nearbyPeople = gson.fromJson(result, NearbyPeople.class);

                if (nearbyPeople.getResult_code() == 200) {
                    List<NearbyDataBean> data = nearbyPeople.getData();
                    if (data .size()!=0) {
                        lasttime = data.get(data.size() - 1).getLasttime();

                    }else {
                        Toast.makeText(getActivity(),"没有数据了",Toast.LENGTH_SHORT).show();
                    }
                    if (data != null) {
                        list.addAll(data);
                        List<NearbyDataBean> nearbyDataBeen = daoSession.getNearbyDataBeanDao().loadAll();
                        if (nearbyDataBeen.size() != 0) {
                            daoSession.getNearbyDataBeanDao().deleteAll();
                        }
                        daoSession.getNearbyDataBeanDao().insertInTx(data);
                    }
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailed(int code) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        findRecycle.setRefreshing(true);


        handler.sendEmptyMessageDelayed(1, 2000);
        list.clear();
        requestData(l + "");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessageDelayed(2, 2000);
        requestData(lasttime + "");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            adapter = null;

            adapter = new MyAdapter(getActivity(), list, 2);
            findRecycle.setIAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(this);

            findRecycle.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//            findRecycle.setLayoutManager(manager);

        } else {
            adapter = null;
            adapter = new MyAdapter(getActivity(), list, 1);
            findRecycle.setIAdapter(adapter);
            adapter.setOnItemClickListener(this);

            findRecycle.setLayoutManager(manager);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), HomepageActivity.class);
        String imagePath = list.get(position).getImagePath();
        int userId = list.get(position).getUserId();

        intent.putExtra("imagepath", imagePath);
        intent.putExtra("userid", userId);
        startActivity(intent);
    }
}
