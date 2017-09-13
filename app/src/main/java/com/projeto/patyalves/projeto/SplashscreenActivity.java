package com.projeto.patyalves.projeto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.projeto.patyalves.projeto.Util.DBHandler;
import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.UsersAPI;
import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashscreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=200;
//    private DBHandler db;
    private DBHandlerP db;
    private UsersAPI userAPI;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        db=new DBHandlerP(this);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        animation.reset();
        ImageView imageView= (ImageView) findViewById(R.id.ivSplash);

        if(imageView!=null){
            imageView.clearAnimation();
            imageView.startAnimation(animation);
        }

        SyncDatabase syncDatabase=new SyncDatabase();
        syncDatabase.execute();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
//                SplashscreenActivity.this.finish();
//            }
//        },SPLASH_DISPLAY_LENGTH);

    }
    private class SyncDatabase extends AsyncTask<String, Void, String>{
        private ProgressDialog progress;
        @Override
        protected void onPreExecute(){
            progress=ProgressDialog.show(SplashscreenActivity.this, "Aguarde","Buscando dados no servidor");
        }

        @Override
        protected String doInBackground(String...params){

            try{
                userAPI = APIUtils.getUserAPI();
                Log.i("carregaUser", "carregando...");
                userAPI.getUser().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            user=new User(response.body().getUsuario(),response.body().getSenha());

                            User UserSearch=db.getUser(user);
                            if(UserSearch!=null){
                                Log.i("carregaUser","existe");
                                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                                SplashscreenActivity.this.finish();
                            }else{
                                Log.i("carregaUser","nao existe");
                                long idSalve=db.createUser(user);

                                if(idSalve!=-1){
                                    startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                                    SplashscreenActivity.this.finish();
                                    Log.i("carregaUser",idSalve+" Salvo com sucesso.");
                                }
                            }
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("carregaUser", "erro ao carregar dados: "+t.getLocalizedMessage());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(String s){
////
//        }
    }


}
