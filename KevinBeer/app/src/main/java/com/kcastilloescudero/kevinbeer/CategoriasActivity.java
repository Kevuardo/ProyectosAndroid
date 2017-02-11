package com.kcastilloescudero.kevinbeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CategoriasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Intent recogidaDatos = getIntent();
        Toast t;
        t = Toast.makeText(this, recogidaDatos.getStringExtra("nombre"), Toast.LENGTH_LONG);
        t.show();
    }
}
