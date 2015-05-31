package com.desmond.parallaxheaderviewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.desmond.parallaxheaderviewpager.slidingTab.SlidingTabLayout;


public class MainActivity extends ActionBarActivity implements ScrollTabHolder {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String IMAGE_TRANSLATION_Y = "image_translation_y";
    private static final String HEADER_TRANSLATION_Y = "header_translation_y";

    private ViewPager mViewPager;
    private View mHeader;
    private ImageView mTopImage;
    private SlidingTabLayout mNavigBar;
    private ViewPagerAdapter mAdapter;

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private int mNumFragments;

    private int mScrollState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValues();

        mTopImage = (ImageView) findViewById(R.id.image);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mNavigBar = (SlidingTabLayout) findViewById(R.id.navig_tab);
        mHeader = findViewById(R.id.header);

        if (savedInstanceState != null) {
            mTopImage.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        setupAdapter();
    }

    private void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight;

        mNumFragments = 4;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, mTopImage.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
    }

    private void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mNavigBar.setOnPageChangeListener(getViewPagerPageChangeListener());
        mNavigBar.setViewPager(mViewPager);
    }

    private ViewPager.OnPageChangeListener getViewPagerPageChangeListener () {
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                if (positionOffsetPixels > 0) {
                    SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                    ScrollTabHolder fragmentContent;
                    if (position < currentItem) {
                        // Revealed the previous page
                        fragmentContent = scrollTabHolders.valueAt(position);
                    } else {
                        // Revealed the next page
                        fragmentContent = scrollTabHolders.valueAt(position + 1);
                    }

//                    Log.d(TAG, "header height " + mHeader.getHeight() + " translationY " + mHeader.getTranslationY());
                    fragmentContent.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()),
                            mHeader.getHeight());
                }
            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
                    return;
                }

                ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
                currentHolder.adjustScroll(
                        (int) (mHeader.getHeight() + mHeader.getTranslationY()),
                        mHeader.getHeight());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mScrollState = state;
            }
        };

        return listener;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(getScrollY(view));
        }
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition){
            scrollHeader(view.getScrollY());
        }
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(scrollY);
        }
    }

    private void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        mTopImage.setTranslationY(-translationY / 3);
    }

    private int getScrollY(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

//    private int getActionBarHeight() {
//        if (mActionBarHeight != 0) {
//            return mActionBarHeight;
//        }
//
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
//            getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
//        } else {
//            getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
//        }
//
//        mActionBarHeight = TypedValue.complexToDimensionPixelSize(
//                mTypedValue.data, getResources().getDisplayMetrics());
//
//        return mActionBarHeight;
//    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private int mNumFragments;

        public ViewPagerAdapter(FragmentManager fm, int numFragments) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<>();
            mNumFragments = numFragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = FirstScrollViewFragment.newInstance(0);
                    break;

                case 1:
                    fragment = SecondScrollViewFragment.newInstance(1);
                    break;

                case 2:
                    fragment = ListViewFragment.newInstance(2);
                    break;

                case 3:
                    fragment = RecyclerViewFragment.newInstance(3);
                    break;

                default:
                    throw new IllegalArgumentException("Wrong page given " + position);
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);

            mScrollTabHolders.put(position, (ScrollTabHolder) object);

            return object;
        }

        @Override
        public int getCount() {
            return mNumFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ScrollView";

                case 1:
                    return "ScrollView";

                case 2:
                    return "ListView";

                case 3:
                    return "RecyclerView";

                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }
    }
}
