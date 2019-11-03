package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 **/
public class AddToProduct implements Instruction{

    //Product_inventory schema
    //p_id (int), p_name (String), p_size (String), color (String), p_detail (String),
    //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)
    private List<String> details;
    private PreparedStatement sqlCommand;

    /**
     * Constructor. Assumes that d is in the correct format of the schema, which is
     * p_id, p_name, p_size, color, p_detail, price, admin_cost, stock, catalog_number, p_desc, p_imagePath
     * @param d list of new product details
     */
    public AddToProduct (List<String> d){
        this.details = d;
    }

    /**
     * Opens the database connection, runs the helper method to create the SQL command, closes the database connection
     */
    @Override
    public void execute() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            sqlCommand = conn.prepareStatement("INSERT INTO product_inventory VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sqlCommand.setInt (1, Integer.parseInt(details.get(0)));
            sqlCommand.setString (2, details.get(1));
            sqlCommand.setString (3, details.get(2));
            sqlCommand.setString (4, details.get(3));
            sqlCommand.setString (5, details.get(4));
            sqlCommand.setDouble (6, Double.parseDouble(details.get(5)));
            sqlCommand.setDouble (7, Double.parseDouble(details.get(6)));
            sqlCommand.setInt (8, Integer.parseInt(details.get(7)));
            sqlCommand.setInt (9, Integer.parseInt(details.get(8)));
            sqlCommand.setString (10, details.get(9));
            sqlCommand.setString (11, details.get(10));
            sqlCommand.execute();
            sqlCommand.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Uses the local "details" list in order to populate a string version of the SQL Command
     * Assumes that all details are in correct order by column name
     * @return returns String version of sql
     */
    @Override
    public String getInstruction() {
        return "INSERT INTO product_inventory VALUES (" + details.get(0)
                + ", " + details.get(1)
                + ", " + details.get(2)
                + ", " + details.get(3)
                + ", " + details.get(4)
                + ", " + details.get(5)
                + ", " + details.get(6)
                + ", " + details.get(7)
                + ", " + details.get(8)
                + ", " + details.get(9)
                + ", " + details.get(10)
                + ")";
    }
}