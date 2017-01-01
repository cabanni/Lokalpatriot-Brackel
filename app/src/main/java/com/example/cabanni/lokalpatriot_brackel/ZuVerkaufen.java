package com.example.cabanni.lokalpatriot_brackel;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by cabanni on 24.12.16.
 */

public class ZuVerkaufen extends Fragment {
    public Bundle bundle;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MyRecyclerViewAdapter.MyRecyclerViewHolder rvLayoutManager;
    View view;
    Activity activity;
    String result;
    String ort;
    String kategorie;
    ArrayList<Pinntext> arrayList = new ArrayList<Pinntext>();
    JSONObject jsonObject;
    JSONArray jsonArray;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);







    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bundle = getArguments();
        this.ort = bundle.getString("ort");
        this.kategorie = bundle.getString("kategorie");
        result = new String();
        // pinnwandConnecter.sendToServer(bundle.getString("ort"), bundle.getString("kategorie")); // holt den Json String vom Server
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();





    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_zu_verkaufen, container, false);
        activity = getActivity(); // holt den context der Activity
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);



        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        // rvAdapter = new RvAdapter();
        // recyclerView.setAdapter(rvAdapter);
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        public String stringUrl = Finals.url;

        Context context;
        Activity activity;
        String jsonString;

        ;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                // Post Variablen vorbereiten
                String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                URL url = new URL(com.example.cabanni.lokalpatriot_brackel.BackgroundTask.stringUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setFixedLengthStreamingMode(textParam.getBytes().length);

                // Post Variablen zum Server schicken
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(textParam);
                outputStreamWriter.flush();
                outputStreamWriter.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    publishProgress();

                }
                //alles schließen
                httpURLConnection.disconnect();
                inputStream.close();
                outputStreamWriter.close();
                this.jsonString = stringBuilder.toString().trim();

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
            //  getActivity().setProgressBarIndeterminate(false);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //  getActivity().setProgressBarVisibility(false);
            result = this.jsonString;
            Log.d("Json String", jsonString);
            try {
                int count = 0;
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("json_response"); // json_response ist der Name des JsonArrays im php-script


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
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(arrayList);
            recyclerView.setAdapter(myRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        }
    }



}
