package UtilityClasses;

import java.sql.*;

/**
 * this class will be used exclusively as a way to connect to the database, to give one connection to it
 * to make our lives easier when trying to modify
 */
public class DatabaseConnection
{
    private static Connection conn = null;

    private static final String DB_URL = "jdbc:oracle:thin:@DB201910211444_medium?TNS_ADMIN=/src/Wallet_DB201910211444";
    //sets up the connection to our private Oracle Autonomous Database. Will later need to be updated to handle user name + password entry

    //Must be run before each SQL command / start of instance
    private static Connection getConnection()
    {
        if(conn == null)
        {
            try
            {
                conn = DriverManager.getConnection(DB_URL, "ADMIN", "ThisIsAGroupProject394!");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        return conn;
    }

    public static ResultSet RunSqlCommand(String sql)
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

    //Must be run after each RunSqlCommand / end of SQL block
    public static void CloseConnection(){
        try {
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
