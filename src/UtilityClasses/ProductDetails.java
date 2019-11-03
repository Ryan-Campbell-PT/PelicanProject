package UtilityClasses;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * I (Ryan) assume that UtilityClasses.ProductDetails will be used as a middle man between the user and the database, where you pass
 * in the item you want to update, and UtilityClasses.ProductDetails will change the information necessary.
 * I viewed this as a singleton/static class that would be accessed anywhere in the project to update the item anywhere necessary
 */
public class ProductDetails
{
    /**
     * this is the connection to the database that will be used throughout the class
     */
    private Connection connection = null;
    private static List<String> prodDetails;

    /**
     * with the creation of an object, this function will be called to add it to the database,
     */
    static void AddProductToDatabase(String n, String s, String c, String d, String p, String cost, String st, String cat, String desc)
    {
        prodDetails.add(0, ModifyDatabase.newProductKey());
        prodDetails.add(1, n);
        prodDetails.add(2, s);
        prodDetails.add(3, c);
        prodDetails.add(4, d);
        prodDetails.add(5, p);
        prodDetails.add(6, cost);
        prodDetails.add(7, st);
        prodDetails.add(8, cat);
        prodDetails.add(9, desc);
        //set image path: will use a default for now.
        String defImagePath = "images/allBirdsShow.png";
        prodDetails.add(10, defImagePath);

        Instruction i = new AddToProduct(prodDetails);
        ModifyDatabase.updateDatabase(i);

    }

    /**
     * this function will be used to add values to the database, for an object that is using
     * parameters/information that isnt generic to UtilityClasses.Product (id, name, image)
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

            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * assuming every UtilityClasses.DisplayItem has their own tag list, this will return it, in uses like tag searching
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

        return new ArrayList<>(Arrays.asList(tagsFromSQL.split("whatever the split would be")));
    }

    public Product getProductDetails()
}
