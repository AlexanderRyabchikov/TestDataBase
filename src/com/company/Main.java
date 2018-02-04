package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final String dbClassName = "com.mysql.jdbc.Driver";

    public static void throughput(Long time, int bytes)
    {
        System.out.println("Speed = " + ((double)bytes / (double)time) * 8 + " bit/sec");
    }
    public static void main(String[] args) throws InterruptedException, SQLException {
        try {
            Class.forName(dbClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        WorkWithDB db = new WorkWithDB( "jdbc:mysql://localhost:3306/testDB?autoReconnect=true&useSSL=false",
                                        "root",
                                        "qwerty");
        String MyQuery =
                "select user_id, " +
                        "connected_date, " +
                        "disconnected_date, " +
                        "uplink, " +
                        "downlink " +
                        "from users " +
                        "where contract_number = 12445";

        ResultSet rs = null;
        Runnable executeQuery = () -> {
            try {
                db.executeQuery(MyQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        db.connect_DB();

        Thread thread = new Thread(executeQuery);
        thread.start();
        thread.join();

        rs = db.getRs();
        Long time;
        while (rs.next())
        {
            System.out.println("id " + rs.getInt(1) + " " +
                    "connected_date  " + rs.getTimestamp(2) + " " +
                    "disconnected_date " + rs.getTimestamp(3) + " " +
                    "uplink " + rs.getInt(4) + " " +
                    "downlink " + rs.getInt(5) + " " +
                    "time = " + (time =
                    (rs.getTimestamp(3).getTime()- rs.getTimestamp(2).getTime()) / 1000)

            );
            throughput(time, rs.getInt(4));
            throughput(time, rs.getInt(5));

        }

        db.closeConnection();
    }
}
