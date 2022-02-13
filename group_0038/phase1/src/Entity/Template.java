package Entity;

import java.io.Serializable;

/**
 * An abstract class that has subclasses which are DailyTemplate and MonthlyTemplate.
 * It initially has a unique id (starts from 10000) and type (daily or monthly),
 * they can't be modified.
 * It also has three attributes: MinTimeBtwEvents, MinTimeOfEvent, MaxTimeOfEvent,
 * they have default values and could be modified by admin users.
 *
 * Template can be created by the constructor immediately.
 *
 * templateId:  unique id, use an iterator to generate id, starting from 10000
 * MinTimeBtwEvents:  default unit is hour
 * MinTimeOfEvent: default unit is hour
 * MaxTimeOfEvent: default unit is hour
 *
 *
 * @author Christine
 * @author Chuanrun Zhang
 * @author Siqing Xu
 */
public abstract class Template implements Serializable {
    private static int iterator = 10000;
    public Integer templateId;
    private String type = "DefaultTemplate";
    public double MinTimeBtwEvents;
    public double MinTimeOfEvent;
    public double MaxTimeOfEvent;

    /**
     * Creates a template with a unique templateID,
     * assigned by iterator (starts from 10000),
     * whenever a template is created,
     * its templateID is one more than the last template's id.
     */
    public Template(){
        this.templateId = iterator;
        iterator++;
    }

    /**
     * Returns an integer called templateId of the template.
     * It is a getter method of templateId.
     *
     * @return the id of the template
     */
    public Integer getTemplateId() {
        return this.templateId;
    }

    /**
     * Sets the iterator to an int given.
     * It is a setter of iterator.
     * It is to make sure every time Main runs,
     * iterator can continue to count from the existing id
     *
     * @param iterator the largest id existing plus 1
     */
    public static void setIterator(int iterator) {
        Template.iterator = iterator;
    }

    /**
     * Sets the minimum time between two events as a double
     * called MinTimeBtwEvents of the template.
     * It is a setter of MinTimeBtwEvents of the template.
     * It is an abstract method, we will overwrite it in the subclass.
     *
     * @param MinTimeBtwEvents the minimum time between two events
     *                         default value is 0.0 hour
     */
    public abstract void setMinTimeBtwEvents(double MinTimeBtwEvents);

    /**
     * Sets the minimum time of a event as a double
     * called MinTimeOfEvent of the template.
     * It is a setter of MinTimeOfEvent of the template.
     * It is an abstract method, we will overwrite it in the subclass.
     *
     * @param MinTimeOfEvent the minimum time of an event
     *                         default value is 0.0 hour
     */
    public abstract void setMinTimeOfEvent(double MinTimeOfEvent);

    /**
     * Sets the maximum time of a event as a double
     * called MaxTimeOfEvent of the template.
     * It is a setter of MaxTimeOfEvent of the template.
     * It is an abstract method, we will overwrite it in the subclass.
     *
     * @param MaxTimeOfEvent the maximum time of an event
     *                         default value is 0.0 hour
     */
    public abstract void setMaxTimeOfEvent(double MaxTimeOfEvent);

    /**
     * Returns the minimum time between two events called MinTimeBtwEvents.
     * It is a getter of MinTimeBtwEvents of a template.
     * It is an abstract method, we will overwrite it in the subclass.
     */
    public abstract double getMinTimeBtwEvents();

    /**
     * Returns the minimum time of an event called MinTimeOfEvents.
     * It is a getter of MinTimeOfEvent of a template.
     * It is an abstract method, we will overwrite it in the subclass.
     */
    public abstract double getMinTimeOfEvent();

    /**
     * Returns the maximum time of an event called MaxTimeOfEvents.
     * It is a getter of MaxTimeOfEvent of a template.
     * It is an abstract method, we will overwrite it in the subclass.
     */
    public abstract double getMaxTimeOfEvent();

    /**
     * Returns a string that is the type of a template.
     * It is a getter of type of a template.
     *
     * @return the type of the template
     */
    public String getTemplateType(){return this.type;}



}
