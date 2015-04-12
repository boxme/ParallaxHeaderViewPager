package com.desmond.parallaxheaderviewpager;

import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by desmond on 10/4/15.
 */
public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight, int headerTranslationY);

    void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                          int totalItemCount, int pagePosition);

    void onScrollViewScroll(ScrollView view, int x, int y,
                            int oldX, int oldY, int pagePosition);
}
