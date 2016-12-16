package com.example.deivi.navigationdrawerlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deivi on 02/12/2016.
 */

public class BDContactos extends SQLiteOpenHelper implements Serializable{

    private int tamanioTablac, tamanioTablaf;
    private static Elemento elemento;
    private static final int VERSION_BASEDATOS = 1;
    private static final String NOMBRE_BASEDATOS = "BDContactos.bd";
    private static final String TABLA_CONTACTOS = "CREATE TABLE Contactos(\n" +
            "  idContacto int PRIMARY KEY,\n" +
            "  nombre VARCHAR(50)\n" +
            ")";

    private static final String NOMBRE_TABLAC = "Contactos";

    public BDContactos(Context context){
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLAC);
        onCreate(db);
    }


    //-------------------------------------------------CONTACTOS------------------------------------------------------------------------
    public long returnId(){
        long count=0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM "+NOMBRE_TABLAC, null);
            c.moveToFirst();
            count=Long.parseLong(c.getString(0));
        }
        db.close();
        return count+1;
    }

    public long borrarYordenar(int index){
        tamanioTablac=(int)returnId()-1;
        //returnId devuelve el count de la tabla contactos + 1, asi que aprovecho el metodo que ya esta hecho
        //no uses el returnId despues de abrir la db=getWritableDatabase, porque te dara error de que
        //esta intentando abrirla cuando ya esta abierta
        //tamanioTablac es un int que uso para no tener que usar el returnId en la condicion del for y que salte que la db ya esta abierta
        //tamanioTablac++ cuando añadas contactos, tamanioTablac-- cuando borres contactos
        //aun asi no deberia haber problema si lo actualias con returnId-1 antes de ordenar

        long nreg_afectados = -1;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            nreg_afectados = db.delete(NOMBRE_TABLAC, "idContacto=" + index, null);
            for(int i=index; i<tamanioTablac; i++) {
                db.execSQL("UPDATE " + NOMBRE_TABLAC + " SET idContacto=" + i + " WHERE idContacto=" + (i + 1));
            }
        }

        if(nreg_afectados>0)
            tamanioTablac=-(int)nreg_afectados;
        db.close();
        return nreg_afectados;
    }

    public long insertarContacto(Elemento e) {
        long nreg_afectados = -1;
        /* Abrimos la BD de Escritura */
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
        /* en este metodo utilizaremos ContentValues */
            ContentValues valores = new ContentValues();
            valores.put("nombre", e.getNombre());
            valores.put("idContacto", e.getId());
            //System.out.println("INSERTADO CONTACTO "+e.getId());
            nreg_afectados = db.insert(NOMBRE_TABLAC, null, valores);
            if(nreg_afectados!=-1)
                tamanioTablac++;
        }
        db.close();
        return nreg_afectados;
    }

    public List<Elemento> listado() {
        SQLiteDatabase db = getReadableDatabase();
        List<Elemento> lista_contactos = new ArrayList<Elemento>();
        if (db != null) {
            String[] campos = {"idContacto", "nombre"};
        /* Como queremos devolver todos los registros el tercer parámetro del query ( String selection ) es null */

            Cursor c = db.query(NOMBRE_TABLAC, campos, null, null, null, null, "nombre ASC", null);
            if (c.moveToFirst()) {
                do {
                    elemento = new Elemento(c.getLong(0), c.getString(1));
                    lista_contactos.add(elemento);
                } while (c.moveToNext());
            }
            c.close();
        }
        db.close();
        return lista_contactos;
    }
}
