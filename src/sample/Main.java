package sample;

import CustomPages.LoginPage;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    public static Stage stage = null;
    static BorderPane pane;
    private static Stack<Pane> centerPaneStack = new Stack<>();

    @Override
    public void start(Stage stage) throws Exception{
        Main.stage = stage;
        //we will want to do LoginPage, but for now its just MainPage
//        new MainPage(stage);
        new LoginPage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
