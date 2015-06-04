package com.desmond.parallaxheaderviewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desmond.parallaxviewpager.NotifyingScrollView;
import com.desmond.parallaxviewpager.ScrollViewFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondScrollViewFragment extends ScrollViewFragment {

    public static final String TAG = SecondScrollViewFragment.class.getSimpleName();

    public static Fragment newInstance(int position) {
        SecondScrollViewFragment fragment = new SecondScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public SecondScrollViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);

        View view = inflater.inflate(R.layout.fragment_second_scroll_view, container, false);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scrollview);
        setScrollViewOnScrollListener();

        return view;
    }
}
