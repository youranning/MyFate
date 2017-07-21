package bwie.com.myfate.MyActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by $USER_NAME on 2017/7/14.
 */

public class BigImage extends Activity implements View.OnLongClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = getIntent().getStringExtra("path");
        System.out.println("path = " + path);
        PhotoView photoView=new PhotoView(this);

        Glide.with(this)
                .load(path)
                .into(photoView);
        setContentView(photoView);
      photoView.setOnLongClickListener(this);
    }





    @Override
    public boolean onLongClick(View v) {
        finish();
        return true;
    }
}
