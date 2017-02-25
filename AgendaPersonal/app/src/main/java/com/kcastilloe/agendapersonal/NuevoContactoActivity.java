package com.kcastilloe.agendapersonal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;

/**
 * La actividad usada para crear un nuevo contacto en la agenda.
 *
 * @author Kevin Castillo Escudero
 */
public class NuevoContactoActivity extends AppCompatActivity {

    private EditText etNombreContacto, etTelefonoContacto, etDireccionContacto, etEmailContacto;
    private ImageView ivImagenContacto;
    private FloatingActionButton fabFoto;
    private Contacto nuevoContacto;
    private GestorBBDD gbd = new GestorBBDD(this);
    private boolean fotoHecha = false;
    private final int REQUEST_FOTO = 1; /* Constante usada para evaluar que se abra la cámara y evitar que se produzcan interrupciones y errores. */

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
        /* Añade un ClickListener al FloatingActionButton. */
        fabFoto.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           try {
                                               Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        /* Evalúa si hay un componente que pueda ejecutar la acción. */
                                               if (intentCamara.resolveActivity(getPackageManager()) != null) {
                                                   startActivityForResult(intentCamara, REQUEST_FOTO);
                                               }
                                           } catch (Exception e) {
                                               Toast.makeText(NuevoContactoActivity.this, "No se ha podido abrir la cámara para hacer una foto al contacto.", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivImagenContacto.setImageBitmap(bitmap);
            fotoHecha = true; /* Evalúa que la foto esté hecha para la creación de un nuevo contacto. */
        }
    }

    /**
     * Método encargado de crear un nuevo registro en la BD con los datos que el usuario inserte en los campos.
     *
     * @param view El elemento desde el que se llama al método; en este caso un botón.
     */
    public void crearNuevoContacto(View view) {
        String nombreContacto = etNombreContacto.getText().toString();
        String telefonoContacto = etTelefonoContacto.getText().toString();
        String direccionContacto = etDireccionContacto.getText().toString();
        String emailContacto = etEmailContacto.getText().toString();
        byte[] fotoContacto = new byte[0];

        /* Evalúa que los TextView no sean nulos. */
        if (nombreContacto.compareToIgnoreCase("") == 0) {
            Toast.makeText(this, "Introduzca un nombre, por favor.", Toast.LENGTH_LONG).show();
        } else {
            if (telefonoContacto.compareToIgnoreCase("") == 0) {
                Toast.makeText(this, "Introduzca un teléfono, por favor.", Toast.LENGTH_LONG).show();
            } else {
                if (direccionContacto.compareToIgnoreCase("") == 0) {
                    Toast.makeText(this, "Introduzca una dirección, por favor.", Toast.LENGTH_LONG).show();
                } else {
                    if (emailContacto.compareToIgnoreCase("") == 0) {
                        Toast.makeText(this, "Introduzca un e-mail, por favor.", Toast.LENGTH_LONG).show();
                    } else {
                        if (!fotoHecha) {
                            Toast.makeText(this, "Haga una foto con la que guardar al contacto.", Toast.LENGTH_LONG).show();
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) ivImagenContacto.getDrawable()).getBitmap();
                            ByteArrayOutputStream bytesEscritura = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytesEscritura);
                            fotoContacto = bytesEscritura.toByteArray();
                            nuevoContacto = new Contacto(nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
                            try {
                                gbd.agregarContacto(nuevoContacto);
                                gbd.listarContactos();
                                Toast.makeText(this, "Contacto creado con éxito.", Toast.LENGTH_LONG).show();
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(this, "Se ha producido un error al crear el contacto.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        }
    }
}
