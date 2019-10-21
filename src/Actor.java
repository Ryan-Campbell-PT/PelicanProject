/**
 * Ryan) Actor will be any class that regular humans will use to interact with the website
 */
public interface Actor
{
    /**
     * actors will implement this to show how the user will login to the website
     * @return whether the login was successful (can be stored to the database, or maybe used by the user itself)
     */
    boolean Login();

    /**
     * Actors will use this as a universal signup function that is implemented by the individual actor
     * @return whether the signup was succesful. Can be stored in the database, or used to send a confirmation email
     * (something that would likely not be done in this project, but just an idea)
     */
    boolean Signup(String username, String password);

    /**
     * all users should have the option to view their account, what shows in that account depends on the actor itself
     */
    void ViewAccount();

    /**
     * (Ryan) have no idea how this would be implemented, as every actor will have a different subset of things to update
     * and you cant have an X amount of function parameters in java
     */
    void UpdateInfo();

    /**
     * Searching should be a basic function, so the search should simply take in a string that will check the
     * database for whatever it is they searched
     */
    void Search(String s);
}
