package com.example.cabanni.lokalpatriot_brackel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentShowPinwandDiesDas;
import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentShowPinwandFeiern;
import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentShowPinwandHilfe;
import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentShowPinwandZuVerkaufen;
import com.example.cabanni.lokalpatriot_brackel.pinnwand.FragmentShowPinwandZuVerschenken;

/**
 * Created by cabanni on 02.01.17.
 */

public class PinnwandRootFragment extends Fragment {

    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pinnwand_root, container, false);


        viewPager = (ViewPager) view.findViewById(R.id.pager);
        FragmentManager fragmentManager = getFragmentManager();
        MyAdapter myAdapter = new MyAdapter(fragmentManager);
        viewPager.setAdapter(myAdapter);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) view.findViewById(R.id.pagerTitle);


        return view;


    }


    class MyAdapter extends FragmentStatePagerAdapter {

        Bundle bundle = new Bundle();


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new FragmentShowPinwandZuVerkaufen();


            if (position == 0) {
                fragment = new FragmentShowPinwandZuVerkaufen();
                return fragment;


            }
            if (position == 1) {
                fragment = new FragmentShowPinwandFeiern();
                return fragment;


            }
            if (position == 2) {
                fragment = new FragmentShowPinwandZuVerschenken();
                return fragment;

            }
            if (position == 3) {
                fragment = new FragmentShowPinwandHilfe();
                return fragment;
            }

            if (position == 4) {
                fragment = new FragmentShowPinwandDiesDas();
                return fragment;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position) {
                case 0:
                    title = Finals.KATEGORIE_ZU_VERKAUFEN;
                    break;
                case 1:
                    title = Finals.KATEGORIE_FEIER;
                    break;
                case 2:
                    title = Finals.KATEGORIE_ZU_VERSCHENKEN;
                    break;
                case 3:
                    title = Finals.KATEGORIE_HILFE;
                    break;
                case 4:
                    title = Finals.KATEGORIE_DIES_DAS;
                    break;

            }
            return title;
        }
    }

}
