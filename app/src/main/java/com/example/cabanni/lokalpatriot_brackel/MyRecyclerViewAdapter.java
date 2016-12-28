package com.example.cabanni.lokalpatriot_brackel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cabanni on 27.12.16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
