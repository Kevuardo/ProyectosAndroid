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

public class LoginActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etNick, etPassword;
    private Button btnLogin;

    private String nick = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Se recogen los elementos de la pantalla, que se devuelven como tipo View y hay que hacer el cast al tipo pertinente. */
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        etNick = (EditText) findViewById(R.id.etNick);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void iniciarSesion(View view){
        nick = etNick.getText().toString();
        password = etPassword.getText().toString();
        Toast t;

        SharedPreferences sp = getSharedPreferences("credenciales", MODE_PRIVATE);
        if (nick.equals(sp.getString("nick", null)) && password.equals(sp.getString("password", null))) {
            t = Toast.makeText(this, "Bienvenido, " + nick, Toast.LENGTH_LONG);
            Intent intentCambio = new Intent(this, CategoriasActivity.class);
            intentCambio.putExtra("nombre", sp.getString("nombre", null));
            startActivity(intentCambio);
        } else {
            t = Toast.makeText(this, "Error de ingreso.\nIngrese de nuevo las credenciales.", Toast.LENGTH_LONG);
            etNick.setText("");
            etPassword.setText("");
        }
    }
}
