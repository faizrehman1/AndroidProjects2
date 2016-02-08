package example.moosa.com.firebaseexampleme;

import java.io.Serializable;

/*
 * Created by Moosa on 5/19/2015.
 */
public class Message implements Serializable{
    private String message;
    private String name;

    public Message() {
    }

    public Message(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
