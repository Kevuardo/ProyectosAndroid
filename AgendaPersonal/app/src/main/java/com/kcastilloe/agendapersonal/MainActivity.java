package com.kcastilloe.agendapersonal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvListaContactos; /* La lista de contactos actual. */
    private TextView tvCabeceraContador;
    private ProgressBar pbProgresoCarga;
    private MenuItem botonBuscar, botonAjustes;
    private GestorBBDD gbd;
    private ListaPersonalizada adaptadorLista;
    private ArrayList<Contacto> alContactos = new ArrayList();

    /* Este método es llamado cuando se crea la actividad. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /* Fuerza la posición a vertical. */
        gbd = new GestorBBDD(this);

        tvCabeceraContador = (TextView) findViewById(R.id.tvCabeceraContador);
        lvListaContactos = (ListView) findViewById(R.id.lvListaContactos);

        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int posicion, long id) {
                String telefono = ((Contacto) av.getAdapter().getItem(posicion)).getTelefono();
                System.out.println(telefono);
            }
        });

        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /* Llama al Intent para que cambie a la actividad que muestra el detalle del contacto.*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Item pulsado: " + (position + 1));
                Toast t;
                t = Toast.makeText(MainActivity.this, "Contacto creado con éxito.", Toast.LENGTH_LONG);
                t.show();
                /*Intent intentCambio = new Intent(this, DetalleContactoActivity.class);
                startActivity(intentCambio);*/
            }
        });
    }

    /* Este método es llamado cuando la actividad pasa a primer plano, incluyendo el inicio de la app. */
    @Override
    protected void onResume() {
        super.onResume();
        rellenarLista();
        actualizarCabecera();
    }

    /* Cuando se cree el menú será con esta disposición. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Analiza cuándo se selecciona un item del ActionBar y la acción a realizar según el que se seleccione. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentCambio;
        switch (item.getItemId()) {
            case R.id.action_buscar:
                intentCambio= new Intent(this, NuevoContactoActivity.class);
                startActivity(intentCambio);
                return true;
            case R.id.action_ajustes:
                intentCambio = new Intent(this,DetalleContactoActivity.class);
                startActivity(intentCambio);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actualizarCabecera(){
        int contador = gbd.contarContactos();
        if (contador == 1) {
            tvCabeceraContador.setText(contador + " contacto");
        } else {
            tvCabeceraContador.setText(contador + " contactos");
        }
    }

    /* Para rellenar la lista de contactos cada vez que se inicia la actividad. Contacta con la BD,
    recoge los datos necesarios, crea objetos Contacto para cada registro, y los muestra en los items.*/
    private void rellenarLista() {
        /* Se recogen los contactos en un ArrayList. */

        alContactos.clear(); /* Se vacía el Arraylist para segurarse. */
        alContactos = gbd.listarContactos();
        adaptadorLista = new ListaPersonalizada(MainActivity.this, R.layout.item_lista_layout, alContactos);
        lvListaContactos.setAdapter(adaptadorLista);
        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /* Llama al Intent para que cambie a la actividad que muestra el detalle del contacto.*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Item pulsado: " + (position + 1));
                /*Toast t;
                t = Toast.makeText(view, "Contacto creado con éxito.", Toast.LENGTH_LONG);
                t.show();*/
                /*Intent intentCambio = new Intent(this, DetalleContactoActivity.class);
                startActivity(intentCambio);*/
            }
        });
    }

    /* Sirve para abrir la actividad necesaria para crear un nuevo contacto. */
    private void crearContacto(View view) {
        Intent intentCambio = new Intent(this, NuevoContactoActivity.class);
        startActivity(intentCambio);

        /* NOTA: PREGUNTAR A ALGUIEN QUE SEPA SI SE PUEDE EVALUAR CÓMO SE CIERRA UNA ACTIVIDAD,
        * PARA SABER SI SE PUEDE EVALUAR SI EL USUARIO LA CIERRA DANDO HACIA ATRÁS O GUARDANDO CONTACTO. */
    }

}
