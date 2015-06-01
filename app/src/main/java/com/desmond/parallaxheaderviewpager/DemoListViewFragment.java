package com.desmond.parallaxheaderviewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.desmond.parallaxviewpager.ListViewFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DemoListViewFragment extends ListViewFragment {

    public static final String TAG = DemoListViewFragment.class.getSimpleName();

    public static Fragment newInstance(int position) {
        DemoListViewFragment fragment = new DemoListViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public DemoListViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPosition = getArguments().getInt(ARG_POSITION);

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        View placeHolderView = inflater.inflate(R.layout.header_placeholder, mListView, false);
        mListView.addHeaderView(placeHolderView);


        setAdapter();
        setListViewOnScrollListener();

        return view;
    }

    private void setAdapter() {
        if (getActivity() == null) return;

        int size = 7;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);

        mListView.setAdapter(adapter);
    }
}
