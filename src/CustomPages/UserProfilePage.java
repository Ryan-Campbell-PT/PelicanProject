package CustomPages;

import UtilityClasses.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class UserProfilePage {
    private BorderPane pane = new BorderPane();
    private Customer current_customer;
    public UserProfilePage(Customer cur_customer){
        this.current_customer = cur_customer;
    }
    private void setupPage(){
        Button user_icon = new Button("This should be an image.");
//        user_icon.setGraphic(new ImageView(current_customer.userprofile.getUserImage(current_customer.getEmail())));

        TextField f_name_new = new TextField();
        TextField l_name_new = new TextField();
        TextField p_addr_new = new TextField();

        Label f_name_curret = new Label();
        Label l_name_curret = new Label();
        Label l_name_current = new Label();
        Label p_addr_current = new Label();


    }
}
