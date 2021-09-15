package com.example.knowledge_android.horizontalscrollview_viewpager_fragment;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;

import java.util.ArrayList;

public class MainActivity10 extends AppCompatActivity {

    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout ll_content;
    private LinearLayout ll_more;
    private RelativeLayout rl_column;
    private ViewPager mViewPager;
    private ImageView button_more_columns;
    private ArrayList<NewsClassify> newsClassifies;
    private int columnSelectIndex;
    private ImageView shade_left;
    private ImageView shade_right;
    private int mScreenWidth;
    private int mItemWidth;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_9);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mItemWidth = mScreenWidth / 7;
        initView();
    }

    private void initView() {
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        setChangeView();
    }

    private void setChangeView() {
        initColumnData();
        initTabColumn();
        initFragment();
    }

    private void initColumnData() {
        newsClassifies = new ArrayList<NewsClassify>();
        for (int i = 0; i < 30; i++) {
            NewsClassify newsClassify = new NewsClassify();
            newsClassify.setId(i);
            newsClassify.setTitle("标题" + i);
            newsClassifies.add(newsClassify);
        }
    }

    private void initTabColumn() {
        ll_content.removeAllViews();
        for (int i = 0; i < newsClassifies.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            TextView localTextView = new TextView(this);
            localTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            localTextView.setBackgroundResource(R.drawable.radio_button_bg);
            localTextView.setGravity(Gravity.CENTER);
            localTextView.setPadding(5, 0, 5, 0);
            localTextView.setId(i);
            localTextView.setText(newsClassifies.get(i).getTitle());
            localTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            if (columnSelectIndex == i) {
                localTextView.setSelected(true);
            }
            localTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "点击了 mColumnHorizontalScrollView");
                    for (int i = 0; i < ll_content.getChildCount(); i++) {
                        View localView = ll_content.getChildAt(i);
                        if (localView != v) {
                            localView.setBackgroundColor(Color.WHITE);
                            //localView.setSelected(false);
                        } else {
                            localView.setBackgroundColor(Color.BLACK);
                            //localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                    Toast.makeText(getApplicationContext(), newsClassifies.get(v.getId()).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            ll_content.addView(localTextView);
        }

        mColumnHorizontalScrollView.setParam(this, mScreenWidth, ll_content, shade_left, shade_right, ll_more, rl_column);
    }

    private void initFragment() {
        for (int i = 0; i < newsClassifies.size(); i++) {
            Bundle data = new Bundle();
            data.putString("text", newsClassifies.get(i).getTitle());
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(data);
            fragments.add(newsFragment);
        }
        NewsFragmentPagerAdapter mAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("mViewPager", "滑动了ViewPager=="+position);

                //滑动底部ViewPager时，改变上边tab颜色
                View childAt = ll_content.getChildAt(position);
                for (int i = 0; i < ll_content.getChildCount(); i++) {
                    View localView = ll_content.getChildAt(i);
                    if (localView != childAt) {
                        localView.setBackgroundColor(Color.WHITE);
                    } else {
                        localView.setBackgroundColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("mViewPager", "点击了ViewPager");
                mViewPager.setCurrentItem(position);
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("mViewPager", "滚动了ViewPager=="+state);
            }
        });
    }

    private void selectTab(int tabPosition) {
        columnSelectIndex = tabPosition;
        View selectedView = ll_content.getChildAt(tabPosition);
        mColumnHorizontalScrollView.smoothScrollTo(selectedView.getLeft() + selectedView.getMeasuredWidth() - mScreenWidth / 2, 0);
        for (int i = 0; i < ll_content.getChildCount(); i++) {
            View localView = ll_content.getChildAt(i);
            boolean isSelect;
            if (i == tabPosition) {
                isSelect = true;
            } else {
                isSelect = false;
            }
            localView.setSelected(isSelect);
        }
    }

}
