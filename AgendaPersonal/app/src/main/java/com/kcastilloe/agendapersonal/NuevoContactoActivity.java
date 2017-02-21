package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

public class NuevoContactoActivity extends AppCompatActivity {

    private EditText etNombreContacto, etTelefonoContacto, etDireccionContacto, etEmailContacto;
    private ImageView ivImagenContacto;
    private FloatingActionButton fabFoto;
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
        ivImagenContacto = (ImageView) findViewById(R.id.ivImagenContacto);
        fabFoto = (FloatingActionButton) findViewById(R.id.fabFoto);

        fabFoto.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(intentCamara, 0);
               }
           }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ivImagenContacto.setImageBitmap(bitmap);
    }

    public void crearNuevoContacto(View view) {
        String nombreContacto = etNombreContacto.getText().toString();
        String telefonoContacto = etTelefonoContacto.getText().toString();
        String direccionContacto = etDireccionContacto.getText().toString();
        String emailContacto = etEmailContacto.getText().toString();
        byte[] fotoContacto = new byte[0];
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
//                    } else {
//                        if (ivImagenContacto.getDrawable() == ) {
//
//                        }

                    } else {
                        ivImagenContacto.getDrawable()
                        nuevoContacto = new Contacto(nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
                        try {
                            gbd.agregarContacto(nuevoContacto);
                            gbd.listarContactos();
                            t = Toast.makeText(this, "Contacto creado con éxito.", Toast.LENGTH_LONG);
                            t.show();
                            Intent intentCambio = new Intent(this, MainActivity.class);
                            startActivity(intentCambio);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
