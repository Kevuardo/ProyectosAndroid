package com.kcastilloescudero.kevinbeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kcastilloescudero.kevinbeer.modelo.CredencialesUsuario;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvBienvenido;
    private EditText etNick, etPassword, etNombre;
    private Button btnRegistrar;

    private String nick = null;
    private String password = null;
    private String nombre = null;

    CredencialesUsuario nuevasCredenciales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /* Se recogen los elementos de la pantalla, que se devuelven como tipo View y hay que hacer el cast al tipo pertinente. */
        tvBienvenido = (TextView) findViewById(R.id.tvBienvenido);
        etNick = (EditText) findViewById(R.id.etNick);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etNombre = (EditText) findViewById(R.id.etNombre);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
    }

    public void registrarUsuario(View view) {
        nick = etNick.getText().toString();
        password = etPassword.getText().toString();
        nombre = etNombre.getText().toString();
        Toast t;

        try {
            /* Crea un nuevo objeto con las credenciales recibidas. */
            nuevasCredenciales = new CredencialesUsuario(nick, password, nombre);
            t = Toast.makeText(this, "Credenciales corectas.\n¡Bienvenido, " + nick +"!", Toast.LENGTH_LONG);
            t.show();
            /* A continuación se almacenan las credenciales en un fichero XML en el dispositivo. */
            SharedPreferences sp = getSharedPreferences("credenciales", MODE_PRIVATE); /* Nombre de fichero y modo de acceso. Privado implica que sólo la app puede acceder, el resto no son recomendables. */
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("nick ", nick);
            editor.putString("password", password);
            editor.putString("nombre", nombre);
            editor.commit(); /* Importante hacer el commit SIEMPRE o pueden ocurrir cosas como borrado involuntario de datos.*/
            Intent intentCambio = new Intent(this, LoginActivity.class);
            startActivity(intentCambio);
        } catch (Exception ex) {
            t = Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
            t.show();
        }
    }
}
