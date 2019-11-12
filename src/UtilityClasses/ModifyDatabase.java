package UtilityClasses;

import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ModifyDatabase{
    /**
     * Takes in a list of type instruction
     * Adds the content of their SQL to the product_log
     * Then executes that SQL
     * Returns false if any of the instructions failed, else returns true
     * @param ls list of type instructions to be completed
     */
     static boolean updateDatabase(ArrayList<Instruction> ls){
        boolean res = true;
        for (Instruction i : ls){
            if (i.execute()){
                writeToProductLog(i.getInstruction());
            }
            else res = false;
        }
        return res;
    }

    /**
     * Updates the database based on a single instruction
     * @param i instruction
     */
    static boolean updateDatabase(Instruction i){
         System.out.println("Received new instruction. Executing now.");
         if(i.execute()){
            writeToProductLog(i.getInstruction());
            return true;
         }
         else return false;
    }


    /**
     * Helper function that takes in a single string and writes it to the product_log
     * @param i String version of SQL
     */
    private static void writeToProductLog (String i){
        System.out.println ("Writing " + i + " to log");
        try {
            FileWriter fw = new FileWriter("logs/product_log", true);
            fw.append("\n");
            fw.append(i);
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
