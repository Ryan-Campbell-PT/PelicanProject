package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProductCat implements Instruction{

    private String name;
    private String change;
    private Connection conn;

    /**
     * Instruction that updates the product_type of a product_name in the product_category table
     * Does not care about value of the old product_type
     * @param name product_name from product_inv
     * @param change updated type
     * @param conn the connection
     */
    UpdateProductCat (String name, String change, Connection conn){
        this.name = name;
        this.change = change;
        this.conn = conn;
    }

    @Override
    public boolean execute() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement p = conn.prepareStatement("UPDATE product_category SET product_type = ? WHERE product_name = ?");
            //string make sense
            p.setString(1, change);
            //product_name exist?
            p.setString(2, name);
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

    @Override
    public String getInstruction() {
        return "UPDATE product_inventory SET product_type = " + change + " WHERE product_name = " + name;
    }
}
