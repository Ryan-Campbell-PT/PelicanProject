package CustomPages;

import UtilityClasses.DatabaseConnection;
import UtilityClasses.ShoppingCart;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this page will contain all the info that is related to an item
 */
public class ItemDescriptionPage
{
    /*
    anchor panes allow for much more precise placing of Nodes, but because of that
    they take a lot of work to set up
    Im using one for the item description, so it allows for more unique look, then an obvious
    grid system
    */
//    public AnchorPane pane = new AnchorPane();
    public FlowPane pane = new FlowPane(Orientation.VERTICAL, 20, 20);

    private String itemName, itemUniqueId; //, itemCost, itemDescription;
    private ImageView itemImage;

    public ItemDescriptionPage(String uniqueId)
    {
        ArrayList<String> textToDisplay = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM product_inventory WHERE p_id = " + uniqueId;
            ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);
            resultSet.next(); //for some reason this is needed to access the info

            itemName = resultSet.getString("p_name");
            itemUniqueId = resultSet.getString("p_id");
            String itemNameAndPrice = itemName + "\t\t\t$" + resultSet.getString("price");
            String itemDesc = resultSet.getString("p_desc");
            String itemColor = "Color: " + resultSet.getString("color");
            String image = null; //resultSet.getString("image_path");
            if(image == null || image.isEmpty())
                itemImage =  new ImageView(new Image(new FileInputStream("images/allBirdsShoe.png")));
            else
                itemImage =  new ImageView(new Image(new FileInputStream(image)));

            textToDisplay.add(itemNameAndPrice);
            textToDisplay.add(itemColor);
            textToDisplay.add(itemDesc);

        } catch(Exception e) { e.printStackTrace(); }
        setupStructure(textToDisplay);
    }

    private Node setupStructure(ArrayList<String> textToDisplay)
    {
        try
        {

            double stageHeight = Main.stage.getHeight();
            double stageWidth = Main.stage.getWidth();
            pane.getChildren().add(itemImage);

            for (String item : textToDisplay)
            {
                pane.getChildren().add(new Text(item));
            }
//            Text nameAndCostText = new Text(itemName + " \t\t $" + itemCost);
//            Text costText = new Text(itemCost);
//            Text descriptionText = new Text(itemDescription);

            Button addToCartButton = new Button("Add to cart");
            addToCartButton.setOnMouseClicked(event ->
            {
                if(ShoppingCart.addItemToCart(Integer.parseInt(this.itemUniqueId)))
                    System.out.println("item suc added to cart");
                else
                    System.out.println("item not added to cart");
            });

//            pane.getChildren().addAll(itemImage, nameAndCostText, descriptionText, addToCartButton);
            pane.setAlignment(Pos.TOP_CENTER);
            MainPage.setCenterPane(this.pane);

        } catch(Exception e) { e.printStackTrace(); }

        return pane;
    }
}
