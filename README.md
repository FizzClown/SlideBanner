# SlideBanner
自己封装的一个自定义Banner

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
