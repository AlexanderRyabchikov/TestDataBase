package com.company;
import java.sql.*;

public class WorkWithDB {

    public String getUrl() {
        return url;
    }

    public String getUserDB() {
        return userDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public Connection getCon() {
        return con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    WorkWithDB(String url,String user,String password){
        this.url = url;
        this.userDB = user;
        this.passwordDB = password;
    }

    public void connect_DB(){

        try{
            this.con = DriverManager.getConnection(getUrl(), getUserDB(), getPasswordDB());
            this.stmt = getCon().createStatement();
        }catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public void executeQuery(String query) throws SQLException{
        rs = getStmt().executeQuery(query);
    }

    public void closeConnection()
    {
        try {
            getStmt().close();
            getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Private section
    private String url;
    private String userDB;
    private String passwordDB;

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

}
