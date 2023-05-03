package models;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

public class Schedule implements Serializable {
    private int id_schedule;
    private String id_user;
    private int room_id;
    private String day;
    private Time start_time;
    private Time end_time;

    private String access_description;

    private User user;

    public Schedule() {
    }

    public Schedule(int id_schedule, String id_user, int room_id, String day, Time start_time, Time end_time, String access_description) {
        this.id_schedule = id_schedule;
        this.id_user = id_user;
        this.room_id = room_id;
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.access_description = access_description;
    }

    public String getAccess_description() {
        return access_description;
    }

    public void setAccess_description(String access_description) {
        this.access_description = access_description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(int id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }


}
