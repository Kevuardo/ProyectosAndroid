package com.kcastilloe.agendapersonal.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.kcastilloe.agendapersonal.modelo.Contacto;

import java.util.ArrayList;

public class GestorBBDD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; /* La versión de la BD. */
    private static final String DATABASE_NAME = "DBAgenda"; /* El nombre de la BD. */
    private static final String[] COLUMNAS = {"id_contacto", "nombre_contacto", "telefono_contacto", "direccion_contacto", "email_contacto"};
    private ArrayList<Contacto> alContactos = new ArrayList();

    public GestorBBDD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLA_CONTACTO = "create table contacto (id_contacto integer primary key autoincrement, " +
                "nombre_contacto text, telefono_contacto text, direccion_contacto text, email_contacto text)";
        db.execSQL(CREATE_TABLA_CONTACTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists books");
        this.onCreate(db);
    }

    /* Método para añadir contactos a la BD. El id no debe pasarse como parámetro porque es autoincremental. */
    public void añadirContacto(Contacto nuevoContacto) {
        ArrayList<Contacto> alBusquedaContactos = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_contacto", nuevoContacto.getNombre());
        values.put("telefono_contacto", nuevoContacto.getTelefono());
        values.put("direccion_contacto", nuevoContacto.getDireccion());
        values.put("email_contacto", nuevoContacto.getEmail());
        db.insert("contacto", null, values);
        db.close();
        System.out.println("Contacto creado con éxito.");
        alBusquedaContactos = listarContactos();

        for (int i = 0; i < alBusquedaContactos.size(); i++){
            System.out.println("\nRegistro " + (i + 1) + ": id =  " + alBusquedaContactos.get(i).getId() +
                    ", nombre = " + alBusquedaContactos.get(i).getNombre() + ", telefono = " +  alBusquedaContactos.get(i).getTelefono() +
                    ", dirección = " +  alBusquedaContactos.get(i).getDireccion() + ", email = " +  alBusquedaContactos.get(i).getEmail() + ".");
        }
    }

    /* Método usado para devolver los datos  de todos los contactos, y volcarlos en el ListView. */
    public ArrayList listarContactos(){
        int idContactoAlmacenado = 0;
        String nombreContactoAlmacenado = null;
        String telefonoContactoAlmacenado = null;
        String direccionContactoAlmacenado = null;
        String emailContactoAlmacenado = null;
        Contacto contactoAlmacenado;

        alContactos.clear(); /* Primero nos aseguramos de que está vacío para evitar que se dupliquen los items en el ListView. */
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto", null);
        if (cursor.moveToFirst()) {
            do {
                idContactoAlmacenado = cursor.getInt(0);
                nombreContactoAlmacenado = cursor.getString(1);
                telefonoContactoAlmacenado = cursor.getString(2);
                direccionContactoAlmacenado = cursor.getString(3);
                emailContactoAlmacenado = cursor.getString(4);
                contactoAlmacenado = new Contacto(idContactoAlmacenado, nombreContactoAlmacenado, telefonoContactoAlmacenado, direccionContactoAlmacenado, emailContactoAlmacenado);
                alContactos.add(contactoAlmacenado);
            } while (cursor.moveToNext());
        }
        return alContactos;
    }

    /* Método usado para devolver datos de un solo contacto, útil para ver el detalle de un único contacto. */
    public Contacto seleccionarContacto(int id) throws Exception {
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        Contacto contactoAlmacenado;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from contacto where id = ?", new String[] {String.valueOf(id)});
        cursor.moveToFirst();
        nombreContacto = cursor.getString(1);
        direccionContacto = cursor.getString(2);
        telefonoContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        contactoAlmacenado = new Contacto(nombreContacto, telefonoContacto, direccionContacto, emailContacto);
        return contactoAlmacenado;
    }
}
