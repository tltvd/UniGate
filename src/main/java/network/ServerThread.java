package network;

import com.example.unigate.DataBase.DatabaseHandler;
import com.example.unigate.DataBase.Const;
import com.example.unigate.models.*;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
            InetAddress clientAddress = socket.getInetAddress();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);


            DatabaseHandler db = new DatabaseHandler();
            db.getDbConnection();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            PackageData pd = null;
            try {
                while ((pd = (PackageData) inputStream.readObject()) != null) {
                    if (pd.getOperationType().equals("SIGN_UP")) {
                        User userFromClient = pd.getUser();
                        db.add(userFromClient);
                        System.out.println("[" + formattedDateTime + "] A NEW USER WAS REGISTERED from Client IP: " + clientAddress.getHostAddress());
                    } else if (pd.getOperationType().equals("UPDATE")) {
                        User userFromClient = pd.getUser();
                        db.update(userFromClient);

                    } else if (pd.getOperationType().equals("OPEN_DOOR_MOBILE")) {
                        User userFromClient = pd.getUser();
                        Door doorFromClient = pd.getDoor();
                        ArrayList<Schedule> schedules = new ArrayList<>();
                        ResultSet infoClient = db.getDostup(doorFromClient, userFromClient);

                        // Get the current time and day of the week
                        LocalTime currentTime = LocalTime.now();
                        DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();

                        while (infoClient.next()) {
                            String day = infoClient.getString(Const.SCHEDULE_day);
                            Time startTime = infoClient.getTime(Const.SCHEDULE_START_TIME);
                            Time endTime = infoClient.getTime(Const.SCHEDULE_END_TIME);

                            // Convert the time values to LocalTime
                            LocalTime startTimeLocal = startTime.toLocalTime();
                            LocalTime endTimeLocal = endTime.toLocalTime();

                            // Check if the current day and time match the schedule
                            if (day.equalsIgnoreCase(currentDayOfWeek.name()) &&
                                    currentTime.isAfter(startTimeLocal) && currentTime.isBefore(endTimeLocal)) {
                                Schedule schedule = new Schedule();
                                schedule.setId_user(infoClient.getString(Const.SCHEDULE_IDUSER));
                                schedule.setDay(day);
                                schedule.setStart_time(startTime);
                                schedule.setEnd_time(endTime);
                                schedule.setId_room(String.valueOf(infoClient.getInt(Const.SCHEDULE_IDROOM)));
                                schedule.setAccess_description(infoClient.getString(Const.SCHEDULE_DESCRIPTION));
                                schedules.add(schedule);
                            }
                        }

                        // Display the retrieved schedules
                        for (Schedule schedule : schedules) {
                            System.out.println("Schedule ID: " + schedule.getId_user());
                            System.out.println("Day: " + schedule.getDay());
                            System.out.println("Start Time: " + schedule.getStart_time());
                            System.out.println("End Time: " + schedule.getEnd_time());
                            System.out.println("Room ID: " + schedule.getId_room());
                            System.out.println("Access Description: " + schedule.getAccess_description());
                            System.out.println();
                        }

                        // Return the door through the stream
                        pd.setDoor(doorFromClient);
                        outputStream.writeObject(pd);
                    } else if (pd.getOperationType().equals("SIGN_IN_MOBILE")) {
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
                        System.out.println("[" + formattedDateTime + "] USER ID: " + user.getId_user() + " FullName: " + user.getFirst_name() + " " + user.getLast_name() + " WAS SIGN IN from Client IP: " + clientAddress.getHostAddress());
                    } else if (pd.getOperationType().equals("UPDATE_DOOR")) {
                        Door doorFromClient = pd.getDoor();
                        db.update(doorFromClient);
                    } else if (pd.getOperationType().equals("SIGN_IN")) {
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
                        System.out.println("[" + formattedDateTime + "] USER ID: " + user.getId_user() + " FullName: " + user.getFirst_name() + " " + user.getLast_name() + " WAS SIGN IN from Client IP: " + clientAddress.getHostAddress());
                    } else if (pd.getOperationType().equals("LIST_USERS")) {
                        ArrayList<User> users = new ArrayList<>();
                        ResultSet infoClient = db.getAllusers();
                        while (infoClient.next()) {
                            User user = new User();
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
                        String s = "";
                        PackageData data = new PackageData(users, s);
                        outputStream.writeObject(data);
                    } else if (pd.getOperationType().equals("LIST_DOORS")) {
                        ArrayList<Door> doors = new ArrayList<>();
                        ResultSet infoClient = db.getAlldoors();
                        while (infoClient.next()) {
                            Door door = new Door();
                            door.setLocation(infoClient.getString(Const.DOORS_LOCATION));
                            door.setName(infoClient.getString(Const.DOORS_NAME));
                            door.setId_room(infoClient.getString(Const.DOORS_ID));
                            door.setIpv4(infoClient.getString(Const.DOORS_IPV4));
                            doors.add(door);
                        }
                        String s = "";
                        String s2 = "";
                        PackageData data = new PackageData(doors, s, s2);
                        outputStream.writeObject(data);
                    } else if (pd.getOperationType().equals("LIST_SCHEDULE")) {
                        ArrayList<Schedule> schedules = new ArrayList<>();
                        ResultSet infoClient = db.getDoorSchedule(pd.getDoor());
                        while (infoClient.next()) {
                            Schedule schedule = new Schedule();
                            schedule.setAccess_description(infoClient.getString(Const.SCHEDULE_DESCRIPTION));
                            schedule.setId_schedule(infoClient.getInt(Const.SCHEDULE_ID));
                            schedule.setStart_time(infoClient.getTime(Const.SCHEDULE_START_TIME));
                            schedule.setEnd_time(infoClient.getTime(Const.SCHEDULE_END_TIME));
                            schedule.setDay(infoClient.getString(Const.SCHEDULE_day));
                            schedule.setId_user(infoClient.getString(Const.SCHEDULE_IDUSER));
                            schedule.setId_room(infoClient.getString(Const.SCHEDULE_IDROOM));
                            schedules.add(schedule);
                        }
                        PackageData data = new PackageData(schedules);
                        outputStream.writeObject(data);
                    } else if (pd.getOperationType().equals("LIST_LOGS")) {
                        ArrayList<Log> logs = new ArrayList<>();
                        ResultSet infoClient = db.getAllLogs();
                        while (infoClient.next()) {
                            Log log = new Log();
                            log.setGranted(infoClient.getString(Const.LOGS_GRANT));
                            log.setId_access(infoClient.getString(Const.LOGS_ID));
                            log.setAccess_time(infoClient.getString(Const.LOGS_TIME));
                            log.setRoom_id(infoClient.getString(Const.LOGS_IDROOM));
                            log.setId_user(infoClient.getString(Const.LOGS_IDUSER));
                            logs.add(log);
                        }
                        String s = "";
                        String s2 = "";
                        String s3 = "";
                        PackageData data = new PackageData(logs, s, s2, s3);
                        outputStream.writeObject(data);
                    } else if (pd.getOperationType().equals("DELETE_DOOR")) {
                        Door doorFromClient = pd.getDoor();
                        db.Delete(doorFromClient);
                    } else if (pd.getOperationType().equals("DELETE_USER")) {
                        User userFromClient = pd.getUser();
                        db.Delete(userFromClient);
                    } else if (pd.getOperationType().equals("ADD_LOG")) {
                        Log logFromClient = pd.getLog();
                        db.add_log(logFromClient);
                        System.out.println("----------A NEW LOG WAS ADDED----------");
                    } else if (pd.getOperationType().equals("ADD_DOOR")) {
                        Door DoorFromClient = pd.getDoor();
                        db.add_door(DoorFromClient);
                        System.out.println("[" + formattedDateTime + "] DOOR ID: " + pd.getDoor().getId_room() + " LOCATION: " + pd.getDoor().getLocation() + " WAS ADDED from Client IP: " + clientAddress.getHostAddress());
                        //System.out.println("----------A NEW DOOR WAS ADDED----------");
                    } else if (pd.getOperationType().equals("ADD_SCHEDULE")) {
                        Schedule ScheduleFromClient = pd.getSchedule();
                        db.add_schedule(ScheduleFromClient);
                        System.out.println("[" + formattedDateTime + "] SCHEDULE ID: " + pd.getSchedule().getId_schedule() + " ID USER: " + pd.getSchedule().getId_user() + " WAS ADDED from Client IP: " + clientAddress.getHostAddress());
                        //System.out.println("----------A NEW ACCESS WAS ADDED----------");
                    } else if (pd.getOperationType().equals("USERNAME_CHECK")) {
                        User user = new User();
                        ResultSet infoClient = db.UsernameCheck(pd.getUser());
                        while (infoClient.next()) {
                            user.setUsername(infoClient.getString(Const.USERS_USERNAME));
                        }
                        if (user.getUsername().isEmpty()) {
                            user.setUsername("isempty");
                        }
                        PackageData data = new PackageData(user);
                        outputStream.writeObject(data);
                    }


                }
            } catch (EOFException ignored) {
            }
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
