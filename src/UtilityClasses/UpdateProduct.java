package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct implements Instruction {
    private int p_id;
    private String column;
    private String change;

    public UpdateProduct (int p_id, String column, String change){
        this.p_id = p_id;
        this.column = column;
        this.change = change;
    }

    @Override
    public void execute() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement sql = conn.prepareStatement("UPDATE product_inventory SET ? = ? WHERE p_id = ?");
            sql.setString (1, column);
            sql.setString (2, change);
            sql.setInt (3, p_id);
            sql.execute();
            sql.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInstruction() {
        return null;
    }
}
