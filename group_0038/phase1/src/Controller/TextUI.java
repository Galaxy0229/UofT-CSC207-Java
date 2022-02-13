package Controller;

import Entity.Template;
import UseCase.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * This class is a controller for text UI, which is to interact with human-user by showing instructions
 * on console and getting user-input.
 *
 * ls: a LoginSystem object
 * facade: a Facade object
 * sc: a Scanner object to read input
 * userType: an attribute to store user's type
 * userId: an attribute to store user's id
 * templateId: an attribute to store template id
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
public class TextUI {
	private LoginSystem ls;
	private Facade facade;
	private Scanner sc;
	private String userType = "";
	private String userId;
	private int templateId;

	/**
	 * A constructor for TextUI object with parameters ls and facade passed in.
	 * It initializes the Scanner object sc.
	 *
	 * @param ls the controller loginSystem, created in Main class
	 * @param facade a Facade object
	 */
	public TextUI(LoginSystem ls, Facade facade) {
		this.ls = ls;
		this.facade = facade;
		sc = new Scanner(System.in);
	}

	/**
	 * Prints the welcome menu on console with instructions and gets user-input by letting users choose options.
	 * Users can choose to play as a trial user without sign up or log in, or sign up an account, or log in with their
	 * id and password, or exit.
	 *
	 * This method is called in Main to run this program.
	 *
	 * @throws IOException when <code>userCreation</code> and <code>process()</code> throws IOException
	 */
	public void run() throws IOException{
		String option;
		boolean notExit = true;
		System.out.println("Hi! Welcome to our Calendar and Scheduling App!");
		while(notExit) {
			System.out.println("Do you want to try as a trial user, sign up, or log in? \n" +
					"Enter '0' for trial user, \n'1' for sign up, \n'2' for log in, \n'3' to exit: ");
			option = sc.nextLine();

			if (option.equals("0")){
				userType = "trial";
				userId = ls.userCreation("", "", userType);
				process();
				break;
			}
			else if (option.equals("1")){
				while (true){
					System.out.println("Do you want to sign up as a regular user or admin user? \n" +
							"Enter '0' for regular user, \n'1' for admin user, \nor " +
							"anything else to return to the previous menu:");
					option = sc.nextLine();
					if (option.equals("0") || option.equals("1")){
						System.out.println("Please enter your email address: ");
						String email = sc.nextLine();
						if (!isValidEmail(email)){
							System.out.println("The email you entered is not a valid email! Please try again.");
						}else {
							System.out.println("Please enter the password: ");
							String pwd = sc.nextLine();
							if (option.equals("0"))
								userType = "regular";
							else
								userType = "admin";
							userId = ls.userCreation(email, pwd, userType);
							if (userId.equals("duplicate email"))
								System.out.println("The email you entered has been signed up! Please try again.");
						}
						if (!userId.equals("duplicate email") && !userId.equals("")){
							System.out.println("Sign up successfully! Your user id is: " + this.userId);
							System.out.println("Please remember your user id as you will need this to log in.");
							if (login(false) == 0){
								process();
								notExit = false;
							}
							break;
						}
					}
					else{
						break;
					}
				}
			}
			else if (option.equals("2")){
				if (login(true) == 0){
					process();
					break;
				}
			}
			else if (option.equals("3"))
				break;
			else{
				System.out.println("The option you entered does not exist! Please try again.");
			}
		}
	}

	/**
	 * Prints the main menu on console with instructions to let users choose the options they want.
	 * A trial user can choose to play with template or play with schedule.
	 * A regular user can choose to play with template, change password, change email, or play with schedule.
	 * An admin user can choose to play with template, change password, or change email.
	 * All three types of users can choose to exit the program.
	 *
	 * This method is being called by run method after user logged in.
	 * It shows different instruction for different types of users.
	 * When exit from this method, it means user logout and
	 * saves all the information to files outside of the program if this user is regular or admin.
	 *
	 * @throws IOException when <code>saveInfo()</code> throws IOException
	 */
	private void process() throws IOException {
		System.out.println("Welcome to main menu!");
		String option;
		while (true){
			if (userType.equals("regular")) {
				System.out.println("Do you want to play with template, change your password, " +
						"change email, play with schedule, or exit?");
				System.out.println("Enter '0' for play with template, \n'1' for change password, " +
						"\n'2' for change email, \n'3' for play with schedule, \nor '-1' for exit");
			}else if (userType.equals("trial")) {
				System.out.println("Do you want to play with template, play with schedule, or exit?");
				System.out.println("Enter '0' for play with template, \n'1' for play with schedule, \nor '-1' for exit");
			}else if (userType.equals("admin")) {
				System.out.println("Do you want to play with template, change your password, " +
						"change email, or exit?");
				System.out.println("Enter '0' for play with template, \n'1' for change password, " +
						"\n'2' for change email, \nor '-1' for exit");
			}
			option = sc.nextLine();
			if (option.equals("1")) {
				if (!userType.equals("trial")){
					System.out.println("Enter your new password: ");
					String newPwd = sc.nextLine();
					ls.pwdChange(userId, newPwd);
					System.out.println("Password Changed Successfully!");
					ls.saveInfo();
				}else{
					runSchedule();
				}
			} else if (option.equals("2") && !userType.equals("trial")) {
				System.out.println("Enter your new email: ");
				String newEmail = sc.nextLine();
				if (!isValidEmail(newEmail)){
					System.out.println("The new email you entered is not a valid email! Please try again.");
				}
				else if (!ls.emailChange(userId, newEmail)) {
					System.out.println("This email has been signed up! Please use another one.");
				}else{
					System.out.println("Email Changed Successfully!");
					ls.saveInfo();
				}
			} else if (option.equals("0")) {
				if (!userType.equals("admin")){
					selectTemplate();
				}
				else{
					editTemplate();
				}
			} else if (option.equals("3")) {
				runSchedule();
			} else if (option.equals("-1")){
				ls.saveInfo();
				break;
			}
			else{
				System.out.println("The option you entered does not exist! Please try again.");
			}
		}
	}

	/**
	 * Return 0 when user login successfully, and -1 to return to the previous menu, which is the welcome menu.
	 * Call this method when a user wants to login in run method.
	 *
	 * @param logDirect user directly login from welcome menu
	 * @return int 0 for login successfully and -1 for return to the previous menu.
	 */
	private int login(boolean logDirect) {
		while (true){
			String option;
			if (!logDirect){
				System.out.println("Enter '0' to log in or enter any other thing to return to the previous menu: ");
				option = sc.nextLine();
			}
			else{
				option = "0";
				logDirect = false;
			}
			if (option.equals("0")) {
				String pwd;
				System.out.println("Please enter your id: ");
				userId = sc.nextLine();
				if (!ls.idExist(userId)) {
					System.out.println("The id you entered does not exist! Please try again.");
				} else {
					System.out.println("Please enter your password: ");
					pwd = sc.nextLine();
					if (ls.idLogin(userId, pwd)) {
						System.out.println("Log in successfully!");
						userType = ls.getType(userId);
						return 0;
					} else {
						System.out.println("Your password is incorrect! Please try again.");
					}
				}
			}
			else {
				return -1;
			}
		}
	}

	/**
	 * A method for admin users which is used to create default templates, or see all templates and modify them.
	 * Enter '1' and then enter template type to create a default template.
	 * Enter '2' to see all template, and then enter the id chosen or enter '0' to exit. Enter id which doesn't
	 * exist will throw TemplateNotFoundException. If id has an invalid format it will throw NumberFormatException.
	 * They will both be caught and corresponding error messages will be printed on console.
	 * After choosing a template, enter number to set the value
	 * of attributes stored in the chosen template.
	 * Enter '-1' to delete the chosen template.
	 * Enter '3' to exit.(return to the main menu)
	 * Enter any other option will go back to the start of the while loop.
	 * option: the number client entered.
	 */
	private void editTemplate(){
		while (true){
			System.out.println("Enter '1' to create a default template, \n" +
					"'2' to see all the template and/or modify the chosen one, \n" +
					"'3' to return to the main menu: ");
			String option = sc.nextLine();
			if(option.equals("1")){
				System.out.println("Please enter template type: MonthlyTemplate or DailyTemplate");
				String tempType = sc.nextLine();
				if (tempType.equals("MonthlyTemplate") || tempType.equals("DailyTemplate")) {
					facade.createTemp(tempType);
					System.out.println("A default Template is created successfully!");
				}else
					System.out.println("The template type you entered is incorrect! Please try again.");
			}
			else if (option.equals("2")) {
				facade.getTempData();
				System.out.println("Please enter the id of the template you want to modify, \n" +
						"enter '0' to return to the main menu, \n" +
						"enter '-1' to delete template: ");
				String option2 = sc.nextLine();
				if (option2.equals("-1")) {
					while (true) {
						System.out.println("Please enter the id of the template you want to delete, \n" +
								"enter '0' to return to the main menu: ");
						option2 = sc.nextLine();
						if (option2.equals("0")){
							facade.exitTemp();
							break;
						}
						try {
							facade.ts.delete(Integer.parseInt(option2));
							facade.exitTemp();
							System.out.println("The template has successfully deleted. Now back to the main menu.");
							break;
						} catch (TemplateNotFoundException e) {
							System.out.println("The template id does not exist. Please enter a existed one.");
						}
					}
					break;
				} else if (option2.equals("0")) {
					facade.exitTemp();
					break;
				} else {
					try {
						Template t = facade.tm.getTemplateById(Integer.parseInt(option2));
						while (true){
							System.out.println("Please enter the minimum time between events(unit hour): ");
							try {
								option2 = sc.nextLine();
								t.setMinTimeBtwEvents(Double.parseDouble(option2));
								break;
							} catch (NumberFormatException e) {
								System.out.println("Fail to change. Please input a number representing the MinTimeBtwEvents. ");
							}
						}
						while (true){
							System.out.println("Please enter the maximum time of an event(unit hour): ");
							try {
								option2 = sc.nextLine();
								t.setMaxTimeOfEvent(Double.parseDouble(option2));
								break;
							} catch (NumberFormatException e) {
								System.out.println("Fail to change. Please input a number representing the MaxTimeOfEvent. ");
							}
						}
						while (true){
							System.out.println("Please enter the minimum time of an event(unit hour): ");
							try {
								String option3 = sc.nextLine();
								if (Double.parseDouble(option3) <= Double.parseDouble(option2)) {
									t.setMinTimeOfEvent(Double.parseDouble(option3));
									break;
								} else {
									System.out.println("Fail to change. Please enter the minimum time which is smaller than or equal to the maximum time.");}
							} catch (NumberFormatException e) {
								System.out.println("Fail to change. Please input a number representing the MinTimeOfEvent. ");
							}
						}
						facade.exitTemp();
					} catch (TemplateNotFoundException e) {
						System.out.println("The template id is not valid. Please try again.");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			else if (option.equals("3")){
				facade.exitTemp();
				break;
			}
			else
				System.out.println("The option you entered does not exist! Please try again.");
		}

	}

	/**
	 * A method for regular/trial users which is used to see all templates and choose one to create their schedules.
	 * Enter '1' to see all templates. And then enter the id of the template chosen.
	 * catch NumberFormatException when the client enter a non-integer template id.
	 * If a valid template id is chosen, calls runSchedule method to show latter instructions.
	 * Enter '2' to exit.
	 * Enter any other option will go back to the start of the while loop.
	 */
	private void selectTemplate(){
		while (true){
			System.out.println("Enter '1' to see all the template and select one, \n" +
					"'2' to return to the main menu:");
			String option = sc.nextLine();
			if (option.equals("1")) {
				facade.getTempData();
				System.out.println("Enter the id of the template you want to choose");
				try {
					Integer potential = Integer.parseInt(sc.nextLine());
					if (facade.tm.checkExistedTemplate(potential)){
						templateId = potential;
						System.out.println("You have chosen template id: " + templateId + ".");
						runSchedule();
						break;
					}
					else {
						System.out.println("The template id does not exist. Please enter a existed one.");
					}
				} catch (NumberFormatException e) {
					System.out.println("You need to input a number representing the template id.");
				}
			}
			else if (option.equals("2")){
				facade.exitTemp();
				break;
			}
			else
				System.out.println("The option you entered does not work! Please try again.");
		}
	}

	/**
	 * A method for regular/trial users which is used to run the schedules.
	 *
	 * Enter '1' to create schedule. Need to enter information of schedule name, schedule status, and also schedule
	 * date according to the template that the user has chosen. If the date information is not correct, throws
	 * ParseException. If no template is chosen, throws TemplateNotFoundException. If the input is invalid, user
	 * is allowed to type the input again, till it is valid.
	 *
	 * Enter '2' to display public schedules.
	 *
	 * Enter '3' to display user's schedules, might throw ScheduleNotFoundException.
	 *
	 * Enter '4' to delete schedule. Need to input the id of schedule that user wants to delete.
	 * If there is no such a schedule or the id format is not correct, throws ScheduleNotFoundException or
	 * NumberFormatException. If the input is invalid, user is allowed to type the input again, till it is valid.
	 *
	 * Enter '5' to see inside or edit one of your schedule. Need to input the id of the schedule that user
	 * wants to see or edit. If there is no such a schedule or the id format is not correct,
	 * throws ScheduleNotFoundException or NumberFormatException. If the input is invalid, user is allowed to
	 * type the input again, till it is valid.
	 *
	 * Enter '6' to return to the main menu.
	 *
	 * Other options are not accepted. User is allowed to type the option again till it is valid.
	 */
	private void runSchedule() {
		boolean bool = true;
		while (bool) {
			System.out.println("\n Enter '1' to create schedule,\n '2' to display public schedules,\n " +
					"'3' to display my schedules,\n '4' to delete schedule, \n " +
					"'5' to see inside or edit one of your schedule, \n " +
					"'6' to return to the main menu: ");
			String option = sc.nextLine();
			if (option.equals("1")) {
				System.out.println("Please enter schedule name: \n (please make sure you have selected a template " +
						"in 'Play with Templates' module.)");
				String scheduleName = sc.nextLine();
				String status;
				while (true) {
					System.out.println("Please enter schedule status: public or private");
					status = sc.nextLine();
					if (!(status.equals("public") || status.equals("private"))) {
						System.out.println("Input invalid.");
					} else {
						break;
					}
				}
				while (true) {
					try {
						String scheduleDate;
						if (facade.tm.getTemplateById(templateId).getTemplateType().equals("DailyTemplate")) {
							System.out.println("Please enter schedule date: yyyy MM dd");
						} else {
							System.out.println("Please enter schedule month: yyyy MM");
						}
						scheduleDate = sc.nextLine();
						if (facade.ss.createSchedule(userId, status, scheduleName, scheduleDate, templateId)) {
							System.out.println("New schedule is created.");
							if (!(ls.getType(userId).equals("trial"))){
								facade.saveSchedule(userId);
							}
							break;
						} else {
							System.out.println("Fail to create new schedule.");
						}

					} catch (ParseException e) {
						System.out.println("Schedule date or month is invalid.");
					} catch (TemplateNotFoundException e) {
						System.out.println("You have not selected a template. Please select one first. You can choose " +
								"'play with template' to select one.");
						bool = false;
						break;
					}
				}
			} else if (option.equals("2")) {
				facade.ss.displayPublicSchedule();

			} else if (option.equals("3")) {
				try {
					facade.ss.displayUserSchedule(userId);
				} catch (ScheduleNotFoundException e) {
					System.out.println(e.getMessage());
				}
			} else if (option.equals("4")) {
				try {
					facade.ss.displayUserSchedule(userId);
				} catch (ScheduleNotFoundException e) {
					System.out.println(e.getMessage());
					break;
				}
				while (true) {
					System.out.println("Input the id of schedule that you want to delete:");
					String scheduleID = sc.nextLine();
					try {
						if (facade.ss.deleteSchedule(Integer.parseInt(scheduleID), userId)) {
							System.out.println("Delete successfully.");
						}
						break;
					} catch (NumberFormatException e) {
						System.out.println("You need to input a number representing the schedule id.");
					} catch (ScheduleNotFoundException e) {
						System.out.println("Schedule is not found.");
					}
				}

			} else if (option.equals("5")) {
				String input_ScheduleId;
				int scheduleId = 0;
				boolean want_to_exit = false;
				while (true) {
					try {
						try {
							facade.ss.displayUserSchedule(userId);
						} catch (ScheduleNotFoundException e) {
							System.out.println(e.getMessage());
						}
						System.out.println("The above are the schedules you have.");
						System.out.println("Please input the id of the schedule you want to see or edit, " +
								"or enter 1 to return to schedule menu.");
						String choice = sc.nextLine();
						if (choice.equals("1")) {
							want_to_exit = true;
							break;
						} else {
							input_ScheduleId = choice;
						}

						scheduleId = Integer.parseInt(input_ScheduleId);
						if (!(facade.sm.checkScheduleBelongs(userId, scheduleId))) {
							System.out.println("You don't own this schedule!");
						} else {
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println("You need to input a number representing the schedule id.");
					}
				}
				if (!want_to_exit) {
					editSchedule(scheduleId);
					if (!(ls.getType(userId).equals("trial"))){
						facade.saveSchedule(userId);
					}
				}

			} else if (option.equals("6")) {
				facade.saveSchedule(userId);
				break;
			} else {
				System.out.println("The option you entered does not exist! Please try again.");
			}
		}
	}

	/**
	 * A helper method for editing certain schedule.
	 *
	 * Enter '1' to create a new event. Need to input the name of a new event user wants to create, or input the name
	 * of the event user has already created. Then input start time and end time of the event. Might throw
	 * ParseException, TemplateNotFoundException, BetweenException, DurationException, StartEndException,
	 * or ScheduleNotFoundException. If the input is invalid, user is allowed to type the input again till it is valid.
	 *
	 * Enter '2' to delete an existing event. Need to input the name of the event user wants to delete,
	 * then input start time and end time of the event. Might throw ParseException or ScheduleNotFoundException.
	 * If the input is invalid, user is allowed to type the input again till it is valid.
	 *
	 * '3' to update status of this schedule. Need to input the schedule status user wants to change this schedule to.
	 * If the input is invalid, user is allowed to type the input again till it is valid.
	 *
	 * Enter '4' to display all the events in this schedule. Might throw ScheduleNotFoundException and
	 * UserNotFoundException.
	 *
	 * Enter '5' return to the schedules menu.
	 *
	 * Other options are not accepted. User is allowed to type the option again till it is valid.
	 */
	private void editSchedule(int scheduleId){
		while (true) {
			System.out.println("\n Enter '1' to create a new event, or add another time for an already added event,\n " +
					"'2' to delete an existing event,\n " +
					"'3' to update status of this schedule,\n " +
					"'4' to display all the events in this schedule, \n" +
					"'5' to return to the schedules menu: ");
			String option = sc.nextLine();
			if (option.equals("1")) {
				System.out.println("Please input the name of a new event you want to create,\n" +
						"or input the name of the event you have already created:");
				String eventName = sc.nextLine();
				while (true) {
					try {
						String startTimeString;
						String endTimeString;
						if (facade.sm.getScheduleByID(scheduleId).getType().equals("Daily")) {
							System.out.println("Please input the start time of this new event: hh:mm \n " +
									"(Please use 24 hour format.)");
							startTimeString = sc.nextLine();
							System.out.println("Please input the end time of this new event: hh:mm \n" +
									"(Please use 24 hour format.)");
						} else {
							System.out.println("Please input the start time of this new event: dd hh:mm \n" +
									"(Please use 24 hour format.)");
							startTimeString = sc.nextLine();
							System.out.println("Please input the end time of this new event: dd hh:mm \n" +
									"(Please use 24 hour format.)");
						}
						endTimeString = sc.nextLine();
						if (facade.ss.addEvent(userId, scheduleId, eventName, startTimeString, endTimeString)) {
							System.out.println("You have added new time to this event successfully.");
							break;
						} else {
							System.out.println("Nothing changes.");
						}

					} catch (ParseException | TemplateNotFoundException | BetweenException | DurationException |
							StartEndException | ScheduleNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
			} else if (option.equals("2")) {
				System.out.println("Please input the event name of the event you want to delete: ");
				String eventName = sc.nextLine();
				while (true) {
					try {
						String startTimeString;
						String endTimeString;
						if (facade.sm.getScheduleByID(scheduleId).getType().equals("Daily")){
							System.out.println("Please input the start time of the event you want to delete: hh:mm \n " +
									"(Please use 24 hour format.)");
							startTimeString = sc.nextLine();
							System.out.println("Please input the end time of the event you want to delete: hh:mm \n " +
									"(Please use 24 hour format.)");
						} else {
							System.out.println("Please input the start time of the event you want to delete: dd hh:mm \n" +
									"(Please use 24 hour format.)");
							startTimeString = sc.nextLine();
							System.out.println("Please input the end time of the event you want to delete: dd hh:mm \n" +
									"(Please use 24 hour format.)");
						}
						endTimeString = sc.nextLine();
						if (!(facade.ss.deleteEvent(userId, scheduleId, eventName, startTimeString, endTimeString))) {
							System.out.println("The event you want to delete does not exist!");
						} else {
							System.out.println("event successfully deleted");
						}
						break;

					} catch (ParseException | ScheduleNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
			} else if (option.equals("3")){
				while (true) {
					System.out.println("Please enter schedule status you want to change to: public or private");
					String status = sc.nextLine();
					if (!(status.equals("public") || status.equals("private"))) {
						System.out.println("Input invalid.");
					} else {
						facade.sm.changeStatus(scheduleId, userId, status);
						System.out.println("Status successfully updated!");
						break;
					}
				}
			}else if (option.equals("4")){
				try {
					facade.ss.displayScheduleEvents(scheduleId, userId);
				} catch (ScheduleNotFoundException | UserNotFoundException e) {
					System.out.println(e.getMessage());
				}
			} else if (option.equals("5")){
				if (!(ls.getType(userId).equals("trial"))){
					facade.saveSchedule(userId);
				}
				break;
			}
			else
				System.out.println("The option you entered does not exist! Please try again.");
		}
	}

	/**
	 * Return true iff email entered in is a valid email address
	 * Otherwise return false.
	 *
	 * This method uses a regular expression for String object to check.
	 * It is a helper method which is called when user signs up or changes email.
	 * A valid email would have this format: at least one letter or number or any character from '_', '+', '&', '*',
	 * '-' followed by a '@' and at least one letter or number and a dot '.' and at least one letter or number.
	 *
	 * @param email user's email address
	 * @return true if this email is a valid email address
	 */
	private boolean isValidEmail(String email) {
		return email.matches("^[a-zA-Z0-9_+&*-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$");
	}
}




