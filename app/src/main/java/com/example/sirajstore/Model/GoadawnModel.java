package com.example.sirajstore.Model;

public class GoadawnModel{

    String name,dhoron,pushId,time;
    float kroymullo,motdam;
    float poriman;
    long timestamp;

    public GoadawnModel() {
    }

    public GoadawnModel(String name, String dhoron, String pushId, String time, float kroymullo, float motdam, float poriman, long timestamp) {
        this.name = name;
        this.dhoron = dhoron;
        this.pushId = pushId;
        this.time = time;
        this.kroymullo = kroymullo;
        this.motdam = motdam;
        this.poriman = poriman;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDhoron() {
        return dhoron;
    }

    public void setDhoron(String dhoron) {
        this.dhoron = dhoron;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getKroymullo() {
        return kroymullo;
    }

    public void setKroymullo(float kroymullo) {
        this.kroymullo = kroymullo;
    }

    public float getMotdam() {
        return motdam;
    }

    public void setMotdam(float motdam) {
        this.motdam = motdam;
    }

    public float getPoriman() {
        return poriman;
    }

    public void setPoriman(float poriman) {
        this.poriman = poriman;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
