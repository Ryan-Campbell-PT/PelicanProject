package CustomPages;

import UtilityClasses.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class UserProfilePage {
    private BorderPane pane = new BorderPane();
    private Stage prev_stage = null;
    private Customer current_customer;
    private Label f_name_current;
    private Label l_name_current;
    private Label p_addr_current;
    private Label pass_current;
    private TextField f_name_new;
    private TextField l_name_new;
    private TextField p_addr_new;
    private TextField pass_new;

    public UserProfilePage(Customer c, Stage s){
        this.current_customer = c;
        this.prev_stage = s;
        setupPage();
    }

    public BorderPane getPane(){
        return this.pane;
    }

    public void setupPage(){
//        Button user_icon = new Button("*image*");
//        user_icon.setGraphic(new ImageView(current_customer.userprofile.getUserImage(current_customer.getEmail())));
        Button user_update = new Button("Send Update");
        Button product_CRUD = new Button("Product CRUD");
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
            if(first_name.equals("")){
                first_name = f_name_current.getText();
            }
            if(last_name.equals("")){
                last_name = l_name_current.getText();
            }
            if(physical_address.equals("")){
                physical_address = p_addr_current.getText();
            }
            if(password.equals("")){
                password = pass_current.getText();
            }
            this.current_customer.UpdateInfo(new String[]{first_name, last_name, physical_address, password, null});
            updateLabels();
            clearTextFields();
        };
        EventHandler<ActionEvent> delete_event = e -> {
            Alert aaaaa = new Alert(Alert.AlertType.CONFIRMATION);
            aaaaa.setContentText("Are you sure you want to delete your account?");
            Optional<ButtonType> result = aaaaa.showAndWait();
            if (result.get() == ButtonType.OK){
                this.current_customer.DeleteAccount();
                System.exit(0);
            }
        };

        EventHandler<ActionEvent> product_CRUD_event = e -> {
            if(this.current_customer.userprofile.getAccessLevel(this.current_customer.getCustomerId())){
                ManageStore tmp = new ManageStore();
                 MainPage.setCenterPane(tmp.start());
            }
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
        pane_information.add(user_delete, 3 ,4);
        pane_information.add(product_CRUD, 4 ,4);

        user_update.setOnAction(update_event);
        user_delete.setOnAction(delete_event);
        product_CRUD.setOnAction(product_CRUD_event);

//        pane.setLeft(user_icon);
        pane.setCenter(pane_information);
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