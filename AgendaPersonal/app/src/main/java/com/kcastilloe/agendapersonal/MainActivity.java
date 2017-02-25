package com.kcastilloe.agendapersonal;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.util.ArrayList;

/**
 * La actividad usada para ver la lista de los contactos guardados.
 *
 * @author Kevin Castillo Escudero
 */
public class MainActivity extends AppCompatActivity {


    private ListView lvListaContactos; /* La lista de contactos actual. */
    private TextView tvCabeceraContador;
    private GestorBBDD gbd;
    private ListaPersonalizada adaptadorLista;
    private Contacto contactoAlmacenado;
    private ArrayList<Contacto> alContactos = new ArrayList();
    private Intent intentCambio;
    private boolean listaVacia = false;
    private int idItemLista = 0; /* El id del item sobre el que se abre el menú contextual. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /* Fuerza la posición a vertical. */
        gbd = new GestorBBDD(this);

        tvCabeceraContador = (TextView) findViewById(R.id.tvCabeceraContador);
        lvListaContactos = (ListView) findViewById(R.id.lvListaContactos);
        registerForContextMenu(lvListaContactos); /* Para añadir el menú contextual. */
    }

    @Override
    protected void onResume() {
        super.onResume();
        rellenarLista();
        actualizarCabecera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                AlertDialog.Builder builderInfo = new AlertDialog.Builder(this);
                builderInfo.setMessage("Creado por Kevin Castillo Escudero, 2017.\n\nContacto: kcastilloescudero@gmail.com");
                builderInfo.setTitle("Información de la app");
                builderInfo.setPositiveButton("Cerrar info", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialogInfo = builderInfo.create();
                dialogInfo.show();
                return true;
            case R.id.action_vaciar:
                AlertDialog.Builder builderVaciar = new AlertDialog.Builder(this);
                builderVaciar.setMessage("¿Eliminar todos los contactos de la agenda?");
                builderVaciar.setTitle("Vaciar agenda");
                builderVaciar.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            listaVacia = gbd.eliminarTodosContactos();
                            if (listaVacia) {
                                Toast.makeText(MainActivity.this, "Se han borrado todos los contactos con éxito.", Toast.LENGTH_SHORT).show();
                                rellenarLista();
                                actualizarCabecera();
                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Error al vaciar la lista de contactos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builderVaciar.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialogVaciar = builderVaciar.create();
                dialogVaciar.show();
                return true;
            case R.id.action_extra:
                AlertDialog.Builder builderExtra = new AlertDialog.Builder(this);
                builderExtra.setMessage("Próximamente disponible, ¡no desesperes! ;)");
                builderExtra.setTitle("¡Pero si está incompleto!");
                builderExtra.setPositiveButton("Cerrar info", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialogExtra = builderExtra.create();
                dialogExtra.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual_listview, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        idItemLista = info.position; /* Recoge el item de la lista en el que se ha llamado al menú contextual. */
        switch (item.getItemId()) {
            case R.id.action_context_llamar:
                /* Llama al contacto con el teléfono con el que está almacenado. */
                contactoAlmacenado = alContactos.get(idItemLista);
                llamarContacto(contactoAlmacenado.getTelefono());
                return true;
            case R.id.action_context_consultar:
                /* Abre la DetalleContactoActivity con el id del contacto almacenado. */
                try {
                    contactoAlmacenado = alContactos.get(idItemLista);
                    intentCambio = new Intent(MainActivity.this, DetalleContactoActivity.class);
                    intentCambio.putExtra("id", contactoAlmacenado.getId());
                    startActivity(intentCambio);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Se ha producido un error al tratar de acceder al detalle del contacto.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_context_compartir:
                /* Comparte el contacto por el método que se seleccione posteriormente. */
                contactoAlmacenado = alContactos.get(idItemLista);
                compartirContacto(contactoAlmacenado);
                return true;
            case R.id.action_context_eliminar:
                /* Elimina el contacto seleccionado. */
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Estás seguro?");
                builder.setTitle("Eliminar contacto");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            contactoAlmacenado = alContactos.get(idItemLista);
                            gbd.eliminarContacto(contactoAlmacenado.getId());
                            rellenarLista();
                            actualizarCabecera();
                            Toast.makeText(MainActivity.this, "Se ha eliminado correctamente el contacto.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Imposible borrar el contacto.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Sirve para contar los contactos almacenados en la BD y mostrar el contador al usuario.
     */
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
            Toast.makeText(this, "Se ha producido un error al actualizar la cabecera.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Para rellenar la lista de contactos cada vez que se inicia la actividad. Contacta con la BD,
     * recoge los datos necesarios, crea objetos Contacto para cada registro, y los muestra en los items.
     */
    private void rellenarLista() {
        /* Se recogen los contactos en un ArrayList. */
        alContactos.clear(); /* Se vacía el Arraylist para asegurarse. */
        try {
            alContactos = gbd.listarContactos();
            adaptadorLista = new ListaPersonalizada(MainActivity.this, R.layout.item_lista_layout, alContactos);
            lvListaContactos.setAdapter(adaptadorLista);

            /* El OnClickListener para los items del ListView. */
            lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /* Llama al Intent para que cambie a la actividad que muestra el detalle del contacto. */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        contactoAlmacenado = alContactos.get(position);
                        intentCambio = new Intent(MainActivity.this, DetalleContactoActivity.class);
                        intentCambio.putExtra("id", contactoAlmacenado.getId());
                        startActivity(intentCambio);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Se ha producido un error al tratar de acceder al detalle del contacto.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Se ha producido un error al listar los contactos.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sirve para abrir la actividad necesaria para crear un nuevo contacto.
     *
     * @param view El elemento desde el que se llama al método; en este caso un botón.
     */
    public void crearContacto(View view) {
        Intent intentCambio = new Intent(this, NuevoContactoActivity.class);
        startActivity(intentCambio);
    }

    /**
     * Método para llamar al contacto seleccionado.
     *
     * @param telefono El número de teléfono
     */
    private void llamarContacto(String telefono) {
        try {
            Intent intentLlamada = new Intent(Intent.ACTION_CALL);
            intentLlamada.setData(Uri.parse(telefono));
            intentLlamada.setData(Uri.parse("tel:" + telefono));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intentLlamada);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Ha ocurrido un error al llamar al contacto.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sirve para compartir el contacto por cualquier medio de los que se ofrecen con el Chooser.
     *
     * @param contactoAlmacenado El contacto del que se recogerán los datos a compartir.
     */
    private void compartirContacto(Contacto contactoAlmacenado) {
        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("text/plain");
        intentCompartir.putExtra(Intent.EXTRA_TEXT, "¡Echa un vistazo a este  contacto en mi agenda! " +
                "\n\nNombre: " + contactoAlmacenado.getNombre() +
                ";\nTeléfono: " + contactoAlmacenado.getTelefono() +
                ";\nE-mail: " + contactoAlmacenado.getEmail());
        startActivity(Intent.createChooser(intentCompartir, "Compartir con..."));
    }
}
