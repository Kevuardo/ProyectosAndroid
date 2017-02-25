package com.kcastilloe.agendapersonal.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kcastilloe.agendapersonal.modelo.Contacto;

import java.util.ArrayList;

/**
 * Clase Java dedicada a las operaciones de gestión en Base de datos SQLite (inserción, modificación, borrado y consulta).
 *
 * @author Kevin Castillo Escudero
 */
public class GestorBBDD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3; /* La versión de la BD. */
    private static final String DATABASE_NAME = "DBAgenda"; /* El nombre de la BD. */
    private ArrayList<Contacto> alContactos = new ArrayList();

    /**
     * Constructor de la clase GestorBBDD.
     *
     * @param context El contexto actual.
     */
    public GestorBBDD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLA_CONTACTO = "create table contacto (id_contacto integer primary key autoincrement, " +
                "nombre_contacto text, telefono_contacto text, direccion_contacto text, email_contacto text, foto_contacto blob)";
        db.execSQL(CREATE_TABLA_CONTACTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists contacto");
        this.onCreate(db);
    }

    /**
     * Método para añadir contactos a la BD. El id no debe pasarse como parámetro porque es autoincremental.
     *
     * @param nuevoContacto El contacto a insertar en la BD.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public void agregarContacto(Contacto nuevoContacto) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_contacto", nuevoContacto.getNombre());
        values.put("telefono_contacto", nuevoContacto.getTelefono());
        values.put("direccion_contacto", nuevoContacto.getDireccion());
        values.put("email_contacto", nuevoContacto.getEmail());
        values.put("foto_contacto", nuevoContacto.getFoto());
        db.insert("contacto", null, values);
        db.close();
    }

    /**
     * Método usado para devolver los datos  de todos los contactos, y volcarlos en el ListView.
     *
     * @return ArrayList con todos los contactos almacenados para volcarlos posteriormente en la lista.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public ArrayList listarContactos() throws Exception {
        int idContactoAlmacenado = 0;
        String nombreContactoAlmacenado = null;
        String telefonoContactoAlmacenado = null;
        String direccionContactoAlmacenado = null;
        String emailContactoAlmacenado = null;
        byte[] fotoContactoAlmacenado;
        Contacto contactoAlmacenado;

        alContactos.clear(); /* Primero nos aseguramos de que está vacío para evitar que se dupliquen los items en el ListView. */
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto order by nombre_contacto", null); /* Se usa order by para ordenarlos alfabéticamente. */
        if (cursor.moveToFirst()) {
            do {
                /* Recoge los contactos uno a uno, y los guarda en un ArrayList. */
                idContactoAlmacenado = cursor.getInt(0);
                nombreContactoAlmacenado = cursor.getString(1);
                telefonoContactoAlmacenado = cursor.getString(2);
                direccionContactoAlmacenado = cursor.getString(3);
                emailContactoAlmacenado = cursor.getString(4);
                fotoContactoAlmacenado = cursor.getBlob(5);
                contactoAlmacenado = new Contacto(idContactoAlmacenado, nombreContactoAlmacenado, telefonoContactoAlmacenado, direccionContactoAlmacenado, emailContactoAlmacenado, fotoContactoAlmacenado);
                alContactos.add(contactoAlmacenado);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alContactos;
    }

    /**
     * Método usado para devolver datos de un solo contacto, útil para ver el detalle de un único contacto.
     *
     * @param id El id del contacto específico a consultar.
     * @return El contacto del que se verán los detalles.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public Contacto seleccionarContacto(int id) throws Exception {
        int idContacto = 0;
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        byte[] fotoContacto = new byte[0];
        Contacto contactoAlmacenado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto where id_contacto = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        idContacto = cursor.getInt(0);
        nombreContacto = cursor.getString(1);
        telefonoContacto = cursor.getString(2);
        direccionContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        fotoContacto = cursor.getBlob(5);
        contactoAlmacenado = new Contacto(idContacto, nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
        cursor.close();
        db.close();
        return contactoAlmacenado;
    }

    /**
     * Sirve para contar el número de contactos de la BD y volcarlos en el TextView de la cabecera del MainActivity.
     *
     * @return El contador de contactos almacenador en la BD.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public int contarContactos() throws Exception {
        int contador = 0;
        int idContactoAlmacenado = 0;
        String nombreContactoAlmacenado = null;
        String telefonoContactoAlmacenado = null;
        String direccionContactoAlmacenado = null;
        String emailContactoAlmacenado = null;
        byte[] fotoContactoAlmacenado = new byte[0];
        Contacto contactoAlmacenado;

        alContactos.clear(); /* Primero nos aseguramos de que está vacío para evitar que se dupliquen los items en el ListView. */
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto", null); /* Se usa order by para ordenarlos alfabéticamente. */
        if (cursor.moveToFirst()) {
            do {
                /* Recoge los contactos uno a uno, y los guarda en un ArrayList. */
                idContactoAlmacenado = cursor.getInt(0);
                nombreContactoAlmacenado = cursor.getString(1);
                telefonoContactoAlmacenado = cursor.getString(2);
                direccionContactoAlmacenado = cursor.getString(3);
                emailContactoAlmacenado = cursor.getString(4);
                fotoContactoAlmacenado = cursor.getBlob(5);
                contactoAlmacenado = new Contacto(idContactoAlmacenado, nombreContactoAlmacenado, telefonoContactoAlmacenado, direccionContactoAlmacenado, emailContactoAlmacenado, fotoContactoAlmacenado);
                alContactos.add(contactoAlmacenado);
            } while (cursor.moveToNext());
        }

        for (int i = 0; i < alContactos.size(); i++) {
            System.out.println("\nRegistro " + (i + 1) + ": id =  " + alContactos.get(i).getId() +
                    ", nombre = " + alContactos.get(i).getNombre() + ", telefono = " + alContactos.get(i).getTelefono() +
                    ", dirección = " + alContactos.get(i).getDireccion() + ", email = " + alContactos.get(i).getEmail() +
                    ", foto = " + alContactos.get(i).getFoto() + ".");
        }

        contador = cursor.getCount();
        cursor.close();
        db.close();
        return contador;
    }

    /**
     * Evalúa si el contacto actual es el primero para facilitar la muestra en DetalleContactoActivity.
     *
     * @param id El id del contacto a evaluar.
     * @return Booleano que evalúa si el contacto es el primer registro en la BD o no, y actuar consecuentemente.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public boolean evaluarPrimerContacto(int id) throws Exception {
        int idPrimerContacto = 0;
        /* Recibe como parámetro el id del contacto que se muestra actualmente. */
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select min(id_contacto) from contacto", null);
        cursor.moveToFirst();
        idPrimerContacto = cursor.getInt(0);
        if (idPrimerContacto == id) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Evalúa si el contacto actual es el último para facilitar la muestra en DetalleContactoActivity.
     *
     * @param id El id del contacto a evaluar.
     * @return Booleano que evalúa si el contacto es el último registro en la BD o no, y actuar consecuentemente.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public boolean evaluarUltimoContacto(int id) throws Exception {
        int idUltimoContacto = 0;
        /* Recibe como parámetro el id del contacto que se muestra actualmente. */
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(id_contacto) from contacto", null);
        cursor.moveToFirst();
        idUltimoContacto = cursor.getInt(0);
        if (idUltimoContacto == id) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Recupera el primer registro en la tabla.
     *
     * @return El contacto que consta como primer registro de la BD.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public Contacto recuperarPrimerContacto() throws Exception {
        int idContacto = 0;
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        byte[] fotoContacto = new byte[0];
        Contacto contactoAlmacenado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto where id_contacto = (select min(id_contacto) from contacto)", null);
        cursor.moveToFirst();
        idContacto = cursor.getInt(0);
        nombreContacto = cursor.getString(1);
        telefonoContacto = cursor.getString(2);
        direccionContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        fotoContacto = cursor.getBlob(5);
        contactoAlmacenado = new Contacto(idContacto, nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
        cursor.close();
        db.close();
        return contactoAlmacenado;
    }

    /**
     * Recupera el último registro en la tabla.
     *
     * @return El contacto que consta como último registro de la BD.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public Contacto recuperarUltimoContacto() throws Exception {
        int idContacto = 0;
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        byte[] fotoContacto = new byte[0];
        Contacto contactoAlmacenado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto where id_contacto = (select max(id_contacto) from contacto)", null);
        cursor.moveToFirst();
        idContacto = cursor.getInt(0);
        nombreContacto = cursor.getString(1);
        telefonoContacto = cursor.getString(2);
        direccionContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        fotoContacto = cursor.getBlob(5);
        contactoAlmacenado = new Contacto(idContacto, nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
        cursor.close();
        db.close();
        return contactoAlmacenado;
    }

    /**
     * Recupera el anterior registro en la tabla al que el usuario está consultando.
     *
     * @param id El id del contacto actual.
     * @return El contacto del que se verán los detalles.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public Contacto recuperarAnteriorContacto(int id) throws Exception {
        int idContacto = 0;
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        byte[] fotoContacto = new byte[0];
        Contacto contactoAlmacenado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto where id_contacto < ? order by id_contacto desc", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        idContacto = cursor.getInt(0);
        nombreContacto = cursor.getString(1);
        telefonoContacto = cursor.getString(2);
        direccionContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        fotoContacto = cursor.getBlob(5);
        contactoAlmacenado = new Contacto(idContacto, nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
        cursor.close();
        db.close();
        return contactoAlmacenado;
    }

    /**
     * Recupera el siguiente registro en la tabla al que el usuario está consultando.
     *
     * @param id El id del contacto actual.
     * @return El contacto del que se verán los detalles.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public Contacto recuperarSiguienteContacto(int id) throws Exception {
        int idContacto = 0;
        String nombreContacto = null;
        String telefonoContacto = null;
        String direccionContacto = null;
        String emailContacto = null;
        byte[] fotoContacto = new byte[0];
        Contacto contactoAlmacenado;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacto where id_contacto > ? order by id_contacto asc", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        idContacto = cursor.getInt(0);
        nombreContacto = cursor.getString(1);
        telefonoContacto = cursor.getString(2);
        direccionContacto = cursor.getString(3);
        emailContacto = cursor.getString(4);
        fotoContacto = cursor.getBlob(5);
        contactoAlmacenado = new Contacto(idContacto, nombreContacto, telefonoContacto, direccionContacto, emailContacto, fotoContacto);
        cursor.close();
        db.close();
        return contactoAlmacenado;
    }

    /**
     * Método usado para vaciar por completo la base de datos.
     *
     * @return Booleano para evaluar que la lista se vacíe.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public boolean eliminarTodosContactos() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("delete from contacto");
        db.close();
        return true;
    }

    /**
     * Sirve para eliminar un contacto de la BD según su ID.
     *
     * @param id El id del contacto desea eliminarse.
     * @return Booleano para evaluar que el contacto se elimine.
     * @throws Exception Excepción para evaluar que las operaciones de BD se ejecuten correctamente.
     */
    public boolean eliminarContacto(int id) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("contacto", "id_contacto = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }
}
