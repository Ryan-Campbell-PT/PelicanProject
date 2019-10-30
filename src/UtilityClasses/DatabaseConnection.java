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

    //Must be run before each SQL command / start of instance
    protected static Connection getConnection()
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
    /*
        Run this to get a result set back from a query
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
     * @param sql
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

    //Must be run after connection is done being used
    public static void CloseConnection(){
        try {
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
