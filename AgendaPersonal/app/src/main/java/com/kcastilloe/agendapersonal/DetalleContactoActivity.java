package com.kcastilloe.agendapersonal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

public class DetalleContactoActivity extends AppCompatActivity {

    private Toolbar tbBarraImagen;
    private CollapsingToolbarLayout ctblDisposicionToolbar;
    private GestorBBDD gbd;
    private EditText etNombreContactoGuardado, etTelefonoContactoGuardado, etDireccionContactoGuardado,etEmailContactoGuardado;
    private Contacto contactoGuardado;
    private int idContacto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);
        etNombreContactoGuardado = (EditText) findViewById(R.id.etNombreContactoGuardado);
        etTelefonoContactoGuardado = (EditText) findViewById(R.id.etTelefonoContactoGuardado);
        etDireccionContactoGuardado = (EditText) findViewById(R.id.etDireccionContactoGuardado);
        etEmailContactoGuardado = (EditText) findViewById(R.id.etEmailContactoGuardado);
        Intent intentApertura = getIntent();
        idContacto = intentApertura.getIntExtra("id", 1);
        gbd = new GestorBBDD(this);
        //initInstancesDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            contactoGuardado = gbd.seleccionarContacto(idContacto);
            etNombreContactoGuardado.setText(contactoGuardado.getNombre());
            etTelefonoContactoGuardado.setText(contactoGuardado.getTelefono());
            etDireccionContactoGuardado.setText(contactoGuardado.getDireccion());
            etEmailContactoGuardado.setText(contactoGuardado.getEmail());
        } catch (Exception e) {
            Toast t;
            t = Toast.makeText(DetalleContactoActivity.this, "No se ha podido acceder al contacto.", Toast.LENGTH_LONG);
            t.show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Analiza cuándo se selecciona un item del ActionBar y la acción a realizar según el que se seleccione. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast t;
        switch (item.getItemId()) {
            case R.id.action_compartir:
                t = Toast.makeText(DetalleContactoActivity.this, "Compartir contacto.", Toast.LENGTH_LONG);
                t.show();
                return true;
            case R.id.action_eliminar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Estás seguro?");
                builder.setTitle("Eliminar contacto");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            finish(); /* Mata la Activity*/
                            Toast t;
                            t = Toast.makeText(DetalleContactoActivity.this, "Se ha eliminado correctamente el contacto.", Toast.LENGTH_LONG);
                            t.show();
                        } catch (Exception e) {
                            Toast t;
                            t = Toast.makeText(DetalleContactoActivity.this, "Imposible borrar el contacto.", Toast.LENGTH_LONG);
                            t.show();
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
            case R.id.action_editar:
                t = Toast.makeText(DetalleContactoActivity.this, "Editar contacto.", Toast.LENGTH_LONG);
                t.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initInstancesDrawer() {

        tbBarraImagen = (Toolbar) findViewById(R.id.tbBarraImagen);
        setSupportActionBar(tbBarraImagen);

        ctblDisposicionToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctblDisposicionToolbar);
        ctblDisposicionToolbar.setTitle("Animated Toolbar");
    }
}
