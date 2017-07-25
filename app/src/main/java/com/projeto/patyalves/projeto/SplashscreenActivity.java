package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.projeto.patyalves.projeto.api.ApiUtils;
import com.projeto.patyalves.projeto.api.UserAPI;
import com.projeto.patyalves.projeto.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SplashscreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH=300;
    private ArrayList<User>users=new ArrayList<>();
    private UserAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        animation.reset();
        ImageView imageView= (ImageView) findViewById(R.id.ivSplash);

        if(imageView!=null){
            imageView.clearAnimation();
            imageView.startAnimation(animation);
        }

        mService = ApiUtils.getUserAPI();
        users= mService.getUsers();


//        SyncDatabase syncDatabase=new SyncDatabase();
//        syncDatabase.execute();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
//                SplashscreenActivity.this.finish();
//            }
//        },SPLASH_DISPLAY_LENGTH);

    }
//    private class SyncDatabase extends AsyncTask<String, Void, String>{
//        @Override
//        protected String doInBackground(String...params){
//            try{
//               // URL url=new URL("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
//                URL url=new URL("http://www.mocky.io/v2/5977bd381100004b11d89a6d");
//                HttpURLConnection connection =(HttpURLConnection)url.openConnection();
//
//                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Accept","application/json");
//                if(connection.getResponseCode()==200){
//                    BufferedReader stream=new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    String linha="";
//                    StringBuilder resposta=new StringBuilder();
//                    while((linha=stream.readLine())!=null){
//                        resposta.append(linha);
//                    }
//                    connection.disconnect();
//                    return resposta.toString();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }

//        @Override
//        protected void onPostExecute(String s){
//            if(s!=null){
//                try{
//                    JSONObject json=new JSONObject(s);
//                    JSONArray jsonArray=json.getJSONArray("ususarios");
//
//                    List<User> users=new ArrayList<User>();
//
//                    for(int i=0; i<jsonArray.length();i++){
//                        JSONObject user=(JSONObject) jsonArray.get(i);
//                        String userTemp=user.getString("usuario");
//                        String password=user.getString("senha");
//
//                        users.add(new User(userTemp,password));
//
//                        Log.i("User", userTemp);
//                        Log.i("Password",password);
//                    }
//
////                    String user=json.getString("usuario");
////                    String password=json.getString("senha");
////
////                    Log.i("User", user);
////                    Log.i("Password",password);
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


}
