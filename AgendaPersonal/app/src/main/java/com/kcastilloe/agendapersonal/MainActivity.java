package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvListaContactos; /* La lista de contactos actual. */
    private MenuItem botonBuscar;
    private MenuItem botonAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvListaContactos = (ListView) findViewById(R.id.lvListaContactos);
        rellenarLista(lvListaContactos);
    }

    /* Cuando se cree el menú será con esta disposición. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar:

                return true;
            case R.id.action_ajustes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Para rellenar la lista de contactos cada vez que se inicia la actividad. */
    public void rellenarLista(ListView lista) {

    }

    /* Sirve para abrir la actividad necesaria para crear un nuevo contacto. */
    public void crearContacto(View view){
        boolean contactoCreado = false;

        Intent intentCambio = new Intent(this, NuevoContactoActivity.class);
        startActivity(intentCambio);
        /* La actividad de añadir un contacto devuelve un booleano para evaluar
        si el contacto se ha añadido o no. */


        /* NOTA: PREGUNTAR A ALGUIEN QUE SEPA SI SE PUEDE EVALUAR CÓMO SE CIERRA UNA ACTIVIDAD,
        * PARA SABER SI SE PUEDE EVALUAR SI EL USUARIO LA CIERRA DANDO HACIA ATRÁS O GUARDANDO CONTACTO. */
    }
}
