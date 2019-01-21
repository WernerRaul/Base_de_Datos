package com.example.raul.base_de_datos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.raul.base_de_datos.Utilidades.Utilidades;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ActivityCircuitoVaronesGrafica extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuito_varones_grafica);
            pieChart1();
            pieChart2();
    }

    private void pieChart1() {
        PieChart pieChart = findViewById(R.id.pieChart);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE Sexo = 'Hombre' and Anciano = 'false'" +
                " and SiervoMinisterial = 'false'", null);
        cursor.moveToNext();
        Cursor cursor1 = db.rawQuery("SELECT COUNT(*) FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE Anciano = 'true'", null);
        cursor1.moveToNext();
        Cursor cursor2 = db.rawQuery("SELECT COUNT(*) FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE SiervoMinisterial = 'true'", null);
        cursor2.moveToNext();

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(Float.parseFloat(cursor.getString(0)), 0));
        yvalues.add(new Entry(Float.parseFloat(cursor1.getString(0)), 1));
        yvalues.add(new Entry(Float.parseFloat(cursor2.getString(0)), 2));

        db.close();

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Publicadores");
        xVals.add("Ancianos");
        xVals.add("Siervos Ministeriales");

        PieData data = new PieData(xVals, dataSet);

        pieChart.setData(data);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        /*OR
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        OR
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        OR
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        OR
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);*/

        pieChart.setDescription("Varones en el Circuito ICA-04");

    }

    private void pieChart2() {
        PieChart pieChart = findViewById(R.id.pieChart2);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "ICA-04", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE PrecRegular = 'false'", null);
        cursor.moveToNext();
        Cursor cursor1 = db.rawQuery("SELECT COUNT(*) FROM " + Utilidades.TABLA_PUBLICADORES +
                " WHERE PrecRegular = 'true'", null);
        cursor1.moveToNext();

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(Float.parseFloat(cursor.getString(0)), 0));
        yvalues.add(new Entry(Float.parseFloat(cursor1.getString(0)), 1));

        db.close();

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Publicadores");
        xVals.add("Precursores Regulares");

        PieData data = new PieData(xVals, dataSet);

        pieChart.setData(data);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        /*OR
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        OR
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        OR
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        OR
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);*/

        pieChart.setDescription("Precursores Regulares y Publicadores del Circuito ICA-04");

    }

}
