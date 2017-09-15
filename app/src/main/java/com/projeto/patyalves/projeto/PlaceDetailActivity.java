package com.projeto.patyalves.projeto;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {

    private LocalAdapter localAdapter;
    private LocalsAPI localAPI;
    private Local local;
    Long id;

//    @BindView(R.id.fab) android.support.design.widget.FloatingActionButton fab;
//    @BindView(R.id.fab1) android.support.design.widget.FloatingActionButton fab1;
//    @BindView(R.id.fab2) android.support.design.widget.FloatingActionButton fab2;
//    @BindView(R.id.fab3) android.support.design.widget.FloatingActionButton fab3;


    private Boolean isFabOpen = false;
//    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private BroadcastReceiver mReceiver;
    private DBHandlerP db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        db=new DBHandlerP(this);

        Bundle bundle=getIntent().getExtras();
         id=bundle.getLong("localId");
        Log.i("detalheLocal", "detalhes do--> "+id);


        final View fabMapa = findViewById(R.id.fabMapa);
        final View fabLigar = findViewById(R.id.fabLigar);
        final View fabVisitei = findViewById(R.id.fabVisitei);

        Log.d("Raj", "Fab try");
//        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
//        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);
//        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
//        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        loadDetails();


    }

    public void visitei(View v){
        Log.i("detalheLocal", "clicou no  visitei");
        Intent intent = new Intent(this,NewVisitedActivity.class);
        intent.putExtra("localId",local.getId());
        startActivity(intent);
    }

    public void ligar(View v){
        String uri="";
        Log.i("detalheLocal", "clicou no  ligar");
        if(local.getTelefone()==null){
            uri = "tel:+55 11 960741456" ;
        }else {
            uri = "tel:" + local.getTelefone().replace(" ", "").replace("-", "");
        }
        Log.i("carregaLocais", "Call to " +uri);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }

        startActivity(intent);
    }

    public void mapa(View v){
        Log.i("detalheLocal", "clicou no  MAP");
        Intent intent = new Intent(this,PlaceDetailMapActivity.class);
        intent.putExtra("latitude", local.getLatitude());
        intent.putExtra("longitude", local.getLongitude());
        startActivity(intent);
    }


//    @OnClick(R.id.fab)
//    public void click(View view){
//        Log.i("detalheLocal", "clicou no fab");
////        animateFAB();
//    }
//    @OnClick(R.id.fab1)
//    public void click1(View view){
//        Log.i("detalheLocal", "Call to " +local.getTelefone());
//
//
//        String uri ="tel:"+local.getTelefone().replace(" ", "").replace("-", "");
//        Log.i("carregaLocais", "Call to " +uri);
//
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        intent.setData(Uri.parse(uri));
////        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
////
////            return;
////        }
//
//        startActivity(intent);
//
//    }
//    @OnClick(R.id.fab2)
//    public void click2(View view){
//        Log.i("detalheLocal", "clicou no fab 2 MAP");
//
//
//
//        Intent intent = new Intent(this,PlaceDetailMapActivity.class);
//        intent.putExtra("latitude", local.getLatitude());
//        intent.putExtra("longitude", local.getLongitude());
//        startActivity(intent);
//
////        Bundle bundle = new Bundle();
////        bundle.putDouble("latitude", local.getLatitude());
////        bundle.putDouble("longitude", local.getLongitude());
////        bundle.putString("nome", local.getName());
////        MapsActivity maps=new MapsActivity();
////        maps.setArguments(bundle);
//
////        FragmentTransaction ft = getFragmentManager().beginTransaction();
////        ft.replace(R.id.content_main,maps);
////        // ft.replace(R.id.content_main,fragment).addToBackStack(null);
////        ft.addToBackStack(null);
////        ft.commit();
//    }
//
//    @OnClick(R.id.fab3)
//    public void click3(View view){
//
//
//        Intent intent = new Intent(this,NewVisitedActivity.class);
//        intent.putExtra("localId",local.getId());
//        startActivity(intent);
//
//
//    }

//    public void animateFAB(){
//
//        if(isFabOpen){
//
//            fab.startAnimation(rotate_backward);
//            fab1.startAnimation(fab_close);
//            fab2.startAnimation(fab_close);
//            fab3.startAnimation(fab_close);
//            fab1.setClickable(false);
//            fab2.setClickable(false);
//            fab3.setClickable(false);
//            isFabOpen = false;
//            Log.d("Raj", "close");
//
//        } else {
//
//            fab.startAnimation(rotate_forward);
//            fab1.startAnimation(fab_open);
//            fab2.startAnimation(fab_open);
//            fab3.startAnimation(fab_open);
//            fab1.setClickable(true);
//            fab2.setClickable(true);
//            fab3.setClickable(true);
//            isFabOpen = true;
//            Log.d("Raj","open");
//
//        }
//    }

    private void loadDetails(){

        localAPI = APIUtils.getLocalsAPI();
        Log.i("detalheLocal", "carregando...");
        localAPI.getLocal(id).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> response) {
                if (response.isSuccessful()) {
                    Log.i("detalheLocal", response.body().toString());
                    Log.i("detalheLocal",response.body().getName());
                   // Log.i("detalheLocal",response.body().getBairro());
                    local=response.body();
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable t) {
                Log.i("detalheLocal", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
