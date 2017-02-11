package com.kcastilloe.pruebaprogressbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb1;
    TextView tvContador;
    EditText etBusqueda;
    String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        tvContador = (TextView) findViewById(R.id.tvContador);
        etBusqueda = (EditText) findViewById(R.id.etBusqueda);
        busqueda = etBusqueda.getText().toString();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void cargar(View v) {
        pb1.setVisibility(View.VISIBLE);
        if (isOnline()) {
            new URLReaderTaskInner().execute("http://www.pirateking.es"); /* Ejecuta la AsyncTask con la URL proporcionada.
                IMPORTANTE: para URLs es necesario poner el protocolo. */
        } else {
            Toast t = Toast.makeText(this, "SIN CONEXION", Toast.LENGTH_LONG);
            t.show();
        }
    }

    /* Clase interna al Activity. */
    /* El AsyncTask maneja 3 tipos de datos: un parámetro de entrada para el doInBackground, un parámetro
    para medir el progreso de la tarea, y un parámetro para el resultado devuelto por el doInBackground.

    Al ejecutar una tarea asíncrona hay que seguir 4 pasos, que son sus métodos. */
    public class URLReaderTaskInner extends AsyncTask<String, Integer, StringBuilder> {

        /* El primer paso. Permite modificar, o configurar, la interfaz de cara a, por ejemplo, un ProgressBar. */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb1.setVisibility(View.VISIBLE);
        }

        /* El segundo paso. Según el primero termina de ejecutarse, permite manejar un hilo ajeno al
        hilo principal de la app (el UI Thread [User Interface Thread]). Se encarga del proceso a ejecutar.

        Recibe los parámetros necesarios para el manejo de la AsyncTask. */
        @Override
        protected StringBuilder doInBackground(String... _url) {
            int contador = 0;
            int indice = 0; /* Para analizar las veces que aparece la palabra elegida. */
            HttpURLConnection urlConnection = null;
            StringBuilder sb = new StringBuilder();
            String linea;

            try {
                URL url = new URL(_url[0]); /* Crea un objeto URL con la URL proporcionada. */
                urlConnection = (HttpURLConnection) url.openConnection(); /* Abre la conexión con la URL proporcionada. */
                InputStream is = new BufferedInputStream(urlConnection.getInputStream()); /* Recoge el contexto de flujo de lectura de la URL. */
                BufferedReader br = new BufferedReader(new InputStreamReader(is)); /* Lee el contenido de la página. */
                while ((linea = br.readLine()) != null) {
                    sb.append(linea); /* Añade todas las líneas de la página al StringBuilder para nalizarlo después. */
                }

                /* javi */
                /*indice = sb.indexOf(busqueda);
                contador++;


                while (sb.indexOf(busqueda, indice + 1) != -1) {

                    indice ++;
                    indice = sb.indexOf(busqueda, indice + 1);
                    contador++;
                    publishProgress(contador++);
                    System.out.println("Estoy dentro");
                }

                System.out.println(contador);
                /* javi */

                indice = sb.indexOf(busqueda);
                contador = 1;
                while (sb.indexOf(busqueda, indice + 1) != -1){
                    indice = sb.indexOf(busqueda, indice + 1);
                    contador++;
                }
                System.out.println(contador);


                /*while (indice != -1) { /* Se evalúa con esta condición debido a que el método indexOf de StringBuilder devuelve valor -1 cuando se llega al final del documento. */
                    /*indice = sb.indexOf(busqueda, indice);
                    if(indice != -1){
                         /* El progreso se refleja por el método onProgressUpdate, que recibe como parámetro el contador. */
                        //indice += busqueda.length();
                   /*}
                }*/
            } catch (MalformedURLException e) {
                Log.e("TESTNET", "URL MAL FORMADA");
            } catch (IOException e) {
                Log.e("TESTNET", "IO ERROR");
            } finally {
                urlConnection.disconnect();
            }
            return sb;
        }

        /* El tercer paso. Evalúa el progreso de la tarea y lo muestra en un TextView. */
        @Override
        protected void onProgressUpdate(Integer... i){
            super.onProgressUpdate(i);
            tvContador.setText("Líneas leídas: " + i[0].toString());
        }

        /* El cuarto y último paso. Fuerza la invisibilidad del ProgressBar y recibe el resultado como parámetro. */
        @Override
        protected void onPostExecute(StringBuilder sb) {
            pb1.setVisibility(View.INVISIBLE);
        }
    }
}
