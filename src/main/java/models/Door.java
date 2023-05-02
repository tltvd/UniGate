package models;

import java.io.Serializable;

public class Door implements Serializable {


    private String id_room;
    private String name;
    private String location;
    private String ipv4;



    public Door() {
    }

    public Door(String id_room, String name, String location, String ipv4) {
        this.id_room = id_room;
        this.name = name;
        this.location = location;
        this.ipv4 = ipv4;
    }

    public String getId_room() {
        return id_room;
    }

    public void setId_room(String id_room) {
        this.id_room = id_room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
}
