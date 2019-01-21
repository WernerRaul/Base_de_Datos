package com.example.raul.base_de_datos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ActivityGraficaPublicador extends AppCompatActivity {

    ConexionSQLiteHelper conn;
    Bundle bolsa;

    TextView txtIdPublicador;
    TextView txtDireccion;
    TextView txtTelefono;
    TextView txtFechaNacimiento;
    TextView txtFechaBautismo;
    TextView txtAnciano;
    TextView txtSiervoMinisterial;
    TextView txtPrecRegular;
    TextView txtObservaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_publicador);

        bolsa=getIntent().getExtras(); //Habilitamos bolsa de la actividad anterior

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("tbl_PUBLICADORES INNER JOIN tbl_CONGREGACIONES ON " +
                "(tbl_PUBLICADORES.ID_Congregacion = tbl_CONGREGACIONES.ID_Congregacion AND " +
                "tbl_PUBLICADORES.Nombre = '" + bolsa.get("nombre") + "') INNER JOIN tbl_ACTIVIDAD ON " +
                "(tbl_ACTIVIDAD.ID_Publicador = tbl_PUBLICADORES.ID_Publicador AND " +
                "tbl_ACTIVIDAD.AñoMes >= date('now','start of month','-"+bolsa.getString("miMes")+" months'))");
        String[] sqlSelect = {
                "tbl_ACTIVIDAD.AñoMes",
                "tbl_ACTIVIDAD.Horas",
                "tbl_ACTIVIDAD.Revisitas",
                "tbl_ACTIVIDAD.Estudios"};
        String[] argumentos = {bolsa.getString("miCongregacion")};
        Cursor c = builder.query(db, sqlSelect, "tbl_CONGREGACIONES.Nombre = ?", argumentos, null, null, null);

        //Grafico////////////////////////////////////////
        LineChart lineChart = findViewById(R.id.idChart);

        ArrayList<Entry> mHoras = new ArrayList<>();
        ArrayList<Entry> mRevisitas = new ArrayList<>();
        ArrayList<Entry> mEstudios = new ArrayList<>();
        ArrayList<String> mMes = new ArrayList<>();

        int i=0;
        while (c.moveToNext()) {
            String cadena[] = {c.getString(0), c.getString(1), c.getString(2), c.getString(3)};
            mMes.add(cadena[0]);
            mHoras.add(new Entry(Float.parseFloat(cadena[1]), i));
            mRevisitas.add(new Entry(Float.parseFloat(cadena[2]), i));
            mEstudios.add(new Entry(Float.parseFloat(cadena[3]), i));
            i++;
        }
        c.close();

        //establecemos datos de axis y
        LineDataSet dataHoras = new LineDataSet(mHoras, "# Horas");
        LineDataSet dataRevisitas = new LineDataSet(mRevisitas, "# Revisitas");
        LineDataSet dataEstudios = new LineDataSet(mEstudios, "# Estudios");

        dataHoras.setDrawCubic(true);
        dataHoras.setDrawFilled(true);

        dataRevisitas.setDrawCubic(true);
        dataRevisitas.setDrawFilled(true);
        dataRevisitas.setFillColor(Color.YELLOW);
        dataRevisitas.setColor(Color.YELLOW);


        dataEstudios.setDrawCubic(true);
        dataEstudios.setDrawFilled(true);
        dataEstudios.setFillColor(Color.RED);
        dataEstudios.setColor(Color.RED);

        ArrayList<LineDataSet> lines = new ArrayList<>();
        lines.add(dataHoras); // add the datasets
        lines.add(dataRevisitas);
        lines.add(dataEstudios);

        LineData data = new LineData(mMes,lines);//, lines);

        lineChart.setData(data); // set the data and list of lables into chart<br />
        lineChart.animateY(5000);
        lineChart.setDescription("Description: "+bolsa.get("nombre"));  // set the description<br />


        lineChart.invalidate();

                txtIdPublicador = findViewById(R.id.txtIdPublicador);
                txtDireccion = findViewById(R.id.txtDireccion);
                txtTelefono = findViewById(R.id.txtTelefono);
                txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
                txtFechaBautismo = findViewById(R.id.txtFechaBautismo);
                txtAnciano = findViewById(R.id.txtAnciano);
                txtSiervoMinisterial = findViewById(R.id.txtSiervoMinisterial);
                txtPrecRegular = findViewById(R.id.txtPrecRegular);
                txtObservaciones = findViewById(R.id.txtObservaciones);

                SQLiteDatabase dbD = conn.getReadableDatabase();
                Cursor cD = dbD.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PUBLICADORES +
                        " WHERE "+ Utilidades.TABLA_PUBLICADORES + "." + Utilidades.CAMPO_Nombre_Publicador +
                        "= '" + bolsa.get("nombre") + "'", null);
                cD.moveToNext();

                //String bc = "ID_Publicador: " + String.valueOf(cD.getString(0));
                txtIdPublicador.setText("ID_Publicador: " + cD.getString(0));
                txtDireccion.setText("Dirección: " + cD.getString(2));
                txtTelefono.setText("Teléfono: " + cD.getString(4));
                txtFechaNacimiento.setText("Fecha de Nacimiento: " + cD.getString(5));
                txtFechaBautismo.setText("Fecha de Bautismo: " + cD.getString(6));
                txtAnciano.setText("Anciano: " + cD.getString(7));
                txtSiervoMinisterial.setText("Siervo Ministerial: " + cD.getString(8));
                txtPrecRegular.setText("Precursor Regular: " + cD.getString(9));
                txtObservaciones.setText("Observaciones: " + cD.getString(11));
                cD.close();

    }
}
