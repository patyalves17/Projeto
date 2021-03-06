package com.projeto.patyalves.projeto;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private Local local;
    Long id;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.fab1) FloatingActionButton fab1;
    @BindView(R.id.fab2) FloatingActionButton fab2;
    @BindView(R.id.fab3) FloatingActionButton fab3;
    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private BroadcastReceiver mReceiver;
    private DBHandlerP db;

    public PlaceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_detail,container,false);
        ButterKnife.bind(this,view);
        db=new DBHandlerP(getContext());

        Bundle bundle=getArguments();
         id = bundle.getLong("localId",0);
        Log.i("carregaLocais", "Fragment 2 --> "+id.toString());
        loadDetails();

        Log.d("Raj", "Fab try");
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);


        return view;
      //  return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }

//    @OnClick(R.id.fab)
//    public void click(View view){
//        Log.d("Raj", "clicou no Fab 1");
//
//    }

    @OnClick(R.id.fab)
    public void click(View view){
        Log.i("carregaLocais", "clicou no fab");
        animateFAB();
    }
    @OnClick(R.id.fab1)
    public void click1(View view){
        Log.i("carregaLocais", "Call to " +local.getTelefone());


        String uri ="tel:"+local.getTelefone().replace(" ", "").replace("-", "");
        Log.i("carregaLocais", "Call to " +uri);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
//        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        startActivity(intent);
 //       Toast.makeText(view.getContext(),"Hi",Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.fab2)
    public void click2(View view){
        Log.i("carregaLocais", "clicou no fab 2 MAP");

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", local.getLatitude());
        bundle.putDouble("longitude", local.getLongitude());
        bundle.putString("nome", local.getName());
        MapsActivity maps=new MapsActivity();
        maps.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_main,maps);
        // ft.replace(R.id.content_main,fragment).addToBackStack(null);
        ft.addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.fab3)
    public void click3(View view){
//        Log.i("SalvaLocais", "salvar o id " +local.getId());
//        local.setImagem("");
//        long idSalve=db.createLocal(local);
//        Log.i("SalvaLocais", "salvou!!!!! ..." + idSalve);




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
                    local=response.body();
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable t) {
                Log.i("carregaLocais", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

}
