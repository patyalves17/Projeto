package com.projeto.patyalves.projeto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.adapter.LocalVisitedAdapter;
import com.projeto.patyalves.projeto.model.Local;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesVisitedFragment extends Fragment {
    private DBHandlerP db;
    private LocalVisitedAdapter localAdapter;
    @BindView(R.id.rvLocais) RecyclerView rvLocais;

    public PlacesVisitedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_places_visited,container,false);
        ButterKnife.bind(this,view);
        db=new DBHandlerP(getContext());

        localAdapter = new LocalVisitedAdapter(this.getContext(),new ArrayList<Local>(),onItemClick());
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        rvLocais.setLayoutManager(layoutManager);
        rvLocais.setAdapter(localAdapter);
        rvLocais.setHasFixedSize(true);

        buscaVisitados();
        return view;
    }

    private void buscaVisitados(){

        List<Local> locais = db.getAllPlaces();
        localAdapter.update(locais);

        for(Local local: locais){

            Log.i("visitados", local.getName());

        }

    }

    private LocalVisitedAdapter.OnItemClickListener onItemClick(){

        return new LocalVisitedAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, Long localId) {
                Log.i("visitados", "clicou no --> "+localId.toString());


            }
        };

    }

}
