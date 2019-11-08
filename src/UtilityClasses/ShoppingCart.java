package UtilityClasses;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ShoppingCart
{
    private static Button cartButton;
    private static VBox pane;

    public ShoppingCart()
    {
        try
        {
            int itemCount = getCartCount();
            cartButton = new Button(
                    Integer.toString(itemCount),
                    new ImageView(new Image(
                                    new FileInputStream("images/shoppingCart")
                            ))
            ); //this will have a button that has the number if items in the cart
        } catch(Exception e) { e.printStackTrace(); }

    }

    private static int getCartCount()
    {
        /*String sql = "SELECT count(itemId) as itemCount FROM shoppingcart WHERE userid = "; //getUserId()
        ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);
        if(resultSet != null)
        {
            resultSet.next();
            return = resultSet.getString("itemCount");
        } */
        return 0;

    }

    //this essentially recreates the entire pane every time its requested
    //its very ineffecient, but i cant always know if the number if items has changed since
    //i can i just dont want to write code ya know
    public static Pane getShoppingCartPane()
    {
        pane = new VBox(30.0);

        String pIdSql = "(SELECT p_id FROM shoppingcart WHERE pid = getUserId())";
        ResultSet productResultSet = DatabaseConnection.RunSqlExecuteCommand(pIdSql);
        try
        {
            //this is getting all the product ids associated with this current user
            while (productResultSet.next())
            {
                String p_id = productResultSet.getString("p_id");
                String itemSql = "SELECT item_image, p_name, price FROM product_inventory WHERE p_id = " + p_id;
                ResultSet itemResultSet = DatabaseConnection.RunSqlExecuteCommand(itemSql);

                //this is getting all the item information associated with that specific item
                itemResultSet.next(); //usually needed for some reason

                Text name = new Text(itemResultSet.getString("p_name"));
                Text price = new Text(itemResultSet.getString("price"));
                String imagePath = itemResultSet.getString("item_image");
                ImageView image;
                if(imagePath == null || imagePath.isEmpty())
                    image = new ImageView(new Image(new FileInputStream("images/steelShoe.jpg")));
                else
                    image = new ImageView(new Image(new FileInputStream(imagePath)));

                //create a specific "box" for the item and its info
                VBox itemPane = new VBox(10, image, name, price);
                //VBox.setVGrow() may be used for scrolling?
                pane.getChildren().add(itemPane); //add it to the overall pane
            }
        } catch(Exception e) { e.printStackTrace(); }

        return pane;
    }

    /**
     * this will allow items to, when added, modify the shopping cart icon to +1 it
     * @return number after function
     */
    public static int addToCartNumber()
    {
        int cartNum = Integer.parseInt(cartButton.getText());
        cartButton.setText(Integer.toString(cartNum + 1));
        return cartNum + 1;
    }

    public static boolean addItemToCart(int itemId)
    {
        /*String sql = "INSERT INTO shoppingcart (userid, itemid, DateTimeAdded) \n" +
                "VALUES (user_id, " + this.itemUniqueId +
                ", CURRENT_TIME() ); ";
        return DatabaseConnection.RunSqlCreateCommand(sql);
         */
        return false;
    }
}
