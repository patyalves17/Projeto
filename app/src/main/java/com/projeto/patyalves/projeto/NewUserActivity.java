package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.projeto.patyalves.projeto.Util.DBHandler;
import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewUserActivity extends AppCompatActivity {
    @BindView(R.id.tilLogin)
    TextInputLayout tilLogin;
    @BindView(R.id.tilSenha) TextInputLayout tilSenha;

    private DBHandlerP db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);
        db=new DBHandlerP(this);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
       // ((ActionBarActivity)getActivity()).getSupportActionBar().

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    //createAccount
    @OnClick(R.id.btnCreate)
    public void createAccount(View v){
        String login = tilLogin.getEditText().getText().toString();
        String password = tilSenha.getEditText().getText().toString();
        Log.i("User",login);
        Log.i("User",password);

        if(!login.equals("") && !password.equals("") ){


            User UserSearch=db.getUser(new User(login,password));
            if(UserSearch!=null){
                Log.i("carregaUser","existe");
                tilLogin.getEditText().setError(getResources().getString(R.string.exist));
            }else{
                Log.i("carregaUser","nao existe");
                long idSalve=db.createUser(new User(login,password));
                if(idSalve!=-1){
                    Toast.makeText(getApplicationContext(), R.string.successUser, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    this.finish();
                    Log.i("carregaUser",idSalve+" Salvo com sucesso.");
                }
            }

//            db.addOnce(new User(login,password));
//            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
//            startActivity(new Intent(NewUserActivity.this, LoginActivity.class));
//             finish();
        }

//        db.getAllUsers();

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
