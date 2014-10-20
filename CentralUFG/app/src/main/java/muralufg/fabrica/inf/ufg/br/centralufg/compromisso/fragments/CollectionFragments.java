/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package muralufg.fabrica.inf.ufg.br.centralufg.compromisso.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class CollectionFragments extends Fragment {
    
    CollectionPagerAdapter mCollectionPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCollectionPagerAdapter = new CollectionPagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);
    }

    public static class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    	
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	Calendar hoje = Calendar.getInstance();
        	hoje.add(Calendar.DAY_OF_MONTH, i);

            /*Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_DIA, hoje.get(Calendar.DAY_OF_MONTH));
            fragment.setArguments(args);*/

            Fragment fragment = new CompromissoFragment();
            Bundle args = new Bundle();
            args.putInt(CompromissoFragment.ARG_DIA, hoje.get(Calendar.DAY_OF_MONTH));
            args.putInt(CompromissoFragment.ARG_MES, hoje.get(Calendar.MONTH));
            args.putInt(CompromissoFragment.ARG_ANO, hoje.get(Calendar.YEAR));
            fragment.setArguments(args);

            return fragment;
        }
        
        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	Calendar hoje = Calendar.getInstance();
        	hoje.add(Calendar.DAY_OF_MONTH, position);
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	        	
            return dateFormat.format(hoje.getTime());
        }
    }


    /*public static class DemoObjectFragment extends Fragment {

        public static final String ARG_DIA = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText("Compromissos");
            return rootView;
        }
    }*/
}
