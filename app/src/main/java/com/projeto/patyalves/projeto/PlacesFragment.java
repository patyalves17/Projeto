package com.projeto.patyalves.projeto;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

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


        localAdapter = new LocalAdapter(this.getContext(),new ArrayList<Local>(),onItemClick());
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

    private LocalAdapter.OnItemClickListener onItemClick(){

        return new LocalAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, Long localId) {
                Log.i("detalheLocal", "clicou no --> "+localId.toString());
//                Bundle bundle = new Bundle();
//                bundle.putLong("localId", localId);
//                PlaceDetailFragment fragment=new PlaceDetailFragment();
//                fragment.setArguments(bundle);
//
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.content_main,fragment);
//                // ft.replace(R.id.content_main,fragment).addToBackStack(null);
//                ft.addToBackStack(null);
//                ft.commit();


                Intent intent = new Intent(getContext(),PlaceDetailActivity.class);
                intent.putExtra("localId",localId);
                startActivity(intent);

//                finish();

            }
        };

    }

    private void carregaLocais(){

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        Long idPessoa = settings.getLong("idPessoa", 0);

        Log.i("carregaLocais","idPessoa Locais"+ idPessoa);


        localAPI = APIUtils.getLocalsAPI();

        if(idPessoa!=0){
            Log.i("carregaLocais","idPessoa Locais"+ idPessoa);
            //localAPI = APIUtils.getLocalsAPI();
            localAPI.getLocaisPessoa(idPessoa).enqueue(new Callback<List<Local>>() {
                @Override
                public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                    if (response.isSuccessful()) {
                        Log.i("carregaLocais", response.body().toString());

                        localAdapter.update(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Local>> call, Throwable t) {
                    Log.i("carregaLocais", "erro ao carregar dados: "+t.getLocalizedMessage());
                }
            });
        }else{
            Log.i("carregaLocais"," All"+ idPessoa);
            localAPI.getLocais().enqueue(new Callback<List<Local>>() {
                @Override
                public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                    if (response.isSuccessful()) {
                        Log.i("carregaLocais", response.body().toString());

                        localAdapter.update(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Local>> call, Throwable t) {
                    Log.i("carregaLocais", "erro ao carregar dados: "+t.getLocalizedMessage());
                }
            });
        }

        Log.i("carregaLocais", "carregando...");


    }

}
