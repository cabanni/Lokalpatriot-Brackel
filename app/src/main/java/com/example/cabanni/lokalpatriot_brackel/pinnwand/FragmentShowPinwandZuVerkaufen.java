package com.example.cabanni.lokalpatriot_brackel.pinnwand;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;

import com.example.cabanni.lokalpatriot_brackel.Finals;
import com.example.cabanni.lokalpatriot_brackel.L;
import com.example.cabanni.lokalpatriot_brackel.MyRecyclerViewAdapter;
import com.example.cabanni.lokalpatriot_brackel.Pinntext;
import com.example.cabanni.lokalpatriot_brackel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by cabanni on 24.12.16.
 */

public class FragmentShowPinwandZuVerkaufen extends Fragment {
    public Bundle bundle;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MyRecyclerViewAdapter.MyRecyclerViewHolder rvLayoutManager;
    View view;
    Activity activity;
    String result;
    String ort = Finals.ORT;
    String kategorie = Finals.KATEGORIE_ZU_VERKAUFEN;
    ArrayList<Pinntext> arrayListPinntext = new ArrayList<Pinntext>();
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProgressBar progressBar;
    String userName;
    String gmail;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", userName);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        result = new String("default");
        // pinnwandConnecter.sendToServer(bundle.getString("ort"), bundle.getString("kategorie")); // holt den Json String vom Server


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();

        //bundle vom Rootfragment holen
        bundle = getArguments();


        if (bundle != null) {
            this.userName = bundle.getString("mUsername", Finals.DEFAULT);
        } else if (savedInstanceState != null) {
            this.userName = savedInstanceState.getString("username");
        } else {
            sharedPreferences = context.getSharedPreferences(Finals.APPDATA, context.MODE_PRIVATE);
            this.userName = sharedPreferences.getString("username", Finals.DEFAULT);

        }





        view = inflater.inflate(R.layout.fragment_zu_verkaufen, container, false);
        activity = getActivity(); // holt den context der Activity
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.myProgessbar);


        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {



        String jsonString =" json unverändert";


        public BackgroundTask() {


        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                // Post Variablen vorbereiten
                //  String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                URL url = new URL(Finals.urlPinnwandAbfrage);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //   httpURLConnection.setFixedLengthStreamingMode(textParam.getBytes().length);


                PrintStream ps = new PrintStream(urlConnection.getOutputStream());
                // send your parameters to your site
                ps.print("ort=" + ort);
                ps.print("&kategorie=" + kategorie);

                InputStream inputStream;


                    inputStream = urlConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        publishProgress();

                    }  this.jsonString = stringBuilder.toString().trim();


                //alles schließen
                inputStream.close();
               ps.close();


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
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //  getActivity().setProgressBarVisibility(false)
            //
            progressBar.setVisibility(View.INVISIBLE);

                result = this.jsonString;


            try {
                Log.d("Json String", jsonString);
                int count = 0;
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("json_response"); // json_response ist der Name des JsonArrays im php-script


                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    count++;
                    Pinntext pinntext = new Pinntext(jo.getString("ueberschrift"), jo.getString("text"), jo.getString("userName"), jo.getString("userMail"),
                            jo.getString("date"), jo.getInt("id"), kategorie);
                    arrayListPinntext.add(pinntext);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(arrayListPinntext, userName, getFragmentManager(), bundle);
            L.m(userName);
            recyclerView.setAdapter(myRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        }
    }


}
