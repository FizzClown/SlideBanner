# SlideBanner
自己封装的一个自定义Banner

[img]http://i1.bvimg.com/636232/5a277d67bcf35d58.gif[/img]

```Java
//Project Gradle中引入
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

```Java
//添加依赖
dependencies {
	compile 'com.github.FizzClown:SlideBanner:1.0.0'
}
```

### 圆形指示器的使用方法
1.XML文件中引入MyViewPager和CircleIndicator
```Xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    tools:context="com.lq.myapplication.MainActivity">

    <com.lq.myapplication.MyViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:count_viewpager="5"
        app:time_space="5000" />

    <com.lq.myapplication.CircleIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="180dp"
        app:circle_count="5" />
</FrameLayout>
```

2.代码中的使用

```Java
//实现接口OnItemClickListener（如果有点击需求的话）
public class MainActivity extends AppCompatActivity implements OnItemClickListener{
    //声明ViewPager
    private MyViewPager viewPager;
    //声明CircleIndicator
    private CircleIndicator indicator;
    //准备好数据源（注意：图片地址的数量和ViewPagerItem的数量及Indicator的Item数量要一致，不然会抛出异常）
    private String[] urls = new String[]{
            "http://a0.att.hudong.com/31/35/300533991095135084358827466.jpg",
            "http://img.idol001.com/origin/2018/01/25/99bd852a32d9370fa7c1663a7f954b8f1516877063_watermark.jpg",
            "http://img2015.zdface.com/20180219/319ec61a85a4f763ef1afdbd02c29848.jpg",
            "http://img3.duitang.com/uploads/item/201508/01/20150801210844_384tM.jpeg",
            "http://img4.duitang.com/uploads/item/201508/23/20150823125007_4k5tF.jpeg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (MyViewPager) findViewById(R.id.viewPager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        //设置ViewPager的Indicator
        viewPager.setIndicator(indicator);
        //设置监听回调
        viewPager.setListener(this);
        //设置数据源
        viewPager.setUrls(urls);
    }

    //重写回调的方法
    @Override
    public void onClick(ImageView img, int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
```
###### RectIndicator和CircleIndicator的使用方法一样，不能同时设置两种指示器
###### 再次提示：数据源的数量和ViewPager的Item数量及Indicator的Item的数量要一致

### 属性介绍
```Xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    //ViewPager
    <declare-styleable name="MyViewPager">
        //viewPager的数量
        <attr name="count_viewpager" format="integer" />
        //图片显示间隔
        <attr name="time_space" format="integer" />
    </declare-styleable>

    //圆形指示器
    <declare-styleable name="CircleIndicator">
        //圆形指示器Item数量
        <attr name="circle_count" format="integer" />
        //滑动圆形的颜色
        <attr name="circle_fill_color" format="color" />
        //没啥效果
        <attr name="circle_stoke_width" format="dimension" />
        //滑动小球的半径
        <attr name="radius" format="dimension" />
        //静态小球之间的间距
        <attr name="circle_divider_width" format="dimension" />
        //静态小球的颜色
        <attr name="circle_empty_color" format="color"/>
    </declare-styleable>

    //矩形指示器
    <declare-styleable name="RectIndicator">
        //Item的数量
        <attr name="rect_count" format="integer" />
        //移动矩形的颜色
        <attr name="rect_fill_color" format="color" />
        //静态矩形的边框大小
        <attr name="rect_stoke_width" format="dimension" />
        //静态矩形的边框颜色
        <attr name="rect_stoke_color" format="color"/>
        //中间字体的颜色
        <attr name="rect_text_color" format="color"/>
        //矩形的边长
        <attr name="rect_length" format="dimension" />
        //静态矩形件的间距
        <attr name="rect_divider_width" format="dimension" />
    </declare-styleable>
</resources>
```
