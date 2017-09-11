package com.projeto.patyalves.projeto;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceDetailFragment extends Fragment {
    private LocalAdapter localAdapter;
    private LocalsAPI localAPI;
    Long id;

     @BindView(R.id.fab) FloatingActionButton fab;


    public PlaceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
         id = bundle.getLong("localId",0);
        Log.i("carregaLocais", "Fragment 2 --> "+id.toString());
        loadDetails();

        Log.d("Raj", "Fab try");

        return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }

//    @OnClick(R.id.fab)
//    public void click(View view){
//        Log.d("Raj", "clicou no Fab 1");
//
//    }

    @OnClick(R.id.fab)
    public void click(View view){
        Log.i("carregaLocais", "clicou no fab");
    }

    private void loadDetails(){

        localAPI = APIUtils.getLocalsAPI();
        Log.i("carregaLocais", "carregando...");
        localAPI.getLocal(id).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> response) {
                if (response.isSuccessful()) {
                    Log.i("carregaLocais", response.body().toString());
                    Log.i("carregaLocais",response.body().getName());
                    Log.i("carregaLocais",response.body().getBairro());

                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable t) {
                Log.i("carregaLocais", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });
    }



}
