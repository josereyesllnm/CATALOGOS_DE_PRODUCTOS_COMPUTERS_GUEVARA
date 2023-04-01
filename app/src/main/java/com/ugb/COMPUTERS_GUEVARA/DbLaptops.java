package com.ugb.COMPUTERS_GUEVARA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//BASE DE DATOS PARA LA ADMINISTRACION DE LAS LAPTOPS.

public class  DbLaptops extends SQLiteOpenHelper {
    public static final String dbname = "db_laptops";
    public static final int v = 2;
    static final String sqlDblaptops = "CREATE TABLE laptops(idlaptops integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text, precio text)";

    public DbLaptops(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {sqLiteDatabase.execSQL(sqlDblaptops) ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//ADMINISTRAR
    public String administrar_laptops(String id, String cod, String des, String mar, String pres, String prec, String accion){
        try{
            SQLiteDatabase db = getWritableDatabase();
            if(accion.equals("nuevo")){
                db.execSQL("INSERT INTO laptops(codigo,descripcion,marca,presentacion,precio) VALUES('"+cod+"','"+des+"','"+mar+"','"+pres+"','"+prec+"')");

            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE laptops SET codigo='"+cod+"', descripcion='"+des+"', marca='"+mar+"', presentacion='"+pres+"',precio='"+prec+"' WHERE idlaptops='"+id+"'");

            } else if (accion.equals("eliminar")){
                db.execSQL("DELETE FROM laptops WHERE idlaptops='"+id+"'");

            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    //CONSULTAR
    public Cursor consultar_laptops(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM laptops ORDER BY codigo";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

}


