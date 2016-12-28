package com.example.cabanni.lokalpatriot_brackel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        textView = (TextView) view.findViewById(R.id.textView);
        Bundle bundle = this.getArguments();
        mUserName = bundle.getString("mUsername");
        textView.setText(this.mUserName);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
