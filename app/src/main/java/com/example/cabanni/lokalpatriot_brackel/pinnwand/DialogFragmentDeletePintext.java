package com.example.cabanni.lokalpatriot_brackel.pinnwand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cabanni.lokalpatriot_brackel.Finals;
import com.example.cabanni.lokalpatriot_brackel.PinnwandConnecter;
import com.example.cabanni.lokalpatriot_brackel.PinnwandRootFragment;
import com.example.cabanni.lokalpatriot_brackel.R;

/**
 * Created by cabanni on 19.01.17.
 */

public class DialogFragmentDeletePintext extends DialogFragment implements View.OnClickListener {
    Button no;
    Button yes;
    Bundle bundle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bundle = getArguments();
        View view = inflater.inflate(R.layout.dialog_delete_pintext, null);
        no = (Button) view.findViewById(R.id.no);
        yes = (Button) view.findViewById(R.id.yes);
        setCancelable(false);
        no.setOnClickListener(this);
        yes.setOnClickListener(this);
        fragmentManager = getFragmentManager();


        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.no) {
            dismiss();


        } else {
            PinnwandConnecter pinnwandConnecter = new PinnwandConnecter();

            if (bundle != null) {
                Integer IDasInteger = bundle.getInt("id", 0);

                if (IDasInteger != 0) {
                    String id = IDasInteger.toString();
                    pinnwandConnecter.sendToServer(id, Finals.urlPinnwandDelete);
                    dismiss();
                }
            }
            dismiss();


            PinnwandRootFragment pinnwandRootFragment = new PinnwandRootFragment();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.root_layout, pinnwandRootFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
