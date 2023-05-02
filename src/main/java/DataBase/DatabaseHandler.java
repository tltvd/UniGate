package DataBase;

import models.Log;
import models.User;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    private static final DateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public Connection getDbConnection()throws SQLException,ClassNotFoundException{
        String connectionString= "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection=DriverManager.getConnection(connectionString,dbUser,dbPass);
        return dbConnection;
    }


    public void update(User user){

        try {
            String str="UPDATE "+Const.USER_TABLE+" SET "+Const.USERS_PASSWORD+"="+user.getPassword()+" WHERE "+Const.USER_TABLE+"."+Const.USERS_ID+" = "+user.getId_user();
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            prSt.executeUpdate();
            prSt.close();

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    public void Delete(User user){
        try {
            String str="DELETE FROM "+Const.USER_TABLE+" WHERE "+Const.USER_TABLE+"."+Const.USERS_ID+"="+user.getId_user();
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            prSt.executeUpdate();
            prSt.close();

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    public void add(User user){
        String insert= "INSERT INTO "+Const.USER_TABLE+"("+Const.USERS_FIRSTNAME+","+Const.USERS_LASTNAME+","+Const.USERS_USERNAME+","+
                Const.USERS_PASSWORD+","+Const.USERS_EMAIL+","+Const.USERS_GENDER+","+ Const.USERS_PHONE+","+ Const.USERS_ROLE+","+Const.USERS_ID+","+Const.USERS_BIRTHDATE+")"+"VALUES(?,?,?,?,?,?,?,?,?,STR_TO_DATE(SUBSTRING(?, 1, 6), '%y%m%d'))";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirst_name());
            prSt.setString(2, user.getLast_name());
            prSt.setString(3, user.getUsername());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getEmail());
            prSt.setString(6, user.getGender());
            prSt.setString(7, user.getPhone());
            prSt.setString(8, user.getRole());
            prSt.setString(9, user.getId_user());
            prSt.setString(10, user.getId_user());

            prSt.executeUpdate();
            prSt.close();

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void add_log(Log log){
        String insert= "INSERT INTO "+Const.LOGS_TABLE+"("+Const.LOGS_IDUSER+","+Const.LOGS_IDROOM+","+Const.LOGS_TIME+","+
                Const.LOGS_GRANT+")"+"VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, log.getId_user());
            prSt.setString(2, log.getRoom_id());
            prSt.setString(3, log.getAccess_time());
            prSt.setString(4, log.getGranted());

            prSt.executeUpdate();
            prSt.close();

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public ResultSet getUser(User user){
        ResultSet resSet=null;
        String str = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());


            resSet=prSt.executeQuery();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAllusers(){
        ResultSet resSet=null;
        String str = "SELECT * FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            resSet=prSt.executeQuery();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAlldoors(){
        ResultSet resSet=null;
        String str = "SELECT * FROM " + Const.DOORS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            resSet=prSt.executeQuery();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAllLogs(){
        ResultSet resSet=null;
        String str = "SELECT * FROM " + Const.LOGS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            resSet=prSt.executeQuery();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet UsernameCheck(User user){
        ResultSet resSet=null;
        String str = "SELECT username FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(str);
            prSt.setString(1, user.getUsername());
            resSet=prSt.executeQuery();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return resSet;
    }

    public Date Dateofbirth(String str) throws ParseException {
        String birthDateStr = str.substring(0, 6);
        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date birthDate = format.parse(birthDateStr);
        return birthDate;
    }




}