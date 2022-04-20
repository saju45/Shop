package com.example.sirajstore.Model;

public class Data {

    private int amount;
    private String name;
    private String note;
    private String id;
    private String date;
    private long timestamp=0;

    public Data() {
    }

    public Data(int amount, String type, String note, String id, String date,long timestamp) {
        this.amount = amount;
        this.name = type;
        this.note = note;
        this.id = id;
        this.date = date;
        this.timestamp=timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return name;
    }

    public void setType(String type) {
        this.name = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
