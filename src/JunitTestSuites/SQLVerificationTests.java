package JunitTestSuites;

import UtilityClasses.DatabaseConnection;
import UtilityClasses.ProductDetails;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SQLVerificationTests {


    /**
     * A group of basic tests that ensure the tests themselves run smoothly
     */
    @Test
    public void HelperTests(){
        //--------------------------------------------------
        // isDouble tests - uses regex
        //--------------------------------------------------
        Assert.assertTrue(isDouble("0.0"));
        Assert.assertTrue(isDouble("2.0"));
        Assert.assertTrue(isDouble("100000.1"));
        Assert.assertTrue(isDouble("1.000001"));
        Assert.assertTrue(isDouble("100000.0001"));
        Assert.assertTrue(isDouble("1"));

        Assert.assertFalse(isDouble("300000.----1"));
        Assert.assertFalse(isDouble("not a double"));
        Assert.assertFalse(isDouble("2.maybe"));
        Assert.assertFalse(isDouble("."));


        //--------------------------------------------------
        // isInteger tests - uses regex
        //--------------------------------------------------
        Assert.assertTrue(isInteger("0"));
        Assert.assertTrue(isInteger("1"));
        Assert.assertTrue(isInteger("1000000"));
        Assert.assertTrue(isInteger("12345678"));

        Assert.assertFalse(isInteger("1.0"));       // Can't have it be a double
        Assert.assertFalse(isInteger("1234no"));
        Assert.assertFalse(isInteger("123no34"));



    }
    /**
     * AddToProductListTest - tests the length and content of the list<strings> in AddToProduct's Constructor
     * - Empty, too short (-1 schema length) too long (+1 schema length), way too long (+4 schema length)
     *
     * //[p_id (int),] p_name (String), p_size (String), color (String), p_detail (String),
     * //price (double), admin_cost (double), stock (int), catalog_number (int), p_desc (String), p_imagePath (String)
     *
     */
    @Test
    public void AddToProductListTest() {
        //--------------------------------------------------
        // Setup
        //--------------------------------------------------
        String schStr = "";
        boolean res = false;
        List<String> legitlist = new ArrayList<String>();
        ProductDetails.startProductUpdates();

        legitlist.add(0, "coolName");
        legitlist.add(1, "Big");
        legitlist.add(2, "pink");
        legitlist.add(3, "Interesting Details");
        legitlist.add(4, "20.0");
        legitlist.add(5, "20.0");
        legitlist.add(6, "10");
        legitlist.add(7, "1");
        legitlist.add(8, "Long Description");


        //--------------------------------------------------
        // Column number test
        //--------------------------------------------------
        /*try {
            ResultSet rs = DatabaseConnection.RunSqlExecuteCommand(
                    "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'product_inventory'");
            int len;
            if (rs == null) throw new SQLException();
            rs.last();    // moves cursor to the last row
            len = rs.getRow(); // get row id
            System.out.println("Number of Columns in Product Schema: " + len);
        }catch(Exception e){
            Assert.fail();
        }
        */

        //--------------------------------------------------
        // Legit-Instruction-Test - every element is accurate
        //--------------------------------------------------
        System.out.println("AddToProductListTest - Legit-Instruction-Test");
        Assert.assertTrue(runAddToProduct(legitlist));


        //--------------------------------------------------
        // Empty String
        //--------------------------------------------------
        System.out.println("AddToProductListTest - Empty-String-Instruction-Test");
        res = ProductDetails.AddProductToDatabase(schStr,schStr,schStr,schStr,schStr,schStr,schStr,schStr,schStr);
        Assert.assertFalse (res);

        //--------------------------------------------------
        // Bad p_id
        //--------------------------------------------------

    }

    @Test
    public void RemoveFromProductTest(){
        //--------------------------------------------------
        // p_id Tests
        //--------------------------------------------------
        ProductDetails.startProductUpdates();
        // Verification doesn't check if a p_id is already in the database, but it could
        Assert.assertTrue(ProductDetails.removeProduct(32));
        Assert.assertTrue(ProductDetails.removeProduct(1));
        Assert.assertTrue(ProductDetails.removeProduct(3200));

        Assert.assertFalse(ProductDetails.removeProduct(-1));
        Assert.assertFalse(ProductDetails.removeProduct(-200));


    }

    @Test
    public void UpdateProductTests(){
        ProductDetails.startProductUpdates();

        Assert.assertFalse(ProductDetails.updateProduct(1, "randomCol", "pink"));
        Assert.assertFalse(ProductDetails.updateProduct(-1, "p_name", "newName"));

    }

    @Test
    public void UpdateProductCategoryTests(){
        //TODO
        Assert.assertTrue(true);
    }



    /**
     *
     * @return
     */
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

    /**
     *  Takes a list of strings and assigns them all to AddToProductDatabase. Used as a convenience method
     * @param ls a list of strings
     * @return boolean that reflects the success of the instruction.
     */
    public boolean runAddToProduct(List<String> ls){
        return ProductDetails.AddProductToDatabase(
                ls.get(0),
                ls.get(1),
                ls.get(2),
                ls.get(3),
                ls.get(4),
                ls.get(5),
                ls.get(6),
                ls.get(7),
                ls.get(8)
        );
    }



}