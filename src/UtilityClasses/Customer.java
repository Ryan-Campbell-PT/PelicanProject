package UtilityClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Actor {
    private ShoppingCart cart;
    private int customerId;

    private String email = null;
    private String password = null;

    public UserProfile userprofile;

    /**
     * the only thing that should be creating a customer is the signup method, or the login method to retrieve it
     */
    private Customer(String username, String password) {
//        ResultSet uniqueCartId;
//        ResultSet uniqueCustomerId;
//        String createdCustomerId = null;
//        String createdCartId = null;
        String sql;
        ResultSet sql_resultset;
        boolean sql_result;
//        try
//        {
//            do
//            {
        //these sql commands connect to the database to get a unique cart and customer id
//                sql = "SELECT FLOOR(RAND() * 99999) AS random_num\n" +
//                        "FROM user_information \n" +
//                        "WHERE \"random_num\" NOT IN (SELECT user_id FROM user_information)\n" +
//                        "LIMIT 1";
//                uniqueCustomerId = DatabaseConnection.RunSqlExecuteCommand(sql);
//                sql = "SELECT FLOOR(RAND() * 99999) AS random_num\n" +
//                        "FROM [cartdatabase] \n" +
//                        "WHERE \"random_num\" NOT IN (SELECT cartid FROM [cartdatabase])\n" +
//                        "LIMIT 1";
//                uniqueCartId = DatabaseConnection.RunSqlExecuteCommand(sql);
//            } while (!uniqueCartId.next() && !uniqueCustomerId.next());
//            createdCustomerId = uniqueCustomerId.getString("random_num");
//            createdCartId = uniqueCartId.getString("random_num");
//        }
//        catch(SQLException e) { e.printStackTrace(); }

        sql = "INSERT INTO user_information (e_addr, pass) values('" + username + "', '" + password + "');";
        sql_result = DatabaseConnection.RunSqlCreateCommand(sql);
        try {
            if (sql_result) {
                sql = "SELECT * FROM user_information WHERE e_addr='" + username + "';";
                sql_resultset = DatabaseConnection.RunSqlExecuteCommand(sql);
                if (sql_resultset.next()) {
                    this.customerId = sql_resultset.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        cart = new ShoppingCart(createdCartId, createdCustomerId);
    }

    private Customer(String email_arg) {
        String sql;
        ResultSet sql_resultset;
        sql = String.format("SELECT * FROM user_information " +
                "WHERE e_addr = '%s'", email_arg);
        sql_resultset = DatabaseConnection.RunSqlExecuteCommand(sql);
        try {
            if (sql_resultset.next()) {
                this.customerId = sql_resultset.getInt("user_id");
                this.email = sql_resultset.getString("e_addr");
                this.password = sql_resultset.getString("pass");
                this.userprofile = new UserProfile();
                ViewAccount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Customer getCustomer(String email_arg) {
        Customer getC = new Customer(email_arg);
        return getC;
    }

    @Override
    public boolean Login() {
        boolean result = false;
        ResultSet sql_resultset;
        String sql = String.format("SELECT user_id FROM user_information " +
                "WHERE e_addr='%s' AND BINARY pass = '%s';", this.email, this.password);
        sql_resultset = DatabaseConnection.RunSqlExecuteCommand(sql);
        try {
            if (sql_resultset.next()) {
                if (sql_resultset.getInt("user_id") == this.customerId) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static boolean Signup(String username, String password) {
        String sql = "SELECT * FROM user_information WHERE e_addr = '" + username + "'";

        ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);

        try {
            if (!resultSet.next()) //the query returned nothing, so its a valid query
            {//create a new customer that will add it to the database
                new Customer(username, password);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void ViewAccount() {
        this.userprofile.getUserInfo(this.email);
    }

    @Override
    public void UpdateInfo(String[] args) {
        this.userprofile.setUserInfo(this.email, args);
    }

    @Override
    public void Search(String s) {

    }

    boolean AddToCart(Product p, int quant) {
        return false;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public String getEmail() {
        return this.email;
    }

    public void DeleteAccount() {
        this.userprofile.deleteAccount(this.customerId);
    }
}
