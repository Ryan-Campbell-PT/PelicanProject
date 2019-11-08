package CustomPages;

import UtilityClasses.DatabaseConnection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class will handle the traditional store page, with a grid of the items and the
 * pictures and name of the items. maybe the cost too
 */
public class ItemGridPage
{
    public GridPane pane = new GridPane();

    public ItemGridPage(String searchTerm)
    {
        pane.setVgap(30); pane.setHgap(30);
        setupPage(searchTerm);
    }

    private void setupPage(String searchTerm)
    {
        String sql;
        //if the search term is null, then its just the generic grid view, with everything showing
        if(searchTerm == null)
            sql = "SELECT p_name, price, p_id FROM product_inventory;";
        else //if its a specific search term, then we are just going to display what we are looking for
            sql = "SELECT p_name, price, p_id \n" +
                    "FROM product_inventory \n" +
                    "where p_name like '%" + searchTerm + "%';"; //this is pretty much a Contains()

        ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);

        int col = 0, row = 0;
        try
        {
            for(int count = 0; resultSet.next() && count < 20; count++) //just display 20 at most for now
            {
                String name = resultSet.getString("p_name");
                String itemId = resultSet.getString("p_id");
                String cost = resultSet.getString("price"); //maybe string depending on our database
                //Need to figure out how to associate image and item
               // String image = resultSet.getString("ItemImage"); //no idea how this will be done

                String image = null; ///resultSet.getString("image_path");
                ImageView tmpImage;
                if(image == null || image.isEmpty())
                     tmpImage = new ImageView(new Image( new FileInputStream("images/allBirdsShoe.png")));
                else
                    tmpImage = new ImageView(new Image( new FileInputStream(image)));

                //if(image != null && !image.isEmpty())
                //    tmpImage = new ImageView(new Image(new FileInputStream(image)));
               // else
                //    tmpImage = new ImageView(new Image(new FileInputStream("images\\logo.png")));

                tmpImage.setFitHeight(300); tmpImage.setFitWidth(300); //make all the images the same size

                Button button = new Button(name + "\t\t $" + cost, tmpImage);
                button.setContentDisplay(ContentDisplay.TOP);
                button.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        //send the page to show the item description
                        new ItemDescriptionPage(itemId);
                    }
                });
                pane.add(button, col++, row);
                if(col >= 3) { col = 0; row++; }
            }
        }
        catch(SQLException | FileNotFoundException e) { e.printStackTrace(); }

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(event.getCode() == KeyCode.ESCAPE)
                    Main.stage.close();
            }
        });

        Main.stage.setScene(scene);
        Main.stage.show();
    }
}
