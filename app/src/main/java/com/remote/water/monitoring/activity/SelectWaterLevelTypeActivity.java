package com.remote.water.monitoring.activity;
import static com.remote.water.monitoring.constants.keyConst.API_URL;
import static com.remote.water.monitoring.constants.Const.isNetworkAvailable;
import static com.remote.water.monitoring.constants.Const.myVolleyError;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.greysonparrelli.permiso.PermisoActivity;
import com.remote.water.monitoring.R;
import com.remote.water.monitoring.other.InitApplication;
import com.remote.water.monitoring.realm.RealmMeasurement;
import com.remote.water.monitoring.util.RealmUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class SelectWaterLevelTypeActivity extends PermisoActivity {

    public static final int REQUEST_CODE_SET_DEFAULT_DIALER = 100;
    static public Context context;
    LinearLayout realtime, linechart;

    public static ImageView live_menu, recorded_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_water_level_type);

        context = SelectWaterLevelTypeActivity.this;

        realtime = findViewById(R.id.realtime);
        linechart = findViewById(R.id.linechart);

        realtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProgressDialog mProgress = new ProgressDialog(context);
                    mProgress.setCancelable(false);
                    mProgress.setIndeterminate(true);

                    mProgress.setTitle("Please wait...");
                    mProgress.show();
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.GET,
                            API_URL + "latest-measurement",
                            response -> {
                                mProgress.dismiss();
                                if (response != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Realm.init(context);
                                        Realm.getInstance(RealmUtility.getDefaultConfig(context)).executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.createOrUpdateObjectFromJson(RealmMeasurement.class, jsonObject);
                                                startActivity(new Intent(getApplicationContext(), MeasurementActivity.class));
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            error -> {
                                error.printStackTrace();
                                Log.d("Cyrilll", error.toString());
                                mProgress.dismiss();
                                myVolleyError(context, error);
                            }
                    ) {
                        @Override
                        public Map getHeaders() throws AuthFailureError {
                            HashMap headers = new HashMap();
                            headers.put("accept", "application/json");
                            headers.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(context).getString("APITOKEN", ""));
                            return headers;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    InitApplication.getInstance().addToRequestQueue(stringRequest);

                } catch (Exception e) {
                    Log.e("My error", e.toString());
                }

            }
        });

        linechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final int[] id = {-1};
                Realm.init(context);
                Realm.getInstance(RealmUtility.getDefaultConfig(context)).executeTransaction(realm -> {

                    RealmMeasurement realmMeasurement = realm.where(RealmMeasurement.class).sort("id", Sort.DESCENDING).findFirst();
                    if (realmMeasurement != null) {
                        id[0] = realmMeasurement.getId();
                    }
                });

                try {
                    ProgressDialog mProgress = new ProgressDialog(context);
                    mProgress.setCancelable(false);
                    mProgress.setIndeterminate(true);

                    mProgress.setTitle("Please wait...");
                    mProgress.show();
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.GET,
                            API_URL + "measurements",
                            response -> {
                                mProgress.dismiss();
                                if (response != null) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        Realm.init(context);
                                        Realm.getInstance(RealmUtility.getDefaultConfig(context)).executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.createOrUpdateAllFromJson(RealmMeasurement.class, jsonArray);
                                                startActivity(new Intent(getApplicationContext(), LineChartActivity2.class));
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            error -> {
                                error.printStackTrace();
                                Log.d("Cyrilll", error.toString());
                                mProgress.dismiss();
                                myVolleyError(context, error);
                            }
                    ) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", String.valueOf(id[0]));
                            return params;
                        }

                        @Override
                        public Map getHeaders() throws AuthFailureError {
                            HashMap headers = new HashMap();
                            headers.put("accept", "application/json");
                            headers.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(context).getString(APITOKEN, ""));
                            return headers;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    InitApplication.getInstance().addToRequestQueue(stringRequest);

                } catch (Exception e) {
                    Log.e("My error", e.toString());
                }*/
                if (isNetworkAvailable(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                } else {
                    Toast.makeText(SelectWaterLevelTypeActivity.this, "Connection error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
