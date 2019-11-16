package CustomPages;

import UtilityClasses.Customer;
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

import java.awt.*;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.Stack;

public class MainPage
{
    static BorderPane pane = new BorderPane();
    private Stage prev_stage = null;
    private static Stack<Pane> centerPaneStack = new Stack<>();
    private Customer current_customer = null;

    public MainPage(Stage stage, Customer c)
    {
        this.current_customer = c;
        this.prev_stage = stage;
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
//        pane.setCenter(getCenterPage());
        setCenterPane((Pane) getCenterPage()); //Ryan fixed prev_boi button not working
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
            Button backButton = new Button("<-");
            backButton.setOnMouseClicked(event -> goBack());

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
            ImageView userProfilePic = new ImageView(
                    new Image(
                            new FileInputStream("images\\default_user_image.png")));
            Button userProfileButton = new Button("", userProfilePic);
            userProfileButton.setScaleX(.5); userProfileButton.setScaleY(.5);
            userProfileButton.setAlignment(Pos.TOP_RIGHT);
            //TODO TO QUIN/DAKOTA
            userProfileButton.setOnMouseClicked(event -> {
                /*this is where you would bring up the UserProfilePage */
                setCenterPane(new UserProfilePage(this.current_customer, this.prev_stage).getPane());
            });

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
//            new LoginPage(this.prev_stage);
//            centerPaneStack.clear();
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
