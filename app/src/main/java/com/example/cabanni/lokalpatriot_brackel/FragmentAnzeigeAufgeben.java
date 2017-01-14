package com.example.cabanni.lokalpatriot_brackel;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cabanni on 05.01.17.
 */

public class FragmentAnzeigeAufgeben extends Fragment {

    protected String[] kategorien;
    ArrayAdapter<String> arrayAdapter;
    String kategorie;
    Spinner spinner;
    String ueberschrift;
    String beschreibung;
    String telefonNummer;
    String username;
    String gmail;
    Button button;
    Bundle bundle;
    EditText editTextUeberschrift;
    EditText editTextBeschreibung;
    EditText editTextTelefonNummer;
    View view;
    Context context;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        username = bundle.getString("mUsername");
        gmail = bundle.getString("email");


        kategorien = new String[]{Finals.KATEGORIE_ZU_VERKAUFEN, Finals.KATEGORIE_FEIER, Finals.KATEGORIE_ZU_VERSCHENKEN, Finals.KATEGORIE_HILFE, Finals.KATEGORIE_DIES_DAS};
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, kategorien);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_anzeige_aufgeben, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinnerKategorie);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView myText = (TextView) view;

                kategorie = (String) parent.getItemAtPosition(position);
                L.m((String) kategorie);
                L.l(getActivity(), kategorie);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        this.button = (Button) view.findViewById(R.id.button_anzeige_abschicken);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findText();
                FragmentAnzeigeAufgeben.BackgroundTask backgroundTask = new FragmentAnzeigeAufgeben.BackgroundTask();
                backgroundTask.execute();

            }
        });


        this.view = view;
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bundle.putString("ueberschrift", ueberschrift);
        bundle.putString("beschreibung", beschreibung);
        bundle.putString("telefonNummer", telefonNummer);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);


    }


    protected void findText() {
        editTextUeberschrift = (EditText) view.findViewById(R.id.editText_ueberschrift);
        ueberschrift = editTextUeberschrift.getText().toString();
        editTextBeschreibung = (EditText) view.findViewById(R.id.editText_beschreibung);
        beschreibung = editTextBeschreibung.getText().toString();
        editTextTelefonNummer = (EditText) view.findViewById(R.id.editText_phone);
        telefonNummer = editTextTelefonNummer.getText().toString();
    }

    public void changeFragmentBack() {
        fragmentManager = getActivity().getSupportFragmentManager();
        Fragment pinnwandRootFragment = new PinnwandRootFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, pinnwandRootFragment);
        fragmentTransaction.addToBackStack(null);


        pinnwandRootFragment.setArguments(bundle);

        fragmentTransaction.commit();


    }


    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        public String stringUrl = Finals.SERVERLOKAL_INSERT_PINWAND;

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                // Post Variablen vorbereiten
                //  String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                URL url = new URL(Finals.URL_INSERT_PINWAND);
                String textParam = "user=" + URLEncoder.encode(username, "UTF-8") + "&gmail=" + URLEncoder.encode(gmail, "UTF-8") +
                        "&ueberschrift=" + URLEncoder.encode(ueberschrift, "UTF-8") + "&beschreibung=" + URLEncoder.encode(beschreibung, "UTF-8") +
                        "&telefon=" + URLEncoder.encode(telefonNummer, "UTF-8") + "&kategorie=" + URLEncoder.encode(kategorie, "UTF-8") +
                        "&ort=" + URLEncoder.encode(Finals.ORT, "UTF-8");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setFixedLengthStreamingMode(textParam.getBytes().length);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


                // Post Variablen zum Server schicken
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());

                outputStreamWriter.write(textParam);


                outputStreamWriter.close();

                //Antwort zurück bekommen
                InputStream inputStream = httpURLConnection.getInputStream();

                httpURLConnection.disconnect();
                inputStream.close();
                ;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            changeFragmentBack();

        }
    }


}
