package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvListaContactos; /* La lista de contactos actual. */
    private MenuItem botonBuscar;
    private MenuItem botonAjustes;
    private GestorBBDD gbd;
    private ListaPersonalizada adaptadorLista;
    private ArrayList<Contacto> alContactos = new ArrayList();

    /* Este método es llamado cuando se crea la actividad. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvListaContactos = (ListView) findViewById(R.id.lvListaContactos);
        //rellenarLista(lvListaContactos);
    }

    /* Este método es llamado cuando la actividad pasa a primer plano, incluyendo el inicio de la app. */
    @Override
    protected void onResume(){
        super.onResume();

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
                Intent intentCambio = new Intent(this, NuevoContactoActivity.class);
                startActivity(intentCambio);
                return true;
            case R.id.action_ajustes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Para rellenar la lista de contactos cada vez que se inicia la actividad. Contacta con la BD,
    recoge los datos necesarios, crea objetos Contacto para cada registro, y los muestra en los items.*/
    public void rellenarLista(ListView lista) {
        /*  */

        String nombreContacto = null;
        ArrayList<String> alNombres = new ArrayList();
        String telefonoContacto = null;
        ArrayList<String> alTelefonos = new ArrayList();
        int idContacto = 0;
        ArrayList<Integer> alId = new ArrayList();
        int idImagenContacto = 0;

        /* Se recogen los contactos en un ArrayList. */
        alContactos = gbd.listarContactos();

        for (Contacto nuevoContacto: alContactos) {
            nombreContacto = nuevoContacto.getNombre();
            alNombres.add(nombreContacto);
            telefonoContacto = nuevoContacto.getTelefono();
            alTelefonos.add(telefonoContacto);
            idContacto = nuevoContacto.getId();
            alId.add(idContacto);
        }

        adaptadorLista = new ListaPersonalizada(MainActivity.this, alNombres, alTelefonos, alId/*, idImagenContacto*/);
        lvListaContactos.setAdapter(adaptadorLista);
        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /* Llama al Intent para que cambie a la actividad que muestra el detalle del contacto.*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intentCambio = new Intent(this, DetalleContactoActivity.class);
                startActivity(intentCambio);*/
            }
        });
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
