package com.projeto.patyalves.projeto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceDetailFragment extends Fragment {


    public PlaceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        Long mLabel = bundle.getLong("localId",0);
        Log.i("carregaLocais", "Fragment 2 --> "+mLabel.toString());

        return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }

}
