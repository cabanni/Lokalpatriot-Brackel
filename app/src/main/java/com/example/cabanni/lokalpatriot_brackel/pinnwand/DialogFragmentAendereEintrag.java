package com.example.cabanni.lokalpatriot_brackel.pinnwand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cabanni.lokalpatriot_brackel.Conn_updatePintext;
import com.example.cabanni.lokalpatriot_brackel.Finals;
import com.example.cabanni.lokalpatriot_brackel.R;

/**
 * Created by cabanni on 20.01.17.
 */
public class DialogFragmentAendereEintrag extends DialogFragment {


    TextView kategorie;
    TextView ueberschrift;
    TextView textLang;
    Button no;
    Button yes;
    Bundle bundle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_aendern_pintext, null);
        bundle = getArguments();
        kategorie = (TextView) view.findViewById(R.id.textView3);
        ueberschrift = (TextView) view.findViewById(R.id.textView6);
        textLang = (TextView) view.findViewById(R.id.textView7);
        no = (Button) view.findViewById(R.id.button2);
        yes = (Button) view.findViewById(R.id.button3);
        kategorie.setText(bundle.getString("kategorie"));
        textLang.setText(bundle.getString("textLang"));
        ueberschrift.setText(bundle.getString("ueberschrift"));


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conn_updatePintext.sendToServer(bundle.getInt("id"), bundle.getString("ueberschrift"), bundle.getString("kategorie"), bundle.getString("textLang"),
                        Finals.URL_UPDATE_PINTEXT);
                fragmentManager = getFragmentManager();

                Fragment pinnwandRootFragment = new PinnwandRootFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.root_layout, pinnwandRootFragment);


                pinnwandRootFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
                dismiss();


            }
        });


        return view;
    }
}
