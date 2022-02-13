package Controller;

import Entity.Schedule;
import Entity.Template;
import UseCase.ScheduleManager;
import UseCase.TemplateManager;
import UseCase.UserManager;

import java.util.HashMap;
import java.util.List;

/**
 * Facade is a class that contains references to each individual class including Template, Schedule and User related
 * entities, use case, controller and Gateways. Facade has roughly the same responsibilities as the original class.
 *
 * ts: TemplateSystem
 * tm: TemplateManager
 * sm: ScheduleManager
 * ss: ScheduleSystem
 * um: UserManager
 * tr: TempReadWriter
 * sr: ScheduleReadWriter
 *
 * @author Kexin Sha
 * @author Jessica Wang
 * @author Hilda Wang
 * @author Siqing Xu
 *
 */
public class Facade {
    TemplateSystem ts;
    TemplateManager tm;
    ScheduleManager sm;
    ScheduleSystem ss;
    UserManager um;
    private TempReadWriter tr = new TempReadWriter();
    private ScheduleReadWriter sr = new ScheduleReadWriter();

    /**
     * The constructor of facade that initialize the Template, Schedule, User related entity, use case and controller.
     *
     * ts: TemplateSystem
     * tm: TemplateManager
     * tr: TempReadWriter
     * sr: ScheduleReadWriter
     * sm: ScheduleManager
     * ss: ScheduleSystem
     * um: UserManager
     *
     * @param um the UserManager
     *
     */
    public Facade(UserManager um) {
        HashMap<Integer, Template> IdToTemplate = tr.readTempFromFile();
        tm = new TemplateManager(IdToTemplate);
        ts = new TemplateSystem(tm);
        HashMap<String, List<Schedule>> schedulesList = sr.readScheduleListFromFile();
        HashMap<Integer, Integer> schedulesTempList = sr.readScheduleTempFromFile();
        sm = new ScheduleManager(schedulesList, schedulesTempList);
        ss = new ScheduleSystem(sm, um, tm);
        this.um = um;
        int max = 10000;
        if (schedulesList.isEmpty())
            sm.setIterator(max);
        else{
            for (int scheduleId : schedulesTempList.keySet()){
                if (scheduleId > max){
                    max = scheduleId;
                }
            }
            sm.setIterator(max+1);
        }


        int tempmax = 10000;
        for (int tempId : IdToTemplate.keySet()){
            if (tempId > tempmax){
                tempmax = tempId;
            }
        }
        tm.setIterator(tempmax+1);
    }


    /**
     * crete a template with the type given.
     *
     * @param TemplateType the type of a template (monthly or daily)
     */
    public void createTemp(String TemplateType){
        ts.create(TemplateType);
    }


    /**
     * Get template records (the id, type, time limitations) in a string representation.
     *
     */
    public void getTempData(){
        ts.GetTemplateRecords();
    }


    /**
     * save the creation or modification of the template to the temp file.
     *
     */
    public void exitTemp() {
        tr.saveTempToFile(TemplateManager.IdToTemplate);
    }


    /**
     * save the creation or modification of the schedule to the schedule file.
     *
     * @param id: the schedule id
     *
     */

    public void saveSchedule(String id){
        HashMap<String, List<Schedule>> scheduleList = sm.getSchedulesList();
        HashMap<Integer, Integer> scheduleTempMap = sm.getScheduleTempMap();
        if (um.getUserType(id).equals("trial")){
            if (scheduleList.containsKey(id)){
                List<Schedule> userSchedules = scheduleList.get(id);
                scheduleList.remove(id);
                for (Schedule s: userSchedules){
                    scheduleTempMap.remove(s.getScheduleID());
                }
            }
        }
        sr.saveScheduleToFile(scheduleList, scheduleTempMap);
    }



}


