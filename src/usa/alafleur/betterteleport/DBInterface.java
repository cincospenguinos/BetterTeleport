package usa.alafleur.betterteleport;

import java.sql.*;
import java.util.List;

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
        
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + schemaName);
            stmt.execute();

            stmt = connection.prepareStatement("USE " + schemaName);
            stmt.execute();

            stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS locations(alias VARCHAR(40) PRIMARY KEY, " +
                    "x INT NOT NULL, y INT NOT NULL, z INT NOT NULL, added_by VARCHAR(30) NOT NULL, date_added DATETIME NOT NULL, description TEXT)");
            stmt.execute();

            // TODO: Seriously consider including a table that details who removed what locations and when
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the location given the parameters passed to the database. Returns true if it was successful.
     *
     * @param alias - alias the location should be known by
     * @param x - int location of x block
     * @param y - location of y block
     * @param z - location of z block
     * @param addedBy - who added the location
     * @param description - a description of the location. May be null.
     * @return true if successful
     */
    public static boolean addLocation(String alias, int x, int y, int z, String addedBy, String description) {
        try {
            PreparedStatement stmt;

            if(description == null){
                stmt = connection.prepareStatement("INSERT INTO locations VALUES(?, ?, ?, ?, ?, NOW(), NULL)");
            } else {
                stmt = connection.prepareStatement("INSERT INTO locations VALUES(?, ?, ?, ?, ?, NOW(), ?)");
                stmt.setString(6, description);
            }

            stmt.setString(1, alias);
            stmt.setInt(2, x);
            stmt.setInt(3, y);
            stmt.setInt(4, z);
            stmt.setString(5, addedBy);
            stmt.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Returns true if the location exists.
     * @param alias
     * @return
     */
    public static boolean hasLocation(String alias){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM locations WHERE alias = ?");
            stmt.setString(1, alias);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the location given the alias provided. If the location does not exist or if there was a DB error,
     * this method returns false.
     *
     * @param alias - location to remove
     * @return true if the location was removed
     */
    public static boolean removeLocation(String alias){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM locations WHERE alias = ?");
            stmt.setString(1, alias);
            ResultSet results = stmt.executeQuery();

            if(results.next()){
                stmt = connection.prepareStatement("DELETE FROM locations WHERE alias = ?");
                stmt.setString(1, alias);
                stmt.execute();
                connection.commit();
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void getCoordinatesFromAlias(){
        // TODO: This
    }

    public static List<String> getAllLocations(){
        // TODO: This
        return null;
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
