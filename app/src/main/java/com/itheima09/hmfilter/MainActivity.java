package com.itheima09.hmfilter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private GridView gridview;
    int[] templetIds = new int[]{
            R.mipmap.f0,
            R.mipmap.f1,
            R.mipmap.f2,

    };
    private ImageView iv;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gv);
        iv = (ImageView) findViewById(R.id.iv);
        //设置列数
        int length = templetIds.length;
        gridview.setNumColumns(length);
        //设置宽度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridview.setLayoutParams(layoutParams);
        //设置数据源
        gridview.setAdapter(new MyAdapter());
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //实现对应的滤镜效果
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                //图片上所有的像素点
                int[] pixels = new int[width * height];//装图片上所有像素点的数组
                //参数2：offset 在pixels时第一个位置
                bitmap.getPixels(pixels,0,width,0,0,width,height);
                switch (position) {
                    case 0:
                        //黑白效果
                        FilterUtils.blackFilter(pixels);
                        break;
                    case 1:
                        //底片的效果
                        FilterUtils.negativeFilter(pixels,width,height);
                        break;
                    case 2:
                        //锐化的效果
                        FilterUtils.sharpenFiler(pixels);
                        break;
                }
                //像素点数组合成为一张图片
                Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
                iv.setImageBitmap(bitmap);
            }

        });
        bitmap = loadOriginalBitmap(getResources(), R.mipmap.img);
        iv.setImageBitmap(bitmap);

    }

    //加载原图片
    public Bitmap loadOriginalBitmap(Resources res, int id) {
        //加载策略
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;//只加载边界信息，不真正加载到内存
        BitmapFactory.decodeResource(res, id, opts);
        //获取图片的原始宽高
        int outWidth = opts.outWidth;//700
        int outHeight = opts.outHeight;
        //480*800作为边界
        if (outWidth > 480 && outHeight > 800) {
            int w_ratio = outWidth / 480;
            int h_ratio = outHeight / 800;
            opts.inSampleSize = Math.max(w_ratio, h_ratio);
        }
        //加载图片
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, id, opts);
        return bitmap;
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return templetIds.length;
        }

        @Override
        public Object getItem(int position) {
            return templetIds[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setImageResource(templetIds[position]);
            return imageView;
        }
    }
}
