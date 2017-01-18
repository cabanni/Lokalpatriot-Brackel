package com.example.cabanni.lokalpatriot_brackel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cabanni on 27.12.16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {


    LayoutInflater inflater;
    ArrayList<Pinntext> data = new ArrayList<Pinntext>();
    String userName;

    public MyRecyclerViewAdapter(ArrayList data, String userName) {


        this.data = data;
        this.userName = userName;
    }


    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinnwand_item, parent, false);

        MyRecyclerViewHolder viewHolder = new MyRecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {

        final Pinntext current = data.get(position);
        holder.textLang.setText(current.getText());
        holder.user.setText(current.getUser());
        holder.datum.setText(current.getMysqlDate());
        holder.ueberschrift.setText(current.getUeberschrift());
        holder.id.setText(current.getId().toString());


        if (current.getUser().equals(userName)) {
            holder.loeschen.setVisibility(View.VISIBLE);
            holder.aendern.setVisibility(View.VISIBLE);
            holder.loeschen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PinnwandConnecter pinnwandConnecter = new PinnwandConnecter();

                    pinnwandConnecter.sendToServer(current.getId().toString(), Finals.urlPinnwandDelete);

                }
            });
        }





    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView datum;
        TextView user;
        TextView textLang;
        TextView ueberschrift;
        TextView id;
        Button loeschen;
        Button aendern;
        public MyRecyclerViewHolder(View itemView) {

            super(itemView);
            ueberschrift = (TextView) itemView.findViewById(R.id.ueberschrift);
            datum = (TextView) itemView.findViewById(R.id.Datum);
            user = (TextView) itemView.findViewById(R.id.User);
            textLang = (TextView) itemView.findViewById(R.id.textLang);
            id = (TextView) itemView.findViewById(R.id.id);
            loeschen = (Button) itemView.findViewById(R.id.loeschen);
            aendern = (Button) itemView.findViewById(R.id.aendern);
        }
    }
}
