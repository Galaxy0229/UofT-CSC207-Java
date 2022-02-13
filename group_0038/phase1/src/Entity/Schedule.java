package Entity;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.text.SimpleDateFormat;
/**
 * An abstract class that has subclasses which are DailySchedule and MonthlySchedule.
 * It initially has a unique id (starts from 10000), type (Daily or Monthly) and status (public or private).
 * events: a hashmap with key as name, values as list of list of start time and end time.
 * scheduleName: schedule's name.
 * scheduleDate: schedule's date.
 * author: user's id
 * status: either public or private.
 * scheduleID: unique id, use an iterator to generate id, starting from 10000.
 * @author Kexin Sha
 * @author Jessica Wang
 * @author Hilda Wang
 * @version 1.8.0
 */
public abstract class Schedule implements Serializable {

    private static int iterator = 10000; //Use an iterator to generate id, starting from 10000
    // the hashmap key is the event name
    // hashmap value is a list of list of start time and end time, which is the format "yyyy MM dd HH:mm“.
    protected HashMap<String, List<List<Date>>> events;
    protected String scheduleName;
    // If this is a daily schedule, then scheduleDate is the exact date of this schedule. e.g. yyyy mm dd
    // If this is a monthly schedule, then scheduleDate is the month and year. e.g. yyyy mm
    protected String scheduleDate;
    protected String author;
    // status: public OR private
    private String status;
    protected int scheduleID;
    private final String type = "Default";

    /**
     * Creates a schedule with a unique scheduleID assigned by iterator (starts from 10000),
     * whenever a schedule is created its scheduleID is one more than the last schedule's id.
     * @param scheduleName schedule's name
     * @param scheduleDate schedule's date in format (yyyy MM dd)
     * @param author id of user who creates this schedule
     * @param status schedule's status, either public or private.
     */
    public Schedule(String scheduleName, String scheduleDate, String author, String status){
        this.events = new HashMap<>();
        this.scheduleName = scheduleName;
        this.scheduleDate = scheduleDate;
        this.author = author;
        this.status = status;
        this.status = status;
        this.scheduleID = iterator;
        iterator++;
    }

    public static void setIterator(int iterator) {
        Schedule.iterator = iterator;
    }

    /**
     * Returns an HashMap called events of the schedule.
     * It is a getter method of events.
     * @return events of schedule
     */
    public HashMap<String, List<List<Date>>> getEvents() {
        return events;
    }

    /**
     * Sets the new schedule name.
     * It is a setter method of scheduleName.
     * @param scheduleName the new schedule name
     */
    // The method will be implemented later in phase 2.
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    /**
     * Gets the shedule date.
     * It is a getter method of scheduleDate.
     * @return scheduleDate
     */
    public String getScheduleDate() {
        return scheduleDate;
    }

    /**
     * Sets the new scheduleDate.
     * Setter of scheduleDate.
     * @param scheduleDate schedule's date
     */
    // The method will be implemented later in phase 2.
    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    /**
     * Gets author's id.
     * Getter of author.
     * @return user's id
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets status of schedule.
     * Getter of status.
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets schedule's id.
     * Getter of scheduleID.
     * @return schedule's id
     */
    public int getScheduleID() {
        return scheduleID;
    }

    /**
     * Sets status, if input is something other than "Public" or "Private", then nothing will happen.
     * Setter of status.
     * @param status the new status
     */
    public void setStatus(String status) {
        if (status.equals("public") || status.equals("private"))
            this.status = status;
    }

    /**
     * Sets events.
     * Setter of events.
     * @param events the new events HashMap
     */
    public void setEvents(HashMap<String, List<List<Date>>> events) {
        this.events = events;
    }

    /**
     * check if a event's start time and end time format is correct.
     * It is an abstract method, we will overwrite it in the subclass.
     * @param startTimeString start time of event
     * @param endTimeString end time of event
     * @return true if date format is correct, false is never returned.
     * @throws ParseException exception occur when date format is incorrect
     */
    public abstract boolean checkDateFormat(String startTimeString, String endTimeString) throws ParseException;


    /**
     * Gets type of schedule.
     * Getter of type.
     * @return schedule's type
     */
    public String getType() {
        return type;
    }

    /**
     * Return schedule's info, including scheduleID, author, type, scheduleName, scheduleDate.
     * @return schedule's info in a string.
     */
    public String toString() {
        return String.format("ID: %d, Owner: %s, Type: %s, Name: %s, DateRange: %s\n",
                scheduleID, author, type, scheduleName, scheduleDate);
    }
}