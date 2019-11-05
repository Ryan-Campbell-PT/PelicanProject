package UtilityClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GiveType implements Instruction {

    private String name;
    private String type;
    private Connection conn;
    private PreparedStatement sql;

    /**
     * Creates a new entry in the product_category database
     * Gives each product_name a "type" that can be searched/sorted
     * @param name product_name
     * @param type product_type
     * @param conn db connection
     */
    public GiveType (String name, String type, Connection conn){
        this.name = name;
        this.type = type;
        this.conn = conn;
    }

    @Override
    public boolean execute() {
        System.out.println ("Inserting new values into Product_Categories");
        try {
            sql = conn.prepareStatement("INSERT INTO product_category VALUES (?, ?)");
            //Name exists in inventory?
            sql.setString(1, name);
            //Actually a string?
            sql.setString(2, type);
            sql.execute();
            sql.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        System.out.println("Exiting");
        return true;
    }

    @Override
    public String getInstruction() {
        return "INSERT INTO product_category VALUES (" + name + ", " + type + ")";
    }
}
