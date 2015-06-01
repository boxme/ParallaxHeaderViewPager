package com.desmond.parallaxviewpager;

import android.widget.ScrollView;

/**
 * Created by desmond on 1/6/15.
 */
public class ScrollViewFragment extends ScrollTabHolderFragment {

    protected static final int NO_SCROLL_X = 0;

    protected NotifyingScrollView mScrollView;

    protected void setScrollViewOnScrollListener() {
        mScrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {

                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onScrollViewScroll(view, l, t, oldl, oldt, mPosition);
                }
            }
        });
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mScrollView == null) return;

        mScrollView.scrollTo(NO_SCROLL_X, headerHeight - scrollHeight);
    }
}
