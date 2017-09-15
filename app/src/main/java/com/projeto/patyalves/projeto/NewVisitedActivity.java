package com.projeto.patyalves.projeto;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewVisitedActivity extends AppCompatActivity {
    Long id;
    private LocalsAPI localAPI;
    private Local local;
    private DBHandlerP db;

    @BindView(R.id.btnSave) Button fab;
    @BindView(R.id.myRatingBar) RatingBar myRatingBar;
    @BindView(R.id.tilMyComentario) TextInputLayout tilMyComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visited);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        db=new DBHandlerP(this);


        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("localId");
        Log.i("detalheLocal", "detalhes do--> "+id);
        loadDetails();

    }

    private void loadDetails(){

        localAPI = APIUtils.getLocalsAPI();
        Log.i("detalheLocal", "carregando...");
        localAPI.getLocal(id).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> response) {
                if (response.isSuccessful()) {
//                    Log.i("detalheLocal", response.body().toString());
//                    Log.i("detalheLocal",response.body().getName());
//                    Log.i("detalheLocal",response.body().getBairro());
                    local=response.body();
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable t) {
                Log.i("detalheLocal", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void save(View view){
        Log.i("SalvaLocais", "salvar o id " +local.getId());
        local.setMycomentario(tilMyComentario.getEditText().getText().toString());
        local.setMyRate(Double.parseDouble(myRatingBar.getRating()+""));

        if(db.getLocal(local.getId())!=null){
            Log.i("SalvaLocais", "update o id " +local.getId());
           // local.setImagem("");
            long idSalve=db.updateLocal(local);
            Log.i("SalvaLocais", "salvou!!!!! ..." + idSalve);
        }else{
            Log.i("SalvaLocais", "salvar o id " +local.getId());
//            local.setImagem("");
            long idSalve=db.createLocal(local);
            Log.i("SalvaLocais", "salvou!!!!! ..." + idSalve);
        }

//        Log.i("SalvaLocais", "salvar o id " +local.getId());
//        Log.i("SalvaLocais", "Comentario " +tilMyComentario.getEditText().getText());
//        Log.i("SalvaLocais", "rating " +myRatingBar.getRating());
//
//
//
//        local.setImagem("");
//        long idSalve=db.createLocal(local);
//        Log.i("SalvaLocais", "salvou!!!!! ..." + idSalve);




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

