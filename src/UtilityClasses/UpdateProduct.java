package UtilityClasses;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProduct implements Instruction {
    private int p_id;
    private String column;
    private String change;
    private Connection conn;

    private int productSchemaLength = 11;       // Number of columns for a product
    private boolean validInstruction;


    /**
     * Updates a single column in the product_inventory table
     * Does not care about the previous value in the column
     * Matches based on product_id
     * @param p_id product_id
     * @param column column_name to be changed
     * @param change the change you want to see in the world
     * @param conn the fabled connection
     */
    UpdateProduct (int p_id, String column, String change, Connection conn){
        this.p_id = p_id;
        this.column = column;
        this.change = change;
        this.conn = conn;

        // Immediately verify if the details is a valid list instruction for AddToProduct;
        this.validInstruction = verifyDBInstruction();
    }

    /**
     * @return if the execute was successful
     */
    @Override
    public boolean execute() {
        System.out.println ("Executing UpdateProduct");
        try {
            System.out.println ("Update: " + p_id + "at column:" + column + "with new value: "+ change);

            // Perform Verification
            if(!verifyDBInstruction()){
                System.out.println("Invalid Command.");
                throw new SQLException("Invalid command given to SQL Database.");
            }
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement p = conn.prepareStatement("UPDATE product_inventory SET " + column + " = ? WHERE p_id = ?");
                p.setInt(2, p_id);

                if (column.equals("admin_cost") || column.equals("price")) {
                    double dUp = Double.parseDouble(change);
                    //is it positive?
                    p.setDouble (2, dUp);
                } else if (column.equals("stock") || column.equals("catalog_number")) {
                    //is it positive?
                    int iUp = Integer.parseInt(change);
                    p.setInt(2, iUp);
                } else {
                    //actually a string?
                    String sUp = change;
                    p.setString(1, sUp);
                }

                p.execute();
                p.close();
                System.out.print("Exiting");
                return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return sql instruction
     */
    @Override
    public String getInstruction() {
        return "UPDATE product_inventory SET + " + column + " = " + change + " WHERE p_id = " + p_id;
    }

    /**
     * getValidation - simple getter for a private variable
     * @return boolean
     */
    @Override
    public boolean getValidation() {
        return validInstruction;
    }

    /**
     * verifyDBInstruction - performs type and paramater checks for this instruction. Called immediately by constructor
     * @return boolean which is assigned to validInstruction
     */
    @Override
    public boolean verifyDBInstruction() {

        //----------------------------------------
        // Setup and TryCatch - errors are expected if invalid input
        //----------------------------------------
        try {
            ResultSet rs = DatabaseConnection.RunSqlExecuteCommand(
                    "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'product_inventory'");

            String cName = "";
            boolean found = false;

            //----------------------------------------
            // Verify "Column"
            //----------------------------------------
            while(rs.next()){
                cName = rs.getString("COLUMN_NAME");
                if (cName == column) {
                    found = true;
                    break;
                }
            }
            // Column name did not match those found on our DB
            if (found != true)  return false;


            //----------------------------------------
            // Verify "change" is correct type to match column
            //----------------------------------------
            //p_id (int), p_name (String), p_size (String), color (String), p_detail (String),
            //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)
            VerificationAndChecking vc = new VerificationAndChecking();
            switch(cName){
                case "p_id" :
                case "stock" :
                case "catalog_number" :
                    // try to parse int
                    vc.isInteger(change);     // If fails, throws exception which is caught below
                    break;

                case "price" :
                case "admin_cost" :
                    // try to parse double
                    vc.isDouble(change);
                    break;

                default : break;
                    // We assume that "change" is a string
            }

            //----------------------------------------
            // Verify non negative p_id
            //----------------------------------------
            if(p_id < 0) return false;


        } catch(Exception e){
            return false;
        }

        //----------------------------------------
        // If reached, we have passed verification
        //----------------------------------------
        return true;
    }
}
