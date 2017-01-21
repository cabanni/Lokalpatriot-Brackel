package com.example.cabanni.lokalpatriot_brackel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cabanni.lokalpatriot_brackel.pinnwand.DialogFragmentDeletePintext;
import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentPinwandChangePost;

import java.util.ArrayList;

/**
 * Created by cabanni on 27.12.16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {


    LayoutInflater inflater;
    ArrayList<Pinntext> data = new ArrayList<Pinntext>();
    String userName;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;
    Bundle bundle;
    AppsSharedPreferences appData;
    View view;


    public MyRecyclerViewAdapter(ArrayList data, String userName, FragmentManager fragmentManager, Bundle bundle) {
        this.bundle = new Bundle();
        this.fragmentManager = fragmentManager;
        this.data = data;
        this.userName = userName;

    }


    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinnwand_item, parent, false);
        this.context = view.getContext();

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

                    try {

                        bundle.putInt("id", current.getId());
                        DialogFragmentDeletePintext dialogFragmentDeletePintext = new DialogFragmentDeletePintext();
                        dialogFragmentDeletePintext.setArguments(bundle);
                        dialogFragmentDeletePintext.show(fragmentManager, "DeleteDialog");
                    } catch (NullPointerException e) {
                        L.m(current.getId().toString());
                    }


                }
            });
            holder.aendern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentPinwandChangePost fragmentPinwandChangePost = new FragmentPinwandChangePost();
                    try {
                        bundle.putInt("id", current.getId());
                        bundle.putString("ueberschrift", current.getUeberschrift());
                        bundle.putString("text", current.getText());
                        bundle.putString("kategorie", current.getKategorie());

                    } catch (NullPointerException e) {
                        bundle = new Bundle();
                        bundle.putInt("id", current.getId());
                        bundle.putString("ueberschrift", current.getUeberschrift());
                        bundle.putString("text", current.getText());
                        bundle.putString("kategorie", current.getKategorie());
                    } finally {
                        fragmentPinwandChangePost.setArguments(bundle);
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.root_layout, fragmentPinwandChangePost);
                        fragmentTransaction.commit();
                    }
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
