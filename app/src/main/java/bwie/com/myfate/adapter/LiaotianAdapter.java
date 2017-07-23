package bwie.com.myfate.adapter;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

import bwie.com.myfate.R;

/**
 * Created by $USER_NAME on 2017/7/19.
 */

public class LiaotianAdapter extends BaseAdapter {
    Context context;
    List<EMMessage> list;
    String myImagepath;
    String otherImagepath;

    public LiaotianAdapter(Context context, List<EMMessage> list,  String myImagepath, String otherImagepath) {
        this.context = context;
        this.list = list;
        this.myImagepath = myImagepath;
        this.otherImagepath = otherImagepath;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;
        OtherViewHolder otherViewHolder = null;
        int typ = getItemViewType(position);

        if (convertView == null) {
            if (typ == 0) {
                viewHolder = new MyViewHolder();
                convertView = View.inflate(context, R.layout.liaotian_my_item, null);
                viewHolder.myContent = (TextView) convertView.findViewById(R.id.liaotian_my_content);
                viewHolder.myHead = (ImageView) convertView.findViewById(R.id.liaotian_my_head);
                convertView.setTag(viewHolder);
            } else {
                otherViewHolder = new OtherViewHolder();
                convertView = View.inflate(context, R.layout.liaotian_other_item, null);
                otherViewHolder.otherContent = (TextView) convertView.findViewById(R.id.liaotian_other_content);
                otherViewHolder.otherHead = (ImageView) convertView.findViewById(R.id.liaotian_other_head);
                convertView.setTag(otherViewHolder);
            }
        } else {

            if (typ == 0) {
                viewHolder = (MyViewHolder) convertView.getTag();
            } else {
                otherViewHolder = (OtherViewHolder) convertView.getTag();
            }
        }

        Spannable span = EaseSmileUtils.getSmiledText(context, list.get(position).getBody().toString().substring(5, list.get(position).getBody().toString().length() - 1));
//        // 设置内容
        if (typ == 0) {


            viewHolder.myContent.setText(span,TextView.BufferType.SPANNABLE);
            Glide.with(context).load(myImagepath).
                    into(viewHolder.myHead);
        } else {
            otherViewHolder.otherContent.setText(span);
            Glide.with(context).load(otherImagepath).
                    into(otherViewHolder.otherHead);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
/*
        if (list.get(position).getTo().equals(usid)) {
            return 0;
        } else {
            return 1;
        }*/
        if (list.get(position).getType() == EMMessage.Type.TXT) {

            if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    class MyViewHolder {
        TextView myContent;
        ImageView myHead;
    }

    class OtherViewHolder {
        TextView otherContent;
        ImageView otherHead;
    }

}
