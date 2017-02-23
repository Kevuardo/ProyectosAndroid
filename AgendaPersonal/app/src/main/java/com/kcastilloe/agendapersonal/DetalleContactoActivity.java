package com.kcastilloe.agendapersonal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.io.ByteArrayInputStream;

public class DetalleContactoActivity extends AppCompatActivity {

    private Toolbar tbBarraImagen;
    private CollapsingToolbarLayout ctblDisposicionToolbar;
    private GestorBBDD gbd;
    private EditText etNombreContactoGuardado, etTelefonoContactoGuardado, etDireccionContactoGuardado,etEmailContactoGuardado;
    private ImageView ivContactoAlmacenado;
    private Contacto contactoGuardado;
    private int idContacto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /* Fuerza la posición a vertical. */
        etNombreContactoGuardado = (EditText) findViewById(R.id.etNombreContactoGuardado);
        etTelefonoContactoGuardado = (EditText) findViewById(R.id.etTelefonoContactoGuardado);
        etDireccionContactoGuardado = (EditText) findViewById(R.id.etDireccionContactoGuardado);
        etEmailContactoGuardado = (EditText) findViewById(R.id.etEmailContactoGuardado);
        ivContactoAlmacenado = (ImageView) findViewById(R.id.ivContactoAlmacenado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intentApertura = getIntent();
        idContacto = intentApertura.getIntExtra("id", 1); /* Recoge el ID que le envía el Intent. */
        gbd = new GestorBBDD(this);
        try {
            contactoGuardado = gbd.seleccionarContacto(idContacto);
            etNombreContactoGuardado.setText(contactoGuardado.getNombre());
            etTelefonoContactoGuardado.setText(contactoGuardado.getTelefono());
            etDireccionContactoGuardado.setText(contactoGuardado.getDireccion());
            etEmailContactoGuardado.setText(contactoGuardado.getEmail());
            byte[] bytesFoto = contactoGuardado.getFoto();
            ByteArrayInputStream bytesLectura = new ByteArrayInputStream(bytesFoto);
            Bitmap imagenContacto = BitmapFactory.decodeStream(bytesLectura);
            ivContactoAlmacenado.setImageBitmap(imagenContacto);
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
        switch (item.getItemId()) {
            case R.id.action_compartir:
                Toast.makeText(DetalleContactoActivity.this, "Compartir contacto.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_eliminar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Estás seguro?");
                builder.setTitle("Eliminar contacto");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            gbd.eliminarContacto(contactoGuardado.getId());
                            finish(); /* Mata la Activity. */
                            Toast.makeText(DetalleContactoActivity.this, "Se ha eliminado correctamente el contacto.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(DetalleContactoActivity.this, "Imposible borrar el contacto.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DetalleContactoActivity.this, "Editar contacto.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarDatosContacto(View view){

    }
}
