/*
The controller class connects the GUIProgram, which is our view, with the Model, which talks with the database.
It passes data between the two, and makes decisions about what to do with the data that is received, and calls the
GUIProgram to update the view
*/
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    // Controller Class by conflict
    private String username;

    private Model model;

    private GUIprogram user;

    public Controller(Model _model) {
        model = _model;
        user = new GUIprogram(Controller.this);
    }

    public void loginRequest(String _username, String password) {
        if(model.verifyCredentials(_username, password)) {
            model.attachUser(this);
            username = _username;
            user.loginSuccessful();
        } else {
            user.loginFailed();
        }
    }

    public void registerRequest(String username, String email, String password) {
        try {
            if (model.registerUser(username, email, password) == true){
                loginRequest(username, password);
                user.registerSuccessful();
            } else {
                user.registerFailed();
            }
        } catch (Exception e) {
        }
    }

    public String accountRecovery(String email) throws Exception {
        String password = model.recoverPassword(email);
        if(password!=null){
            return password;}
        else{
            user.accountRecoveryFail();
        }
        return null;
    }

    public void userExit() {
        model.detachUser(this);
    }
    
    public void deleteAccount(String username, String password){
        try{
            if(model.deleteAccount(username, password)){
                user.deleteAccountSuccesful();
            } else {
                user.deleteAccountFail();
            }
        } catch(Exception e){
        }
    }
    
    public void changePassword(String username, String newPassword){
        try {
            if(model.changePassword(username, newPassword)){
                user.passwordChangeSuccess();
            } else {
                user.passwordChangeFail();
            }
        } catch (Exception ex) {
        }
    }

    public void receiveMessage(String message) {
        user.receiveMessage(message);
    }

    public void sendMessage(String message) {
        model.sendMessage(username + ": " + message);
    }
}
