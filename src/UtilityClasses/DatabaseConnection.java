package UtilityClasses;

import java.sql.*;

/**
 * this class will be used exclusively as a way to connect to the database, to give one connection to it
 * to make our lives easier when trying to modify
 */
public class DatabaseConnection
{
    private static Connection conn = null;

    private static final String DB_URL = "jdbc:mysql://3.14.159.254:3306/software_projects";
    //sets up the connection to our private Oracle Autonomous Database. Will later need to be updated to handle user name + password entry

    /**
     * Logs into the MySQL database currently being hosted on our server from the first week
     * Being managed through phpMyAdmin (including valid login combinations)
     * @return connection
     */
    static Connection getConnection()
    {
        if(conn == null)
        {
            try
            {
                System.out.println ("Finding driver");
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println ("Connecting to the Database");
                conn = DriverManager.getConnection(DB_URL, "qreidy", "Pelican");
                System.out.println ("Connected");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return conn;
    }

    /**
     * Run this if you need to get a list of query results.
     * @param sql String that will be converted to SQL
     * @return A ResultSet containing the results of the SQL query
     */
    public static ResultSet RunSqlExecuteCommand(String sql)
    {
        ResultSet result = null;
        try
        {
            PreparedStatement p = getConnection().prepareStatement(sql);
            result = p.executeQuery();
        }

        catch(SQLException e)
        {
            e.printStackTrace();
        }

        //for updates/deletes, expected result is an empty result set
        return result;
    }

    /**
     * running a sql command to get something back, like select * from database,
     * is different than running something to create something, like create table ding,
     * so this will be used to allow for creation commands, and the above is to get something back
     * @param sql sql command
     * @return whether it succeeded
     */
    public static boolean RunSqlCreateCommand(String sql)
    {
        boolean ret;
        try
        {
            PreparedStatement p = getConnection().prepareStatement(sql);
            p.execute(sql);
            ret = true;
        }

        catch(SQLException e)
        {
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }

    /**
     * Helper method that closes the connection. Contains the try/catch so that the main doesn't get bogged down.
     * @param c connection
     */
    static void closeConnection(Connection c){
        try {
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println ("Closed the connection");
    }

}
