package bwie.com.myfate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import bwie.com.myfate.MyActivity.BigImage;
import bwie.com.myfate.bean.HomePage;
import bwie.com.myfate.bean.PhotolistBean;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by $USER_NAME on 2017/7/13.
 */

public class HomePagePageAdapter extends PagerAdapter implements View.OnClickListener {
    List<PhotolistBean> list;
    Context context;
    String imagepath;
    private String path;

    public HomePagePageAdapter(List<PhotolistBean> list, Context context, String imagepath) {
        this.list = list;
        this.context = context;
        this.imagepath = imagepath;
    }

    @Override
    public int getCount() {
        return list .size()!=0? list.size() : 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        super.destroyItem(container, position, object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        CircleImageView imageView = new CircleImageView(context);
        path = null;
        if (list.size() == 0) {
            Glide.with(context)
                    .load(imagepath)
                    .into(imageView);
            path =imagepath;
        } else {

            Glide.with(context)
                    .load(list.get(position).getImagePath())
                    .into(imageView);
            path =list.get(position).getImagePath();
        }

        imageView.setOnClickListener(this);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, BigImage.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }


}
