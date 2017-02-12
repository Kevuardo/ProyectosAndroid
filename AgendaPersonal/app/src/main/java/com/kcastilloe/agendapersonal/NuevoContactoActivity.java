package com.kcastilloe.agendapersonal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class NuevoContactoActivity extends AppCompatActivity {

    private EditText etNombreContacto;
    private EditText etApellidosContacto;
    private EditText etTelefonoContacto;
    private EditText etDireccionContacto;
    private EditText etEmailContacto;


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

    }
}
