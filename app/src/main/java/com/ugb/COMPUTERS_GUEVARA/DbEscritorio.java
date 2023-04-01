package com.ugb.COMPUTERS_GUEVARA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//BASE DE DATOS ESCRITORIO
public class DbEscritorio extends SQLiteOpenHelper {
    public static final String dbname = "db_escritorio";
    public static final int v = 2;
    static final String sqlDbescritorio = "CREATE TABLE escritorio(idescritorio integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text, precio text, escritorio text)";
    public DbEscritorio(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {sqLiteDatabase.execSQL(sqlDbescritorio) ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public String administrar_escritorio(String id, String cod, String des, String mar, String pres, String prec, String accion) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (accion.equals("nuevo1")) {
                db.execSQL("INSERT INTO escritorio(codigo,descripcion,marca,presentacion,precio) VALUES('" + cod + "','" + des + "','" + mar + "','" + pres + "','" + prec + "')");

            } else if (accion.equals("modificar2")) {
                db.execSQL("UPDATE escritorio SET codigo='" + cod + "', descripcion='" + des + "', marca='" + mar + "', presentacion='" + pres + "',precio='" + prec + "' WHERE idescritorio='" + id + "'");

            } else if (accion.equals("eliminar3")) {
                db.execSQL("DELETE FROM escritorio WHERE idescritorio='" + id + "'");

            }
            return "ok";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

    }
    public Cursor consultar_escritorio(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM escritorio ORDER BY codigo";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
}
