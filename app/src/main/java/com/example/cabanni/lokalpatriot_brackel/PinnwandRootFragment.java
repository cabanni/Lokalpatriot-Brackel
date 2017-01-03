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

            Fragment fragment = new FragmentZuVerkaufen();

            switch (position) {
                case 0:
                    bundle.putString("kategorie", Finals.KATEGORIE_ZU_VERKAUFEN);
                    break;
                case 1:
                    bundle.putString("kategorie", Finals.KATEGORIE_FEIER);
                    break;
                case 2:
                    bundle.putString("kategorie", Finals.KATEGORIE_ZU_VERSCHENKEN);
                    break;
                case 3:
                    bundle.putString("kategorie", Finals.KATEGORIE_HILFE);
                    break;
                case 4:
                    bundle.putString("kategorie", Finals.KATEGORIE_DIES_DAS);
                    break;


            }
            fragment.setArguments(bundle);

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
