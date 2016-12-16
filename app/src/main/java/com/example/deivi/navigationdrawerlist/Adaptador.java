package com.example.deivi.navigationdrawerlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Deivi on 30/11/2016.
 */
public class Adaptador extends BaseAdapter {
    private ArrayList<Elemento> lista;
    private final Activity actividad;
    BDContactos bd;

    public Adaptador(Activity a, ArrayList<Elemento> v, BDContactos bd) {
        super();
        this.lista = v;
        this.actividad = a;
        this.bd = bd;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return lista.get(arg0).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = actividad.getLayoutInflater();
        View view = ly.inflate(R.layout.item, null, true);

        TextView tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvNombre.setText((CharSequence) lista.get(position).getNombre());

        return view;
    }
}
