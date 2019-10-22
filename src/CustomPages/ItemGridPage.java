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
        pane.setPadding(new Insets(30, 10, 30, 10));
        setupPage();
    }

    private void setupPage()
    {
        String sql = "SELECT image, name, cost, uniqueId FROM [itemdatabase]";
        ResultSet resultSet = DatabaseConnection.RunSqlCommand(sql);

        int col = 0, row = 0;
        try
        {
            for(int count = 0; resultSet.next() && count < 20; count++) //just display 20 at most for now
            {
                String name = resultSet.getString("name");
                String itemId = resultSet.getString("uniqueId");
                double cost = resultSet.getDouble("cost"); //maybe string depending on our database
                byte[] image = resultSet.getBytes("image"); //no idea how this will be done

                //this is just a placeholder until we figure out how to get images from database
                ImageView tmpImage = new ImageView(new Image(new FileInputStream("images\\logo.png")));

                Button button = new Button(name, tmpImage);
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
