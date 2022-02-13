package Entity;

import java.util.UUID;

/**
 * A parent class that has PermanentUser class as its child.
 * This class creates User for trial users.
 * It has an userId attribute.
 *
 * userId: a randomly generated unique id for this trial users generated by <code>UUID</code>.
 * @author Qing Lyu
 * @author Zhen Cheng
 */
public class User{
	private String userId;

	/**
	 * A constructor of User object.
	 * It creates a User with a unique userId assigned by iterator which
	 * starts from 10000.
	 * It created whenever a user is created, its templateID is one more
	 * than the last user's id.
	 */
	public User(){
		userId = UUID.randomUUID().toString();
	}

	/**
	 * Return this user's id
	 * It is a getter for userId attribute
	 *
	 * @return user's unique id
	 */
	public String getUserId() {
		return userId;
	}
}
