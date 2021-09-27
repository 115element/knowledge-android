package com.example.knowledge_android.fragment_use;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.knowledge_android.R;
import com.example.knowledge_android.fragment_use.fragment.AnotherRightFragment;

//注意Fragment请使用，androidx包下的，app包下的已废弃。

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                AnotherRightFragment fragment = new AnotherRightFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.right_layout, fragment);
                transaction.addToBackStack(null); //FragmentTransaction中提供了一个addToBackStack()方法，可以用于将一个事务添加到返回栈中,用于返回按钮返回到上一个Fragment
                transaction.commit();
                break;
            default:
                break;
        }
    }
/*
    可以看到，首先我们给左侧碎片中的按钮注册了一个点击事件，然后将动态添加碎片的逻辑都放在了点击事件里进行。结合代码可以看出，动态添加碎片主要分为5步。

    创建待添加的碎片实例。
    获取到FragmentManager，在活动中可以直接调用getFragmentManager()方法得到。
    开启一个事务，通过调用beginTransaction()方法开启。
    向容器内加入碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例。
    提交事务，调用commit()方法来完成。
*/


//    这里我们在事务提交之前调用了FragmentTransaction的addToBackStack()方法，它可以接收一个名字用于描述返回栈的状态，
//    一般传入null即可。现在重新运行程序，并点击按钮将AnotherRightFragment添加到活动中，然后按下Back键，
//    你会发现程序并没有退出，而是回到了RightFragment界面，再次按下Back键程序才会退出。

/*
《碎片和活动之间进行通信》
虽然碎片都是嵌入在活动中显示的，可是实际上它们的关系并没有那么亲密。你可以看出，碎片和活动都是各自存在于一个独立的类当中的，
它们之间并没有那么明显的方式来直接进行通信。如果想要在活动中调用碎片里的方法，或者在碎片中调用活动里的方法，应该如何实现呢？

为了方便碎片和活动之间进行通信，FragmentManager提供了一个类似于findViewById()的方法，专门用于从布局文件中获取碎片的实例，代码如下所示：

      RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_fragment);
　　  调用FragmentManager的findFragmentById()方法，可以在活动中得到相应碎片的实例，然后就能轻松地调用碎片里的方法了。

掌握了如何在活动中调用碎片里的方法，那在碎片中又该怎样调用活动里的方法呢？其实这就更简单了，在每个碎片中都可以通过调用getActivity()方法来得到和当前碎片相关联的活动实例，代码如下所示：

    MainActivity activity = (MainActivity) getActivity();

有了活动实例之后，在碎片中调用活动里的方法就变得轻而易举了。另外当碎片中需要使用Context对象时，也可以使用getActivity()方法，因为获取到的活动本身就是一个Context对象了。

这时不知道你心中会不会产生一个疑问，既然碎片和活动之间的通信问题已经解决了，那么碎片和碎片之间可不可以进行通信呢？

说实在的，这个问题并没有看上去的复杂，它的基本思路非常简单，首先在一个碎片中可以得到与它相关联的活动，然后再通过这个活动去获取另外一个碎片的实例，
这样也就实现了不同碎片之间的通信功能，因此这里我们的答案是肯定的。
    */
}
