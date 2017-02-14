package com.kcastilloe.agendapersonal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaPersonalizada extends ArrayAdapter<String> {

    private Activity contexto;
    private ArrayList<String> alNombres = new ArrayList();
    private ArrayList<String> alTelefonos = new ArrayList();
    private ArrayList<Integer> alId = new ArrayList();
    //private final Integer[] imagenContacto;
    private TextView tvNombre, tvTelefono;

    public ListaPersonalizada (Activity contexto, ArrayList nombresContacto, ArrayList telefonosContacto, ArrayList idsContacto /*, Integer[] imagenContacto*/) {
        super(contexto, R.layout.item_lista_layout, nombresContacto);
        this.contexto = contexto;
        this.alNombres = nombresContacto;
        this.alTelefonos = telefonosContacto;
        this.alId = idsContacto;
        //this.imagenContacto = imagenContacto;
    }

    /* Recoge la vista en la que se ha de hacer el volcado de datos sobre la lista. */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View vistaFila = inflater.inflate(R.layout.item_lista_layout, null, true);

        tvNombre = (TextView) vistaFila.findViewById(R.id.tvNombreItem);
        tvTelefono = (TextView) vistaFila.findViewById(R.id.tvTelefonoItem);

        /*ImageView imageView = (ImageView) vistaFila.findViewById(R.id.img);
        tvNombre.setText("");

        imageView.setImageResource(imageId[position]);*/
        return vistaFila;
    }

}
