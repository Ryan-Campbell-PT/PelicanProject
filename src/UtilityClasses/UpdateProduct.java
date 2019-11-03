package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct implements Instruction {
    private int p_id;
    private String column;
    private String change;
    private Connection conn;

    UpdateProduct (int p_id, String column, String change, Connection conn){
        this.p_id = p_id;
        this.column = column;
        this.change = change;
        this.conn = conn;
    }

    @Override
    public void execute() {
        try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement p = conn.prepareStatement("UPDATE product_inventory SET " + column + " = ? WHERE p_id = ?");
                //p.setString (1, column);
                p.setInt(2, p_id);

                if (column.equals("admin_cost") || column.equals("price")) {
                    double dUp = Double.parseDouble(change);
                    p.setDouble (2, dUp);
                } else if (column.equals("stock") || column.equals("catalog_number")) {
                    int iUp = Integer.parseInt(change);
                    p.setInt(2, iUp);
                } else {
                    String sUp = change;
                    p.setString(1, sUp);
                }

                p.execute();
                p.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInstruction() {
        return "UPDATE product_inventory SET + " + column + " = " + change + " WHERE p_id = " + p_id;
    }
}
