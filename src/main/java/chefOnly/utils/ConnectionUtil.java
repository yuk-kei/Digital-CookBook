package chefOnly.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Figure out the connection of the database
 *
 */
public class ConnectionUtil {


    public static Connection getConnection() {
        Connection connection;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbook?useSSL=false&characterEncoding=utf8&serverTimezone=UTC", "root", "michaelhe1161");
            return connection;

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }
    }

    /**
     * Disconnect to the database
     * @throws SQLException the sql exception
     */
    public static void disconnect() throws SQLException {
        Objects.requireNonNull(getConnection()).close();
    }

}