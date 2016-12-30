package com.example.cabanni.lokalpatriot_brackel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cabanni on 24.12.16.
 */

public class ZuVerkaufen extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    View view;
    Activity activity;
    Bundle bundle;
    String result;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zu_verkaufen, container, false);
        activity = getActivity(); // holt den context der Activity
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rvLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(rvLayoutManager);
        PinnwandConnecter.sendToServer(bundle.getString("ort"), bundle.getString("kategorie"));
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        // rvAdapter = new RvAdapter();
        // recyclerView.setAdapter(rvAdapter);
    }

}
