package sample;

import CustomPages.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class entry_point extends Application {
    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception {
        entry_point.stage = stage;
        stage.setTitle("Entry point");
        LoginPage login = new LoginPage();
        login.setupPage();
    }

    public static void main(String[] args){
        launch(args);
    }
}
