package com.example.spring_02.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mySqladmin {
    public static void main(String[] args) {

        Connection con ;
        PreparedStatement pStmt;

        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/highway", "root", "Ty20021104.");
            System.out.println("good");
            String sql = "insert into  user (用户名,密码,name)value ('2006050140','123456','maxPower')";
            pStmt=con.prepareStatement(sql);

          pStmt.execute();


        } catch (
                Exception e) {
            System.out.println(e);
        }

    }
}
