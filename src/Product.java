import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Random;

/**
 * created as abstract because it allows us to do basic things like shared methods that
 * an interface doesnt allow
 */
public abstract class Product
{
    private static int idCounter = 0;

    private int Id;
    public int getId() { return Id; }

    private Image image;
    protected void setImage(Image i) { this.image = i; }

    public String name;

    protected Product()
    {
        Id = idCounter++;
    }

    /**
     * my thought for this function is that it will take all the information local to the object
     * and declare it on the database; will likely be called at the end of the constructor of the product
     * MAY NOT ACTUALLY BE NEEDED???
     */
    protected void Submit()
    {
        String sqlQuery = "INSERT INTO [table](id, image, name, ...) VALUES (id, image, name, ...)";
//        ProductDetails
    }

    /**
     * since every object is different and dynamic, this will be used when the object needs to be updated to the database
     * @param queryObject the object returned by the sql query that contains all information currently stored
     *                    in the database for that object
     * @param conn the connection needed to update the information for this object
     */
    public abstract void UpdateProduct(ResultSet queryObject, Connection conn);
}
