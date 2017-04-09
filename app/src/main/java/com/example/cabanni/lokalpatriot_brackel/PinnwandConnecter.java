package com.example.cabanni.lokalpatriot_brackel;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Klasse für die "Pinnwand" Fragmente
 * Created by cabanni on 29.12.16.
 */

public class PinnwandConnecter {


    public void sendToServer(final String param1, final String urlServer) {


        new Thread(new Runnable() {


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
                    ps.print("&id=" + param1);

                    //Antwort zurück bekommen
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();

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
