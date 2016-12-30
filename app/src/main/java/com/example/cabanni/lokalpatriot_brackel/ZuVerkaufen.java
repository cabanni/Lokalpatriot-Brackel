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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    ArrayList<Pinntext> arrayList = new ArrayList<Pinntext>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = PinnwandConnecter.sendToServer(bundle.getString("ort"), bundle.getString("kategorie")); // holt den Json String vom Server

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("json_response"); // json_response ist der Name des JsonArrays im php-script

            int count = 0;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                count++;
                Pinntext pinntext = new Pinntext(jo.getString("text"), jo.getString("userName"), jo.getString("userMail"),
                        jo.getInt("userPunkte"), jo.getString("date"));
                arrayList.add(pinntext);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        // rvAdapter = new RvAdapter();
        // recyclerView.setAdapter(rvAdapter);
    }

}
