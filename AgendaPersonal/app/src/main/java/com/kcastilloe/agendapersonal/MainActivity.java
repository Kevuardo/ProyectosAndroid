package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvListaContactos; /* La lista de contactos actual. */
    private TextView tvCabeceraContador;
    private GestorBBDD gbd;
    private ListaPersonalizada adaptadorLista;
    private Contacto contactoAlmacenado;
    private ArrayList<Contacto> alContactos = new ArrayList();
    private Intent intentCambio;

    /* Este método es llamado cuando se crea la actividad. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /* Fuerza la posición a vertical. */
        gbd = new GestorBBDD(this);

        tvCabeceraContador = (TextView) findViewById(R.id.tvCabeceraContador);
        lvListaContactos = (ListView) findViewById(R.id.lvListaContactos);
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
        switch (item.getItemId()) {
            case R.id.action_agregar:
                return true;
            case R.id.action_ajustes:
                try {
                    alContactos = gbd.listarContactos();
                    contactoAlmacenado = alContactos.get(0);
                    intentCambio = new Intent(this, DetalleContactoActivity.class);
                    intentCambio.putExtra("id", contactoAlmacenado.getId());
                    startActivity(intentCambio);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Sirve para contar*/
    private void actualizarCabecera() {
        int contador = 0;
        try {
            contador = gbd.contarContactos();
            if (contador == 1) {
                tvCabeceraContador.setText(contador + " contacto");
            } else {
                tvCabeceraContador.setText(contador + " contactos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Para rellenar la lista de contactos cada vez que se inicia la actividad. Contacta con la BD,
    recoge los datos necesarios, crea objetos Contacto para cada registro, y los muestra en los items.*/
    private void rellenarLista() {
        /* Se recogen los contactos en un ArrayList. */

        alContactos.clear(); /* Se vacía el Arraylist para asegurarse. */
        try {
            alContactos = gbd.listarContactos();
            adaptadorLista = new ListaPersonalizada(MainActivity.this, R.layout.item_lista_layout, alContactos);
            lvListaContactos.setAdapter(adaptadorLista);

            lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /* Llama al Intent para que cambie a la actividad que muestra el detalle del contacto.*/
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        contactoAlmacenado = alContactos.get(position);
                        intentCambio = new Intent(MainActivity.this, DetalleContactoActivity.class);
                        intentCambio.putExtra("id", contactoAlmacenado.getId());
                        startActivity(intentCambio);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            lvListaContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Toast.makeText(MainActivity.this, "Pulsación larga en " + position, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Sirve para abrir la actividad necesaria para crear un nuevo contacto. */
    public void crearContacto(View view) {
        Intent intentCambio = new Intent(this, NuevoContactoActivity.class);
        startActivity(intentCambio);
    }

    /* Método para llamar al contacto seleccionado. */
    private void llamarContacto() {

    }
}
