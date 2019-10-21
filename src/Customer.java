import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Actor
{
    private ShoppingCart cart;
    private String customerId;

    /**
     * the only thing that should be creating a customer is the signup method, or the login method to retrieve it
     */
    private Customer(String username, String password)
    {
        ResultSet uniqueCartId = null; ResultSet uniqueCustomerId = null;
        String sql = null;
        String createdCustomerId = null; String createdCartId = null;
        try
        {
            do
            {
                //these sql commands connect to the database to get a unique cart and customer id
                sql = "SELECT FLOOR(RAND() * 99999) AS random_num\n" +
                        "FROM [customerdatabase] \n" +
                        "WHERE \"random_num\" NOT IN (SELECT customerid FROM [customerdatabase])\n" +
                        "LIMIT 1";
                uniqueCustomerId = DatabaseConnection.RunSqlCommand(sql);
                sql = "SELECT FLOOR(RAND() * 99999) AS random_num\n" +
                        "FROM [cartdatabase] \n" +
                        "WHERE \"random_num\" NOT IN (SELECT cartid FROM [cartdatabase])\n" +
                        "LIMIT 1";
                uniqueCartId = DatabaseConnection.RunSqlCommand(sql);
            } while (!uniqueCartId.next() && !uniqueCustomerId.next());
            createdCustomerId = uniqueCustomerId.getString("random_num");
            createdCartId = uniqueCartId.getString("random_num");
        }
        catch(SQLException e) { e.printStackTrace(); }

        sql = "INSERT INTO [customerdatabase] (username, password, uniqueid) values(" + username + ", " + password +
                " " + createdCustomerId + ");";
        cart = new ShoppingCart(createdCartId, createdCustomerId);
    }

    public static Customer getCustomer(String username)
    {
        return null;
    }

    @Override
    public boolean Login()
    {
        return false;
    }

    @Override
    public boolean Signup(String username, String password)
    {
        String sql = "SELECT username FROM [database] WHERE username = " + username;
        ResultSet resultSet = DatabaseConnection.RunSqlCommand(sql);
        try
        {
            if (!resultSet.next()) //the query returned nothing, so its a valid query
            {//create a new customer that will add it to the database
                new Customer(username, password);
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void ViewAccount()
    {

    }

    @Override
    public void UpdateInfo()
    {

    }

    @Override
    public void Search(String s)
    {

    }

    boolean AddToCart(Product p)
    {
        return false;
    }
}
