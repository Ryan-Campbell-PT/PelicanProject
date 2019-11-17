package CustomPages;

import UtilityClasses.Customer;
import UtilityClasses.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.awt.*;

import static java.lang.Thread.sleep;

public class LoginPage {
    public TilePane pane = new TilePane(Orientation.VERTICAL);
    private String username = null;
    private String password = null;
    private Stage prev_stage = null;

    public LoginPage(Stage stage)
    {
        this.prev_stage = stage;
        setupPane();
        Scene scene = new Scene(pane,
                Toolkit.getDefaultToolkit().getScreenSize().width / 2.0,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2.0);

        scene.setOnKeyPressed(event -> { if(event.getCode() == KeyCode.ESCAPE) stage.close(); } );
        stage.setScene(scene);
        stage.show();
        primeDatabaseConnection();
    }

    /** METHODS */

    public void setupPane(){
        Label group_name = new Label("Pelican Shop");
        TextField username = new TextField();
        username.setPromptText("Enter email address");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter password");
        Button login = new Button("Login");
        Button signup = new Button("Sign-up");
        HBox button_box = new HBox();
        Label login_status = new Label("");
        EventHandler<ActionEvent> login_event = e -> {
            this.username = username.getText();
            this.password = password.getText();
            Customer customer_tmp = Customer.getCustomer(this.username);
            customer_tmp.setPassword(this.password);
            /**
             * if username exists, test the username and password combo
             * if ^, allow user access to next screen
             * else clear password field
             */
            if(customer_tmp==null){
                password.clear();
                login_status.setText("Username password combination were not found.");
            }else{
                if(customer_tmp.Login()){
                    /*Goto gridstore with Customer object passed on
                    * for now goto user profile*/
                    login_status.setText("Login Successful");
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    new MainPage(this.prev_stage, customer_tmp);
                }
                else{
                    password.clear();
                    login_status.setText("We were unable to log you in.");
                }
            }
        };
        EventHandler<ActionEvent> signup_event = e -> {
            this.username = username.getText();
            this.password = password.getText();
            boolean signup_result;
            signup_result = Customer.Signup(this.username , this.password);
            if(!signup_result){
                password.clear();
                login_status.setText("Cannot use your input to sign you up.");
            }else{
                username.clear();
                password.clear();
                login_status.setText("Sign-up Successful, now login.");
            }
        };

        login.setOnAction(login_event);
        password.setOnAction(login_event);
        signup.setOnAction(signup_event);

        pane.setHgap(10);
        pane.setVgap(10);
        pane.getChildren().add(group_name);
        pane.getChildren().add(username);
        pane.getChildren().add(password);
        button_box.getChildren().add(login);
        button_box.getChildren().add(signup);
        pane.getChildren().add(button_box);
        pane.getChildren().add(login_status);
        pane.setAlignment(Pos.CENTER);
    }
    private void primeDatabaseConnection(){
        DatabaseConnection.primeConnection();
    }
}
