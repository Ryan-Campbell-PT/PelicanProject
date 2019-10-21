import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

public class Shoe extends Product
{
    //these are just test values to display how to use UpdateProduct
    int v1;
    int v2;
    String s1;
    double d1;

    @Override
    public void UpdateProduct(ResultSet queryObject, Connection conn)
    { //this may be wrong at first, for now its just a general skeleton
        try
        {
            //these variables are what are currently stored in the database
            int v1Local = queryObject.getInt("v1");
            int v2Local = queryObject.getInt("v2");
            String s1Local = queryObject.getString("s1");
            double d1Local = queryObject.getDouble("d1");

            String sqlQuery = "UPDATE [table] SET";
            if(v1 != v1Local)
                sqlQuery += " v1 = " + this.v1;
            if(v2 != v2Local)
                    sqlQuery += " v2 = " + this.v2;
            if(!s1.equals(s1Local))
                sqlQuery += " s1 = " + this.s1;
            if(d1 != d1Local)
                sqlQuery += " d1 = " + this.d1;

            sqlQuery += " WHERE Id = " + this.getId();

        } catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
