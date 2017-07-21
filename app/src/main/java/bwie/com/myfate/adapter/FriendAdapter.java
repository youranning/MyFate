package bwie.com.myfate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bwie.com.myfate.R;
import bwie.com.myfate.bean.Friend;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by $USER_NAME on 2017/7/15.
 */

public class FriendAdapter extends BaseAdapter {

    Context context;
    List<Friend.DataBean> list;

    public FriendAdapter(Context context, List<Friend.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }



    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.find_one_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.find_first_item_name);
            viewHolder.age = (TextView) convertView.findViewById(R.id.find_first_item_age);
            viewHolder.sex = (TextView) convertView.findViewById(R.id.find_first_item_sex);
            viewHolder.head = (CircleImageView) convertView.findViewById(R.id.find_first_item_head);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getNickname());
        viewHolder.age.setText(list.get(position).getArea());
        viewHolder.sex.setText(list.get(position).getGender());
        Glide.with(context)
                .load(list.get(position).getImagePath())
                .into(viewHolder.head);
        return convertView;
    }


    class ViewHolder {
        CircleImageView head;
        TextView name;
        TextView age;
        TextView sex;
    }
}
