package com.example.ulises.controladoratermotanques;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public  class ObjetoHTTP {

    protected int IDH;
    protected int TIPO;
    protected String URL;
    protected String NOMBRE;


    public String BuscarPorID(final int id, final Context context) {


        for (int x = 1; x < 254; x++) {
            String num = "" + x;
            final String url = "http://192.168.1." + num + "/";


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
                                if (oid == id) {

                                    URL = url;
                                    NOMBRE = nombre;
                                    TIPO = tipo;
                                    IDH = oid;

                                }

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
                            //Toast.makeText(context, crudo, Toast.LENGTH_LONG).show();
                            requestQueue.stop();

                        }
                    }
            );

            // Add StringRequest to the RequestQueue
            requestQueue.add(stringRequest);
        }

        return "";

    }



    public void ActualizarEstado(Context context)
    {

    }


    public String Nombre() {
        return NOMBRE;
    }

    public int Tipo() {
        return TIPO;
    }

    public String Url() {
        return URL;
    }



    public int c(){return 0;}
    public String b(){return "";}

}

