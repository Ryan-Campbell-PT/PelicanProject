package UtilityClasses;

import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Random;

class ModifyDatabase{
    /**
     * Takes in a list of type instruction
     * Adds the content of their SQL to the product_log
     * Then executes that SQL
     * @param ls list of type instructions to be completed
     */
    static void updateDatabase(List<Instruction> ls){
        for (Instruction i : ls){
            writeToProductLog(i.getInstruction());
            i.execute();
        }
    }

    static void updateDatabase(Instruction i){
        writeToProductLog(i.getInstruction());
        i.execute();
    }

    /**
     * Helper function that takes in a single string and writes it to the product_log
     * @param i String version of SQL
     */
    static void writeToProductLog (String i){
        try {
            FileWriter fw = new FileWriter("logs/product_log" , true);
            fw.write(i + "/n");
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * newProductKey will generate a new p_id for a new product being added
     * it does this by randomly generating an integer, queries the p_id table for that value
     * if it doesn't exist, then it catches the error and breaks out of the loop, and then returns that key
     * @return new product key (p_id)
     */
    static String newProductKey() {
        int key;
        Random ran = new Random();
        while (true) {
            key = ran.nextInt(10000);
            String sql = "SELECT p_id FROM product_inventory WHERE p_id = " + key;
            try {
                PreparedStatement p = DatabaseConnection.getConnection().prepareStatement(sql);
                p.execute();
            } catch (Exception e) {
                break;
            }
        }
        return Integer.toString(key);
    }

}