package sample;

import CustomPages.ItemDescriptionPage;
import CustomPages.ItemGridPage;
import CustomPages.MainPage;
import UtilityClasses.DatabaseConnection;
import UtilityClasses.ProductDetails;
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
import java.util.Stack;

public class Main extends Application {

    public static Stage stage = null;
    static BorderPane pane;
    private static Stack<Pane> centerPaneStack = new Stack<>();

    @Override
    public void start(Stage stage) throws Exception{
        Main.stage = stage;
        //we will want to do LoginPage, but for now its just MainPage
        new MainPage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
