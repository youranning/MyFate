package bwie.com.myfate.MyFragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myfate.R;
import bwie.com.myfate.adapter.FriendAdapter;
import bwie.com.myfate.bean.Friend;
import bwie.com.myfate.bean.MyEvENT;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;


public class MsgFragment extends Fragment {

    List<Friend.DataBean> list = new ArrayList<>();
    private FriendAdapter adapter;
    private ListView listView;

    public FriendAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FriendAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.msg, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
        listView = (ListView) view.findViewById(R.id.friend_list);
        adapter = new FriendAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void requestData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", System.currentTimeMillis() + "");
        String sign1 = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));

        map.put("user.sign", sign1);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                list.clear();

                Gson gson = new Gson();
                Friend friend = gson.fromJson(result, Friend.class);
                List<Friend.DataBean> data = friend.getData();
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        int relation = data.get(i).getRelation();
                        System.out.println("data = " + relation);
                        if (data.get(i).getRelation() == 0) {
                            list.add(data.get(i));
                        }
                    }
                    System.out.println("result = " + result);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainActivityEvent(MyEvENT event) {
        System.out.println("isChecked = " + event.isNight());

        requestData();
    }

}
