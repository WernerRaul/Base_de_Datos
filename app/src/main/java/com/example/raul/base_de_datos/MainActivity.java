package com.example.raul.base_de_datos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, "ICA-04",null,1);
    }


    public void onClickCongregaciones(View view) {
        Intent miIntent=new Intent(MainActivity.this,CongregacionesMainActivity.class);
        startActivity(miIntent);

    }

    public void onClickPublicadores(View view) {
        Intent miIntent=new Intent(MainActivity.this, PublicadoresMainActivity.class);
        startActivity(miIntent);
    }
}
