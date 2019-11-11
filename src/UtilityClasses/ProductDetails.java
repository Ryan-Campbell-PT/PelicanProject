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
    private static Connection connection = null;
    private static List<String> prodDetails = new ArrayList<>();

    /**
     * Opens the connection to the MYSQL connection
     */
    public static void startProductUpdates(){
        System.out.println ("Starting the connection");
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Closes the connection to the MYSQL connection
     */
    public static void endProductUpdates(){
        System.out.println ("Closing the connection");
        DatabaseConnection.closeConnection(connection);
    }

    /**
     *Uses the existing Instruction interface, and the static methods in the modifyDatabase class to handle the connections / execution
     * @param n Name
     * @param s Size
     * @param c Color
     * @param d Description
     * @param p Price
     * @param cost Cost
     * @param st Stock
     * @param cat Catalog
     * @param desc Description
     */
    public static void AddProductToDatabase(String n, String s, String c, String d, String p, String cost, String st, String cat, String desc)
    {
        System.out.println ("Adding product details.");
        prodDetails.add(0, n);
        prodDetails.add(1, s);
        prodDetails.add(2, c);
        prodDetails.add(3, d);
        prodDetails.add(4, p);
        prodDetails.add(5, cost);
        prodDetails.add(6, st);
        prodDetails.add(7, cat);
        prodDetails.add(8, desc);
        //set image path: will use a default for now.
        String defImagePath = "images/allBirdsShow.png";
        prodDetails.add(9, defImagePath);

        System.out.println("Creating new instruction.");
        AddToProduct i = new AddToProduct(prodDetails, connection);

        System.out.println ("Updating the database.");
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
    public static void updateProduct (int pid, String col, String up){
        UpdateProduct i = new UpdateProduct(pid, col, up, connection);
        ModifyDatabase.updateDatabase(i);
    }

    /**
     * Creates an instruction to remove a product from the Product_inventory table based on a single p_id
     * @param pid product_id
     */
    public static void removeProduct (int pid){
        RemoveProduct i = new RemoveProduct(pid, connection);
        ModifyDatabase.updateDatabase(i);
    }

    /**
     * Creates instruction to give product_name a product_type in product_category
     * @param name product_name
     * @param type product_type
     */
    public static void giveProductType (String name, String type){
        GiveType i = new GiveType (name, type, connection);
        ModifyDatabase.updateDatabase(i);
    }

    /**
     * Updates product_type in product_category based on a product_name
     * @param name product_name
     * @param type product_type
     */
    public static void updateProductType (String name, String type){
        UpdateProductCat i = new UpdateProductCat(name, type, connection);
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
