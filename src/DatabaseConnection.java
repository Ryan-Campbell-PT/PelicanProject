import java.sql.*;

/**
 * this class will be used exclusively as a way to connect to the database, to give one connection to it
 * to make our lives easier when trying to modify
 */
public class DatabaseConnection
{
    private static Connection connection;

    private static Connection getConnection()
    {
        if(connection == null)
        {
            try
            {
                connection = DriverManager.getConnection("database url", "username", "password");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        return connection;
    }

    static ResultSet RunSqlCommand(String sql)
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
