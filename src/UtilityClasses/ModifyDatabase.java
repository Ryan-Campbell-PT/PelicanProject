package UtilityClasses;

import java.sql.PreparedStatement;
import java.util.Random;

public class ModifyDatabase{
    /*
    Purpose of this class is to handle all modifications to the database.
    Expects to receive a list of type Instruction.
    Calls the execute command on each of those instructions
    ex. foreach (Instruction i : incomingInstructions)
        writeToLog(i.getInstruction())
        verifyItsOk(i)
        i.execute()
     */

    private int newProductKey() {
        int key;
        Random ran = new Random();
/*
        while (true) {
            key = ran.nextInt(10000);
            String sql = "SELECT p_id FROM product_inventory WHERE EXISTS "
        }
*/
        //This was the janky solution that was hilarious so we're keeping it in

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
        return key;
    }
}
