package com.example.sirajstore.Model;

public class ProductModel {

    String name,poriman,buy,sell;

    String pushId;
    String time;


    public ProductModel() {
    }

    public ProductModel(String name, String poriman, String buy, String sell) {
        this.name = name;
        this.poriman = poriman;
        this.buy = buy;
        this.sell = sell;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
