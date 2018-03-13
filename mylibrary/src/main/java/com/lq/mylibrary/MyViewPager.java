package com.lq.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";
    private List<View> views = new ArrayList<>();
    private int currentPosition;
    private CountDownTimer timer;
    private List<ImageView> imageViews = new ArrayList<>();
    private RectIndicator rectIndicator;
    private CircleIndicator indicator;
    private Context context;
    private OnItemClickListener listener;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
        int count = array.getInt(R.styleable.MyViewPager_count_viewpager, 3);
        int time_space = array.getInt(R.styleable.MyViewPager_time_space, 3000);
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null);
            ImageView iv = view.findViewById(R.id.iv_img);
            imageViews.add(iv);
            views.add(view);
            addView(view);
        }
        setOffscreenPageLimit(views.size());
        setAdapter(new MyAdapter());
        array.recycle();
        setImageViews(imageViews);
        timer = new CountDownTimer(time_space, time_space) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (currentPosition < views.size() - 1) {
                    currentPosition += 1;
                    setCurrentItem(currentPosition);
                } else {
                    currentPosition = 0;
                    setCurrentItem(currentPosition);
                }
                timer.start();
            }
        };
        timer.start();
    }

    public void setIndicator(CircleIndicator indicator) {
        this.indicator = indicator;
    }

    public void setUrls(String[] urls) {
        if (urls.length != imageViews.size()) {
            throw new RuntimeException("图片地址的数量必须和图片数量一致");
        }
        for (int i = 0; i < urls.length; i++) {
            Glide.with(context).load(urls[i]).into(imageViews.get(i));
            if (listener != null) {
                final int finalI = i;
                imageViews.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(imageViews.get(finalI), finalI);
                    }
                });
            }
        }
    }

    public void setRectIndicator(RectIndicator indicator) {
        this.rectIndicator = indicator;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setImageViews(List<ImageView> ivImgs) {
        this.imageViews = ivImgs;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //Log.e(TAG, "onScrollChanged() returned: " + "l=" + l + ",t=" + t + ",oldl=" + oldl + ",oldt=" + oldt);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        currentPosition = position;
        if (indicator != null && rectIndicator != null) {
            throw new RuntimeException("不能同时设置两种指示器");
        }

        if (indicator == null && rectIndicator == null) {
            throw new NullPointerException("请设置至少一种指示器");
        }

        if (indicator != null) {
            indicator.moveCircle(position, offsetPixels, views.size());
        }

        if (rectIndicator != null) {
            rectIndicator.moveRect(position, offsetPixels, views.size());
        }
        //Log.e(TAG, "onPageScrolled: " + "position=" + position + ",offset=" + offset + ",offsetPixels=" + offsetPixels);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return getCurrentItem();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return views.get(position);
        }
    }
}
