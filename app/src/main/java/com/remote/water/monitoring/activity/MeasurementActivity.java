package com.remote.water.monitoring.activity;

import static com.remote.water.monitoring.constants.keyConst.MEASUREMENT_WS_URL;
import static com.remote.water.monitoring.receiver.NetworkReceiver.activeActivity;
import static com.remote.water.monitoring.util.Socket.EVENT_CLOSED;
import static com.remote.water.monitoring.util.Socket.EVENT_OPEN;
import static com.remote.water.monitoring.util.Socket.EVENT_RECONNECT_ATTEMPT;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.greysonparrelli.permiso.PermisoActivity;
import com.john.waveview.WaveView;
import com.remote.water.monitoring.R;
import com.remote.water.monitoring.realm.RealmMeasurement;
import com.remote.water.monitoring.util.RealmUtility;
import com.remote.water.monitoring.util.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by kai.wang on 6/17/14.
 */
public class MeasurementActivity extends PermisoActivity {

    private SeekBar seekBar;
    private WaveView waveView;
    private Socket measurementSocket;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        waveView = (WaveView) findViewById(R.id.wave_view);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        measurementSocket = Socket
                .Builder.with(MEASUREMENT_WS_URL)
                .build();
        measurementSocket.connect();

        measurementSocket.onEvent(EVENT_OPEN, new Socket.OnEventListener() {
            @Override
            public void onMessage(String event) {
                Log.d("mywebsocket3", "Connected");

                measurementSocket.join("measurement:595f806b-e400-473b-befa-8e82fb0f993a");

                measurementSocket.onEventResponse("measurement:595f806b-e400-473b-befa-8e82fb0f993a", new Socket.OnEventResponseListener() {
                    @Override
                    public void onMessage(String event, String data) {

                    }
                });

                measurementSocket.setMessageListener(new Socket.OnMessageListener() {
                    @Override
                    public void onMessage(String data) {
                        JSONObject jsonObject = null;
                        JSONObject jsonResponse = null;
                        String message = "";
                        try {
                            jsonObject = new JSONObject(data);
                            switch (jsonObject.getInt("t")) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    break;
                                case 6:
                                    break;
                                case 7:
                                    jsonResponse = jsonObject.getJSONObject("d");
                                    Log.d("mywebsocket3", jsonResponse.toString());

                                    if (jsonResponse.getJSONObject("data").has("tankid")) {
                                        Realm.init(activeActivity);
                                        JSONObject finalJsonResponse = jsonResponse;
                                        Realm.getInstance(RealmUtility.getDefaultConfig(MeasurementActivity.this)).executeTransaction(realm -> {
                                            RealmMeasurement realmMeasurement = null;
                                            try {
                                                realmMeasurement = realm.createOrUpdateObjectFromJson(RealmMeasurement.class, finalJsonResponse.getJSONObject("data"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            waveView.setProgress(realmMeasurement.getWaterlevel());
                                        });
                                    }
                                    break;
                                case 8:
                                    break;
                                case 9:
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                /*try {
                    JSONObject jsonData = new JSONObject()
                            .put(
                                    "guid", PreferenceManager.getDefaultSharedPreferences(homeactivity).getString(GUID, "")
                            );
                    if (isNetworkAvailable(homeactivity)) {

                        if (measurementSocket.getState() == Socket.State.OPEN) {
                            if (measurementSocket != null) {
                                measurementSocket.send("measurement:595f806b-e400-473b-befa-8e82fb0f993a", jsonData.toString());
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });

        measurementSocket.onEvent(EVENT_RECONNECT_ATTEMPT, new Socket.OnEventListener() {
            @Override
            public void onMessage(String event) {
                Log.d("mywebsocket3", "reconnecting");
            }
        });
        measurementSocket.onEvent(EVENT_CLOSED, new Socket.OnEventListener() {
            @Override
            public void onMessage(String event) {
                Log.d("mywebsocket3", "connection closed");
            }
        });

        Realm.init(getApplicationContext());
        Realm.getInstance(RealmUtility.getDefaultConfig(getApplicationContext())).executeTransaction(realm -> {
            RealmMeasurement realmMeasurement = realm.where(RealmMeasurement.class)
                    .sort("id", Sort.DESCENDING)
                    .findFirst();

            int progress = (int) ((float)realmMeasurement.getWaterlevel() / 300.0 * 100);
            waveView.setProgress(progress);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activeActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (measurementSocket != null) {
            measurementSocket.leave("measurement:595f806b-e400-473b-befa-8e82fb0f993a");
            measurementSocket.clearListeners();
            measurementSocket.close();
            measurementSocket.terminate();
            measurementSocket = null;
        }
    }
}