package sample;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Controller
{
    Controller(Stage stage)
    {
        try
        {
            ImageView image = new ImageView(new Image(new FileInputStream("images\\logo.png")));
            Text text = new Text("look at all these details");
            GridPane pane = new GridPane();
            pane.add(image, 0, 0);
            pane.add(text, 0, 1);
            text.setTextAlignment(TextAlignment.CENTER);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e)
        {
        }
    }
}