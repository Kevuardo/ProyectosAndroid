package com.kcastilloe.pruebafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kcastilloe.pruebafirebase.objetos.ReferenciasFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Nueva instancia de la base de datos Firebase en mi app. */
        FirebaseDatabase bddPrueba = FirebaseDatabase.getInstance();

        /* Nueva referencia a mi base de datos de  Firebase. */
        DatabaseReference referenciaTutorial = bddPrueba.getReference(ReferenciasFirebase.REFERENCIA_TUTORIAL); /* Si la referencia a la BDD no contiene ningún parámetro, recibirá todos los datos almacenados en la misma. */
        referenciaTutorial.child(ReferenciasFirebase.REFERENCIA_PERSONA);
        /* Una vez creada la referencia, ya se pueden recoger datos de la BDD en función de dicha referencia.
        * Hay 2 formas: hacerlo cada vez que se produce un cambio en los datos, o tan solo 1 vez, cuando se quiera. */

//        /* Ejecución individual: */
//        referenciaBdd.addListenerForSingleValueEvent(new ValueEventListener() {
//            /* Cuando cambien los datos. */
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                /* DataSnapshot es la colección de datos recogida de la BDD, y puede ser 1 solo valor o una lista de valores. */
//                int valor = dataSnapshot.getValue(Integer.class);
//                Log.i("Valor: ", valor + "");
//            }
//
//            /* Cuando se cancele la referencia a la BDD por algún motivo, o se produzca un error en la BDD. */
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("Error en BDD: ", databaseError.getMessage());
//            }
//        });
//
//        /* Ejecución dinámica por cambio de datos. */
//        referenciaBdd.addValueEventListener(new ValueEventListener() {
//            /* Cuando cambien los datos. */
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                /* DataSnapshot es la colección de datos recogida de la BDD, y puede ser 1 solo valor o una lista de valores. */
//                int valor = dataSnapshot.getValue(Integer.class);
//                Log.i("Valor: ", valor + "");
//            }
//
//            /* Cuando se cancele la referencia a la BDD por algún motivo, o se produzca un error en la BDD. */
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("Error en BDD: ", databaseError.getMessage());
//            }
//        });
    }
}
