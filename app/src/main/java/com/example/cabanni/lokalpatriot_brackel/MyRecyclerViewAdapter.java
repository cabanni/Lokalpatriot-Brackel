package com.example.cabanni.lokalpatriot_brackel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cabanni on 27.12.16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {


    LayoutInflater inflater;
    ArrayList<Pinntext> data = new ArrayList<Pinntext>();

    public MyRecyclerViewAdapter(ArrayList data) {
        this.data = data;

    }


    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinnwand_item, parent, false);

        MyRecyclerViewHolder viewHolder = new MyRecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {

        Pinntext current = data.get(position);
        holder.textLang.setText(current.getText());
        holder.user.setText(current.getUser());
        holder.datum.setText(current.getMysqlDate());



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
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
