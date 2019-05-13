package com.example.ulises.controladoratermotanques;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDevice extends AppCompatActivity {

    private String URL;
    private String NOMBRE;
    private int TIPO;
    private int IDH;

        private Button Connectar;
        private Button Recordar;
        private  EditText et2;
        private EditText et1;
        private TextView tv1;
        private Button Buscar;
        private ImageButton ChekNombre;
        private ImageButton ChekPasword;
        private ImageButton Chekssid;
        private ImageButton ChekPass;
        private EditText et3;
        private EditText et4;

    private Context context = this;

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
        setContentView(R.layout.activity_add_device);

        Buscar = (Button) findViewById(R.id.button6);
        tv1 = (TextView) findViewById(R.id.texto01);
        et1 = (EditText) findViewById(R.id.editText8);
        et2 = (EditText) findViewById(R.id.editText9);
        ChekNombre = (ImageButton) findViewById(R.id.imageButton);
        ChekPasword = (ImageButton) findViewById(R.id.imageButton3);
        Recordar = (Button) findViewById(R.id.button7);
        et3 = (EditText) findViewById(R.id.editText10);
        et4 = (EditText) findViewById(R.id.editText12);
        Chekssid = (ImageButton) findViewById(R.id.imageButton4);
        ChekPass = (ImageButton) findViewById(R.id.imageButton5);
        Connectar = (Button) findViewById(R.id.button2);

        Connectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendInteger("123", 123);
            }
        });
        Chekssid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendString("ssid", et3.getText());
            }
        });
        ChekPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendString("pass", et4.getText());
            }
        });

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Actualizar();
            }
        });

        ChekNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              SendString("APssid", et1.getText());
            }
        });
        ChekPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable p = et2.getText();
                SendString("APpass", p);
                tv1.setText(p);
            }
        });
        Recordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> listaIds = new ArrayList<>();
                ArrayList<Integer> listaTipos = new ArrayList<>();


                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String language = settings.getString("mykey", "no salio");
                Toast.makeText(context, language, Toast.LENGTH_LONG).show();



                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(language);
                    //Toast.makeText(context, "hecho", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                try {
                    JSONObject jObjet = jsonarray.getJSONObject(0);
                    int id = jObjet.getInt("ID");
                    String a = new String(String.valueOf(id));
                    Toast.makeText(context, a, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                if(IDH != 0 && TIPO != 0) {
                    jsonarray.put(getPerson(IDH, TIPO));
                }

                String json = jsonarray.toString();


                final String eulaKey = "mykey";
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(eulaKey, json);
                editor.commit();

                /*
                for(int i = 0; i < jsonarray.length(); i++)
                {

                    try {
                        JSONObject jObjet = jsonarray.getJSONObject(i);
                        int id = jObjet.getInt("ID");
                        int tipo = jObjet.getInt("tipo");
                        String a = new String(String.valueOf(id));
                        Toast.makeText(context, a, Toast.LENGTH_LONG).show();
                        listaIds.add(id);
                        listaTipos.add(tipo);
                    } catch (JSONException e) {
                        Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }

                listaIds.add(IDH);
                listaTipos.add(TIPO);
/*

                JSONArray employees = new JSONArray();


                for(int x = 0; x < listaIds.size(); x++)
                {
                    employees.put(getPerson(listaIds.get(x), listaTipos.get(x)));
                }


                JSONObject response= new JSONObject();
                try {
                    response.put("IDs", employees );
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String json = employees.toString();
                Toast.makeText(context, json, Toast.LENGTH_LONG).show();

                SharedPreferences sharepref2 = getPreferences(context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref2.edit();
                try {
                    editor.putString("ID", json);
                    editor.commit();
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }


*/

            }
        });

    }

    public void Actualizar()
    {
        final String url = "http://192.168.4.1/";
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        try {
                            JSONObject reader = new JSONObject(response);
                            int oid = (int) reader.get("id");
                            String nombre = (String) reader.get("Nombre");
                            int tipo = (int) reader.get("Dispositivo");

                                URL = url;
                                NOMBRE = nombre;
                                TIPO = tipo;
                                IDH = oid;
                                switch (TIPO)
                                {
                                    case 1: tv1.setText("Termotanque Solar Encontrado!"); break;
                                    default: break;
                                }
                                et1.setText(NOMBRE);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        requestQueue.stop();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String crudo = error.toString();
                        Toast.makeText(context, "Dispositivo no encontrado. Intente nuevamente", Toast.LENGTH_LONG).show();
                        requestQueue.stop();

                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }

    public void SendString(String campo, Editable valor)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = "http://192.168.4.1/['" + campo + "':'" + valor + "']/";
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                        requestQueue.stop();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String crudo = error.toString();
                        Toast.makeText(context, "Dispositivo no encontrado. Intente nuevamente", Toast.LENGTH_LONG).show();
                        requestQueue.stop();

                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }
    public void SendInteger(String campo, int valor)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = "http://192.168.4.1/['" + campo + "':" + valor + "]/";
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                        requestQueue.stop();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String crudo = error.toString();
                        Toast.makeText(context, "Dispositivo no encontrado. Intente nuevamente", Toast.LENGTH_LONG).show();
                        requestQueue.stop();

                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);
    }
}
