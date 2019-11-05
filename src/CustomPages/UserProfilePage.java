package CustomPages;

import UtilityClasses.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.entry_point;

import static java.lang.Thread.sleep;

public class UserProfilePage {
    private BorderPane pane = new BorderPane();
    private Customer current_customer;
    private Label f_name_current;
    private Label l_name_current;
    private Label p_addr_current;
    private Label pass_current;
    private TextField f_name_new;
    private TextField l_name_new;
    private TextField p_addr_new;
    private TextField pass_new;

    public void setupPage(Customer cur_customer){
        this.current_customer = cur_customer;
        Button user_icon = new Button("*image*");
//        user_icon.setGraphic(new ImageView(current_customer.userprofile.getUserImage(current_customer.getEmail())));
        Button user_update = new Button("Send Update");
        Button user_delete = new Button("Delete account?");

        String[] user_data_current = current_customer.userprofile.getUserInfo(current_customer.getEmail());
        f_name_current = new Label(user_data_current[1]);
        l_name_current = new Label(user_data_current[2]);
        p_addr_current = new Label(user_data_current[3]);
        pass_current = new Label(user_data_current[5]);

        f_name_new = new TextField();
        f_name_new.setPromptText("New first name...");
        l_name_new = new TextField();
        l_name_new.setPromptText("New last name...");
        p_addr_new = new TextField();
        p_addr_new.setPromptText("New physical address...");
        pass_new = new TextField();
        pass_new.setPromptText("New password...");

        EventHandler<ActionEvent> update_event = e -> {
            String first_name = f_name_new.getText();
            String last_name = l_name_new.getText();
            String physical_address = p_addr_new.getText();
            String password = pass_new.getText();
            this.current_customer.UpdateInfo(new String[]{first_name, last_name, physical_address, password, null});
            updateLabels();
            clearTextFields();
        };
        EventHandler<ActionEvent> delete_event = e -> {
            this.current_customer.DeleteAccount();
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            LoginPage loginPage = new LoginPage();
            loginPage.setupPage();
        };

        GridPane pane_information = new GridPane();
        pane_information.setHgap(10);
        pane_information.setVgap(10);
        pane_information.add(f_name_current , 0 , 0);
        pane_information.add(f_name_new , 1 , 0 );
        pane_information.add(l_name_current , 0 , 1);
        pane_information.add(l_name_new , 1 , 1 );
        pane_information.add(p_addr_current , 0 , 2);
        pane_information.add(p_addr_new , 1 , 2 );
        pane_information.add(pass_current , 0 , 3);
        pane_information.add(pass_new , 1 , 3 );
        pane_information.add(user_update, 3 ,3);
        pane_information.add(user_delete, 4 ,4);

        user_update.setOnAction(update_event);
        user_delete.setOnAction(delete_event);

        pane.setLeft(user_icon);
        pane.setCenter(pane_information);

        Scene scene = new Scene(pane , 600 , 450);
        entry_point.stage.setTitle("Pelican Shop: Profile");
        entry_point.stage.setScene(scene);
        entry_point.stage.show();
    }

    private void clearTextFields() {
        f_name_new.clear();
        l_name_new.clear();
        p_addr_new.clear();
        pass_new.clear();
    }

    private void updateLabels(){
        String[] user_data_current = current_customer.userprofile.getUserInfo(current_customer.getEmail());
        f_name_current.setText(user_data_current[1]);
        l_name_current.setText(user_data_current[2]);
        p_addr_current.setText(user_data_current[3]);
        pass_current.setText(user_data_current[5]);
    }
}
