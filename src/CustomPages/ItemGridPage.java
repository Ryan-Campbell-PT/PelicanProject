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
    GridPane pane = new GridPane();

    public ItemGridPage()
    {
        pane.setVgap(30); pane.setHgap(30);
        setupPage();
    }

    private void setupPage()
    {
        String sql = "SELECT ItemImage, ItemName, ItemCost, uniqueId FROM itemdetails";
        ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);

        int col = 0, row = 0;
        try
        {
            for(int count = 0; resultSet.next() && count < 20; count++) //just display 20 at most for now
            {
                String name = resultSet.getString("ItemName");
                String itemId = resultSet.getString("uniqueId");
                String cost = resultSet.getString("ItemCost"); //maybe string depending on our database
                String image = resultSet.getString("ItemImage"); //no idea how this will be done

                ImageView tmpImage;
                if(image != null && !image.isEmpty())
                    tmpImage = new ImageView(new Image(new FileInputStream(image)));
                else
                    tmpImage = new ImageView(new Image(new FileInputStream("images\\logo.png")));

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
