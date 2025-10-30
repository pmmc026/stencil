package com.exemplo.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    @SuppressWarnings("exports")
    public static Connection connectDB(){
        
        Connection conn = null;

        try {
            String url = "jdbc:sqlite:characters.db";
            conn = DriverManager.getConnection(url);
            // connected to database
            return conn;
        } catch(SQLException e){
            // connection failed
            e.printStackTrace();
        }
        return null;

    }

}
