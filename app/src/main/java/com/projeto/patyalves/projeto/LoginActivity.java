package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.projeto.patyalves.projeto.Util.DBHandler;
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

    private static final String TWITTER_KEY = "3TXPDtvCPOR4MQM9QrIKJLHdO";
    private static final String TWITTER_SECRET = "6txlJmRbuSrKtvMZWM5p2DhvzsUJdxjG7Psof7wF50xVgz6m8L";

    @BindView(R.id.tilLogin) TextInputLayout tilLogin;
    @BindView(R.id.tilSenha) TextInputLayout tilSenha;
    @BindView(R.id.login_button) TwitterLoginButton loginButton;
    TwitterSession session;

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        db=new DBHandler(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);

        startActivity(new Intent(this, MainActivity.class));
        finish();

      //  session = TwitterCore.getInstance().getSessionManager().getActiveSession();
       // TwitterCore.getInstance().getSessionManager().clearActiveSession();

       // Log.i("TwitterCore", TwitterCore.getInstance().getSessionManager().getActiveSession(). ),

        if(session!=null){
            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;

            Log.i("TwitterToken", String.valueOf(session));
            Log.i("TwitterToken", session.getUserName());
            Log.i("TwitterToken", String.valueOf(session.getUserId()));
            Log.i("TwitterToken", token);
            Log.i("TwitterToken", secret);

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }else{
           // TwitterCore.getInstance().getSessionManager().clearActiveSession();
        }

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.i("Twitter", "Thats OK");
               // TwitterSession session = result.data;
                //String name = session.getUserName();
//                Log.i("Twitter", String.valueOf(result));
//                Log.i("Twitter", result.data.getUserName());

                //loginButton.setVisibility(View.GONE);

//                Toast.makeText(getApplicationContext(),"Success "+getResources().getString(R.string.app_name),Toast.LENGTH_SHORT).show();


                session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                Log.i("Twitter", token);
                Log.i("Twitter", secret);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.i("Twitter", "Something Wrong");
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
    public void doLogin(View v){
        String login = tilLogin.getEditText().getText().toString();
        String password = tilSenha.getEditText().getText().toString();
        //Toast.makeText(v.getContext(),login + password,Toast.LENGTH_SHORT).show();

        if(!login.equals("") && !password.equals("") ){
            if(db.login(new User(login,password))){
                startActivity(new Intent(v.getContext(), MainActivity.class));
                finish();
            }else{
                // exemplo no textinput
                tilLogin.setError("usuário errado.");
                // exemplo no próprio edittext
                tilSenha.getEditText().setError("senha errada");
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
