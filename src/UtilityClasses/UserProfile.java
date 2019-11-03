package UtilityClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class is to facilitate getting information about the user for us to use in the user
 * profile page.
 */
public class UserProfile{
    final String PATH_TO_SHARE = "eggserver.info/public/";
    public Image getUserImage(String user_id){
        Image icon = null;
        URL url;
        String sql;
        ResultSet sql_result;

        sql = "SELECT u_icon FROM user_information WHERE u_id=" + user_id + ";";
        sql_result = DatabaseConnection.RunSqlExecuteCommand(sql);

        if(sql_result == null) {
            /*
            User image not found, given default image
            */
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
                String user_icon_path = PATH_TO_SHARE;
                try {
                    sql_result.next();
                    user_icon_path.concat(sql_result.getString("u_icon"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
     * @param user_id 4head
     * @return If the query found a user, return true. Else the user does not exist and return false.
     */
    public boolean setUserImage(String user_id){
        return false;
    }

    /**
     *
     * @param user_id 4head
     * @return  User's data in an array for ease of manipulation, if null no user exists
     */
    public String[][] getUserInfo(String user_id) throws SQLException {
        String sql;
        ResultSet sql_result;

        sql = "SELECT * FROM user_information WHERE user_id=" + user_id + ";";
        sql_result = DatabaseConnection.RunSqlExecuteCommand(sql);

        if(sql_result == null) {
            /*
            User not found, return null
            */
            return null;
        }
        else{
            /*
            User found, returning their information.
            Get user's info which is found by the query on user_information matching e_address == user_id
             */
            ArrayList<String[]> user_information = new ArrayList<String[]>(); //This is the array that will end up being returned.
            sql_result.next(); //This gets the result set to the 1st row of the query. The result set starts before the 1st row of data.

            //get all data for row (This looks horrible and needs to be clea    ned up...)
            user_information.add(new String[]{"user_id" , Integer.toString(sql_result.getInt("user_id"))});
            user_information.add(new String[]{"f_name" , sql_result.getString("f_name")});
            user_information.add(new String[]{"l_name" , sql_result.getString("l_name")});
            user_information.add(new String[]{"p_addr" , sql_result.getString("p_addr")});
            user_information.add(new String[]{"e_addr" , sql_result.getString("e_addr")});
            user_information.add(new String[]{"pass" , sql_result.getString("pass")});
            user_information.add(new String[]{"a_level" , Integer.toString(sql_result.getInt("a_level"))});
            user_information.add(new String[]{"u_icon" , sql_result.getString("u_icon")});
/*
            //Test that the query and extraction worked
            for(int i = 0; i < user_information.size(); i++){
                System.out.println(user_information.get(i)[0] + " " + user_information.get(i)[1]);
            }
*/
            return user_information.toArray(new String[user_information.size()][2]); //Cast to fix the '.toArray()' function returning an object of type 'Object'
        }
    }

    /**
     * @TODO
     * @param user_id 4 h e a d
     * @return If the query found a user and was able to update database, return true. Else the user does not exist || update unsuccessful and return false.
     */
    public boolean setUserInfo(String user_id){
        return false;
    }
}
