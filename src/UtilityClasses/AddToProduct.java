package UtilityClasses;

import java.util.List;

public class AddToProduct implements Instruction{

    private List<String> details;
    private String sqlCommand;

    private String createSqlCommand(){
        sqlCommand.format("INSERT INTO product_inventory VALUES (");
        return sqlCommand;
    }

    @Override
    public void execute() {

    }

    @Override
    public String getInstruction() {
        return sqlCommand;
    }
}
