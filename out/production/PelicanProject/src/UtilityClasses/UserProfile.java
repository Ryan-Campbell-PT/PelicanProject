package UtilityClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;

/**
 * This class is to facilitate getting information about the user for us to use in the user
 * profile page.
 */
public class UserProfile{
    public Image getUserImage(String user_email){
        Image icon = null;
        URL url;
        String sql;
        ResultSet sql_result;

        sql = "SELECT u_id , u_icon FROM software_project.user_information WHERE u_email EQUALS " + user_email + ";";
        sql_result = DatabaseConnection.RunSqlExecuteCommand(sql);

        if(sql_result == null) {
//          User image not found, given default image
            try {
                url = new URL("eggserver.info/public/default_user_image.png");
                icon = ImageIO.read(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else{
            /*
            Get user's icon which is specified by the path in the result set from the query earlier.
            User image found, giving them their image.
             */
            try {
                //spec is currently not really implemented, just placeholder.
                url = new URL("eggserver.info/public/some_user_image.png");
                icon = ImageIO.read(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return icon;
    }

    /**
     * @TODO
     * @param user_email 4head
     * @return If the query found a user, return true. Else the user does not exist and return false.
     */
    public boolean setUserImage(String user_email){
        return false;
    }

    /**
     * @TODO
     * @param user_email 4head
     * @return  User's data in an array for ease of manipulation
     */
    public String[] getUserInfo(String user_email){
        return null;
    }

    /**
     * @TODO
     * @param user_email 4 h e a d
     * @return If the query found a user, return true. Else the user does not exist and return false.
     */
    public boolean setUserInfo(String user_email){
        return false;
    }
}
