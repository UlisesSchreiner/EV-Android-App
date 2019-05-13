package com.example.ulises.controladoratermotanques;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {


    private static LayoutInflater inflater = null;

    Context context;


    ArrayList<Integer>datosImg;
    ArrayList<String> s1;
    ArrayList<String> s2;
    ArrayList<String> s3;
    ArrayList<String> s4;

public Adaptador(Context context, ArrayList<Integer>imagenes, ArrayList<String>s1, ArrayList<String>s2, ArrayList<String>s3, ArrayList<String>s4)
{



    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;
    this.s4 = s4;

    this.context = context;

    this.datosImg = imagenes;
    inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
}

    @Override
    public int getCount() {
        return datosImg.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView titulo = (TextView) vista.findViewById(R.id.textView2);
        TextView duracion = (TextView) vista.findViewById(R.id.textView3);
        TextView director = (TextView) vista.findViewById(R.id.textView);
        TextView estConexion = (TextView) vista.findViewById(R.id.textView4);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView);


        titulo.setText(s1.get(i));
        director.setText(s2.get(i));
        duracion.setText(s3.get(i));
        estConexion.setText(s4.get(i));
        imagen.setImageResource(datosImg.get(i));


        /*
        titulo.setText(datos[i][0]);
        director.setText(datos[i][1]);
        duracion.setText(datos[i][2]);
        estConexion.setText(datos[i][3]);
        imagen.setImageResource(datosImg.get(i));
*/

        /*

        imagen.setTag(i);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visorImagen = new Intent(context, VisorImagen.class);
                visorImagen.putExtra("IMG", datosImg[(Integer)v.getTag()]);
                context.startActivity(visorImagen);
            }
        });

*/

        return vista;
    }
}
