package CustomPages;

import UtilityClasses.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.Stack;

public class MainPage
{
    static BorderPane pane = new BorderPane();
    private static Stack<Pane> centerPaneStack = new Stack<>();

    public MainPage(Stage stage)
    {
        setupStructure();
        Scene scene = new Scene(pane,
                Toolkit.getDefaultToolkit().getScreenSize().width / 2.0,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2.0);

        scene.setOnKeyPressed(event -> { if(event.getCode() == KeyCode.ESCAPE) stage.close(); } );
        stage.setScene(scene);
        stage.show();
    }

    private void setupStructure()
    {
        pane.setTop(getTopPane());
        pane.setLeft(getLeftPane());
        pane.setCenter(getCenterPage());
    }

    private Node getCenterPage()
    {
        return new ItemGridPage(null).pane;
    }

    /**
     * this function will be used by anywhere in the project, which allows for the back button functionality
     */
    public static void setCenterPane(Pane p)
    {
        setCenterPaneLocal(p, true);
    }

    private static void setCenterPaneLocal(Pane p, boolean addToBackButton)
    {
        if(addToBackButton)
            centerPaneStack.push(p);
        pane.setCenter(p);
    }

    private Node getLeftPane()
    {
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

    private Node getTopPane()
    {
        HBox topPanel = null;
        try
        {
            //back button
            Button backButton = new Button("<-");
            backButton.setOnMouseClicked(event -> goBack());

            //search stuff
            TextField searchBar = new TextField();
            searchBar.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    setCenterPane(new ItemGridPage(searchBar.getCharacters().toString()).pane);
                }
            });

            Button searchButton = new Button("Press to search");

            // user profile
            ImageView userProfilePic = new ImageView(
                    new Image(
                            new FileInputStream("images\\googleImage.png")));
            Button userProfileButton = new Button("this button could be used as their avatar image", userProfilePic);
            userProfileButton.setAlignment(Pos.TOP_RIGHT);
            //TODO TO QUIN/DAKOTA
            userProfileButton.setOnMouseClicked(event -> { /*this is where you would bring up the UserProfilePage */});

            //no need to touch this, just be sure to add whatever
            topPanel = new HBox(10, backButton, searchBar, searchButton, userProfileButton);
            topPanel.setPadding(new Insets(0, 0, 40, 0));

        } catch(Exception e) { e.printStackTrace(); }
        return topPanel;
    }

    private boolean goBack()
    {
        if(centerPaneStack.size() == 1)
        {
            return false;
        }
        else
        {
            centerPaneStack.pop(); //take the current pane off
            Pane prevPane = centerPaneStack.peek(); //check out the one before it
            setCenterPaneLocal(prevPane, false);
            return true;
        }
    }

}
