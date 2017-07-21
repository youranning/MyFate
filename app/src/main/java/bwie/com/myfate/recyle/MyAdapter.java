package bwie.com.myfate.recyle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bwie.com.myfate.IApplication;
import bwie.com.myfate.R;
import bwie.com.myfate.bean.NearbyDataBean;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by $USER_NAME on 2017/6/25.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    Context context;
    List<NearbyDataBean> list;
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public MyAdapter(Context context, List<NearbyDataBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (type == 1) {
            View view = View.inflate(context, R.layout.find_one_item, null);
            view.setOnClickListener(this);
            viewHolder = new FirstViewHolder(view);
        } else if (type == 2) {
            View view = View.inflate(context, R.layout.find_two_item, null);
            view.setOnClickListener(this);
            viewHolder = new TwoViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (type == 1) {
            FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
            firstViewHolder.name.setText(list.get(position).getNickname());
            firstViewHolder.age.setText(list.get(position).getArea());
            firstViewHolder.sex.setText(list.get(position).getGender());
            Glide.with(context)
                    .load(list.get(position).getImagePath())
                    .into(firstViewHolder.head);
            firstViewHolder.itemView.setTag(position);
        }
        if (type == 2) {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.name.setText(list.get(position).getNickname());
            Glide.with(context)
                    .load(list.get(position).getImagePath())
                    .into(twoViewHolder.head);
            twoViewHolder.itemView.setTag(position);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {
        CircleImageView head;
        TextView name;
        TextView age;
        TextView sex;

        public FirstViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.find_first_item_name);
            age = (TextView) itemView.findViewById(R.id.find_first_item_age);
            sex = (TextView) itemView.findViewById(R.id.find_first_item_sex);
            head = (CircleImageView) itemView.findViewById(R.id.find_first_item_head);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {
        CircleImageView head;
        TextView name;

        public TwoViewHolder(View itemView) {
            super(itemView);
            head = (CircleImageView) itemView.findViewById(R.id.find_second_pic);
            name = (TextView) itemView.findViewById(R.id.find_second_name);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
