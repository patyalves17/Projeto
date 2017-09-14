package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.projeto.patyalves.projeto.Util.DBHandler;
import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

//    private static final String TWITTER_KEY = "3TXPDtvCPOR4MQM9QrIKJLHdO";
//    private static final String TWITTER_SECRET = "6txlJmRbuSrKtvMZWM5p2DhvzsUJdxjG7Psof7wF50xVgz6m8L";

    @BindView(R.id.tilLogin) TextInputLayout tilLogin;
    @BindView(R.id.tilSenha) TextInputLayout tilSenha;
    @BindView(R.id.login_button) TwitterLoginButton loginButton;
    @BindView(R.id.cbKeep) CheckBox cbKeep;

    TwitterSession session;

    private DBHandlerP db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        db=new DBHandlerP(this);


        session = TwitterCore.getInstance().getSessionManager().getActiveSession();

//        Log.i("TwitterToken", String.valueOf(session));
        if(session!=null){
            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{  // It isn't from twitter =(
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
            String username = settings.getString("username", null);
            String password = settings.getString("password", null);

            Log.i("login","username: "+ username);
            Log.i("login","password: "+  password);
            if(username!=null && password!=null ){
                tilLogin.getEditText().setText(username);
                tilSenha.getEditText().setText(password);
                cbKeep.setChecked(true);
                doLogin();
            }


        }

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls

                loginButton.setVisibility(View.GONE);

                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.i("Twitter", "Something Wrong");
                Log.i("Twitter", exception.getMessage());
                Toast.makeText(getApplicationContext(),"Failure "+getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.btnLogin)
    public void doLogin(){

        Log.i("login","doLogin");
        String login = tilLogin.getEditText().getText().toString();
        String password = tilSenha.getEditText().getText().toString();

        if(!login.equals("") && !password.equals("") ){
            if(db.getLogin(new User(login,password))!=null){
                if (cbKeep.isChecked()){
                    Log.i("login","Is checked");
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", login).commit();
                    editor.putString("password", password).commit();
                    editor.commit();
                }

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else{
                tilLogin.getEditText().setError(getResources().getString(R.string.wrong_login));
                tilSenha.getEditText().setError(getResources().getString(R.string.wrong_password));
            }
        }
    }

    @OnClick(R.id.tvNewUser)
    public void newUser(){
        Log.i("NewUser","New User");
        startActivity(new Intent(LoginActivity.this, NewUserActivity.class));
       // finish();
    }


}
