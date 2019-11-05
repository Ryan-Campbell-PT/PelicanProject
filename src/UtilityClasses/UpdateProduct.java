package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct implements Instruction {
    private int p_id;
    private String column;
    private String change;
    private Connection conn;

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
    }

    /**
     * @return if the execute was successful
     */
    @Override
    public boolean execute() {
        try {
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
}
