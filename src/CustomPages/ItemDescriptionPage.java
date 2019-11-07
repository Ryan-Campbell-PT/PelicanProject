package CustomPages;

import UtilityClasses.DatabaseConnection;
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
 * todo: figure out which kind of page would be best for something like this
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
            //images dont work atm
//            String image = resultSet.getString("ItemImage");
//            itemImage = new ImageView(new Image(new FileInputStream(image)));
            itemImage =  new ImageView(new Image(new FileInputStream("images/dressShoe.jpg")));
            //etc...

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
            addToCartButton.setOnMouseClicked(event -> addToCart());

//            pane.getChildren().addAll(itemImage, nameAndCostText, descriptionText, addToCartButton);
            pane.setAlignment(Pos.TOP_CENTER);
            MainPage.setCenterPane(this.pane);
/**
 * these "setTop/Left/Right/BottomAnchor functions set the Nodes anchor position
 * a distance away from the sides of the whole scene. So setting the Top anchor to
 * 0.0 puts the item at the top of the scene, and setting the left anchor to 0.0
 * puts the item in the top left corner of the screen
 */

/*
            //i dont really know how these anchor things work
            AnchorPane.setTopAnchor(itemImage, 0.0);
            AnchorPane.setRightAnchor(itemImage, 0.0);
            AnchorPane.setBottomAnchor(itemImage, 500.0);
            AnchorPane.setRightAnchor(itemImage, 500.0);
            Text itemName = new Text("Item name");
            AnchorPane.setTopAnchor(itemName, 150.0);
            AnchorPane.setLeftAnchor(itemName, 500.0);
            AnchorPane.setBottomAnchor(itemName, 500.0);
            AnchorPane.setRightAnchor(itemName, 500.0);

            Text itemCost = new Text("cost: $20");
            AnchorPane.setTopAnchor(itemCost, 200.0);
            AnchorPane.setLeftAnchor(itemCost, 500.0);
            AnchorPane.setBottomAnchor(itemCost, 500.0);
            AnchorPane.setRightAnchor(itemCost, 500.0);

            Text itemDescription = new Text("this is the item description \n im not sure why its up here");
            AnchorPane.setTopAnchor(itemCost, 200.0);
            AnchorPane.setLeftAnchor(itemCost, 500.0);
            AnchorPane.setBottomAnchor(itemCost, 500.0);
            AnchorPane.setRightAnchor(itemCost, 500.0);


            pane.getChildren().addAll(itemImage, itemCost, itemName, itemDescription);
*/


        } catch(Exception e) { e.printStackTrace(); }

        return pane;
    }

    private void addToCart()
    {
        /*String sql = "INSERT INTO shoppingcarttmp (userid, itemid, DateTimeAdded) \n" +
                "VALUES (floor(rand() * (999999 - 100000) + 100000), " + this.itemUniqueId +
                ", CURRENT_TIME() ); ";
        if(DatabaseConnection.RunSqlCreateCommand(sql))
            System.out.println("Successfully executed AddToCart");*/

    }
}
