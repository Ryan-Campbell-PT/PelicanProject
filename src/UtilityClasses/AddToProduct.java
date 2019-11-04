package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private Connection conn;
    private int productSchemaLength = 11;       // Number of columns for a product
    private boolean validInstruction;


    /**
     * Constructor. Assumes that d is in the correct format of the schema, which is
     * p_id, p_name, p_size, color, p_detail, price, admin_cost, stock, catalog_number, p_desc, p_imagePath
     * @param d list of new product details
     * @param conn the connection
     */
    AddToProduct(List<String> d, Connection conn){
        this.details = d;
        this.conn = conn;

    }


    /**
     * getValidation - used to retrieve private variable validInstruction
     *  so we don't have to cast instruction each time to get a variable
     */
    @Override
    public boolean getValidation() {
        return validInstruction;
    }


    /**
     * verifyListDetails
     *  - Type and Parameter Checks for each element within the given list
     *  - Schema for Product is (contains 11 items):
     * //p_id (int), p_name (String), p_size (String), color (String), p_detail (String),
     * //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)
     */
    @Override
    public boolean verifyDBInstruction() {
        try {

            // Setup
            Double tmpDouble;
            Integer tmpInteger;

            //----------------------------------------
            // Verify details length against known columns
            //----------------------------------------
            ResultSet rs = DatabaseConnection.RunSqlExecuteCommand(
                    "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'product_inventory'");
            int len;
            if (rs == null) throw new SQLException();
            rs.last();    // moves cursor to the last row
            len = rs.getRow(); // get row id
            if (len != details.size()) return false;

            if(details.size() != productSchemaLength) return false;

            //----------------------------------------
            // Verify each element of details against expected order
            //----------------------------------------
            // p_id (int)               - 0
            tmpInteger = Integer.parseInt(details.get(0));     // If fails, throws exception which is caught below

            // p_name (String)          - 1
            // p_size (String)          - 2
            // color (String)           - 3
            // p_detail (String)        - 4

            // price (double)           - 5
            tmpDouble = Double.parseDouble(details.get(5));

            // admin_cost (double)      - 6
            tmpDouble = Double.parseDouble(details.get(6));

            // stock (int)              - 7
            tmpInteger = Integer.parseInt(details.get(7));

            // catalog_number (int)     - 8
            tmpInteger = Integer.parseInt(details.get(8));

            // p_desc (String)
            // p_imagePath (String)

        } catch (Exception e) {
            // Converting one of our Strings to an int or double has failed, thus it was improperly entered
            // and we know that this instruction if invalid
            return false;
        }

        //----------------------------------------
        // If reached, we have successfully passed verification
        //----------------------------------------
        return true;
    }


    /**
     * Opens the database connection, runs the helper method to create the SQL command, closes the database connection
     */
    @Override
    public boolean execute() {
        System.out.println ("Executing AddToProduct");
        try {
            System.out.println ("Adding: ");
            for (String s : details){
                System.out.print (s + " ");
            }
            System.out.println ();

            // Perform Verification
            if(!verifyDBInstruction()){
                System.out.println("Invalid Command.");
                throw new SQLException("Invalid command given to SQL Database.");
            }


            sqlCommand = conn.prepareStatement("INSERT INTO product_inventory" +
                    " (p_name, p_size, color, p_detail, price, admin_cost, stock, catalog_number, p_desc, image_path)" +
                    "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            //Is it a valid string?
            sqlCommand.setString (1, details.get(0));
            //Actually a string?
            sqlCommand.setString (2, details.get(1));
            //Actually a string?
            sqlCommand.setString (3, details.get(2));
            //Actually a string?
            sqlCommand.setString (4, details.get(3));
            //Positive number?
            sqlCommand.setDouble (5, Double.parseDouble(details.get(4)));
            //Positive number?
            sqlCommand.setDouble (6, Double.parseDouble(details.get(5)));
            //Positive number?
            sqlCommand.setInt (7, Integer.parseInt(details.get(6)));
            //Optional
            sqlCommand.setInt (8, Integer.parseInt(details.get(7)));
            //Optional
            sqlCommand.setString (9, details.get(8));
            //Image exist?
            sqlCommand.setString (10, details.get(9));
            sqlCommand.execute();
            sqlCommand.close();

            System.out.println ("Exiting");
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
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
                + ")";
    }





}
