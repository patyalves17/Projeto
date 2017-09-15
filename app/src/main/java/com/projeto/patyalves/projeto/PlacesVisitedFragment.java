package com.projeto.patyalves.projeto;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;

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
    private Boolean clickLong=false;
    Long idLocal;
    List<Local> locais;

    public PlacesVisitedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_places_visited,container,false);
        ButterKnife.bind(this,view);
        db=new DBHandlerP(getContext());

        localAdapter = new LocalVisitedAdapter(this.getContext(),new ArrayList<Local>(),onItemClick(),onLongClick());
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        rvLocais.setLayoutManager(layoutManager);
        rvLocais.setAdapter(localAdapter);
        rvLocais.setHasFixedSize(true);

        buscaVisitados();
        return view;
    }

    private LocalVisitedAdapter.OnLongClickListener onLongClick() {

        return new LocalVisitedAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View v, Long id) {
                idLocal=id;
                Log.i("visitados", "fragment clicou Longooooo --> " + id);
                clickLong=true;
                int delete=db.deleteLocal(idLocal);

                Log.i("visitados", "deletadoooooooo "+ delete);
                if(delete==1){
                    clickLong=false;
                }

//                AlertDialog.Builder builder;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
//                } else {
//                    builder = new AlertDialog.Builder(getContext());
//                }
//                builder.setTitle("Are you sure?")
//                        .setMessage("Are you sure that you want to delete this?")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Log.i("visitados", "clicou Yes");
//                                deleteItem();
//                                // exit
//                                //MainActivity.super.onBackPressed();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                                Log.i("visitados", "clicou NO");
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }
        };

    }

    public void deleteItem(){
        Log.i("visitados", "deletaaaaa");
        db.deleteLocal(idLocal);

    }

    private void buscaVisitados(){

        locais = db.getAllPlaces();
        localAdapter.update(locais);


    }

    private LocalVisitedAdapter.OnItemClickListener onItemClick(){

        return new LocalVisitedAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, Long localId) {
                if(!clickLong) {
                    Log.i("visitados", "clicou no --> " + localId.toString());


                    Intent intent = new Intent(getContext(),NewVisitedActivity.class);
                    intent.putExtra("localId",localId);
                    startActivity(intent);

                }


            }


        };

    }

//    private LocalVisitedAdapter.OnLongClickListener onLongClickListenern(){
//
//        return new LocalVisitedAdapter.OnLongClickListener(){
//            @Override
//            public void onLongClick(View view, Long localId) {
//                Log.i("visitados", "clicou no --> "+localId.toString());
//
//
//            }
//        };
//
//    }

}
