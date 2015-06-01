package com.desmond.parallaxviewpager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Created by desmond on 12/4/15.
 */
public class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

    protected static final String ARG_POSITION = "position";

    protected ScrollTabHolder mScrollTabHolder;
    protected int mPosition;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mScrollTabHolder = (ScrollTabHolder) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ScrollTabHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollTabHolder = null;
        super.onDetach();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {}

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {}
}
