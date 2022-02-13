import Controller.Facade;
import Controller.LoginSystem;
import Controller.TextUI;
import UseCase.TemplateNotFoundException;
import UseCase.UserManager;

import java.io.IOException;

/**
 * This class is the Main class, a Controller, which has a main method to run this program.
 *
 * @author Kexin Sha
 * @author Jessica Wang
 * @author Hilda Wang
 * @author Siqing Xu
 * @author Qing Lyu
 * @author Zhen Cheng
 * @author Christine Chen
 * @author Chuanrun Zhang
 */
public class Main {

	/**
	 * UserManager object um, Facade object facade, LoginSystem object ls are created.
	 * TextUI object ui is created by passing ls and facade as parameters.
	 *
	 * This method is called when we runs this program.
	 * It calls the run method in ui to runs the text UI of this program.
	 *
	 * @param args is an argument
	 * @throws IOException when the UserManager object um or run method in ui throw IOException
	 */
	public static void main(String[] args) throws IOException{
		UserManager um = new UserManager();
		Facade facade = new Facade(um);
		LoginSystem ls = new LoginSystem(um);

		TextUI ui = new TextUI(ls, facade);
		ui.run();
	}
}
