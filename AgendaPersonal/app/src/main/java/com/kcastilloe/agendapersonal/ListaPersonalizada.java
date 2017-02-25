package com.kcastilloe.agendapersonal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcastilloe.agendapersonal.modelo.Contacto;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Clase usada para aplicar un modelo sobre la lista en la que se ha de hacer el volcado de datos de los contactos.
 *
 * @author Kevin Castillo Escudero
 */
public class ListaPersonalizada extends ArrayAdapter<Contacto> {

    private ArrayList<Contacto> alContactos = new ArrayList();
    private TextView tvNombre, tvTelefono, tvEmail;
    private ImageView ivImagen;
    private int idVistaElemento = 0;

    /**
     * Constructor de ListaPersonalizada.
     *
     * @param context         El contexto actual.
     * @param idVistaElemento El id de la lista sobre la que se aplicará el modelo.
     * @param alContactos     La coleción de datos que se usará para rellenar los items de la lista.
     */
    public ListaPersonalizada(Context context, int idVistaElemento, ArrayList<Contacto> alContactos) {
        super(context, idVistaElemento, alContactos);
        this.idVistaElemento = idVistaElemento;
        this.alContactos = alContactos;
    }

    /* Recoge la vista en la que se ha de hacer el volcado de datos sobre la lista. */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaFila = convertView;
        if (vistaFila == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vistaFila = inflater.inflate(idVistaElemento, parent, false);
        }

        /* Carga del nombre del contacto. */
        tvNombre = (TextView) vistaFila.findViewById(R.id.tvNombreItem);
        tvNombre.setText(alContactos.get(position).getNombre());

        /* Carga del teléfono del contacto. */
        tvTelefono = (TextView) vistaFila.findViewById(R.id.tvTelefonoItem);
        tvTelefono.setText(alContactos.get(position).getTelefono());

        /* Carga del teléfono del contacto. */
        tvEmail = (TextView) vistaFila.findViewById(R.id.tvEmailItem);
        tvEmail.setText(alContactos.get(position).getEmail());

        /* Carga de la imagen del contacto. */
        ivImagen = (ImageView) vistaFila.findViewById(R.id.ivImagenItem);
        byte[] bytesFoto = alContactos.get(position).getFoto();
        ByteArrayInputStream bytesLectura = new ByteArrayInputStream(bytesFoto);
        Bitmap imagenContacto = BitmapFactory.decodeStream(bytesLectura);
        ivImagen.setImageBitmap(imagenContacto);

        return vistaFila; /* Devuelve la vista personalizada de la fila. */
    }
}
