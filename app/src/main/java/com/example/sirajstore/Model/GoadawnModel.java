package com.example.sirajstore.Model;

public class GoadawnModel{

    String name,poriman,pushId,time;
    int buy;
    int motdam;

    public GoadawnModel() {
    }

    public GoadawnModel(String name, String poriman, int buy, int motdam) {
        this.name = name;
        this.poriman = poriman;
        this.buy = buy;
        this.motdam = motdam;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoriman() {
        return poriman;
    }

    public void setPoriman(String poriman) {
        this.poriman = poriman;
    }

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getMotdam() {
        return motdam;
    }

    public void setMotdam(int motdam) {
        this.motdam = motdam;
    }
}
