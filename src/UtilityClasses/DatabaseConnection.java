package UtilityClasses;

import java.sql.*;

/**
 * this class will be used exclusively as a way to connect to the database, to give one connection to it
 * to make our lives easier when trying to modify
 */
public class DatabaseConnection
{
    private static Connection connection;

    private static final String DB_URL = "jdbc:oracle:thin:@DB201910211444_medium?TNS_ADMIN=/src/Wallet_DB201910211444";
//jdbc:oracle:thin:@dbname_medium?TNS_ADMIN=/users/test/wallet_dbname/"
    private static Connection getConnection()
    {
        if(connection == null)
        {
            try
            {
                connection = DriverManager.getConnection(DB_URL, "ADMIN", "ThisIsAGroupProject394!");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        return connection;
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

        return result;
    }
}
