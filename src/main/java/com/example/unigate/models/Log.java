package com.example.unigate.models;

import java.io.Serializable;

public class Log implements Serializable {

    private String id_access;
    private String id_user;
    private String room_id;
    private String access_time;
    private String granted;

    public Log() {
    }

    public Log(String id_access, String id_user, String room_id, String access_time, String granted) {
        this.id_access = id_access;
        this.id_user = id_user;
        this.room_id = room_id;
        this.access_time = access_time;
        this.granted = granted;
    }

    public Log(String room_id, String id_user, String access_time, String granted) {
        this.room_id = room_id;
        this.id_user = id_user;
        this.access_time = access_time;
        this.granted = granted;
    }

    public Log(int idRoom, String idUser, String str, String statusLogs) {

    }

    public String getId_access() {
        return id_access;
    }

    public void setId_access(String id_access) {
        this.id_access = id_access;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAccess_time() {
        return access_time;
    }

    public void setAccess_time(String access_time) {
        this.access_time = access_time;
    }

    public String getGranted() {
        return granted;
    }

    public void setGranted(String granted) {
        this.granted = granted;
    }
}
