package usa.alafleur.betterteleport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface between the database and the plugin.
 */
public class DBInterface {

    private static Connection connection;
    private static String schemaName;

    /**
     * Sets up a connection given the information provided.
     *
     * @param dbUsername - username to interact with the DB
     * @param dbPassword - password of the user to interact with the DB
     * @param url - the JDBC url to connect to
     * @return Exception or null
     */
    public static Exception openConnection(String dbUsername, String dbPassword, String url){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            connection.setAutoCommit(false); // So that way we have to commit everything
        } catch (ClassNotFoundException e) {
            return e;
        } catch (SQLException e) {
            return e;
        }

        return null;
    }

    /**
     * Setup the schema given the schema name provided.
     *
     * TODO: Should we return something here, just to indicate if it works or not?
     *
     * @param schema - name of the schema to create/setup
     */
    public static void setupSchema(String schema){
        schemaName = schema;

        // TODO: This
        try {
            PreparedStatement stmt = connection.prepareStatement("USE " + schemaName);
            stmt.execute();

            stmt = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + schemaName);
            stmt.execute();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS betterteleport()");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection.
     */
    public static Exception closeConnection(){
        try {
            if(connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            return e;
        }

        return null;
    }
}
