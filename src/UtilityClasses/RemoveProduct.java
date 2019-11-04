package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
