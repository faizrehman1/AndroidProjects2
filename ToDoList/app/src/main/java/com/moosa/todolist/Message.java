package com.moosa.todolist;

/*
 * Created by Moosa on 5/6/2015.
 */
public class Message {
    private String title;
    private String sender;
    private boolean read;

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", sender='" + sender + '\'' +
                ", id="+
                ", read=" + read +
                '}';
    }

    public Message( String title, String sender, boolean read) {
        this.title = title;
        this.sender = sender;
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
