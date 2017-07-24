package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        String login = tilLogin.getEditText().getText().toString();
        String password = tilSenha.getEditText().getText().toString();
        Toast.makeText(v.getContext(),login + password,Toast.LENGTH_SHORT).show();

        if(login.equals("paty")){
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
