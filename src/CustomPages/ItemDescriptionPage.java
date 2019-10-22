package CustomPages;

import UtilityClasses.DatabaseConnection;
import javafx.scene.Scene;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this page will contain all the info that is related to an item
 * todo: figure out which kind of page would be best for something like this
 */
public class ItemDescriptionPage
{
    ItemDescriptionPage(String uniqueId)
    {
        try
        {
            String sql = "SELECT * FROM [itemdatabase] WHERE uniqueid = " + uniqueId;
            ResultSet resultSet = DatabaseConnection.RunSqlCommand(sql);

            String name = resultSet.getString("name");
            String itemId = resultSet.getString("uniqueId");
            double cost = resultSet.getDouble("cost"); //maybe string depending on our database
            byte[] image = resultSet.getBytes("image"); //no idea how this will be done
            //etc...
        } catch(SQLException e) { e.printStackTrace(); }
    }
}
