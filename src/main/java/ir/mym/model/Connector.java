package ir.mym.model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by mym on 3/24/17.
 */
public class Connector {

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/takhtiClubDB.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        return connection;
    }

}
