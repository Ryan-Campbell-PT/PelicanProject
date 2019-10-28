package UtilityClasses;

import java.util.List;
import java.util.Random;

public class AddToProduct implements Instruction{

    private List<String> details;
    private String sqlCommand;

    private String createSqlCommand(){
        sqlCommand.format("INSERT INTO product_inventory VALUES (");
        return sqlCommand;
    }

    private int newKey(){
        int key;
        Random ran = new Random();
        key = ran.nextInt(1000);


        return key;
    }
    @Override
    public void execute() {

    }

    @Override
    public String getInstruction() {
        return sqlCommand;
    }
}
