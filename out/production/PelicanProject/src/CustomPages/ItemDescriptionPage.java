package CustomPages;

import UtilityClasses.DatabaseConnection;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private String itemName, itemCost, itemUniqueId, itemDescription;
    private ImageView itemImage;

    public ItemDescriptionPage(String uniqueId)
    {
        try
        {
            String sql = "SELECT * FROM product_inventory WHERE p_id = " + uniqueId;
            ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);
            resultSet.next(); //for some reason this is needed to access the info
            itemName = resultSet.getString("p_name");
            itemUniqueId = resultSet.getString("p_id");
            itemCost = resultSet.getString("price");
//            String image = resultSet.getString("ItemImage");
//            itemImage = new ImageView(new Image(new FileInputStream(image)));
            itemDescription = resultSet.getString("p_desc");
            //etc...
        } catch(Exception e) { e.printStackTrace(); }
        setupStructure();
    }

    private Node setupStructure()
    {
        try
        {

            double stageHeight = Main.stage.getHeight();
            double stageWidth = Main.stage.getWidth();
            Text nameAndCostText = new Text(itemName + " \t\t $" + itemCost);
//            Text costText = new Text(itemCost);
            Text descriptionText = new Text(itemDescription);

            Button addToCartButton = new Button("Add to cart");
            addToCartButton.setOnMouseClicked(event -> addToCart());
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
            pane.getChildren().addAll(/*itemImage, */nameAndCostText, descriptionText, addToCartButton);
            pane.setAlignment(Pos.TOP_CENTER);
            Main.setCenterPane(this.pane);

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
