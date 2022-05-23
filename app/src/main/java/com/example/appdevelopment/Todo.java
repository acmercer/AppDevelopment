package com.example.appdevelopment;

import java.io.Serializable;
import java.util.HashMap;

public class Todo implements Serializable {

    private String name;
    private String message;
    private String repeat;
    private String alertDate;
    private String alert;
    private String alertTime;
        //long id;

    public Todo(){

    }
    public Todo(String message, String repeat, String alert, String alertDate, String alertTime){
        //this.name = name;
        this.message = message;
        this.repeat = repeat;
        this.alert = alert;
        this.alertDate = alertDate;
        this.alertTime = alertTime;
    }
    public String getName() {
            return name;
        }

    public String getRepeat() {
        return repeat;
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setName(String name) {
            this.name = name;
        }
    public String getAlert(){
        return alert;
    }
    public void setAlert(String alert){
        this.alert = alert;
    }

    public String getAlertDate() {
    return alertDate;
}

    public void setAlertDate(String alertDate) {
    this.alertDate = alertDate;
}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public HashMap<String,String> toFirebaseObject() {
            HashMap<String,String> todo =  new HashMap<String,String>();
            todo.put("message", message);
            todo.put("alert", alert);
            todo.put("alertDate", alertDate);
            todo.put("alertTime", alertTime);
            todo.put("repeat", repeat);

            return todo;
        }

    }

