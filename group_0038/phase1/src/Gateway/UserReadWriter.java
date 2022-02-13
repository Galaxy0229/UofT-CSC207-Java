package Gateway;

import Entity.PermanentUser;
import UseCase.UserManager;

import java.io.*;
import java.util.ArrayList;

/**
 * A representation of the class to save and read user data to the filePath, which is one of
 * the attribute.
 *
 * filePath: a string that represents the path of a file
 * um: an UserManager object
 *
 * @author Qing Lyu
 * @author Zhen Cheng
 */
public class UserReadWriter {
	private String filePath;
	private UserManager um;

	/**
	 * A constructor that creates an UserReadWriter object.
	 * It initializes an UserReadWriter object by passing filePath and um as parameters and assigns
	 * these values to attributes.
	 *
	 * If the file from the filePath does not exist or is empty, calls <code>createNewFile</code>
	 * Otherwise, calls the method readSerFile to read serializable objects
	 *
	 * @param filePath a string that represents the path of a file
	 * @param um the UserManager object
	 * @throws IOException when <code>createNewFile</code> throws IOException
	 */
	public UserReadWriter(String filePath, UserManager um) throws IOException {
		this.filePath = filePath;
		this.um = um;

		File file = new File(filePath);
		if (file.exists() && file.length() != 0) {
			readSerFile();
		} else {
			file.createNewFile();
		}
	}

	/**
	 * Reads the file with filePath and deserializes the read input and stores them to UserManager's attribute.
	 * If the filePath is wrong or the PermanentUser class is not found, this method
	 * will catch the exception and print out error messages.
	 *
	 * This method is called by the constructor when this UserReadWriter object is creating. It stores the input to
	 * the attribute of um, permanentUsers, by
	 * calling the setter setPermanentUsers of um
	 *
	 */
	public void readSerFile(){
		try {
			InputStream is = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(is);
			ObjectInput input = new ObjectInputStream(buffer);

			um.setPermanentUsers((ArrayList<PermanentUser>) input.readObject());
			input.close();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("We cannot read from input." + ex);
		}
	}

	/**
	 * Saves the user information to the filePath.
	 *
	 * This method is called at the time this user chooses to log out or exit.
	 * It saves and stores the data from the attribute of um by calling the getter
	 * getPermanentUsers.
	 *
	 * @throws IOException when the filePath is not found
	 */
	public void saveToFile() throws IOException {

		OutputStream file = new FileOutputStream(filePath);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);

		output.writeObject(um.getPermanentUsers());
		output.close();
	}
}
