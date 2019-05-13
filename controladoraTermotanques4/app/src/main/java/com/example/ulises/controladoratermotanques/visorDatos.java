package com.example.ulises.controladoratermotanques;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class visorDatos extends AppCompatActivity {

    private String HID;
    private String Url;
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


    EditText et7;
    EditText et6;
    EditText et5;
    EditText et4;
    EditText et3;
    EditText et2;
    EditText et1;
    ImageButton B1;
    ImageButton B2;
    TextView tv1;


    Switch sw1;
    Switch sw2;
    Switch sw3;
    Switch sw4;

    private static final String USERNAME = "jnaokrrg";
    private static final String PASSWORD = "aMHg3lfP3r6i";


    Context context = this;

    String clientId = MqttClient.generateClientId();


    RequestQueue requestQueue;

    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";
    TextView serverResp;
    ImageView serverImg;




    private static MqttConnectOptions setUpConnectionOptions(String username, String password) {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        return connOpts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_datos);

        Bundle miBundle = this.getIntent().getExtras();

        Url = miBundle.getString("nombre");
        HID = miBundle.getString("ID");

        et7 = (EditText) findViewById(R.id.editText7);
        et6 = (EditText) findViewById(R.id.editText6);
        et5 = (EditText) findViewById(R.id.editText5);
        et4 = (EditText) findViewById(R.id.editText4);
        et3 = (EditText) findViewById(R.id.editText3);
        et2 = (EditText) findViewById(R.id.editText2);
        et1 = (EditText) findViewById(R.id.editText);
        B1 = (ImageButton) findViewById(R.id.Button3);
        B2 = (ImageButton) findViewById(R.id.imageButton2);
        tv1 = (TextView) findViewById(R.id.textView5);


        sw1 = (Switch) findViewById(R.id.switch2);
        sw2 = (Switch) findViewById(R.id.switch3);
        sw3 = (Switch) findViewById(R.id.switch5);
        sw4 = (Switch) findViewById(R.id.switch6);

        ActualizarEstado();

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consulta();
                  //consulta2();



            }
        });




        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setear datos umbral 1
            }
        });


        sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Set("estadoUmbral2", 1);
                } else {
                    Set("estadoUmbral2", 2);
                }
            }
        });

        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Set("estadoUmbral1", 1);
                } else {
                    Set("estadoUmbral1", 2);
                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Set("estadoSupUmbrales", 1);
                } else {
                    Set("estadoSupUmbrales", 2);
                }
            }
        });

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Set("estadoSist", 1);
                } else {
                    Set("estadoSist", 2);
                }
            }
        });

    }

public void consulta2() {

    final String savedata= "{'hola':5}";
    String URL="http://192.168.1.19:3000/app";

    requestQueue = Volley.newRequestQueue(getApplicationContext());
    StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject objres=new JSONObject(response);
                Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

            }
            //Log.i("VOLLEY", response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {


            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            //Log.v("VOLLEY", error.toString());
        }
    })
    {
        @Override
        public String getBodyContentType() { return "application/json; charset=utf-8"; }

        @Override
        public byte[] getBody() throws AuthFailureError {
            try {
                return savedata == null ? null : savedata.getBytes("utf-8");
            } catch (UnsupportedEncodingException uee) {
                //Log.v("Unsupported Encoding while trying to get the bytes", data);
                return null;
            }
        }

    };
    requestQueue.add(postRequest);
}



    public void consulta()
    {
        String url = "http://192.168.1.19:3000/app";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "test");
                return params;
            }
        };

        queue.add(strRequest);

    }




    public void ParseJson(String res) throws JSONException {
        JSONObject reader = new JSONObject(res);
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

        String temperatra = String.valueOf(estadoTemperatura);
        tv1.setText(temperatra);
        String estadocalefactor;
        if(estCalefactor == 1){estadocalefactor = "Calefactor Encendido";}else {estadocalefactor = "Calefactor Apagado";}
        //tv2.setText(estadocalefactor);
        String temperaturaobjetivo = String.valueOf(tempObj);
        et1.setText(temperaturaobjetivo);
        String hmu1 = String.valueOf(HoraMinimaUmbral1);
        et3.setText(hmu1);
        String tou1 = String.valueOf(tempObjUmbral1);
        et2.setText(tou1);
        String hmu11 = String.valueOf(HoraMaximaUmbral1);
        et4.setText(hmu11);
        String tempou2 = String.valueOf(tempObjUmbral2);
        et5.setText(tempou2);
        String hoaminimaumbral2 = String.valueOf(HoraMinimaUmbral2);
        et6.setText(hoaminimaumbral2);
        String horamaximaubral2 = String.valueOf(HoraMaximaUmbral2);
        et7.setText(horamaximaubral2);
        if(estadoSist == 1){sw1.setChecked(true);}else {sw1.setChecked(false);}
        if(estadoSupUmbrales == 1){sw2.setChecked(true);}else {sw2.setChecked(false);}
        if(estadoUmbral1 == 1){sw3.setChecked(true);}else {sw3.setChecked(false);}
        if(estadoUmbral2 == 1){sw4.setChecked(true);}else {sw4.setChecked(false);}

    }





    public void ActualizarEstado()
    {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string


                        try {

                            ParseJson(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        requestQueue.stop();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Task1().execute("hola");
                        String crudo = error.toString();
                        //Toast.makeText(context, crudo, Toast.LENGTH_LONG).show();
                        requestQueue.stop();

                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);

    }


    public void Set(String parametro, int valor)
    {

        final String url = Url + "['" + parametro + "':" + valor + "]";
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Do something with response string

                        Toast.makeText(context, url, Toast.LENGTH_LONG).show();

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

    class Task1 extends AsyncTask<String, Void, String[]> {
        final MqttAndroidClient client =
                new MqttAndroidClient(context, "tcp://54.227.205.125:18129",
                        clientId);

        @Override
        protected void onPreExecute() {


            try {
                MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);
                IMqttToken token = client.connect(connOpts);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // We are connected
                        Toast.makeText(context, "no conecto", Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onSuccess");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Toast.makeText(context, "se conecto", Toast.LENGTH_LONG).show();
                        // Log.d(TAG, "onFailure");

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings;
        }

        @Override
        protected void onPostExecute(String[] s) {

            String topic = "TT/IN/" + HID;
            int qos = 1;
            try {
                IMqttToken subToken = client.subscribe(topic, qos);
                subToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // The message was published
                        Toast.makeText(context, "suses", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        Toast.makeText(context, "fallo", Toast.LENGTH_LONG).show();
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    try {
                        MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);
                        IMqttToken token = client.connect(connOpts);
                    }catch (MqttException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                  //  Toast.makeText(context, new String(message.getPayload()), Toast.LENGTH_LONG).show();
                    ParseJson(new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

/*
            butnPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topic = "TT/OUT/9862124";
                    String payload = "{\"estadoSist\":1}";
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }
                }
            });



            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topic = "TT/OUT/9862124";
                    String payload = "{\"estadoSist\":2}";
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }
                }
            });

*/
            //Toast.makeText(context,HID, Toast.LENGTH_LONG).show();

        }


    }


}
