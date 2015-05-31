package com.desmond.parallaxheaderviewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends ScrollTabHolderFragment {

    public static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private static final int NO_SCROLL_X = 0;
    private static final String ARG_POSITION = "position";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutMgr;
    private int mScrollY;
    private int mPosition;

    public static Fragment newInstance(int position) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public RecyclerViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mPosition = getArguments().getInt(ARG_POSITION);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        setupRecyclerView();
        return view;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mRecyclerView == null) return;

        mScrollY = headerHeight - scrollHeight;
        mLayoutMgr.scrollToPositionWithOffset(0, -mScrollY);
    }

    private void setupRecyclerView() {
        mLayoutMgr = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutMgr);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.addItems(createItemList());
        mRecyclerView.setAdapter(recyclerAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mScrollY += dy;

                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onRecyclerViewScroll(recyclerView, mScrollY, mPosition);
                }
            }
        });
    }

    private List<String> createItemList() {
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        list.add("Item 6");
        list.add("Item 7");
        list.add("Item 8");
        list.add("Item 9");
        list.add("Item 10");
        list.add("Item 11");
        list.add("Item 12");
        list.add("Item 13");
        list.add("Item 14");
        list.add("Item 15");
        list.add("Item 16");
        list.add("Item 17");
        list.add("Item 18");
        list.add("Item 19");
        return list;
    }
}
