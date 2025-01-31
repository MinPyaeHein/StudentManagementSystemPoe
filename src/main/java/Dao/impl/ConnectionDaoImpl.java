package Dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDaoImpl {
    public Connection connectionWithSqlDb() throws ClassNotFoundException, SQLException, IOException {

        Class.forName("org.postgresql.Driver");
        InputStream fis=  getClass().getClassLoader().getResourceAsStream("db.properties");

        Properties prop=new Properties();
        prop.load(fis);
        fis.close();

        String url= prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password= prop.getProperty("db.password");
        System.out.println("url: "+url);
        System.out.println("user: "+user);
        System.out.println("password: "+password);
        Connection c= DriverManager.getConnection(url,user,password);
        return c;

    }

}
