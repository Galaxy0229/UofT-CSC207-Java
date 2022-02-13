package UseCase;

import Entity.DailyTemplate;
import Entity.MonthlyTemplate;
import Entity.Schedule;
import Entity.Template;
import org.omg.PortableServer.IdAssignmentPolicy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class called TemplateManager which is used to create, store, remove, check and get any template.
 * It has one attribute called IdToTemplate.
 *
 * TemplateManager can be created by the constructor immediately with the given HashMap IdToTemplate.
 *
 * IdToTemplate: a HashMap with the id of the template as key, which is Integer,
 *              and the corresponding template as value, which is Template.
 *
 * @author Christine
 * @author Chuanrun Zhang
 * @author Siqing Xu
 */
public class TemplateManager implements Serializable {
    public static HashMap<Integer, Template> IdToTemplate;


    /**
     * A constructor that create a TemplateManager with the parameter idToTemplate.
     *
     * @param idToTemplate a HashMap with the id of the template as key and the corresponding template as value.
     */
    public TemplateManager(HashMap<Integer, Template> idToTemplate) {
        IdToTemplate = idToTemplate;
        if (IdToTemplate.isEmpty()) {
            Template d1 = new DailyTemplate();
            Template m1 = new MonthlyTemplate();
            IdToTemplate.put(d1.getTemplateId(), d1);
            IdToTemplate.put(m1.getTemplateId(), m1);
        }
    }

    /**
     * Return a list of Integers which are the ids of templates.
     * Gets ids of all templates stored in this TemplateManager.
     * @return l a list of Integers which are the ids of templates
     */
    public ArrayList<Integer> getTemplateId() {
        ArrayList<Integer> l = new ArrayList<Integer>(IdToTemplate.keySet());
        return l;
    }

    /**
     * Sets the iterator to an int given.
     * It is a setter of iterator in Template class.
     * It is to make sure every time Main runs,
     * iterator can continue to count from the existing id
     *
     * @param iterator the largest id existing plus 1
     */
    public void setIterator(int iterator) {
        Template.setIterator(iterator);
    }

    /**
     * Return a list of Templates stored in this TemplateManager.
     * Gets all templates stored in this TemplateManager.
     * @return a list of Templates
     */
    public ArrayList<Template> getTemplates() {
        return new ArrayList<Template>(IdToTemplate.values());
    }

    /**
     * Creates a template with the given TemplateType.
     *
     * If TemplateType is "DailyTemplate", a DailyTemplate is created.
     * If TemplateType is "MonthlyTemplate", a MonthlyTemplate is created.
     * The new template created is stored in IdToTemplate.
     *
     * @param TemplateType the type of template that the user wants to create
     */
    public void createTemplate(String TemplateType) {
        if (TemplateType.equals("DailyTemplate")) { /* DailyTemplate case */
            DailyTemplate dt = new DailyTemplate();
            IdToTemplate.put(dt.getTemplateId(), dt);
        }
        if (TemplateType.equals("MonthlyTemplate")) { /* MonthlyTemplate case */
            MonthlyTemplate mt = new MonthlyTemplate();
            IdToTemplate.put(mt.getTemplateId(), mt);
        }
    }
    /**
     * Returns true or false value.
     * This method is used to check whether the given templateId is in IdToTemplate Hashmap.
     *
     * @param templateId an Integer which may be an id of a template.
     */
    public boolean checkExistedTemplate(Integer templateId) {
        if (IdToTemplate.keySet().contains(templateId)) {
            return true; /* the given templateId is in IdToTemplate */
        }
        return false; /* the given templateId isn't in IdToTemplate */
    }

//    public void checkExistedTemplate(Integer templateId) throws UseCase.TemplateNotFoundException {
//        if (!IdToTemplate.keySet().contains(templateId)) {
//            throw new UseCase.TemplateNotFoundException("name not existed");
//        }
//    }

    /**
     * Returns true or false value.
     * This method is used to remove the template with the given TemplateId from IdToTemplate.
     * If the template with the given TemplateId exists, this template will be removed and return true.
     * Else, it returns false.
     *
     * @param TemplateId an Integer which may be an id of a template.
     */
    public void removeTemplate(Integer TemplateId) throws TemplateNotFoundException{
        if (checkExistedTemplate(TemplateId)){
            IdToTemplate.remove(TemplateId); /* the template with the given templateId is removed from IdToTemplate */
        } else {
            throw new TemplateNotFoundException("The template id does not exist."); /* opposite case */
        }
    }


    /**
     * Returns the template with the given TemplateId.
     * Use checkExistedTemplate as a helper function whether this template exists or not first.
     * If there does not exist such a template, it throws TemplateNotFoundException.
     *
     * This method is used to get the template with the given TemplateId from IdToTemplate.
     *
     * @param TemplateId an Integer which may be an id of a template.
     */
    public Template getTemplateById(Integer TemplateId) throws TemplateNotFoundException {
        if (!checkExistedTemplate(TemplateId)){
            throw new TemplateNotFoundException("No such Template Existed"); /* the template with the given id does not exist case */
        }
        return IdToTemplate.get(TemplateId); /* the template with the given id exists case */
    }


}