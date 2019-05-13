package com.example.ulises.controladoratermotanques;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {


    private Button B1;
    private ProgressBar progres;
    final Context context = this;
    ListView lista;

    final ArrayList<Integer> listaIdes = new ArrayList<Integer>();
    final ArrayList <ObjetoHTTP> listaEquipos = new ArrayList<ObjetoHTTP>();
    final ArrayList<Integer> datosImg = new ArrayList<Integer>();


    final ArrayList<String> s1 = new ArrayList<>();
    final ArrayList<String> s2 = new ArrayList<>();
    final ArrayList<String> s3 = new ArrayList<>();
    final ArrayList<String> s4 = new ArrayList<>();

    JSONObject  getPerson(int id, int tipo){
        JSONObject obj = new JSONObject();
        try {
            obj.put("ID", id);
            obj.put("tipo", tipo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        B1 = (Button)findViewById(R.id.button);

        progres = (ProgressBar)findViewById(R.id.progressBar);
        lista = (ListView) findViewById(R.id.lista);
        new Task1().execute("holaa");






        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    JSONArray employees = new JSONArray();

                    employees.put(getPerson(9862124,1));
                    employees.put(getPerson(8873563,1));
                employees.put(getPerson(331563,1));
                employees.put(getPerson(8873563,1));





                String json = employees.toString();

                final String eulaKey = "mykey";
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(eulaKey, json);
                editor.commit();


            Intent intent = new Intent(context,AddDevice.class);
             startActivity(intent);
            }
        });





        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalles = new Intent(view.getContext(), visorDatos.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("nombre",s3.get(position));
                miBundle.putString("ID",s1.get(position));
                visorDetalles.putExtras(miBundle);
                startActivity(visorDetalles);
            }
        });

    }



class Task1 extends AsyncTask <String, Void, String>
{
    @Override
    protected void onPreExecute() {
        progres.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

        SharedPreferences sharepref =  getPreferences(0);
        String dato = sharepref.getString("ID", "no hay dato");

        JSONArray jsonarray = null;
        try {
            jsonarray = new JSONArray(dato);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = jsonarray.getJSONObject(i);
                int name = jsonobject.getInt("ID");
                int url = jsonobject.getInt("tipo");
                String a = new String(String.valueOf(name));
                listaIdes.add(name);
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
                //////////////////////////////////////////////////////


        for(int x = 0; x<listaIdes.size(); x++) {
            int h = listaIdes.get(x);
            listaEquipos.add(new TermotanqueSolar(h, context));
            s1.add(new String(String.valueOf(listaIdes.get(x))));
        }

              //////////////////////////////////////////////////


        return strings[0];
    }
    @Override
    protected void onPostExecute(String s) {
        progres.setVisibility(View.INVISIBLE);
        for(int x = 0; x<listaEquipos.size();x++)
        {
            String tipo = String.valueOf(listaEquipos.size());
            //s1.add(x,new String(String.valueOf(listaEquipos.get(x).IDH)));
            s2.add(x,listaEquipos.get(x).Nombre());
            s3.add(x,listaEquipos.get(x).Url());
            s4.add(x,"conectado");
            datosImg.add(R.drawable.termo);
        }
         s2.add(1, "Termotanque02");
         s4.add(1, "desconectado");
        lista.setAdapter(new Adaptador(context, datosImg, s1, s2, s3, s4));
    }
}


}
