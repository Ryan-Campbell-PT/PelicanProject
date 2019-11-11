package UtilityClasses;

//import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class is to facilitate getting information about the user for us to use in the user
 * profile page.
 */
public class UserProfile{
    //This is the path to the share for user icon
    final String PATH_TO_SHARE = "eggserver.info/public/user_icons";
    /**
     *
     * @param email 4head
     * @return  User's data in an array for ease of manipulation, if null no user exists
     */
    public String[] getUserInfo(String email){
        String sql;
        ResultSet sql_result;

        sql = "SELECT * FROM user_information WHERE e_addr='" + email + "';";
        sql_result = DatabaseConnection.RunSqlExecuteCommand(sql);

        if(sql_result == null) {
            /*
            User not found, return null
            */
            return null;
        }
        else{
            ArrayList<String> user_information = new ArrayList<String>(); //This is the array that will end up being returned.
            try {
            /*
            User found, returning their information.
            Get user's info which is found by the query on user_information matching e_address == email
             */
                sql_result.next(); //This gets the result set to the 1st row of the query. The result set starts before the 1st row of data.

                //get all data for row (This looks horrible and needs to be cleaned up...)
                user_information.add(Integer.toString(sql_result.getInt("user_id")));
                user_information.add(sql_result.getString("f_name"));
                user_information.add(sql_result.getString("l_name"));
                user_information.add(sql_result.getString("p_addr"));
                user_information.add(sql_result.getString("e_addr"));
                user_information.add(sql_result.getString("pass"));
                user_information.add(Integer.toString(sql_result.getInt("a_level")));
                user_information.add(sql_result.getString("u_icon"));
            } catch (SQLException e){
                e.printStackTrace();
            }
            return user_information.toArray(new String[user_information.size()]);
        }
    }

    /**
     * @param email 4 h e a d
     * @param args this is the list of the user's information that will be updated on the server.
     *           Only include columns: +--------+--------+--------+------+--------+
     *                                 | f_name | l_name | p_addr | pass | u_icon |
     *                                 +--------+--------+--------+------+--------+
     *                              In ^this^ order
     * @return If the query found a user and was able to update database, return true. Else the user does not exist || update unsuccessful and return false.
     */
    public boolean setUserInfo(String email , String[] args){
        String sql;
        int sql_result = 0;
        PreparedStatement sql_prepared;
        int rows_effected;
        Connection conn;

        sql =   "UPDATE user_information " +
                "SET f_name = ?, l_name = ?, p_addr = ?, pass = ?, u_icon = ? " +
                "WHERE e_addr = ?";
        try {
            conn = DatabaseConnection.getConnection();
            sql_prepared = conn.prepareStatement(sql);
            sql_prepared.setString(1 , args[0]);
            sql_prepared.setString(2 , args[1]);
            sql_prepared.setString(3 , args[2]);
            sql_prepared.setString(4 , args[3]);
            sql_prepared.setString(5 , args[4]);
            sql_prepared.setString(6 , email);
            sql_result = sql_prepared.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sql_result == 0 ? false : true;
    }

    public Image getUserImage(String user_id){
        BufferedImage icon = null;
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
    public void deleteAccount(int u_id){
        boolean result;
        String sql;
        sql = String.format("DELETE FROM user_information WHERE user_id=%d;" , u_id);
        result = DatabaseConnection.RunSqlCreateCommand(sql);
        if(result){
            System.out.println(String.format("Deleted user with 'user_id' of %d" , u_id));
        }
        else{
            System.out.println(String.format("Unable to delete user with 'user_id' of %d" , u_id));
        }
    }
}
