package com.kcastilloe.agendapersonal;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;
import com.kcastilloe.agendapersonal.persistencia.GestorBBDD;

import java.io.ByteArrayInputStream;

/**
 * La actividad usada para ver los detalles del contacto.
 *
 * @author Kevin Castillo Escudero
 */
public class DetalleContactoActivity extends AppCompatActivity {

    private GestorBBDD gbd;
    private EditText etNombreContactoGuardado, etTelefonoContactoGuardado, etDireccionContactoGuardado, etEmailContactoGuardado;
    private ImageView ivContactoAlmacenado;
    private Contacto contactoGuardado;
    private int idContacto = 0;
    private boolean primerContacto = false;
    private boolean ultimoContacto = false;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compartir_sms:
                compartirViaSMS(contactoGuardado);
                return true;
            case R.id.action_compartir_gmail:
                compartirViaGmail(contactoGuardado);
                return true;
            case R.id.action_compartir_whatsapp:
                compartirViaWhatsApp(contactoGuardado);
                return true;
            case R.id.action_compartir_twitter:
                compartirViaTwitter(contactoGuardado);
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
            case R.id.action_ubicar_localizar:
                ubicarContactoMaps(contactoGuardado);
                return true;
            case R.id.action_ubicar_navegar:
                navegarContactoMaps(contactoGuardado);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sirve para compartir el contacto a través de SMS.
     *
     * @param contactoGuardado El contacto del que se recogerán los datos a compartir.
     */
    private void compartirViaSMS(Contacto contactoGuardado) {
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setType("vnd.android-dir/mms-sms");
        intentSms.putExtra("sms_body", "¡Echa un vistazo a este  contacto en mi agenda! " +
                "\n\nNombre: " + contactoGuardado.getNombre() +
                ";\nTeléfono: " + contactoGuardado.getTelefono() +
                ";\nE-mail: " + contactoGuardado.getEmail());
        startActivity(intentSms);
    }

    /**
     * Sirve para compartir el contacto a través de Gmail.
     *
     * @param contactoGuardado El contacto del que se recogerán los datos a compartir.
     */
    private void compartirViaGmail(Contacto contactoGuardado) {
        try {
            PackageManager pm = getPackageManager();
            /* Evalúa si el paquete de Gmail existe, es decir, si está instalada. */
            PackageInfo info = pm.getPackageInfo("com.google.android.gm", PackageManager.GET_META_DATA);
            Intent intentEmail = new Intent(Intent.ACTION_SEND);
            intentEmail.setPackage("com.google.android.gm");
            intentEmail.setData(Uri.parse("kevincerbero@gmail.com"));
            intentEmail.setType("text/plain");
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Compartir contacto");
            intentEmail.putExtra(Intent.EXTRA_TEXT, "¡Echa un vistazo a este  contacto en mi agenda! " +
                    "\n\nNombre: " + contactoGuardado.getNombre() +
                    ";\nTeléfono: " + contactoGuardado.getTelefono() +
                    ";\nE-mail: " + contactoGuardado.getEmail());
            startActivity(intentEmail);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Gmail no está instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sirve para compartir el contacto a través de WhatsApp.
     *
     * @param contactoGuardado El contacto del que se recogerán los datos a compartir.
     */
    private void compartirViaWhatsApp(Contacto contactoGuardado) {
        try {
            PackageManager pm = getPackageManager();
            /* Evalúa si el paquete de WhatsApp existe, es decir, si está instalada. */
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            Intent intentWhatsApp = new Intent(Intent.ACTION_SEND);
            //intentWhatsApp.setType("image/*");
            intentWhatsApp.setType("text/plain");
            String textoMensaje = "¡Echa un vistazo a este  contacto en mi agenda!" +
                    "\n\nNombre: " + contactoGuardado.getNombre() +
                    ";\nTeléfono: " + contactoGuardado.getTelefono() +
                    ";\nE-mail: " + contactoGuardado.getEmail();
            intentWhatsApp.setPackage("com.whatsapp");

            intentWhatsApp.putExtra(Intent.EXTRA_TEXT, textoMensaje);
            startActivity(intentWhatsApp);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp no está instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sirve para compartir el contacto a través de Twitter.
     *
     * @param contactoGuardado El contacto del que se recogerán los datos a compartir.
     */
    private void compartirViaTwitter(Contacto contactoGuardado) {
        try {
            PackageManager pm = getPackageManager();
            /* Evalúa si el paquete de Twitter existe, es decir, si está instalada. */
            PackageInfo info = pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
            Intent intentTwitter = new Intent(Intent.ACTION_SEND);
            //intentTwitter.setType("image/*");
            intentTwitter.setType("text/plain");
            String textoMensaje = "¡Echa un vistazo a este  contacto en mi agenda!" +
                    "\n\nNombre: " + contactoGuardado.getNombre() +
                    ";\nTeléfono: " + contactoGuardado.getTelefono() +
                    ";\nE-mail: " + contactoGuardado.getEmail();
            intentTwitter.setPackage("com.twitter.android");

            intentTwitter.putExtra(Intent.EXTRA_TEXT, textoMensaje);
            startActivity(intentTwitter);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Twitter no está instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método encargado de actualizar los campos de muestra del detalle del contacto al navegar entre contactos.
     *
     * @param view El elemento desde el que se llama al método; en este caso un botón.
     */
    public void mostrarDatosContacto(View view) {
        /* Evalúa qué botón lo ha activado: siguiente o anterior.*/
        int idContactoActual = contactoGuardado.getId();
        if (view.getId() == R.id.btnContactoAnterior) {
            /* A continuación evalúa si el contacto es el primero de la lista. */
            try {
                primerContacto = gbd.evaluarPrimerContacto(idContactoActual);
                /* En caso afirmativo, carga los datos del último contacto. */
                if (primerContacto) {
                    try {
                        contactoGuardado = gbd.recuperarUltimoContacto();
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
                } else {
                    /* En caso negativo, recoge el anterior contacto. */
                    try {
                        contactoGuardado = gbd.recuperarAnteriorContacto(idContactoActual);
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
            } catch (Exception e) {
                Toast.makeText(this, "Se ha producido un error al listar los contactos.", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.btnContactoSiguiente) {
            /* A continuación evalúa si el contacto es el último de la lista. */
            try {
                ultimoContacto = gbd.evaluarUltimoContacto(idContactoActual);
                /* En caso afirmativo, carga los datos del primer contacto. */
                if (ultimoContacto) {
                    try {
                        contactoGuardado = gbd.recuperarPrimerContacto();
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
                } else {
                    /* En caso negativo, recoge el siguiente contacto. */
                    try {
                        contactoGuardado = gbd.recuperarSiguienteContacto(idContactoActual);
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
            } catch (Exception e) {
                Toast.makeText(this, "Se ha producido un error al listar los contactos.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Llama al contacto que se muestra actualmente en pantalla.
     *
     * @param view El elemento desde el que se llama al método; en este caso un botón.
     */
    public void llamarContactoDetalle(View view) {
        try {
            String telefono = contactoGuardado.getTelefono();
            Intent intentLlamada = new Intent(Intent.ACTION_CALL);
            intentLlamada.setData(Uri.parse(telefono));
            intentLlamada.setData(Uri.parse("tel:" + telefono));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intentLlamada);
        } catch (Exception e) {
            Toast.makeText(DetalleContactoActivity.this, "Ha ocurrido un error al llamar al contacto.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Ubica al contacto en Maps.
     *
     * @param contactoGuardado El contacto del que se recogerá la dirección a ubicar.
     */
    private void ubicarContactoMaps(Contacto contactoGuardado) {
        try {
            PackageManager pm = getPackageManager();
            /* Evalúa si el paquete de Maps existe, es decir, si está instalada. */
            PackageInfo info = pm.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_META_DATA);
            Intent intentMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/maps/place/" + contactoGuardado.getDireccion()));
            intentMaps.setPackage("com.google.android.apps.maps");
            startActivity(intentMaps);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Maps no está instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Ubica al contacto y abre la navegación en Maps.
     *
     * @param contactoGuardado El contacto del que se recogerá la dirección a ubicar.
     */
    private void navegarContactoMaps(Contacto contactoGuardado) {
        try {
            PackageManager pm = getPackageManager();
            /* Evalúa si el paquete de Maps existe, es decir, si está instalada. */
            PackageInfo info = pm.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_META_DATA);
            Intent intentMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + contactoGuardado.getDireccion()));
            intentMaps.setPackage("com.google.android.apps.maps");
            startActivity(intentMaps);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Maps no está instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
        }
    }
}
