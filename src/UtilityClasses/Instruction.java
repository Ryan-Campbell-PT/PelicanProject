package UtilityClasses;

public interface Instruction {
    boolean execute();
    String getInstruction();
    boolean getValidation();
    boolean verifyDBInstruction();
}
