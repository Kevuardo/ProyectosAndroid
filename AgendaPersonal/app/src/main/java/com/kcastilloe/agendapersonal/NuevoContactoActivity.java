package com.kcastilloe.agendapersonal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;

public class NuevoContactoActivity extends AppCompatActivity {

    private EditText etNombreContacto;
    private EditText etApellidosContacto;
    private EditText etTelefonoContacto;
    private EditText etDireccionContacto;
    private EditText etEmailContacto;
    private Contacto nuevoContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        etNombreContacto = (EditText) findViewById(R.id.etNombreContacto);
        etApellidosContacto = (EditText) findViewById(R.id.etApellidosContacto);
        etTelefonoContacto = (EditText) findViewById(R.id.etTelefonoContacto);
        etDireccionContacto = (EditText) findViewById(R.id.etDireccionContacto);
        etEmailContacto = (EditText) findViewById(R.id.etEmailContacto);
    }

    private void crearNuevoContacto() {
        String nombreContacto = etNombreContacto.getText().toString();
        String apellidosContacto = etApellidosContacto.getText().toString();
        String telefonoContacto = etTelefonoContacto.getText().toString();
        String direccionContacto = etDireccionContacto.getText().toString();
        String emailContacto = etEmailContacto.getText().toString();
        Toast t;



        if (nombreContacto.compareToIgnoreCase("") == 0) {
            t = Toast.makeText(this, "Introduzca un nombre, por favor.", Toast.LENGTH_LONG);
            t.show();
        } else {
            if (apellidosContacto.compareToIgnoreCase("") == 0) {
                t = Toast.makeText(this, "Introduzca al menos un apellido, por favor.", Toast.LENGTH_LONG);
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
                            nuevoContacto = new Contacto(nombreContacto, apellidosContacto, telefonoContacto, direccionContacto, emailContacto);
                            t = Toast.makeText(this, "Contacto creado con éxito.", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                }
            }
        }
    }
}
