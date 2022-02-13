package Entity;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An subclass of the parent class Schedule.
 * It initially has a unique id (starts from 10000), type (Monthly) and status (public or private).
 *
 * events: a hashmap with key as name, and value is in the format of list of list of start time("yyyy MM dd HH:mm“) and end time("yyyy MM dd HH:mm“).
 * scheduleName: schedule's name.
 * scheduleDate: schedule's date, in format of 'yyyy MM'
 * author: user's id
 * status: either public or private.
 * scheduleID: unique id, use an iterator to generate id, starting from 10000.
 * @author Kexin Sha
 * @author Jessica Wang
 * @author Hilda Wang
 * @version 1.8.0
 */
public class MonthlySchedule extends Schedule implements Serializable {
    private final String type = "Monthly";

    /**
     * Inherits the method of the super class Template.
     * Creates a schedule with a unique scheduleID assigned by iterator (starts from 10000),
     * whenever a schedule is created its scheduleID is one more than the last schedule's id.
     * @param scheduleName schedule's name
     * @param scheduleDate schedule's date in format (yyyy MM)
     * @param author id of user who creates this schedule
     * @param status schedule's status, either public or private.
     */
    public MonthlySchedule(String scheduleName, String scheduleDate, String author, String status) {
            super(scheduleName, scheduleDate, author, status);
        }

    public String getType() {
        return type;
    }

    /**
     * check if a event's start time and end time format is correct(dd HH:mm).
     * @param startTimeString start time of event
     * @param endTimeString end time of event
     * @return true if date format is correct, false is never returned.
     * @throws ParseException exception occur when date format is incorrect
     */
    public boolean checkDateFormat(String startTimeString, String endTimeString)throws ParseException {
        Date dateST = new SimpleDateFormat("dd HH:mm").parse(startTimeString);
        Date dateET = new SimpleDateFormat("dd HH:mm").parse(endTimeString);
        return true;
    }

    public String toString() {
        return String.format("ID: %d, Owner: %s, Type: %s, Name: %s, DateRange: %s\n",
                scheduleID, author, type, scheduleName, scheduleDate);
    }

}
