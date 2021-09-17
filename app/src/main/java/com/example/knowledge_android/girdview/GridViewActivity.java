package com.example.knowledge_android.girdview;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private GridView mGridView;
    private ProvinceAdapter mProvinceAdapter;
    private String[] provinceNames = new String[]{"北京", "上海", "广东", "广西", "天津", "重庆", "湖北", "湖南", "河北", "河南", "山东"};
    private int[] bgColor = new int[]{R.color.color_00ff00, R.color.color_ff0000, R.color.color_ff0000, R.color.color_ffff00,
            R.color.color_8e35ef, R.color.color_303F9F, R.color.color_00ff00, R.color.color_ff0000, R.color.color_ff0000,
            R.color.color_ffff00, R.color.color_8e35ef};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_14);
        initView();
    }

    //获取GridView控件，并进行初始化设置
    //　程序中，首先使用findViewById方法获取到了GridView控件，接下来使用setAdapter方法给它设置提供数据的适配器。
    //  程序中，引入了两份数据provinceNames 和bgColor，其中provinceNames定义了依次显示在GridView各网格中的省份名称，
    //  bgColor定义了依次显示在GridView网格中的省份名称的背景色，这些只是为了更方便读者从视觉上认识GridView。
    private void initView() {
        mGridView = (GridView) this.findViewById(R.id.grid_view);
        List<ProvinceBean> provinceBeanList = new ArrayList<>();
        for (int i = 0; i < provinceNames.length; i++) {
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName(provinceNames[i]);
            provinceBean.setColor(bgColor[i]);
            provinceBeanList.add(provinceBean);
        }
        mProvinceAdapter = new ProvinceAdapter(this, provinceBeanList);
        mGridView.setAdapter(mProvinceAdapter);
    }

/*
    GridView类的主要方法
    ===========================================Public方法============================================
    ListAdapter:getAdapter()
    返回关联的Adapter

    int:getColumnWidth()
    返回列的宽度

    int:getGravity()
    返回描述子视图被放置的方式的标识。默认为Gravity.LEFT。

    int:getHorizontalSpacing()
    返回列间的水平间隔大小。
    仅会计算当前布局。如果调用了setHorizontalSpacing(int)来设置间隔，但布局还没有完成，这个方法会返回一个旧值。如果想要明确地获取这个间隔，使用getRequestedHorizontalSpacing()方法请求。

    int:getNumColumns()
    返回列数。如果网格没有被布局，则返回AUTO_FIT。

    int:getRequestedColumnWidth()
    返回请求的列宽度。
    这可能不是真实的列宽度。使用getColumnWidth()获取当前真实的列宽度。

    int:getRequestedHorizontalSpacing()
    返回请求的列间的水平间隔。
    这个值可能是布局期间的局部样式，也可能是默认的样式，或是使用setHorizontalSpacing(int)方法设置的值。如果布局尚未完成或GridView计算得到了一个和请求的不同的水平间隔，它与getHorizontalSpacing()将有不同的返回值。

    int:getStretchMode()
    返回扩展模式。

    int:getVerticalSpacing()
    返回行间的垂直间隔。

    onInitializeAccessibilityNodeInfoForItem(View view, int position, AccessibilityNodeInfo info)
    使用列表中实际项的信息初始化一个AccessibilityNodeInfo。
    View	呈现列表项的视图
    position	在Adapter中列表项的位置
    info	需要初始化的信息项（Node Info）

    boolean:onKeyDown(int keyCode, KeyEvent event)
    KeyEvent.Callback.onKeyDown()的默认实现：如果视图可点击，当KEYCODE_DPAD_CENTER或KEYCODE_ENTER被释放时，执行视图按下事件。
    软键盘上的按键事件通常不会触发这个监听，尽管有些条件下可能会触发，但不要依赖它去捕获关键盘按键事件。
    返回值：处理返回true，否则返回false。

    boolean:onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
    KeyEvent.Callback.onKeyMultiple()的默认实现：一直返回false。
    软键盘上的按键事件通常不会触发这个监听，尽管有些条件下可能会触发，但不要依赖它去捕获关键盘按键事件。
    返回值：处理返回true，否则返回false。

    boolean:onKeyUp(int keyCode, KeyEvent event)
    KeyEvent.Callback.onKeyUp()的默认实现：当KEYCODE_DPAD_CENTER或KEYCODE_ENTER被释放时，执行视图点击事件。
    软键盘上的按键事件通常不会触发这个监听，尽管有些条件下可能会触发，但不要依赖它去捕获关键盘按键事件。
    返回值：处理返回true，否则返回false。

    setAdapter(ListAdapter adapter)
    为GridView设置数据。

    setColumnWidth(int columnWidth)
    设置宽度。

    setGravity(int gravity)
    设置网格的中重心。重心描述了子视图的摆放方式。默认是Gravity.LEFT。

    setHorizontalSpacing(int horizontalSpacing)
    设置列间的水平间隔。

    setNumColumns(int numColumns)
    设置列数。

    setRemoteViewsAdapter(Intent intent)
    设置该AbsListView使用远程视图适配器，该适配器通过特定的Intent连接到一个RemoteViewsService。

    setSelection(int position)
    设置当前选中项。

    setStretchMode(int stretchMode)
    设置列表项如何拓展填充闲置空间的方式。
    可以设置下面常量值中的一个：
    NO_STRETCH	            0	扩展无效
    STRETCH_COLUMN_WIDTH	2	扩展列
    STRETCH_SPACING	        1	扩展列间的空间
    STRETCH_SPACING_UNIFORM	3	均匀地扩展列间的空间

    setVerticalSpacing(int verticalSpacing)
    设置行间的垂直间隔。

    smoothScrollByOffset(int offset)
    平滑地滚动到具体的适配器位置的偏移位置。视图会滚动到指定位置显示出来。

    smoothScrollToPosition(int position)
    平滑地滚动到具体的适配器位置。视图会滚动到指定位置显示出来。

    =======================================Protected方法=============================================
    attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count)
    子视图可以覆写该方法来在提供的child上设置布局动画参数。
    child	和动画参数关联的子视图
    params	持有动画参数的子视图的布局参数
    index	子视图在视图组中的索引
    count	视图组中子视图的数量

    computeVerticalScrollExtent()
    计算滚动条把手在纵向滚动范围内占用的幅度。该值用于计算滚动条把手在滚动条滑道中的长度。
    范围使用与computeVerticalScrollRange()和computeVerticalScrollOffset()相同的任意单位。
    默认的长度是视图的可绘制高度。
    返回值:滚动条把手在纵向滚动范围内占用的幅度。

    computeVerticalScrollOffset()
    计算滚动条把手在纵向滚动范围内的位置。该值用于计算滚动条把手在滚动条滑道中的位置。
    范围使用与computeVerticalScrollRange()和computeVerticalScrollExtent()相同的任意单位。
    默认位置是视图的滚动条位置。
    返回值:滚动条把手的纵向位置

    computeVerticalScrollRange()
    计算垂直滚动条的垂直范围。
    范围使用与computeVerticalScrollExtent()和computeVerticalScrollOffset()相同的任意单位。
    返回值:纵向滚动条代表的整个纵向范围。默认纵向范围时视图的绘制高度。

    layoutChildren()
    子类必须实现该方法，来布局子视图

    onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect)
    当视图焦点状态改变时，视图系统调用该方法。由定向导航导致的焦点变更时，direction和previouslyFocusedRect提供了焦点是从那里来的进一步信息。
    gainFocus	            如果视图具有焦点，值为真；否则为假
    direction	            当调用requestFocus()为该视图设置焦点时，该值为焦点移动的方向。其值为FOCUS_UP、FOCUS_DOWN、FOCUS_LEFT或者FOCUS_RIGHT
    previouslyFocusedRect	失去焦点的视图的矩形坐标，使用该视图的坐标系统.如果指定了，它将传入可以知道焦点来自哪里的详细信息（作为对direction 的补充）。否则，其值为null

    onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    对视图及其内容进行测量，来决定布局的宽和高。
*/
}
