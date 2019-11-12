package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RemoveProduct implements Instruction {

    private int p_id;
    private PreparedStatement sql;
    private Connection conn;


    /**
     * Removes a product based on it's product_id.
     * Requires that id be passed in as an argument
     * @param p_id product_id to be removed
     * @param conn db connection
     */
    RemoveProduct(int p_id, Connection conn){
        this.p_id = p_id;
        this.conn = conn;
    }

    /**
     * Deletes a single product from the product_inventory database based on the product_id
     * Product id can be accessed by Product.getID();
     * @return if the execute was successful
     */
    @Override
    public boolean execute() {
        System.out.println ("Removing product with p_id = " + p_id);
        try {
            // Perform Verification
            if(!verifyDBInstruction()){
                System.out.println("Invalid Command.");
                throw new SQLException("Invalid command given to SQL Database.");
            }

            sql = conn.prepareStatement("DELETE FROM product_inventory WHERE p_id = ?");
            sql.setInt(1, p_id);
            sql.execute();
            sql.close();

            System.out.println("Removed");
            return true;
        }

        catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Returns a string version of the SQL for the product_log
     * @return String version of the SQL
     */
    @Override
    public String getInstruction() {
        return "DELETE FROM product_inventory WHERE p_id = " + p_id;
    }

    @Override
    public boolean getValidation() {
        return true;
    }

    @Override
    public boolean verifyDBInstruction() {

        //----------------------------------------
        // Verify non negative p_id
        //----------------------------------------
        if(p_id < 0) return false;

        //----------------------------------------
        // Verify p_id is an int (seems silly, but eh)
        //----------------------------------------
        VerificationAndChecking vc = new VerificationAndChecking();
        String strID = Integer.toString(p_id);
        if(!vc.isInteger(strID)) return false;

        //----------------------------------------
        // Check if p_id exists - untested
        //----------------------------------------
        ResultSet rs = DatabaseConnection.RunSqlExecuteCommand(
                "SELECT p_id FROM product_inventory WHERE p_id = " + p_id);
        // If we haven't found the p_id, don't bother trying to remove it, might be difficult to test
        if (rs == null) return false;


        //----------------------------------------
        // If reached, we have passed verification
        //----------------------------------------
        return true;
    }
}
