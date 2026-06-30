package com.mycompany.laundryservice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private final String address = "jdbc:mysql://localhost:3306/laundry_service_db";
    private final String userName = "root";
    private final String passWord = "";

    public Connection createConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(
                address, userName, passWord);
        return conn;
    }
    
    public boolean canConnectToDB() {
        boolean isConnected = true;
        try {
            Connection conn = createConnection();
            //need isara
            conn.close();
        } catch (SQLException e) {
            isConnected = false;
            System.out.println(e.getMessage());
        }
        return isConnected;
    }
    
    public static void main(String[] args) {
        DBConn connTest = new DBConn();
        
        if (connTest.canConnectToDB()) {
            System.out.println("Connection successful");
        } else {
            System.out.println("Connection Failed");
        }
    }
}
