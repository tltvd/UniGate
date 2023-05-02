package network;

import DataBase.DatabaseHandler;
import DataBase.Const;
import models.Door;
import models.Log;
import models.PackageData;
import models.User;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private final Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }


    public void run (){
        try {
            DatabaseHandler db=new DatabaseHandler();
            db.getDbConnection();
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
            PackageData pd=null;
            try {
                while ((pd = (PackageData) inputStream.readObject()) != null) {
                    if (pd.getOperationType().equals("SIGN_UP")) {
                        User userFromClient = pd.getUser();
                        db.add(userFromClient);
                        System.out.println("----------A NEW USER WAS REGISTERED----------");
                    }
                    else if (pd.getOperationType().equals("UPDATE")) {
                        User userFromClient = pd.getUser();
                        db.update(userFromClient);
                    }
                    else if (pd.getOperationType().equals("SIGN_IN")) {
                        User user = new User();
                        ResultSet infoClient = db.getUser(pd.getUser());
                        while (infoClient.next()) {
                            user.setUsername(infoClient.getString(Const.USERS_USERNAME));
                            user.setPassword(infoClient.getString(Const.USERS_PASSWORD));
                            user.setRole(infoClient.getString(Const.USERS_ROLE));
                            user.setEmail(infoClient.getString(Const.USERS_EMAIL));
                            user.setFirst_name(infoClient.getString(Const.USERS_FIRSTNAME));
                            user.setLast_name(infoClient.getString(Const.USERS_LASTNAME));
                            user.setId_user(infoClient.getString(Const.USERS_ID));
                            user.setPhone(infoClient.getString(Const.USERS_PHONE));
                        }
                        PackageData data = new PackageData(user);
                        outputStream.writeObject(data);
                    }
                    else if (pd.getOperationType().equals("LIST_USERS")) {
                        ArrayList<User> users = new ArrayList<>();
                        ResultSet infoClient = db.getAllusers();
                        while (infoClient.next()) {
                            User user=new User();
                            user.setId_user(infoClient.getString(Const.USERS_ID));
                            user.setUsername(infoClient.getString(Const.USERS_USERNAME));
                            user.setPassword(infoClient.getString(Const.USERS_PASSWORD));
                            user.setFirst_name(infoClient.getString(Const.USERS_FIRSTNAME));
                            user.setLast_name(infoClient.getString(Const.USERS_LASTNAME));
                            user.setPhone(infoClient.getString(Const.USERS_PHONE));
                            user.setEmail(infoClient.getString(Const.USERS_EMAIL));
                            user.setGender(infoClient.getString(Const.USERS_GENDER));
                            user.setRole(infoClient.getString(Const.USERS_ROLE));
                            user.setBirthdate(infoClient.getString(Const.USERS_BIRTHDATE));
                            users.add(user);
                        }
                        String s="";
                        PackageData data = new PackageData(users,s);
                        outputStream.writeObject(data);
                    }
                    else if (pd.getOperationType().equals("LIST_DOORS")) {
                        ArrayList<Door> doors = new ArrayList<>();
                        ResultSet infoClient = db.getAlldoors();
                        while (infoClient.next()) {
                            Door door=new Door();
                            door.setLocation(infoClient.getString(Const.DOORS_LOCATION));
                            door.setName(infoClient.getString(Const.DOORS_NAME));
                            door.setId_room(infoClient.getString(Const.DOORS_ID));
                            door.setIpv4(infoClient.getString(Const.DOORS_IPV4));
                            doors.add(door);
                        }
                        String s="";
                        String s2="";
                        PackageData data = new PackageData(doors,s,s2);
                        outputStream.writeObject(data);
                    }
                    else if (pd.getOperationType().equals("LIST_LOGS")) {
                        ArrayList<Log> logs = new ArrayList<>();
                        ResultSet infoClient = db.getAllLogs();
                        while (infoClient.next()) {
                            Log log=new Log();
                            log.setGranted(infoClient.getString(Const.LOGS_GRANT));
                            log.setId_access(infoClient.getString(Const.LOGS_ID));
                            log.setAccess_time(infoClient.getString(Const.LOGS_TIME));
                            log.setRoom_id(infoClient.getString(Const.LOGS_IDROOM));
                            log.setId_user(infoClient.getString(Const.LOGS_IDUSER));
                            logs.add(log);
                        }
                        String s="";
                        String s2="";
                        String s3="";
                        PackageData data = new PackageData(logs,s,s2,s3);
                        outputStream.writeObject(data);
                    }
                    else if (pd.getOperationType().equals("DELETE_USER")) {
                        User userFromClient= pd.getUser();
                        db.Delete(userFromClient);
                    }
                    else if (pd.getOperationType().equals("ADD_LOG")) {
                        Log logFromClient = pd.getLog();
                        db.add_log(logFromClient);
                        System.out.println("----------A NEW LOG WAS ADDED----------");
                    }
                    else if (pd.getOperationType().equals("USERNAME_CHECK")) {
                        User user = new User();
                        ResultSet infoClient = db.UsernameCheck(pd.getUser());
                        while (infoClient.next()) {
                            user.setUsername(infoClient.getString(Const.USERS_USERNAME));
                        }
                        if(user.getUsername().isEmpty()){
                            user.setUsername("isempty");
                        }
                        PackageData data = new PackageData(user);
                        outputStream.writeObject(data);
                    }


                }
            }catch (EOFException ignored){
            }
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
