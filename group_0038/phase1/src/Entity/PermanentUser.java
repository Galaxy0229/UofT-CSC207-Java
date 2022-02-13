package Entity;

import java.io.Serializable;

/**
 * A subclass of the parent class User.
 * This class create PermanentUser object for regular and admin users.
 * It has three attributes: email, password, and userType. Regular or
 * admin users must entered in this three arguments in order to create
 * account.
 *
 * email: the email entered by this user
 * password: the password entered by this user
 * userType: the type that this user wants to experience("admin" or "regular")
 *
 * @author Qing Lyu
 * @author Zhen Cheng
 */
public class PermanentUser extends User implements Serializable {
	private String email;
	private String password;
	private String userType;

	/**
	 * A constructor for PermanentUser.
	 * Create a PermanentUser object that with parameters user entered in.
	 * Also called <code>super</code> method to call the constructor of
	 * parent class User to generate an unique id for this user.
	 *
	 * @param email user's email
	 * @param password user's password
	 * @param userType the type of this user
	 */
	public PermanentUser(String email, String password, String userType){
		super();
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	/**
	 * Return a string that is the email of this user.
	 * It is a getter for email attribute
	 *
	 * @return user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Return a string that is the password of this user.
	 * It is a getter for password attribute
	 *
	 * @return password for this user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Return a string that is the type of this user.
	 * It is a getter for userType attribute
	 *
	 * @return type of this user
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Set email attribute to a new one that user entered in.
	 * It is a setter of email attribute
	 *
	 * @param email user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Set password attribute to a new one that user entered in.
	 * It is a setter for password attribute
	 *
	 * @param password user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
