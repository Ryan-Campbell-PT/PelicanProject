package CustomPages;

import UtilityClasses.DatabaseConnection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this page will contain all the info that is related to an item
 * todo: figure out which kind of page would be best for something like this
 */
public class ItemDescriptionPage
{
    ItemDescriptionPage(String uniqueId)
    {
        try
        {
            String sql = "SELECT * FROM itemdetails WHERE uniqueid = " + uniqueId;
            ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);
            resultSet.next(); //for some reason this is needed to access the info
            String name = resultSet.getString("ItemName");
            String itemId = resultSet.getString("uniqueId");
            String cost = resultSet.getString("ItemCost");
            String image = resultSet.getString("ItemImage");
            String description = resultSet.getString("ItemDescription");
            //etc...
        } catch(SQLException e) { e.printStackTrace(); }
    }

    private Node setupStructure()
    {
        /*
        anchor panes allow for much more precise placing of Nodes, but because of that
        they take a lot of work to set up
        Im using one for the item description, so it allows for more unique look, then an obvious
        grid system
         */
        AnchorPane pane = new AnchorPane();

        try
        {
            ImageView itemImage = new javafx.scene.image.ImageView(
                    new Image(new FileInputStream("images\\googleImage.png"))
            );

            //i dont really know how these anchor things work
            AnchorPane.setTopAnchor(itemImage, 100.0);
            AnchorPane.setLeftAnchor(itemImage, 500.0);
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

        } catch(Exception e) { e.printStackTrace(); }

        return pane;
    }
}
