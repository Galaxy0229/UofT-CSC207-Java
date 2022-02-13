package Controller;

import Gateway.UserReadWriter;
import UseCase.UserManager;

import java.io.IOException;

/**
 * A class called LoginSystem which is a controller of UserManager.
 * It has two attributes. One is um, which is an UserManager object. The other one is urw,
 * which is an UserReadWriter object
 *
 * LoginSystem can be created by the constructor immediately with the given UserManager.
 *
 * um: an UserManager object
 * urw: an UserReadWriter object
 *
 * @author Qing Lyu
 * @author Zhen Cheng
 */
public class LoginSystem {
    private UserManager um;
    private UserReadWriter urw;

    /**
     *  A constructor that creates a LoginSystem object.
     *  It initializes an UserReadWriter object by passing um as a parameter,
     *  and assigns this object to the attribute urw.
     *
     * @param um an UserManager object
     * @throws IOException exception occurs when we initialize the UserReadWriter urw.
     */
    public LoginSystem(UserManager um) throws IOException {
        this.um = um;
        urw = new UserReadWriter("phase1/usersInfo.ser", um);
    }

    /**
     *
     * Creates user account by pass in three parameters: email, password, and userType
     * and returns this user's id.
     * Returns -1 for email duplication.
     *
     * This method is called when user wants to sign up.
     * It calls the um method createUser and return its result.
     *
     * @param email email for this user
     * @param password password for this user
     * @param userType user type for this user
     * @return user's id or "duplicate email" indicates email duplicating
     * @throws IOException when saveToFile of urw throws an IOException
     */
    public String userCreation(String email, String password, String userType) throws IOException {
        String id = um.createUser(email, password, userType);
        if (id.equals("duplicate email")){
            saveInfo();
        }
        return id;
    }

    /**
     * Returns true iff user login successfully.
     * Returns false iff user's id or password is incorrect.
     *
     * This method is called when user wants to log in by using id and password.
     * It calls the method checkIdPwd of um to return whether the userId matches with the password user entered in.
     *
     * @param id this user's id
     * @param password the password this user entered in
     * @return a boolean that shows whether the id matches to password or not
     */
    public boolean idLogin(String id, String password){
        return um.checkIdPwd(id, password);
    }

    /**
     * Returns true iff email this user entered has not been signed up.
     * Otherwise, returns false.
     *
     * This method is called when the user wants to change original email to email, which is the parameter.
     * It calls the method of um, changeEmail to return whether this user changes email successfully.
     *
     * @param id this user's id
     * @param email the new email this user entered in to change to
     * @return a boolean shows that whether the email changes to the new one or not
     */
    public boolean emailChange(String id, String email){
        return um.changeEmail(id, email);
    }

    /**
     * Changes this user's password to a new one based on user entered.
     *
     * This method is called when this user wants to change their password.
     *
     * @param id this user's id
     * @param password the new password this user entered in
     */
    public void pwdChange(String id, String password){
        um.changePwd(id, password);
    }

    /**
     * Saves everything to the ser file with filepath "phase1/usersInfo.ser".
     *
     * This method is called when this user does something related to account, including signs up, changes password,
     * changes email, or log out(exit this program).
     * It calls the method saveToFile of urw.
     *
     * @throws IOException when saveToFile is called and throws an IOException
     */
    public void saveInfo() throws IOException {
        urw.saveToFile();
    }

    /**
     * Return user's type if the userId entered in exists.
     * Returns 'trial' if this user is a trial user.
     * Returns 'regular' if this user is a regular user.
     * Returns 'admin' if this user is an admin user.
     * Otherwise, return 'No such user'.
     *
     * This method is called to returns this user's type based on user's id.
     * It calls the method getUserType of um
     *
     * @param id the userId for this user
     * @return this user's type
     */
    public String getType(String id){
        return um.getUserType(id);
    }

    /**
     * Return user's id if the email entered in exists.
     * Otherwise, return -1 for not found the user.
     *
     * This method is called to returns this user's id based on user's email.
     * It calls the method getId of um. It is not used in Phase 1, but may be used in Phase 2.
     *
     * @param email user's email that they entered id
     * @return user's id or -1 for not found
     */
    public String getId(String email){
        return um.getUserId(email);
    }

    /**
     * Returns true iff this user's id exists.
     *
     * This method is calls checkId method in um and returns its result.
     * It is called when user enters an invalid user's id to log in.
     *
     * @param id user's id
     * @return true if it id exists
     */
    public boolean idExist(String id){
        return um.checkId(id);
    }
}
