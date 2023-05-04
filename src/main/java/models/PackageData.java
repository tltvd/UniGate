package models;
import java.io.Serializable;
import java.util.ArrayList;


public class PackageData implements Serializable {
    private String operationType;
    private User user;
    private Door door;
    private Log log;
    private Schedule schedule;
    private ArrayList<Log> logsArray;
    private ArrayList<Schedule> scheduleArray;
    private ArrayList<User> usersArray;
    private ArrayList<Door> doorArray;

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public PackageData(String operationType, Schedule schedule) {
        this.operationType = operationType;
        this.schedule = schedule;
    }

    public PackageData(ArrayList<Schedule> scheduleArray) {
        this.scheduleArray = scheduleArray;
    }

    public ArrayList<Schedule> getScheduleArray() {
        return scheduleArray;
    }

    public void setScheduleArray(ArrayList<Schedule> scheduleArray) {
        this.scheduleArray = scheduleArray;
    }

    public PackageData(ArrayList<User> usersArray, String s) {
        this.usersArray = usersArray;
    }
    public PackageData(User user) {
        this.user = user;
    }
    public PackageData(String operationType, User user) {
        this.operationType = operationType;
        this.user = user;
    }
    public PackageData(String operationType, Door door) {
        this.operationType = operationType;
        this.door = door;
    }
    public PackageData(String operationType) {  //get information about car or user
        this.operationType = operationType;
    }

    public PackageData(ArrayList<Door> doorArray,String s,String s2) {
        this.doorArray = doorArray;
    }

    public PackageData(String operationType, Log log) {
        this.operationType=operationType;
        this.log=log;
    }

    public PackageData(ArrayList<Log> logsArray, String s, String s2,String s3) {
        this.logsArray=logsArray;
    }


    public ArrayList<User> getUsersArray() {
        return usersArray;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public ArrayList<Door> getDoorsArray() {
        return doorArray;
    }

    public void setDoorsArray(ArrayList<Door> doorArray) {
        this.doorArray = doorArray;
    }

    public void setUsersArray(ArrayList<User> usersArray) {
        this.usersArray = usersArray;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public ArrayList<Log> getLogsArray() {
        return logsArray;
    }

    public void setLogsArray(ArrayList<Log> logsArray) {
        this.logsArray = logsArray;
    }

    public ArrayList<Door> getDoorArray() {
        return doorArray;
    }

    public void setDoorArray(ArrayList<Door> doorArray) {
        this.doorArray = doorArray;
    }
}
