import com.sun.deploy.util.IconEncoder;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * I (Ryan) assume that ProductDetails will be used as a middle man between the user and the database, where you pass
 * in the item you want to update, and ProductDetails will change the information necessary.
 * I viewed this as a singleton/static class that would be accessed anywhere in the project to update the item anywhere necessary
 */
public class ProductDetails
{
    /**
     * this is the connection to the database that will be used throughout the class
     */
    private Connection connection;

    private ProductDetails()
    {
        // https://docs.oracle.com/javase/tutorial/jdbc/basics/
        // sql database stuff
        try
        {
            connection = DriverManager.getConnection("stuff for database", "username", "password");
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }

    /**
     * with the creation of an object, this function will be called to add it to the database,
     */
    static void AddProductToDatabase(Product p)
    {
        String sqlQuery = "INSERT INTO [table]";
    }

    /**
     * this function will be used to add values to the database, for an object that is using
     * parameters/information that isnt generic to Product (id, name, image)
     * @param productId the productId that will be used to update the information
     * @param parameterName what the name of the value will be in the database
     * @param parameterValue the actual information for that value
     */
    static void AddParameter(int productId, String parameterName, Object parameterValue)
    {

    }

    /**
     * checks the database for this item, and changes any parameters that have changed on it
     * @param i the object that has all the information, that now wants to be synced with the database
     */
    void UpdateItem(Product i)
    {
        try
        {
            String getObjectSQL = "SELECT * WHERE id EQUALS " + i.getId();
            PreparedStatement p = connection.prepareStatement(getObjectSQL);
            ResultSet result = p.executeQuery();

            String itemReceivedFromSQL;
            while (result.next())
            {
                //if done correctly, this loop should only go through once,
                // and s should contain the info recived from the query

//                itemReceivedFromSQL = result.getString(1);

                i.UpdateProduct(result, connection); //let the object handle the updating
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * assuming every DisplayItem has their own tag list, this will return it, in uses like tag searching
     */
    ArrayList<String> ShowItemTags(Product i) throws SQLException
    {
        //connect to database with this sql statement
        String getObjectSQL = "SELECT * WHERE id EQUALS " + i.getId();
        PreparedStatement p = connection.prepareStatement(getObjectSQL);
        ResultSet result = p.executeQuery();

        String tagsFromSQL;
        //should only execute once
        tagsFromSQL = result.getString(1);

        return new ArrayList<String>(Arrays.asList(tagsFromSQL.split("whatever the split would be")));
    }
}
