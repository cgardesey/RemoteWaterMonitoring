package com.remote.water.monitoring.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class RealmMeasurement extends RealmObject {

    private int id;
    @PrimaryKey
    private String measurementid;
    private int waterlevel;
    private String tankid;
    private String created_at;
    private String updated_at;

    public RealmMeasurement() {

    }

    public RealmMeasurement(String measurementid, int waterlevel, String tankid, String created_at, String updated_at) {
        this.measurementid = measurementid;
        this.waterlevel = waterlevel;
        this.tankid = tankid;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasurementid() {
        return measurementid;
    }

    public void setMeasurementid(String measurementid) {
        this.measurementid = measurementid;
    }

    public int getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(int waterlevel) {
        this.waterlevel = waterlevel;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
