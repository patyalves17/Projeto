package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

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

        tilMyComentario.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
        tilMyComentario.getEditText().setRawInputType(InputType.TYPE_CLASS_TEXT);

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

                    Local localE=db.getLocal(id);
                    if(localE!=null){
                        local.setMycomentario(localE.getMycomentario());
                        local.setMyRate(localE.getMyRate());

                        myRatingBar.setRating(Float.parseFloat(local.getMyRate().toString()));
                        tilMyComentario.getEditText().setText(local.getMycomentario());
                    }

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
            Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,PlaceDetailActivity.class);
            intent.putExtra("localId",local.getId());
            startActivity(intent);
            this.finish();

        }else{
            Log.i("SalvaLocais", "salvar o id " +local.getId());
//            local.setImagem("");
            long idSalve=db.createLocal(local);
            Log.i("SalvaLocais", "salvou!!!!! ..." + idSalve);
            Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,PlaceDetailActivity.class);
            intent.putExtra("localId",local.getId());
            startActivity(intent);
            this.finish();
        }


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

