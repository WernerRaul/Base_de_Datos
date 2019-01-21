package com.example.raul.base_de_datos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PublicadoresMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicadores_main);
    }
    public void onClickConsultaActividad(View view) {
        Intent miIntent = new Intent(PublicadoresMainActivity.this ,ActivityConsultaActividad.class);
        startActivity(miIntent);
    }

    public void onClickConsultaActividadCircuito(View view) {
        Intent miIntent = new Intent(PublicadoresMainActivity.this ,ActivityCircuitoVaronesGrafica.class);
        startActivity(miIntent);
    }

}
