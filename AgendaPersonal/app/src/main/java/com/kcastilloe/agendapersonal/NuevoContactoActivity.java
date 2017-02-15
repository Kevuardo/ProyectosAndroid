package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

public class NuevoContactoActivity extends AppCompatActivity {

    private EditText etNombreContacto;
    private EditText etTelefonoContacto;
    private EditText etDireccionContacto;
    private EditText etEmailContacto;
    private Contacto nuevoContacto;
    private GestorBBDD gbd = new GestorBBDD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); /* Fuerza la posición a vertical. */
        etNombreContacto = (EditText) findViewById(R.id.etNombreContacto);
        etTelefonoContacto = (EditText) findViewById(R.id.etTelefonoContacto);
        etDireccionContacto = (EditText) findViewById(R.id.etDireccionContacto);
        etEmailContacto = (EditText) findViewById(R.id.etEmailContacto);
    }

    public void crearNuevoContacto(View view) {
        String nombreContacto = etNombreContacto.getText().toString();
        String telefonoContacto = etTelefonoContacto.getText().toString();
        String direccionContacto = etDireccionContacto.getText().toString();
        String emailContacto = etEmailContacto.getText().toString();
        Toast t;

        if (nombreContacto.compareToIgnoreCase("") == 0) {
            t = Toast.makeText(this, "Introduzca un nombre, por favor.", Toast.LENGTH_LONG);
            t.show();
        } else {
            if (telefonoContacto.compareToIgnoreCase("") == 0) {
                t = Toast.makeText(this, "Introduzca un teléfono, por favor.", Toast.LENGTH_LONG);
                t.show();
            } else {
                if (direccionContacto.compareToIgnoreCase("") == 0) {
                    t = Toast.makeText(this, "Introduzca una dirección, por favor.", Toast.LENGTH_LONG);
                    t.show();
                } else {
                    if (emailContacto.compareToIgnoreCase("") == 0) {
                        t = Toast.makeText(this, "Introduzca un e-mail, por favor.", Toast.LENGTH_LONG);
                        t.show();
                    } else {
                        nuevoContacto = new Contacto(nombreContacto, telefonoContacto, direccionContacto, emailContacto);
                        gbd.añadirContacto(nuevoContacto);
                        gbd.listarContactos();
                        t = Toast.makeText(this, "Contacto creado con éxito.", Toast.LENGTH_LONG);
                        t.show();
                        Intent intentCambio = new Intent(this, MainActivity.class);
                        startActivity(intentCambio);
                    }
                }
            }
        }
    }
}
