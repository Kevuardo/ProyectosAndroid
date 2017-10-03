package com.kcastilloe.pruebafirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kcastilloe.pruebafirebase.objetos.Persona;
import com.kcastilloe.pruebafirebase.objetos.ReferenciasFirebase;

public class MainActivity extends AppCompatActivity {

    private EditText etNombrePersona, etApellidosPersona, etDniPersona, etEdadPersona;
    private Button btnGuardarPersona;
    private Persona nuevaPersona;
    private DatabaseReference referenciaTutorial;
    private FirebaseDatabase bddPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNombrePersona = (EditText) findViewById(R.id.etNombrePersona);
        etApellidosPersona = (EditText) findViewById(R.id.etApellidosPersona);
        etDniPersona = (EditText) findViewById(R.id.etDniPersona);
        etEdadPersona = (EditText) findViewById(R.id.etEdadPersona);
        btnGuardarPersona = (Button) findViewById(R.id.btnGuardarPersona);

        btnGuardarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevaPersona();
            }
        });

        /* Nueva instancia de la base de datos Firebase en mi app. */
        bddPrueba = FirebaseDatabase.getInstance();

        /* Nueva referencia a mi base de datos de  Firebase. */
        referenciaTutorial = bddPrueba.getReference(ReferenciasFirebase.REFERENCIA_TUTORIAL); /* Si la referencia a la BDD no contiene ningún parámetro, recibirá todos los datos almacenados en la misma. */

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

    /**
     * Sirve para crear un nuevo objeto Persona en la BDD de Firebase.
     */
    private void crearNuevaPersona() {
        String nombrePersona = etNombrePersona.getText().toString();
        String apellidosPersona = etApellidosPersona.getText().toString();
        String dniPersona = etDniPersona.getText().toString();
        String edadPersona= etEdadPersona.getText().toString();

        /* Evalúa que los TextView no sean nulos. */
        if (nombrePersona.compareToIgnoreCase("") == 0) {
            Toast.makeText(this, "Introduzca un nombre, por favor.", Toast.LENGTH_LONG).show();
        } else {
            if (apellidosPersona.compareToIgnoreCase("") == 0) {
                Toast.makeText(this, "Introduzca unos apellidos, por favor.", Toast.LENGTH_LONG).show();
            } else {
                if (dniPersona.compareToIgnoreCase("") == 0) {
                    Toast.makeText(this, "Introduzca un DNI, por favor.", Toast.LENGTH_LONG).show();
                } else {
                    if (edadPersona.compareToIgnoreCase("") == 0) {
                        Toast.makeText(this, "Introduzca una edad, por favor.", Toast.LENGTH_LONG).show();
                    } else {
                        nuevaPersona = new Persona(nombrePersona, apellidosPersona, Integer.parseInt(edadPersona), dniPersona);
//                        referenciaTutorial.child("persona").setValue(nuevaPersona);
                        referenciaTutorial.push().setValue(nuevaPersona);
                    }
                }
            }
        }
    }
}
