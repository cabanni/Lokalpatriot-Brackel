package com.example.cabanni.lokalpatriot_brackel;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cabanni on 25.12.16.
 */


public class BackgroundTask extends AsyncTask<Void, Void, Void> {

    public static String stringUrl = "http://192.168.43.170/lokalpatriot/lokalpatriot.php";

    Context context;
    Activity activity;
    String jsonString;
    String ort;
    String kategorie;


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // Post Variablen vorbereiten
            String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
            URL url = new URL(BackgroundTask.stringUrl);
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

            }
            httpURLConnection.disconnect();
            this.jsonString = stringBuilder.toString().trim();
            Log.d("Json String", jsonString);
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
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
