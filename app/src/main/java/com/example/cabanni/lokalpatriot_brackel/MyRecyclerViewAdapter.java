package com.example.cabanni.lokalpatriot_brackel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cabanni on 27.12.16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {


    LayoutInflater inflater;

    public MyRecyclerViewAdapter(Context context) {


        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.pinnwand_item, parent, false);

        MyRecyclerViewHolder viewHolder = new MyRecyclerViewHolder(view);

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
        TextView datum;
        TextView user;
        TextView textLang;
        public MyRecyclerViewHolder(View itemView) {

            super(itemView);
            datum = (TextView) itemView.findViewById(R.id.Datum);
            user = (TextView) itemView.findViewById(R.id.User);
            textLang = (TextView) itemView.findViewById(R.id.textLang);
        }
    }
}
