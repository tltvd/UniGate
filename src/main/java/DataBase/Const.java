package DataBase;

public class Const {

    public static final String SALT="8fa985e47a9d6f1bd3bbb75427442f6b";
    public static final String USER_TABLE="users";
    public static final String USERS_ID="id_user";
    public static final String USERS_ROLE="role";
    public static final String USERS_BIRTHDATE="birthdate";
    public static final String USERS_USERNAME="username";
    public static final String USERS_FIRSTNAME="first_name";
    public static final String USERS_LASTNAME="last_name";
    public static final String USERS_EMAIL="email";
    public static final String USERS_PHONE="phone";
    public static final String USERS_PASSWORD="password";
    public static final String USERS_GENDER="gender";


    public static final String DOORS_TABLE="rooms";
    public static final String DOORS_ID="id_room";
    public static final String DOORS_NAME="name";
    public static final String DOORS_LOCATION="location";
    public static final String DOORS_IPV4="ipv4";

    public static final String LOGS_TABLE="access_logs";
    public static final String LOGS_ID="id_access";
    public static final String LOGS_IDUSER="id_user";
    public static final String LOGS_IDROOM="room_id";
    public static final String LOGS_TIME="access_time";
    public static final String LOGS_GRANT="granted";


    public static final String SCHEDULE_TABLE="schedules";
    public static final String SCHEDULE_DESCRIPTION="access_description";
    public static final String SCHEDULE_ID="id_schedule";
    public static final String SCHEDULE_IDUSER="id_user";
    public static final String SCHEDULE_IDROOM="id_room";
    public static final String SCHEDULE_day="day";
    public static final String SCHEDULE_START_TIME="start_time";
    public static final String SCHEDULE_END_TIME="end_time";


}



