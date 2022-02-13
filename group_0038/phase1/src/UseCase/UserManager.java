package UseCase;

import Entity.PermanentUser;
import Entity.User;

import java.util.ArrayList;

/**
 * A class to create user, and check, change some certain features like password and email
 * It has two parameters: permanentUsers and trialUsers.
 *
 * permanentUsers: An ArrayList of PermanentUser objects that contains all the regular and admin users
 * trailUsers: An ArrayList of User object that contains all the trial users
 *
 * @author Qing Lyu
 * @author Zhen Cheng
 */
public class UserManager{
	private ArrayList<PermanentUser> permanentUsers;
	private ArrayList<User> trialUsers;

	/**
	 * A constructor for UserManager.
	 * Initialize the two attributes permanentUsers and trialUsers
	 */
	public UserManager() {
		permanentUsers = new ArrayList<>();
		trialUsers = new ArrayList<>();
	}

	/**
	 * Sets permanentUsers attribute.
	 * It is a setter for permanentUsers attribute
	 *
	 * @param permanentUsers ArrayList of permanentUsers that contains all the info
	 *                       of regular and admin
	 */
	public void setPermanentUsers(ArrayList<PermanentUser> permanentUsers) {
		this.permanentUsers = permanentUsers;
	}

	/**
	 * Returns a ArrayList that contains PermanentUser objects
	 * It is a getter for permanentUsers attributes
	 *
	 * @return ArrayList of PermanentUser objects
	 */
	public ArrayList<PermanentUser> getPermanentUsers() {
		return permanentUsers;
	}

	/**
	 * Return a ArrayList that contains User objects
	 * It is a getter for trialUsers attributes
	 * This method was not used in Phase 1, but may be used in Phase 2.
	 *
	 * @return ArrayList of User objects
	 */
	public ArrayList<User> getTrialUsers() {
		return trialUsers;
	}

	/**
	 * Creates user account by pass in three parameters: email, password, and userType
	 * and return this user's id.
	 * Returns -1 for email duplication.
	 * For trail user, the parameters email and password will be empty strings.
	 * The type of trial user is 'trial',
	 * type of regular user is 'regular',
	 * type of admin user is 'admin'.
	 *
	 * @param email email for this user
	 * @param password password for this user
	 * @param userType this user's type
	 * @return user's id or "duplicate email" indicates email duplication
	 */
	public String createUser(String email, String password, String userType){
		if (userType.equals("trial")) {
			User user = new User();
			trialUsers.add(user);
			return user.getUserId();
		}
		else {
			for (PermanentUser permanentUser : permanentUsers) {
				if (permanentUser.getEmail().equals(email)) {
					return "duplicate email";
				}
			}
			PermanentUser user = new PermanentUser(email, password, userType);
			permanentUsers.add(user);
			return user.getUserId();
		}
	}

	/**
	 * Returns true iff email this user entered has not been signed up, or the email
	 * this user wants to change is the same as before.
	 * Otherwise, return false.
	 * Call this method when this user want to change their email address.
	 *
	 * @param id this user's id
	 * @param newEmail the new email this user entered in to change to
	 * @return a boolean shows that whether the email changes to the new one or not
	 */
	public boolean changeEmail(String id, String newEmail){
		int index = 0;
		for (int i = 0; i < permanentUsers.size(); i++){
			if (!permanentUsers.get(i).getUserId().equals(id) && permanentUsers.get(i).getEmail().equals(newEmail))
				return false;
			else if (permanentUsers.get(i).getUserId().equals(id))
				index = i;
		}
		if (!permanentUsers.isEmpty())
			permanentUsers.get(index).setEmail(newEmail);
		return true;
	}

	/**
	 * Changes this user's password to a new one based on user entered.
	 * Call this method when this user wants to change their password.
	 *
	 * @param id this user's id
	 * @param password the new password this user entered in
	 */
	public void changePwd(String id, String password){
		for (PermanentUser permanentUser : permanentUsers) {
			if (permanentUser.getUserId().equals(id)) {
				permanentUser.setPassword(password);
			}
		}
	}

	/**
	 * Returns true iff the userId matches with the password user entered in
	 * If the userId does not exist, return false.
	 *
	 * @param id this user's id
	 * @param password the password this user entered in
	 * @return a boolean that shows whether the id matches to password or not
	 */
	public boolean checkIdPwd(String id, String password){
		for (PermanentUser permanentUser : permanentUsers) {
			if (permanentUser.getUserId().equals(id)) {
				return permanentUser.getPassword().equals(password);
			}
		}
		return false;
	}

	/**
	 * Returns user's type if the userId entered in is match to
	 * one of the user in ArrayList trialUsers or permanentUsers.
	 * Otherwise, return 'No such user'.
	 * It is a getter for userType.
	 * They type of trial user is 'trial',
	 * type of regular user is 'regular',
	 * type of admin user is 'admin'.
	 *
	 * @param id the userId for this user
	 * @return this user's type
	 */
	public String getUserType(String id){
		for (User tu: trialUsers){
			if (tu.getUserId().equals(id)){
				return "trial";
			}
		}
		for (PermanentUser pu: permanentUsers){
			if (pu.getUserId().equals(id)){
				return pu.getUserType();
			}
		}
		return "No such user";
	}

	/**
	 * Returns user's id if the email entered in is match to
	 * one of the user in ArrayList permanentUsers.
	 * Otherwise, return -1 for not found the user
	 * It is a getter for userId.
	 *
	 * @param email user's email that they entered id
	 * @return user's id or "not found" for not found
	 */
	public String getUserId(String email){
		for (PermanentUser pu: permanentUsers){
			if (pu.getEmail().equals(email))
				return pu.getUserId();
		}
		return "not found";
	}

	/**
	 * Returns true iff this user's id can be found in objects in permanentUsers.
	 *
	 * This method is to check if the the user with this id exists.
	 *
	 * @param id user's id
	 * @return true if it id exists
	 */
	public boolean checkId(String id){
		for (PermanentUser pu: permanentUsers){
			if (pu.getUserId().equals(id))
				return true;
		}
		return false;
	}
}
