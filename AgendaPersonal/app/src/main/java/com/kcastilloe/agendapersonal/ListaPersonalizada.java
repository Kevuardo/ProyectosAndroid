package com.kcastilloe.agendapersonal;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcastilloe.agendapersonal.modelo.Contacto;

import java.util.ArrayList;

public class ListaPersonalizada extends ArrayAdapter<Contacto> {

    private ArrayList<Contacto> alContactos = new ArrayList();
    private TextView tvNombre, tvTelefono, tvEmail;
    private int idVistaElemento = 0;

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
        /*ImageView imagen = (ImageView) vistaFila.findViewById(R.id.imagen1);
        Drawable drawable = getContext().getResources().getDrawable(getContext().getResources().getIdentifier("@drawable/" + alContactos.get(position).getImagen(),null, getContext().getPackageName()));
        imagen.setImageDrawable(drawable);*/


        return vistaFila; /* Devuelve la vista personalizada de la fila. */
    }
}
