package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Image image = new Image(new FileInputStream("images\\logo.png"));
        ImageView logo1 = new ImageView(image);
        ImageView logo2 = new ImageView(image);

        /*
        Everything in javafx requires some form of a 'pane' which essentially just holds the contents of that page
        a gridpane holds elements in a grid form, which is best suited for our page view of all items to choose from
        (like any clothing website, with images and details next to it)
         */
        GridPane pane = new GridPane();
        pane.setMinSize(300, 300);
        //padding sets the distance between elements (I dont know what an inset is though)
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.TOP_LEFT);

        //buttons shouldnt be a surprise
        Button butt1 = new Button("ding", logo1);
        butt1.setContentDisplay(ContentDisplay.TOP);
        Button butt2 = new Button("suh", logo2);
        /*
        a click listener does exactly that, it pays attention to when it is clicked, and the 'event' variable stores
        a ton of info on how it was clicked (dragged/long clicked)
        and the handle() function is what is called when the event is clicked,
        so in this case, it takes us to the "details" page
         */
        butt2.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                new Controller(primaryStage);
            }
        });

        butt2.setContentDisplay(ContentDisplay.TOP);
        //this is just adding the elements to the pane to be displayed
        pane.add(butt1, 0, 0);
        pane.add(butt2, 1, 0);

        Scene scene = new Scene(pane);
        //and this is just what I added so the window goes away when you press escape
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if(event.getCode() == KeyCode.ESCAPE)
                    primaryStage.close();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
