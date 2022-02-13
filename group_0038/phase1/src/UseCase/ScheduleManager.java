package UseCase;

import Entity.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A class which is used to edit schedule and edit events in schedule.
 * Can be created by the constructor immediately with the given HashMap schedulesList and scheduleTempMap.
 * schedulesList: HashMap that map userID to the list of schedules they created.
 * scheduleTempMap: HashMap that map ScheduleID to template name.
 */
public class ScheduleManager {
    // map userID to the list of schedules they created
    private HashMap<String, List<Schedule>> schedulesList;
    // map ScheduleID to template name
    private HashMap<Integer, Integer> scheduleTempMap;

    /**
     * constructs ScheduleManager with schedulesList and scheduleTempMap.
     * @param schedulesList HashMap that map userID to the list of schedules they created.
     * @param scheduleTempMap HashMap that map ScheduleID to template name.
     */
    public ScheduleManager(HashMap<String, List<Schedule>> schedulesList, HashMap<Integer, Integer> scheduleTempMap){
        this.schedulesList = schedulesList;
        this.scheduleTempMap = scheduleTempMap;
    }

    /**
     * Setter for iterator.
     * @param iterator that helps creating schedule id.
     */
    public void setIterator(int iterator) {
        Schedule.setIterator(iterator);
    }

    /**
     * Gets schedulesList.
     * Getter of schedulesList.
     * @return HashMap that map userID to the list of schedules they created.
     */
    public HashMap<String, List<Schedule>> getSchedulesList() {
        return schedulesList;
    }

    /**
     * Getter of scheduleTempMap.
     * @return HashMap that map ScheduleID to template id.
     */
    public HashMap<Integer, Integer> getScheduleTempMap() {
        return scheduleTempMap;
    }

    /**
     * delete a schedule with given scheduleID and userID, return true if delete successfully, false otherwise.
     * @param scheduleID schedule's id.
     * @param userID user's id.
     * @return return true if delete successfully, false otherwise.
     */
    public boolean deleteSchedule(Integer scheduleID, String userID){
        if (schedulesList.containsKey(userID)){
            for (Schedule s : schedulesList.get(userID)) {
                if (s.getScheduleID() == scheduleID) {
                    schedulesList.get(userID).remove(s);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Given scheduleID, userID, change the matched schedule to new status.
     * @param scheduleID schedule's id.
     * @param userID user's id.
     * @param status either 'Public' or 'Private'. If it is something other than "Public" or "Private", then nothing will happen
     */
    public void changeStatus(Integer scheduleID, String userID, String status){
        if (schedulesList.containsKey(userID)){
            for (Schedule s: schedulesList.get(userID)){
                if (s.getScheduleID() == scheduleID){
                    s.setStatus(status);
                }
            }
        }

    }

    /**
     * add event to a given schedule, return true if event is successfully added.
     * @param scheduleID schedule's id.
     * @param eventName name of event that is going to be added.
     * @param startTimeString start time of event, in format of (dd HH:mm) for MonthlySchedule, (HH:mm) for DailySchedule.
     * @param endTimeString end time of event, in format of (dd HH:mm) for MonthlySchedule, (HH:mm) for DailySchedule.
     * @param t TemplateManager
     * @return true if add event successfully, false otherwise.
     * @throws ParseException when start time and end time not in correct format
     * @throws TemplateNotFoundException which is threw from helper validEvent.
     * @throws BetweenException when Between time is too short.
     * @throws DurationException when duration is too short or too long.
     * @throws StartEndException when the start time is after end time.
     * @throws ScheduleNotFoundException when the given scheduleID is not valid.
     */
    //
    public boolean addEvent(int scheduleID, String eventName, String startTimeString, String endTimeString, TemplateManager t)
            throws ParseException, TemplateNotFoundException, BetweenException, DurationException, StartEndException, ScheduleNotFoundException {
        Schedule s = getScheduleByID(scheduleID);
        List<Date> DateList = new ArrayList<>();
        if (s.checkDateFormat(startTimeString, endTimeString)){
            String dateFormat = "yyyy MM dd HH:mm";
            isDateValid(getScheduleByID(scheduleID).getScheduleDate() + " " + startTimeString, dateFormat);
            isDateValid(getScheduleByID(scheduleID).getScheduleDate() + " " + endTimeString, dateFormat);
            Date dateST = new SimpleDateFormat("yyyy MM dd HH:mm").parse(getScheduleByID(scheduleID).getScheduleDate() + " " + startTimeString);
            Date dateET = new SimpleDateFormat("yyyy MM dd HH:mm").parse(getScheduleByID(scheduleID).getScheduleDate() + " " + endTimeString);
            DateList.add(dateST);
            DateList.add(dateET);
        }
        if (validEvent(s, t, DateList)){
            HashMap<String, List<List<Date>>> events = s.getEvents();
            if (events.containsKey(eventName)){
                events.get(eventName).add(DateList);
            }
            else{
                List<List<Date>> StartEnd = new ArrayList<>();
                StartEnd.add(DateList);
                events.put(eventName, StartEnd);
            }
            s.setEvents(events);
            return true;}
        // will never return false -> will throw exception instead
        return false;
    }


    /**
     * check whether the the given DateList is valid for a event that can be added to the given schedule.
     * @param s Schedule.
     * @param t TemplateManager.
     * @param DateList List of dates which include start time and end time.
     * @return true if the DateList is valid, but never returns false.
     * @throws TemplateNotFoundException when there is no matching template with the schedule.
     * @throws StartEndException when the start time is after end time.
     * @throws DurationException when duration is too short or too long.
     * @throws BetweenException when Between time is too short.
     */
    public boolean validEvent(Schedule s, TemplateManager t, List<Date> DateList) throws TemplateNotFoundException,
            StartEndException, DurationException, BetweenException {
        int tempID = scheduleTempMap.get(s.getScheduleID());
        Template temp = t.getTemplateById(tempID);
        double max = temp.getMaxTimeOfEvent();
        double min = temp.getMinTimeOfEvent();
        double btw = temp.getMinTimeBtwEvents();
        Date time1 = DateList.get(0);
        Date time2 = DateList.get(1);
        if (time1.after(time2)) {
            throw new StartEndException("Error: The start time is after the end time.");
        }
        double diff = (time2.getTime() - time1.getTime());
        double denominator = (60 * 60 * 1000);
        double duration = diff / denominator;
        System.out.println("duration: " + duration * 60 + " minutes");
        if (duration < min) {
            throw new DurationException("Error: Duration is too short.");
        } else if (duration > max) {
            throw new DurationException("Error: Duration is too long.");
        }
        if (btw == -1) {
            return true;
        }
        HashMap<String, List<List<Date>>> events = s.getEvents();
        Collection<List<List<Date>>> all_time = events.values();
        for (List<List<Date>> event_time_list : all_time) {
            for (List<Date> event_time : event_time_list) {
                if (event_time.get(0).after(time1) && btw > event_time.get(0).getTime() - time2.getTime()) {
                    throw new BetweenException("Error: Between time is too short, move event forward.");
                }
                if (time1.after(event_time.get(0)) && btw > time1.getTime() - event_time.get(1).getTime()) {
                    throw new BetweenException("Error: Between time is too short, move event afterward.");
                }
            }
        }
        return true;
    }

    /**
     * delete event from given schedule, return true if delete successfully.
     * @param scheduleID schedule's id.
     * @param EventName name of event that is going to be deleted.
     * @param startTimeString start time of event that is going to be deleted, in format (HH:mm) for DailySchedule, (dd HH:mm) for MonthlySchedule.
     * @param endTimeString end time of event that is going to be deleted.
     * @return true if the event is successfully deleted.
     * @throws ParseException when startTimeString or endTimeString is not in correct format.
     * @throws ScheduleNotFoundException when scheduleID does not exist.
     */
    public boolean deleteEvent (int scheduleID, String EventName, String startTimeString, String endTimeString) throws ParseException, ScheduleNotFoundException {
        Schedule s = getScheduleByID(scheduleID);
        HashMap<String, List<List<Date>>> events = s.getEvents();
        if (!(events.containsKey(EventName))){
            return false;
        }
        Date dateST;
        Date dateET;
        List<List<Date>> listOfDates = events.get(EventName);
        s.checkDateFormat(startTimeString, endTimeString);
        if (s.getType().equals("Daily")) {
            dateST = new SimpleDateFormat("yyyy MM dd HH:mm").parse(s.getScheduleDate() + " " + startTimeString);
            dateET = new SimpleDateFormat("yyyy MM dd HH:mm").parse(s.getScheduleDate() + " " + endTimeString);
        }
        else{
            dateST = new SimpleDateFormat("yyyy MM dd HH:mm").parse(s.getScheduleDate() + " " + startTimeString);
            dateET = new SimpleDateFormat("yyyy MM dd HH:mm").parse(s.getScheduleDate() + " " + endTimeString);
        }
        for (List<Date> time: listOfDates){
            if (time.get(0).equals(dateST) && time.get(1).equals(dateET)){
                listOfDates.remove(time);
                if (listOfDates.isEmpty()){
                    events.remove(EventName);
                }
                s.setEvents(events);
                return true;
            }
        }
        return false;
    }

    /**
     * create new schedule with given name, date, author, template
     * @param status schedule's stauts, either Public or Private.
     * @param scheduleName schedule's name.
     * @param scheduleDate schedule's date, in format of (yyyy-MM-dd) for DailySchedule, (yyyy-MM) for MonthlySchedule.
     * @param author user's id.
     * @param t TemplateManager.
     * @param templateId id of template that is used as basis to create this schedule.
     * @throws TemplateNotFoundException when the given id does not exist.
     * @throws ParseException when scheduleDate is in incorrect format.
     */
    public void createSchedule(String status, String scheduleName, String scheduleDate, String author, TemplateManager t,
                               Integer templateId) throws TemplateNotFoundException, ParseException {
        Template temp = t.getTemplateById(templateId);
        String type = temp.getTemplateType();
        Schedule s;
        if (type.equals("DailyTemplate")){
            String dateFormat = "yyyy MM dd";
            isDateValid(scheduleDate, dateFormat);
            s = new DailySchedule(scheduleName, scheduleDate, author, status);
        }
        else{
            String dateFormat = "yyyy MM";
            isDateValid(scheduleDate, dateFormat);
            s = new MonthlySchedule(scheduleName, scheduleDate, author, status);
        }
        if (schedulesList.containsKey(author)){
            schedulesList.get(author).add(s);
        }
        else{
            List<Schedule> newlist = new ArrayList<>();
            newlist.add(s);
            schedulesList.put(author,newlist);
        }

        scheduleTempMap.put(s.getScheduleID(), templateId);
    }

    /**
     * Return all schedules that are created by the given user id.
     * @param userID user's id.
     * @return list of schedules.
     * @throws ScheduleNotFoundException when schedulesList is empty.
     */
    public List<Schedule> getAllUserSchedule(String userID) throws ScheduleNotFoundException {
        List<Schedule> user_schedule = new ArrayList<>();
        if (!(schedulesList.containsKey(userID))|| schedulesList.get(userID).isEmpty()) {
            throw new ScheduleNotFoundException("You don't have any schedules.");
        }
        for (Schedule s: schedulesList.get(userID)) {
            user_schedule.add(s);
        }
        return user_schedule;
    }

    /**
     * Display the schedule events by the given schedule id.
     * @param scheduleID schedule's id.
     * @param userID user's id.
     * @return schedule's events.
     * @throws ScheduleNotFoundException No schedule created by the user's id has the same schedule's id.
     * @throws UserNotFoundException user's id does not exist.
     */
    public HashMap<String, List<List<Date>>> getScheduleEvents(Integer scheduleID, String userID) throws ScheduleNotFoundException, UserNotFoundException {
        HashMap<String, List<List<Date>>> schedule_events = new HashMap<>();
        boolean scheduleID_exist = false;
        if (schedulesList.containsKey(userID)) {
            for (Schedule s : schedulesList.get(userID)) {
                if (s.getScheduleID() == scheduleID) {
                    scheduleID_exist = true;
                    schedule_events = s.getEvents();
                }
            }
            if (!scheduleID_exist) {throw new ScheduleNotFoundException("schedule ID not found.");}
            return schedule_events;
        } else {throw new UserNotFoundException("user ID not found.");}
    }


    /**
     * Gets all public schedules in a list.
     * @return list of public schedules.
     */
    public List<Schedule> getAllPublicSchedule() {
        List<Schedule> public_schedule = new ArrayList<>();
        for (List<Schedule> s_list : schedulesList.values()) {
            for (Schedule s : s_list) {
                if (s.getStatus().equals("public")) {
                    public_schedule.add(s);
                }
            }
        }
        return public_schedule;
    }


    /**
     * Checks whether there exists schedule with the given scheduleID belongs to the user with the given userID.
     * @param userID user's id.
     * @param scheduleID schedule's id.
     * @return true iff this schedule belongs to the user.
     */
    public boolean checkScheduleBelongs(String userID, int scheduleID){
        if (schedulesList.containsKey(userID)){
            for (Schedule s: schedulesList.get(userID)){
                if (s.getScheduleID() == scheduleID){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the schedule with the given ScheduleID is public.
     * @param scheduleID shcedule's id.
     * @return true iff this schedule is public.
     */
    public boolean isPublic(int scheduleID){
        for (List<Schedule> s_list: schedulesList.values()){
            for (Schedule s: s_list){
                if (s.getScheduleID() == scheduleID && s.getStatus().equals("public")){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Return the schedule with the given scheduleID.
     * @param scheduleID schedule's id.
     * @return true iff the schedule exist, but never returns false.
     * @throws ScheduleNotFoundException when the given scheduleID does not exist.
     */
    public Schedule getScheduleByID(int scheduleID) throws ScheduleNotFoundException {
        for (List<Schedule> s_list: schedulesList.values()){
            for (Schedule s: s_list){
                if (s.getScheduleID() == scheduleID){
                    return s;
                }
            }
        }
        throw new ScheduleNotFoundException("This schedule does not exist");
    }

    /**
     * Checks if the user input date or time format is correct.
     * @param date user input date or time.
     * @param dateFormat correct date format.
     * @throws ParseException when the time format is incorrect.
     */
    // reference: https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
    private static void isDateValid(String date, String dateFormat) throws ParseException {
        DateFormat df = new SimpleDateFormat(dateFormat);
        df.setLenient(false);
        df.parse(date);
    }

}