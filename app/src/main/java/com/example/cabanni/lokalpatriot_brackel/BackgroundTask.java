package com.example.cabanni.lokalpatriot_brackel;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cabanni on 25.12.16.
 */


public class BackgroundTask extends AsyncTask<Void, Void, Void> {

    String jsonString = "http://192.168.43.170/lokalpatriot/lokalpatriot.php";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(jsonString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");

            }
            httpURLConnection.disconnect();
            String jsonString = stringBuilder.toString().trim();
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
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
