package com.example.cabanni.lokalpatriot_brackel;

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
 * Klasse für die "Pinnwand" Fragmente
 * Created by cabanni on 29.12.16.
 */

public class PinnwandConnecter {


    private String result;

    protected static String getTextFromInputStream(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                // stringBuilder.append("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().trim();
    }

    public String getResult() {
        return result;
    }

    /**
     * @param ort
     * @param kategorie
     * @return String aus Datenbankabfrage.
     */
    public void sendToServer(final String ort, final String kategorie) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                    URL url = new URL(BackgroundTask.stringUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setFixedLengthStreamingMode(textParam.getBytes().length);

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    outputStreamWriter.write(textParam);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    result = getTextFromInputStream(inputStream);
                    Log.d("Antwort ist: ", result);
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();



    }
}
