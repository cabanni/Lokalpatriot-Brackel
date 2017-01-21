package com.example.cabanni.lokalpatriot_brackel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Klasse für die "Pinnwand" Fragmente
 * Created by cabanni on 29.12.16.
 */

public class Conn_updatePintext {


    public static void sendToServer(final Integer param1, final String param2, final String param3, final String param4, final String urlServer) {


        new Thread(new Runnable() {


            public String jsonString;
            ;

            @Override
            public void run() {
                try {
                    //  String textParam = "ort=" + URLEncoder.encode(username, "UTF-8") + "&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                    URL url = new URL(urlServer);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


                    PrintStream ps = new PrintStream(httpURLConnection.getOutputStream());
                    ;
                    ps.print("&id=" + param1.toString());
                    ps.print("&ueberschrift=" + param2);
                    ps.print("&kategorie=" + param3);
                    ps.print("&textLang=" + param4);


                    //Antwort zurück bekommen
                    //Antwort zurück bekommen
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);


                    }

                    this.jsonString = stringBuilder.toString().trim();
                    L.m(jsonString);
                    ps.flush();
                    ps.close();

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
