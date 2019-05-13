package com.example.ulises.controladoratermotanques;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class TermotanqueSolar extends ObjetoHTTP {

    private int estadoTemperatura;
    private int estadoSist;
    private int tempObj;
    private int estadoSupUmbrales;
    private int estadoUmbral1;
    private int tempObjUmbral1;
    private int HoraMinimaUmbral1;
    private int HoraMaximaUmbral1;
    private int estadoUmbral2;
    private int tempObjUmbral2;
    private int HoraMinimaUmbral2;
    private int HoraMaximaUmbral2;
    private int estCalefactor;
    private int ZonaHoraria;


    TermotanqueSolar(int id_file, Context contex)
    {
        BuscarPorID(id_file, contex);
    }

    @Override
    public void ActualizarEstado(Context context)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string

                        try {
                            JSONObject reader = new JSONObject(response);
                            estadoTemperatura =  (int) reader.get("estadoTemperatura");
                            estadoSist = (int) reader.get("estadoSist");
                            tempObj = (int) reader.get("tempObj");
                            estadoSupUmbrales = (int) reader.get("estadoSupUmbrales");
                            estadoUmbral1 = (int) reader.get("estadoUmbral1");
                            tempObjUmbral1 = (int) reader.get("tempObjUmbral1");
                            HoraMinimaUmbral1 = (int) reader.get("HoraMinimaUmbral1");
                            HoraMaximaUmbral1 = (int) reader.get("HoraMaximaUmbral1");
                            estadoUmbral2 = (int) reader.get("estadoUmbral2");
                            tempObjUmbral2 = (int) reader.get("tempObjUmbral2");
                            HoraMinimaUmbral2 = (int) reader.get("HoraMinimaUmbral2");
                            HoraMaximaUmbral2 = (int) reader.get("HoraMaximaUmbral2");
                            estCalefactor = (int) reader.get("estCalefactor");
                            ZonaHoraria = (int) reader.get("ZonaHoraria");
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
    @Override
    public int c()
    {
        return ZonaHoraria;
    }


}
