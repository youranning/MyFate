package bwie.com.myfate.recyle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import bwie.com.myfate.R;
import bwie.com.myfate.bean.HomePage;
import bwie.com.myfate.bean.PhotolistBean;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by $USER_NAME on 2017/7/13.
 */

public class HomePage_RecyleAdapter extends RecyclerView.Adapter<HomePage_RecyleAdapter.MyViewHolder> implements View.OnClickListener {
    Context context;
    List<PhotolistBean>list;
    public HomePage_RecyleAdapter(Context context,  List<PhotolistBean>list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.homepage_recycle_item, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImagePath())
                .into(holder.pic);
        holder.itemView.setTag(position);

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (CircleImageView) itemView.findViewById(R.id.homepage_recyle_item_pic);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private MyAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
