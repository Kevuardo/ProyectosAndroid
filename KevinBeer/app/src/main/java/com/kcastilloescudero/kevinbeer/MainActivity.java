package com.kcastilloescudero.kevinbeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cambiarPantalla(View view) {
        SharedPreferences sp = getSharedPreferences("credenciales", MODE_PRIVATE);
        Toast t;

        /* El método contains devuelve un booleano: true si lo contiene, false si no. */
        if (sp.contains("nick")) {
            /* El método getString busca por campo ("nombre") y necesita un segundo parámetro que
            será el mensaje por defecto si no lo encuentra.*/
            t = Toast.makeText(this, sp.getString("nombre", "Sin nombre"), Toast.LENGTH_LONG);
            t.show();
            t = Toast.makeText(this, "Kevin Beer App", Toast.LENGTH_LONG);
            t.show();
            Intent intentCambio = new Intent(this, WelcomeActivity.class);
            startActivity(intentCambio);
        } else {
            t = Toast.makeText(this, "Kevin Beer App", Toast.LENGTH_LONG);
            t.show();
            Intent intentCambio = new Intent(this, WelcomeActivity.class);
            startActivity(intentCambio);
        }
    }
}
