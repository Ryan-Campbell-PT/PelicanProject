package CustomPages;

import UtilityClasses.DatabaseConnection;
import UtilityClasses.Product;
import UtilityClasses.ProductDetails;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class ManageStore extends Application {
/**
 * Needs 3 buttons: update item, remove item, add item
 * Will eventually be expanded to contain all the necessary store management stuff
 * Needs text fields for each field in product_inventory
 * just making extra sure about a git issue
 */
    private TextField name;
    private TextField size;
    private TextField color;
    private TextField detail;
    private TextField price;
    private TextField cost;
    private TextField stock;
    private TextField catNum;
    private TextField desc;
    private Button add;

    private TextField pid;
    private TextField removeName;
    private Button remove;
    private Button rName;

    private TextField upId;
    private TextField upVal;
    private TextField upField;
    private Button uItem;

    private Text message;
    private Button returnHome;

    /**
     * Sets up the GridPane where all the buttons/text fields exist
     * @return Grid of buttons / text fields
     */
    private GridPane generateButtonsAndFields(){
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(5);
        grid.setHgap(5);

        //Product Name
        name = new TextField();
        name.setPromptText("Product Name");
        name.getText();
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);

        //Product Size
        size = new TextField();
        size.setPromptText("Product Size");
        GridPane.setConstraints(size, 0, 1);
        grid.getChildren().add(size);

        //Product Size
        color = new TextField();
        color.setPromptText("Product Color");
        GridPane.setConstraints(color, 0, 2);
        grid.getChildren().add(color);

        //Product Detail
        detail = new TextField();
        detail.setPromptText("Product Detail");
        GridPane.setConstraints(detail, 0, 3);
        grid.getChildren().add(detail);

        //Price
        price = new TextField();
        price.setPromptText("Price");
        GridPane.setConstraints(price, 0, 4);
        grid.getChildren().add(price);

        //Cost
        cost = new TextField();
        cost.setPromptText("Cost");
        GridPane.setConstraints(cost, 0, 5);
        grid.getChildren().add(cost);

        //Stock
        stock = new TextField();
        stock.setPromptText("Stock");
        GridPane.setConstraints(stock, 0, 6);
        grid.getChildren().add(stock);

        //Catalog
        catNum = new TextField();
        catNum.setPromptText("Catalog Number");
        GridPane.setConstraints(catNum, 0, 7);
        grid.getChildren().add(catNum);

        //Product Description
        desc = new TextField();
        desc.setPromptText("Description");
        GridPane.setConstraints(desc, 0, 8);
        grid.getChildren().add(desc);

        //Add Item Button
        add = new Button("Add Product");
        GridPane.setConstraints(add, 1, 0);
        grid.getChildren().add(add);

        //Remove this PID
        pid = new TextField();
        pid.setPromptText("Product Id");
        GridPane.setConstraints(pid, 2, 0);
        grid.getChildren().add(pid);

        //Remove all products with this name
        removeName = new TextField();
        removeName.setPromptText("Product Name");
        GridPane.setConstraints(removeName, 2, 1);
        grid.getChildren().add(removeName);

        //Update Item: PID
        upId = new TextField();
        upId.setPromptText("Product Id");
        GridPane.setConstraints(upId, 2, 4 );
        grid.getChildren().add(upId);

        //Update Item: Field
        upField = new TextField();
        upField.setPromptText("Update Field");
        GridPane.setConstraints(upField, 2, 5);
        grid.getChildren().add(upField);

        //Update Item: New Value
        upVal = new TextField();
        upVal.setPromptText("Update Value");
        GridPane.setConstraints(upVal, 2, 6);
        grid.getChildren().add(upVal);

        //Remove Item Button
        remove = new Button ("Remove 1 product");
        GridPane.setConstraints (remove, 3, 0);
        grid.getChildren().add(remove);

        //Remove All PName Button
        rName = new Button ("Remove all with this name");
        GridPane.setConstraints(rName, 3, 1);
        grid.getChildren().add(rName);

        //Update Item Button
        uItem = new Button ("Update Item");
        GridPane.setConstraints(uItem, 3, 4);
        grid.getChildren().add(uItem);

        //Home Button
        returnHome = new Button ("Home");
        GridPane.setConstraints(returnHome, 6,8);
        grid.getChildren().add(returnHome);

        //Message
        message = new Text("");
        GridPane.setConstraints(message, 3, 8);
        grid.getChildren().add(message);
        return grid;
    }

    /**
     * Since the AddToProducts wants a List of the details, figure its easier to
     * just have this helper method which will generate that structure
     * @return List of Product Details
     */
    private List<String> getAddFields(){
        List<String> details = new ArrayList<>();
        details.add(0, name.getText());
        name.setText("");
        details.add(1, size.getText());
        size.setText("");
        details.add(2, color.getText());
        color.setText("");
        details.add(3, detail.getText());
        detail.setText("");
        details.add(4, price.getText());
        price.setText("");
        details.add(5, cost.getText());
        cost.setText("");
        details.add(6, stock.getText());
        stock.setText("");
        details.add(7, catNum.getText());
        catNum.setText("");
        details.add(8, desc.getText());
        desc.setText("");
        details.add(9, "images/allBirdsShoe.png");
        return details;
    }

    private void defineButtonFunctions(){
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> dt = getAddFields();
                if (dt.get(0).isEmpty() | dt.get(4).isEmpty() | dt.get(5).isEmpty() | dt.get(6).isEmpty() | dt.get(7).isEmpty()) {
                    message.setText("Could not add product- missing required fields");
                }
                else {
                    try {
                        ProductDetails.AddProductToDatabase(dt.get(0), dt.get(1), dt.get(2), dt.get(3), dt.get(4), dt.get(5), dt.get(6), dt.get(7), dt.get(8));
                        message.setText("Successfully added product.");
                    } catch (Exception e) {
                        message.setText("Could not add product");
                        e.printStackTrace();
                    }
                }
            }
        });

        returnHome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProductDetails.endProductUpdates();
                Platform.exit();
            }
        });

        uItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (upId.getText().isEmpty() | upField.getText().isEmpty() | upVal.getText().isEmpty()){
                    message.setText("Missing required fields");
                }
                else {
                    try {
                        ProductDetails.updateProduct(Integer.parseInt(upId.getText()), upField.getText(), upVal.getText());
                        message.setText("Successful update.");
                    } catch (Exception e) {
                        message.setText("Unsuccessful update.");
                        e.printStackTrace();
                    }
                    upId.setText("");
                    upField.setText("");
                    upVal.setText("");
                }
            }
        });

        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ProductDetails.removeProduct(Integer.parseInt(pid.getText()));
                    message.setText("Successfully removed.");
                }
                catch (Exception e){
                    message.setText("Unsuccessful remove.");
                    e.printStackTrace();
                }
                pid.setText("");
            }
        });

        rName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String sql = "SELECT p_id FROM product_inventory WHERE p_name = '" + removeName.getText() + "'";
                    ResultSet rs = DatabaseConnection.RunSqlExecuteCommand(sql);

                    while (rs.next()) {
                        ProductDetails.removeProduct(rs.getInt("p_id"));
                        //ProductDetails.removeProduct(rs.getRow());
                    }
                    message.setText("Successfully removed items.");
                }
                catch(SQLException e){
                    message.setText("Unsuccessfully removed items.");
                    e.printStackTrace();
                }

                removeName.setText("");
            }
        });

    }

    /**
     * Starts the javaFX application
     * @param s stage
     */
    public void start(Stage s){
        try {
            ProductDetails.startProductUpdates();
            Scene sc = new Scene (generateButtonsAndFields(), 800, 400);
            defineButtonFunctions();
            s.setScene(sc);
            s.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * It's just a main to test the panel
     * @param args cmd line
     */
    public static void main (String args[]){
        launch (args);
    }
}
