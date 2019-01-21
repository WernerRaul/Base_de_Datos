package com.example.raul.base_de_datos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityConsultaActividadPublicadores extends AppCompatActivity {
    TextView lblCongregacionBolsa;
    Bundle bolsaRe;

    RadioButton rdbPublicyPrecdelaCong;
    RadioButton rdbPrecAuxAct;
    RadioButton rdbPrecRegAct;
    RadioButton rdbBajaAct;
    RadioButton rdbIrregulares;
    RadioButton rdbNoHacenRevisitas;
    RadioButton rdbNoCondEstBib;
    RadioButton rdbSiCondEstBib;
    RadioButton rdbAnalisisDeVarones;
    RadioButton rdbTarjetas;
    RadioButton rdbPromediosCongregacion;

    Bundle bolsa = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_actividad_publicadores);

        lblCongregacionBolsa = findViewById(R.id.lblCongregacionBolsa);

        bolsaRe=getIntent().getExtras();
        lblCongregacionBolsa.setText(bolsaRe.getString("miCongregacion"));

    }

    public void onClick(View view) {
            rdbPromediosCongregacion = findViewById(R.id.rdbPromediosCongregacion);
            rdbPublicyPrecdelaCong = findViewById(R.id.rdbPublicyPrecdelaCong);
            rdbPrecAuxAct = findViewById(R.id.rdbPrecAuxAct);
            rdbPrecRegAct = findViewById(R.id.rdbPrecRegAct);
            rdbBajaAct = findViewById(R.id.rdbBajaAct);
            rdbIrregulares = findViewById(R.id.rdbIrregulares);
            rdbNoHacenRevisitas = findViewById(R.id.rdbNoHacenRevisitas);
            rdbNoCondEstBib = findViewById(R.id.rdbNoCondEstBib);
            rdbSiCondEstBib = findViewById(R.id.rdbSiCondEstBib);
            rdbAnalisisDeVarones = findViewById(R.id.rdbAnalisisDeVarones);
            rdbTarjetas = findViewById(R.id.rdbTarjetas);

            //se pone en bolsa que radio button esta chequeado como true y otros como false
            bolsa.putBoolean("rdbPublicyPrecdelaCong",rdbPublicyPrecdelaCong.isChecked());
            bolsa.putBoolean("rdbPrecAuxAct",rdbPrecAuxAct.isChecked());
            bolsa.putBoolean("rdbPrecRegAct",rdbPrecRegAct.isChecked());
            bolsa.putBoolean("rdbBajaAct",rdbBajaAct.isChecked());
            bolsa.putBoolean("rdbIrregulares",rdbIrregulares.isChecked());
            bolsa.putBoolean("rdbNoHacenRevisitas",rdbNoHacenRevisitas.isChecked());
            bolsa.putBoolean("rdbNoCondEstBib",rdbNoCondEstBib.isChecked());
            bolsa.putBoolean("rdbSiCondEstBib",rdbSiCondEstBib.isChecked());
            bolsa.putBoolean("rdbAnalisisDeVarones",rdbAnalisisDeVarones.isChecked());
            bolsa.putString("miCongregacion",bolsaRe.getString("miCongregacion"));
            bolsa.putInt("miMes", bolsaRe.getInt("miMes"));

            //esto es para redirigir a qué Activity es la intencion de ir
            if (rdbTarjetas.isChecked()) {
                bolsa.putBoolean("rdbTarjetas", rdbTarjetas.isChecked());
                bolsa.putInt("miIdCongregacion", bolsaRe.getInt("miIdCongregacion"));
                bolsa.putInt("miMes", bolsaRe.getInt("miMes"));
                Intent miIntent = new Intent(ActivityConsultaActividadPublicadores.this, ActivityTarjetasGraficas.class);
                miIntent.putExtras(bolsa);
                startActivity(miIntent);
            } else if(rdbPromediosCongregacion.isChecked()) {
                bolsa.putInt("miIdCongregacion", bolsaRe.getInt("miIdCongregacion"));
                bolsa.putInt("miMes", bolsaRe.getInt("miMes"));
                Intent miIntent = new Intent(ActivityConsultaActividadPublicadores.this, ActivityConsultaPromediosCongregacion.class);
                miIntent.putExtras(bolsa);
                startActivity(miIntent);
            } else{
                Intent miIntent = new Intent(ActivityConsultaActividadPublicadores.this, ActivityConsultaFinal.class);
                miIntent.putExtras(bolsa);
                startActivity(miIntent);
            }
    }
}
