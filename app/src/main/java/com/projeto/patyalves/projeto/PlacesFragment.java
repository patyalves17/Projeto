package com.projeto.patyalves.projeto;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.adapter.OnItemClickListener;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.model.ResponseLocal;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

    @BindView(R.id.rvLocais) RecyclerView rvLocais;
    private LocalAdapter localAdapter;
    private LocalsAPI localAPI;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;



    public PlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places,container,false);
        ButterKnife.bind(this,view);//equivalente ao findViewById pra todas as coisas da manteiga na faca


        localAdapter = new LocalAdapter(new ArrayList<Local>());
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());


        rvLocais.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0)  {//check for scroll down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();

                    LinearLayoutManager layoutManager2 = ((LinearLayoutManager)rvLocais.getLayoutManager());
                    int pos = layoutManager2.findLastCompletelyVisibleItemPosition();

                        if ( (pastVisiblesItems+1) >= totalItemCount)
                        {
                            loading = false;
                            Log.i("carregaLocais", "Last Item Wow !");
                        }

                }
            }
        });

        rvLocais.setLayoutManager(layoutManager);
        rvLocais.setAdapter(localAdapter);
        rvLocais.setHasFixedSize(true);

        Log.i("MainActivity", "precarregando dados");
        carregaLocais();


      //  return inflater.inflate(R.layout.fragment_places, container, false);

        return view;
    }

    private void carregaLocais(){
        localAPI = APIUtils.getLocalsAPI();
        Log.i("carregaLocais", "carregando...");
        localAPI.getLocais().enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivityLine", response.body().toString());

                    localAdapter.update(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Local>> call, Throwable t) {
                Log.i("MainActivity", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });

    }

}