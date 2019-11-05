package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * I (Ryan) assume that UtilityClasses.ProductDetails will be used as a middle man between the user and the database, where you pass
 * in the item you want to update, and UtilityClasses.ProductDetails will change the information necessary.
 * I viewed this as a singleton/static class that would be accessed anywhere in the project to update the item anywhere necessary
 *
 * I (Quin) upon revision have been changed this class so that it will act as the creator of a bunch of different instructions
 * ModifyDatabase class acts as a helper to do the actual execution.
 */
public class ProductDetails
{
    /**
     * this is the connection to the database that will be used throughout the class
     */
    private Connection connection = null;
    private static List<String> prodDetails;

    /**
     * with the creation of an object, this function will be called to add it to the database
     * Uses the existing Instruction interface, and the static methods in the modifyDatabase class to handle the connections / execution
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

        AddToProduct i = new AddToProduct(prodDetails);
        ModifyDatabase.updateDatabase(i);
    }

    /**
     * UpdateProduct takes in an existing productId, existing columnName, and a new value for that column
     * Creates a new updateProduct instruction and executes it
     * Doesn't care about the removed or replaced value
     * TODO: CHECK CONDITIONS ON PARAMETERS
     * @param pid existing product id
     * @param col existing column name in
     * @param up value that the column is being updated to
     */
    static void updateProduct (int pid, String col, String up){
        UpdateProduct i = new UpdateProduct(pid, col, up);
        ModifyDatabase.updateDatabase(i);
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
}