package com.ugb.COMPUTERS_GUEVARA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitylaptops extends AppCompatActivity {
    DbLaptops db_laptops;
    String accion="nuevo";
    String id="";
    Button btn;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_laptops();
            }
        });


        mostrar_datos_laptops();
    }
    void mostrar_datos_laptops(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if(accion.equals("modificar")){
            String laptops[] = parametros.getStringArray("Laptops");
            id = laptops[0];

            temp = findViewById(R.id.txtcodigo);
            temp.setText(laptops[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(laptops[2]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(laptops[3]);

            temp = findViewById(R.id.txtpresentacion);
            temp.setText(laptops[4]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(laptops[5]);
        }

    }
    void guardar_laptops(){
        try {
            temp = (TextView) findViewById(R.id.txtcodigo);
            String codigo = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpresentacion);
            String presentacion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();

            db_laptops = new DbLaptops(MainActivitylaptops.this, "",null,1);
            String result = db_laptops.administrar_laptops(id, codigo, descripcion, marca, presentacion,precio, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListalaptops();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar la omputadora laptops: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListalaptops(){
        Intent ilistalaptops = new Intent(MainActivitylaptops.this, LAPTOPS.class);
        startActivity(ilistalaptops);

    }
}