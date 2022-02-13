package Controller;

import Entity.Schedule;
import UseCase.*;

import java.text.ParseException;


/**
 * A class called ScheduleSystem which is a controller of ScheduleManager.
 * It has 3 attribute tm, um and sm.
 *
 * ScheduleSystem can be created by the constructor immediately with the given attributes.
 *
 * tm: a TemplateManager
 * sm: a ScheduleManager
 * um: a UserManager
 *
 * @author Kexin Sha
 * @author Jessica Wang
 * @author Hilda Wang
 */
public class ScheduleSystem {
    private ScheduleManager sm;
    private UserManager um;
    private TemplateManager tm;




    /**
     * A constructor that create a ScheduleSystem with the parameter sm, um, tm.
     *
     * @param tm a TemplateManager
     * @param um a UseManager
     * @param sm a ScheduleManager
     */
    public ScheduleSystem(ScheduleManager sm, UserManager um, TemplateManager tm) {
        this.sm = sm;
        this.um = um;
        this.tm = tm;
    }


    /**
     * Creates a Schedule with the given attributes. Return true if the schedule is created successfully.
     * return false otherwise.
     *
     * @param userID the id of user
     * @param status public or private
     * @param scheduleName the name of the schedule
     * @param scheduleDate the date of schedule
     * @param templateID the id of the template
     *
     * @return boolean Return true if the schedule is created successfully. return false otherwise.
     *
     */
    public boolean createSchedule(String userID, String status, String scheduleName, String scheduleDate, Integer templateID) throws ParseException {
        if (um.getUserType(userID).equals("regular")|| um.getUserType(userID).equals("trial") ){
            try {
                sm.createSchedule(status, scheduleName, scheduleDate, userID, tm, templateID);
                return true;
            } catch (TemplateNotFoundException e) {
                System.out.println("Error: Template invalid!");
            }
        }
        else{
            System.out.println("Error: Action invalid! You are not a regular user!");
        }
        return false;
    }

    /**
     * Delete a schedule according to its schedule ID.
     * Return true if the schedule is deleted successfully. Return false otherwise.
     * @param scheduleID the id of the schedule
     * @param userID the id of the user
     *
     * It throws a ScheduleNotFoundException if the schedule is not found according to the schedule id.
     *
     * @return boolean  Return true if the schedule is deleted successfully. return false otherwise.
     *
     */
    public boolean deleteSchedule(Integer scheduleID, String userID) throws ScheduleNotFoundException {
        if (sm.deleteSchedule(scheduleID, userID)) {
            return true;
        } else {
            throw new ScheduleNotFoundException("Schedule is not found");
        }
    }

    /**
     * Add an event to the corresponding schedule.
     * Return true if the event is added successfully. Return false otherwise.
     * @param scheduleID the id of the schedule
     * @param userID the id of the user
     * @param eventName the name of the event
     * @param startTimeString the start time of the event
     * @param endTimeString the end time of the event
     *
     * It throws a ScheduleNotFoundException if the schedule is not found according to the schedule id.
     * It throws a TemplateNotFoundException if the template is not found according to the template id.
     *
     * @return boolean  Return true if the event is added successfully. return false otherwise.
     *
     */
    public boolean addEvent(String userID, int scheduleID, String eventName, String startTimeString, String endTimeString)
            throws ScheduleNotFoundException, BetweenException, DurationException, TemplateNotFoundException, ParseException,
            StartEndException {
        if (um.getUserType(userID).equals("regular") || um.getUserType(userID).equals("trial") ){
            if (sm.isPublic(scheduleID) || sm.checkScheduleBelongs(userID, scheduleID)){
                sm.addEvent(scheduleID, eventName, startTimeString, endTimeString, tm);
                return true;
        } else {
            System.out.println("admin user cannot add event.");
        }
    }
        return false;
    }

    /**
     * Display schedules in the public schedule list.
     *
     */
    public void displayPublicSchedule(){
        if (sm.getAllPublicSchedule().isEmpty()) {
            System.out.println("There is no public schedule.");
        }
        for (Schedule s : sm.getAllPublicSchedule()) {
            System.out.println(s);
        }
    }


    /**
     * Display schedules the particular user created.
     *
     * It throws a ScheduleNotFoundException if the schedule is not found according to the schedule id.
     *
     */
    public void displayUserSchedule(String userID) throws ScheduleNotFoundException {
        if (um.getUserType(userID).equals("regular")|| um.getUserType(userID).equals("trial") ) {
            for (Schedule s : sm.getAllUserSchedule(userID)) {
                System.out.println(s);
            }
        } else {
            System.out.println("admin user has no schedule to display.");
        }
    }

    public void displayScheduleEvents(Integer scheduleID, String userID) throws ScheduleNotFoundException, UserNotFoundException {
        if (sm.getScheduleEvents(scheduleID, userID).isEmpty()) {
            System.out.println("There is no event in this schedule.");
        }
        System.out.println(sm.getScheduleEvents(scheduleID, userID));
    }

    /**
     * Delete an event created by the user.
     * Return true if the event id deleted successfully. Return false otherwise.
     * @param scheduleID the id of the schedule
     * @param userID the id of the user
     * @param eventName the name of the event
     * @param startTimeString the start time of the event
     * @param endTimeString the end time of the event
     *
     * @return boolean.   Return true if the event is deleted successfully. return false otherwise.
     *
     */
    public boolean deleteEvent(String userID, int scheduleID, String eventName, String startTimeString, String endTimeString) throws ScheduleNotFoundException, ParseException {
        if (um.getUserType(userID).equals("regular") || um.getUserType(userID).equals("trial") ) {
            if (sm.checkScheduleBelongs(userID, scheduleID)) {
                return sm.deleteEvent(scheduleID, eventName, startTimeString, endTimeString);
            }
        } else {
            System.out.println("admin user cannot delete event.");
        }
        return false;
    }


}
