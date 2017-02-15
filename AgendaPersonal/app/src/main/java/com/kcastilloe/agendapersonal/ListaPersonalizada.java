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

    private Activity contexto;
    private ArrayList<Contacto> alContactos = new ArrayList();
    private TextView tvNombre, tvTelefono;
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
        //Creación de la vista – general
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vistaFila = inflater.inflate(idVistaElemento, parent, false);
        }

        /*LayoutInflater inflater = contexto.getLayoutInflater();
        View vistaFila = inflater.inflate(R.layout.item_lista_layout, null, true);*/



        //Creación de la vista – TextView
        tvNombre = (TextView) vistaFila.findViewById(R.id.tvNombreItem);
        tvNombre.setText(alContactos.get(position).getNombre());
        tvTelefono = (TextView) vistaFila.findViewById(R.id.tvTelefonoItem);
        tvNombre.setText(alContactos.get(position).getTelefono());

        //Creación de la vista – ImageView
        /*ImageView imagen = (ImageView) vistaFila.findViewById(R.id.imagen1);
        Drawable drawable = getContext().getResources().getDrawable(getContext().getResources().getIdentifier("@drawable/" + alContactos.get(position).getImagen(),null, getContext().getPackageName()));
        imagen.setImageDrawable(drawable);*/
        //Devolución de la vista
        return vistaFila;
    }
}
