package com.projeto.patyalves.projeto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.User;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TwitterSession session;
    private DBHandlerP db;
    User userT;
    private LocalsAPI localAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new DBHandlerP(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        MainActivity.SyncProfile syncProfile=new MainActivity.SyncProfile();
//        syncProfile.execute();

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(session!=null){
            Log.i("TwitterID", String.valueOf(session.getUserId()));


//            User userT = new User();
            userT = new User();
            userT.setUsuario(session.getUserName());
            userT.setSecretTwitter(session.getAuthToken().secret);
            userT.setTokenTwitter(session.getAuthToken().token);
            userT.setUserIdTwitter(String.valueOf(session.getUserId()));


            User UserSearch=db.getUser(userT);
            if(UserSearch!=null){
                Log.i("Twitter","existe");
//                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
//                Long idPessoa = settings.getLong("idPessoa", 0);
//
//                Log.i("Twitter","idPessoa"+ idPessoa);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                SharedPreferences.Editor editor = settings.edit();
                editor.putLong("idPessoa", UserSearch.getIdpessoa()).commit();
                editor.commit();

                buscaLugares();
            }else{
                Log.i("Twitter","Nop existe");

                MainActivity.SyncProfile syncProfile=new MainActivity.SyncProfile();
                syncProfile.execute();
            }
        }else{ // It isn't from twitter =(
            buscaLugares();
        }



////fragment dos locais
//        PlacesFragment formFragment = new PlacesFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.content_main, formFragment);
//        //transaction.addToBackStack(null);
//        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Log.i("Locais", "Carrega Locais");

            buscaLugares();

        } else if (id == R.id.nav_gallery) {
            PlacesVisitedFragment visitedFragment = new PlacesVisitedFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.content_main, visitedFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }  else if (id == R.id.nav_logout) {
            Log.i("TwitterLogout", "TwitterLogout");
            TwitterCore.getInstance().getSessionManager().clearActiveSession();

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
            SharedPreferences.Editor editor = settings.edit();
            editor.clear().commit();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else if (id == R.id.nav_about) {
            AboutFragment visitedFragment = new AboutFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.content_main, visitedFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buscaLugares(){

        //fragment dos locais
        PlacesFragment formFragment = new PlacesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, formFragment);
        //transaction.addToBackStack(null);
        transaction.commit();

    }

    private class SyncProfile extends AsyncTask<String, Void, String> {
        private ProgressDialog progress;


        @Override
        protected void onPreExecute(){
            progress=ProgressDialog.show(MainActivity.this, "Aguarde","Buscando dados no servidor");
        }

        @Override
        protected String doInBackground(String...params){
            Log.i("Twitter","doInBackground getTokenTwitter "+ userT.getTokenTwitter());



            try{
                    //{token}/{secret}/{idTwitter}")

//                userT.setUsuario(session.getUserName());
//                userT.setSecretTwitter(session.getAuthToken().secret);
//                userT.setTokenTwitter(session.getAuthToken().token);
//                userT.setUserIdTwitter(String.valueOf(session.getUserId()));




                localAPI = APIUtils.getLocalsAPI();
                Log.i("carregaUser", "carregando...");
                localAPI.getProfile(userT.getTokenTwitter(),userT.getSecretTwitter(),userT.getUserIdTwitter()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("twitter", "onResponse...");
                        if (response.isSuccessful()) {


                            if(response.body()!=null){
                                Log.i("twitter", String.valueOf(response.body().getIdpessoa()));
                                userT.setIdpessoa(response.body().getIdpessoa());

                                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putLong("idPessoa", userT.getIdpessoa()).commit();
                                editor.commit();


//                                User userProfile=new User();
//                                userProfile.setIdpessoa(response.body().getIdpessoa());
//
//                                Log.i("twitter","userProfile--> "+ userProfile.getIdpessoa());
                            }


                            long idSalve=db.createUser(userT);
                            if(idSalve!=-1){
                                Log.i("Twitter",idSalve+" Salvo com sucesso.");




//
                            }
                            progress.dismiss();
                            buscaLugares();


                            //Long=response.body().;
                          // User userProfile=new User();


//                            User UserSearch=db.getUser(user);
//                            if(UserSearch!=null){
//                                Log.i("carregaUser","existe");
//                                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
//                                SplashscreenActivity.this.finish();
//                            }else{
//                                Log.i("carregaUser","nao existe");
//                                long idSalve=db.createUser(user);
//
//                                if(idSalve!=-1){
//                                    startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
//                                    SplashscreenActivity.this.finish();
//                                    Log.i("carregaUser",idSalve+" Salvo com sucesso.");
//                                }
//                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("twitter", "erro ao carregar dados: "+t.getLocalizedMessage());
                    }
                });
//
//                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                TwitterAuthToken authToken = session.getAuthToken();
//                String token = authToken.token;
//                String secret = authToken.secret;
//                long idUser=session.getUserId();
//                Log.i("TwitterTokenActivity", token);
//                Log.i("TwitterTokenActivity", secret);
//                Log.i("TwitterIdUserActivity", String.valueOf(idUser));
//
//
//                URL url=new URL("http://172.16.71.42:8084/api/profile/"+token+"/"+secret+"/"+idUser);
//                HttpURLConnection connection =(HttpURLConnection )url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Accept","application/json");
//
//
//                if(connection.getResponseCode()==200){
//                    Log.i("MainActivity","It' ok");
//                }
//
            }catch (Exception e){
                e.printStackTrace();
            }





            ////fragment dos locais
//        PlacesFragment formFragment = new PlacesFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.content_main, formFragment);
//        //transaction.addToBackStack(null);
//        transaction.commit();
            return null;
        }

//        @Override
//        protected void onPostExecute(String s){
//            Log.i("MainActivity", "onPostExecute");
//            progress.dismiss();
//            if(s!=null){
//                try{
//                    JSONObject json=new JSONObject(s);
////                    String user=json.getString("usuario");
////                    String password=json.getString("senha");
////
////
////                    Log.i("UserActivity", user);
////                    Log.i("PasswordActivity",password);
//
//
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
