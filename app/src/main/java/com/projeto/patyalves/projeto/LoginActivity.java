package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.tilLogin) TextInputLayout tilLogin;
    @BindView(R.id.tilSenha) TextInputLayout tilSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void doLogin(View v){
        Toast.makeText(this,tilLogin.getEditText().getText().toString(),Toast.LENGTH_SHORT).show();

        /*if(tilLogin.getEditText().getText().toString().equals("paty")){
            Intent intent=new Intent(this,MainActivity.class );
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Login ou senha incorretos",Toast.LENGTH_SHORT).show();

        }*/


    }
}
