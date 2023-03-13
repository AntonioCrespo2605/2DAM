package com.example.erp.dataBaseObjects;

public class Message {
    private String date;
    private String content;
    private boolean received;

    public Message(String date, String content, boolean received) {
        this.date = date;
        this.content = content;
        this.received = received;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
