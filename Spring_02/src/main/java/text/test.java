package text;

import java.sql.Connection;
import java.sql.DriverManager;

public class test {
    public static void main(String[] args) {
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","Ty20021104.");
            System.out.println("good");
        }
         catch (Exception e)
         {
             System.out.println(e);
         }

    }
}
