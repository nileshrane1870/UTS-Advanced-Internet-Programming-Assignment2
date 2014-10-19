/*
 * The log in controller for the log in aspect for the web site
 */

package au.edu.uts.aip.detentiontracker.web;
import au.edu.uts.aip.detentiontracker.domain.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LoginController implements Serializable {

    @EJB
    private DetentionTrackerBean detentionTrackerBean;
    
    private Login currentLogin = new Login();

    public Login getCurrentLogin() {
        return currentLogin;
    }

    
    
    
    
    
    /**
     * Creates a new a new user in the login table
     * @return Will either return the user to the log in page (if creation was successful)
     * OR will return user back to the register page with an error message
     * @throws NoSuchAlgorithmException This error is thrown if the password encryption fails
     */
    
    //first creation.
    public String createUser()  throws NoSuchAlgorithmException  {
        if(!detentionTrackerBean.userExists(currentLogin.getUsername())){
           // If new username does not already exists...
            detentionTrackerBean.createInitialLogin(currentLogin);
            return "login?faces-redirect=true";
        }
       // else if it does already exists...
        displayMessage("Username already exists OR you have entered incorrect characters (letters & numbers only!)");
        return null;
    }
    
    /**
     * Will log the user into the web site using container managed security
     * @return Will take the user to the Welcome page or will return the user back to
     * the log in page with an error message
     */
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();       
        try {
            //If the log in is successful..
            request.login(currentLogin.getUsername(), currentLogin.getPassword());
            return "welcome?faces-redirect=true";         
        } catch (ServletException e) {
            //else...
            displayMessage("Username or password is incorrect!");          
            return null;
        }
    }
    
    /**
     * Displays a message on a chosen web page
     * @param message The message that is to be displayed to the user
     */
    private void displayMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }
    
    /**
     * Logs the user out of the web site
     * @return returns the user to the log in page
     * @throws ServletException if the servlet fails to log out
     */
    public String logout() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        try{
            request.logout();
        }
        catch (ServletException e) {
            displayMessage("Unable to log out at this time.");
        }
        return "login?faces-redirect=true";
    }
    
}