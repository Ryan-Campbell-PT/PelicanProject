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
     * @param ls list of type instructions to be completed
     */

     static void updateDatabase(ArrayList<Instruction> ls){
        for (Instruction i : ls){
            writeToProductLog(i.getInstruction());
            i.execute();
        }
    }


    static void updateDatabase(Instruction i){
         System.out.println("Received new instruction. Executing now.");
         i.execute();
         writeToProductLog(i.getInstruction());
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
