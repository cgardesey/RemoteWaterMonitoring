package com.remote.water.monitoring.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class RealmTank extends RealmObject {

    private int id;
    @PrimaryKey
    private String tankid;
    private String location;
    private String description;
    private String created_at;
    private String updated_at;

    public RealmTank() {

    }

    public RealmTank(String tankid, String location, String description, String created_at, String updated_at) {
        this.tankid = tankid;
        this.location = location;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
