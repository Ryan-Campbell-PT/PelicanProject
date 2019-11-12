package UtilityClasses;

import java.util.regex.Pattern;

/**
 * A handful of methods that are helpful for testing/checking in production
 */
public class VerificationAndChecking {


    public boolean isDouble(String str){
        Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
        if (!doublePattern.matcher(str).matches()) return false;
        else return true;
    }
    public boolean isInteger(String str) {
        Pattern intPattern = Pattern.compile("^\\d+$");
        if (!intPattern.matcher(str).matches()) return false;
        else return true;
    }
}
