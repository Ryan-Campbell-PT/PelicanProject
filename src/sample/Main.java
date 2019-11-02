package sample;

import CustomPages.ItemGridPage;
import UtilityClasses.DatabaseConnection;
import UtilityClasses.UserProfile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    public static Stage stage = null;
    static BorderPane pane;

    @Override
    public void start(Stage stage) throws Exception{
        Main.stage = stage;
//        generalStructure(stage);
        generalStructure(stage, null);

    }

    void generalStructure(Stage stage, Pane p)
    {
        Main.pane = new BorderPane(null, getTop(), null, null, getLeft());
        //all these helper functions are just to make this function a lot less crowded
        if(p == null)
            setCenterPane(new ItemGridPage(null).pane);
        else
            setCenterPane(p);

//        new ItemDescriptionPage("208299");

        Scene scene = new Scene(pane,
                Toolkit.getDefaultToolkit().getScreenSize().width / 2.0,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2.0);

        scene.setOnKeyPressed(event -> { if(event.getCode() == KeyCode.ESCAPE) stage.close(); });
        stage.setScene(scene);
        stage.show();
    }

    public static void setCenterPane(Pane p)
    {
        Main.pane.setCenter(p);
    }

    private Node getCenter()
    {
        GridPane pp = new GridPane();
        pp.setAlignment(Pos.CENTER);
        pp.setHgap(30);
        pp.setVgap(30);

        try
        {

            ImageView i1 = new ImageView(new Image(new FileInputStream("images\\logo.png")));
            ImageView i2 = new ImageView(new Image(new FileInputStream("images\\javaFXPanes.png")));

            //these change the size of the image
//            i1.setFitWidth(i1.getImage().getWidth() / 2);
//            i1.setFitHeight(i1.getImage().getHeight() / 2);
            Button b1 = new Button("Look at this button", i1);
            Button b2 = new Button("They are soo cool", i2);

            pp.add(b1, 0, 0);
            pp.add(b2, 0, 1);
        } catch(Exception e) { e.printStackTrace(); }

        return pp;
    }

    private Node getLeft()
    {
//        Text t1 = new Text("we could make ");
//        Text t2 = new Text("into categories");
//        Text t3 = new Text("that can be ");
//        Text t4 = new Text("searched on");
        VBox categories = new VBox(20);
        categories.setPadding(new Insets(0, 20, 0, 0));

        String sql = "select distinct product_type from product_category";

        ResultSet resultSet = DatabaseConnection.RunSqlExecuteCommand(sql);
        try
        {
            while(resultSet.next())
            {
                String category = resultSet.getString("product_type");
                Text t = new Text(category);
                categories.getChildren().add(t);
            }
        } catch(Exception e) { e.printStackTrace(); }

        return categories;
    }

    private Node getTop()
    {
        HBox topPanel = null;
        try
        {
            TextField searchBar = new TextField();
            searchBar.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    generalStructure(Main.stage, new ItemGridPage(searchBar.getCharacters().toString()).pane);
                }
            });

            Button searchButton = new Button("Press to search");
            ImageView userProfilePic = new ImageView(
                    new Image(
                            new FileInputStream("images\\googleImage.png")));
            Button userProfileButton = new Button("this button could be used as their avatar image", userProfilePic);
            userProfileButton.setAlignment(Pos.TOP_RIGHT);

            topPanel = new HBox(10, searchBar, searchButton, userProfileButton);
            topPanel.setPadding(new Insets(0, 0, 40, 0));

        } catch(Exception e) { e.printStackTrace(); }
        return topPanel;
    }

    void prevBoi(Stage stage) throws FileNotFoundException
    {
        Main.stage = stage;

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
                new Controller(stage);
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
                    stage.close();
            }
        });
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

        UserProfile test = new UserProfile();
        String[][] test_return;
        try {
            test_return = test.getUserInfo("314");
            for (String[] e : test_return) {
                System.out.println(e[0] + " : " + e[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
