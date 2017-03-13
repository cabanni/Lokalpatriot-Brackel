package com.example.cabanni.lokalpatriot_brackel.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cabanni.lokalpatriot_brackel.Finals;
import com.example.cabanni.lokalpatriot_brackel.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * Created by cabanni on 28.01.17.
 */

public class ChatFragment extends Fragment {

    BufferedReader bufferedReader;
    OutputStreamWriter outputStreamWriter;
    Socket sock;
    Boolean eingelogt = false;
    EditText editText;
    ListView listView;
    Button button;
    Bundle bundle;
    String username;
    String ort;
    ArrayList <String> nachrichten = new ArrayList<String>();
    String chatText;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bundle=getArguments();
        username=bundle.getString("username");
        ort=bundle.getString("ort");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_chat,container,false);
        editText= (EditText) view.findViewById(R.id.editText2);
        listView=(ListView) view.findViewById(R.id.listView);
        button=(Button) view.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatText=editText.getEditableText().toString();
                editText.setText("");


                try{
                    outputStreamWriter.write(chatText + Finals.lineSeparator);
                    outputStreamWriter.flush();
                }catch (Exception ex){
                    ex.printStackTrace();

                }
            }
        });


        return view;
    }

    


    private void netzwerkEinrichten() {
        try {
            sock=new Socket("195.110.59.187", 55787);
            sock.setKeepAlive(true);
            InputStreamReader inputStreamReader = new InputStreamReader(sock.getInputStream(), "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            outputStreamWriter = new OutputStreamWriter(sock.getOutputStream(), "UTF-8");
            System.out.println("Netzwerkverbindung steht");

        }  catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class EingehenderReader implements Runnable{

        @Override
        public void run() {
            String nachricht;
            try {
                while ((nachricht=bufferedReader.readLine())!=null){

                    if(!nachricht.matches(".*<hold>.*")){
                        System.out.println("gelesen :" + nachricht);
                        nachrichten.add(nachricht+ "\n");
                    }


                }
            }catch (Exception ex){ex.printStackTrace();}

        }



    }public class HoldConnection implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true){
                try {
                    outputStreamWriter.write("<hold>"+ Finals.lineSeparator);
                    Thread.sleep(10000);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    netzwerkEinrichten();
                } catch (InterruptedException e) {
                    netzwerkEinrichten();
                    e.printStackTrace();

                }
            }
        }

    }


}
