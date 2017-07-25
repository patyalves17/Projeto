package com.projeto.patyalves.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.goToLogin)
    public void goToLogin(View v){
            startActivity(new Intent(v.getContext(), LoginActivity.class));
            finish();
    }


    //
}
