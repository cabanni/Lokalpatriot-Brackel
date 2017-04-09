package com.example.cabanni.lokalpatriot_brackel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by cabanni on 25.12.16.
 */

public class StartFragment extends Fragment {

    String mUserName = new String();
    View view;
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start_fragment, container, false);

        Bundle bundle = this.getArguments();
        mUserName = bundle.getString("mUsername");

        WebView webView= (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(Finals.SERVERLOKAL_START_FRAGMENT);




        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
