package com.example.cabanni.lokalpatriot_brackel.pinnwand;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cabanni.lokalpatriot_brackel.Finals;
import com.example.cabanni.lokalpatriot_brackel.R;

/**
 * Created by cabanni on 19.01.17.
 */

public class FragmentPinwandChangePost extends Fragment {

    String[] kategorien = new String[]{
            Finals.KATEGORIE_ZU_VERKAUFEN, Finals.KATEGORIE_FEIER, Finals.KATEGORIE_ZU_VERSCHENKEN, Finals.KATEGORIE_HILFE, Finals.KATEGORIE_DIES_DAS};


    Bundle bundle;
    Spinner spinner;
    EditText ueberschrift;
    EditText langText;
    String kategorie;
    String textUeberschrift;
    String textLang;
    Button button;
    Integer id;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;
    ArrayAdapter arrayAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pinnwand_change_post, container, false);
        bundle = getArguments();
        this.context = view.getContext();
        arrayAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, kategorien);
        spinner = (Spinner) view.findViewById(R.id.spinnerKategorieAendern);

        spinner.setAdapter(arrayAdapter);
        button = (Button) view.findViewById(R.id.buttonAendern);
        ueberschrift = (EditText) view.findViewById(R.id.editText_ueberschriftAendern);
        langText = (EditText) view.findViewById(R.id.editTextLangTextAendern);
        fragmentManager = getFragmentManager();
        if (bundle != null) {
            this.kategorie = bundle.getString("kategorie", Finals.DEFAULT);
            this.textUeberschrift = bundle.getString("ueberschrift", Finals.DEFAULT);
            this.textLang = bundle.getString("text", Finals.DEFAULT);
            this.id = bundle.getInt("id", 0);
            ueberschrift.setText(this.textUeberschrift);
            langText.setText(this.textLang);
        }
        int spinnerPosition = arrayAdapter.getPosition(this.kategorie);
        spinner.setSelection(spinnerPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategorie = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragmentAendereEintrag dialogFragmentAendereEintrag = new DialogFragmentAendereEintrag();
                if (bundle != null) {
                    bundle.putString("kategorie", kategorie);
                    bundle.putString("ueberschrift", ueberschrift.getEditableText().toString());
                    bundle.putString("textLang", langText.getEditableText().toString());
                    dialogFragmentAendereEintrag.setArguments(bundle);
                } else {
                    bundle = new Bundle();
                    bundle.putString("kategorie", kategorie);
                    bundle.putString("ueberschrift", ueberschrift.getEditableText().toString());
                    bundle.putString("textLang", langText.getEditableText().toString());
                    bundle.putInt("id", id);
                    dialogFragmentAendereEintrag.setArguments(bundle);

                }
                dialogFragmentAendereEintrag.show(fragmentManager, "aenderePinwandEintrag");
            }
        });


        return view;


    }
}
