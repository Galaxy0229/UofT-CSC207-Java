+ Introduction

This is a Calendar and Scheduling app that is used to create, edit and store schedules. Each schedule is based on a
template and each schedule can have multiple events added to it. Users can interact with the app through text UI.

We have two types of schedule templates: Monthly Template and Daily Template.
We also have two corresponding types of schedules: Monthly Schedule and Daily Schedule
We have three different types of users including:

-Admin user
can login and edit schedule template such as modify the restrictions on events (e.g. max duration of a event,
min duration of a event) and delete templates.

-Regular user
can login and select a schedule template in order to create their own schedule, see a list of their previously
created schedules, modify their own schedules (e.g. add new event, delete existing event, change schedule status
from public to private), view all the events in their schedules, and look at a list of all public schedules.

-Trial user
can do the same set of things as regular user without logging in, but everything created by the trial user
will not be stored after exit.

+ Instructions

We have two initial templates with different types (Monthly and Daily) and we can create monthly schedules and
daily schedules according to the templates.

First we can choose to sign up as a new user (admin or regular), log in, or use this app as a trial user in the welcome
menu.

We can sign up using an email address and a password. User id will be provided to the user from the console after
successfully sign up. Then, we can log in using the correct user id and password combination.

After we successfully log in (or if we choose to be a trial user),
The main menu will show a list of things we can do according to our user type.

If we choose to be a regular user, we can interact with template such as see all the templates and select one.
After we select a template by inputting the corresponding template id, we can make our own schedule
base on it. Creating a schedule requires a schedule name, a status (public/private) and a schedule date in a specific
format (the format is yyyy MM for a monthly schedule and yyyy MM dd for a daily schedule). After we create a schedule,
we can add/delete event by inputting the name, start time and end time of this event. Again, the start time and end time
for a event has specific format (the format is dd hh:mm for a monthly schedule and hh:mm for a daily schedule).
The event will only be added to the schedule if it follows the restrictions set by the chosen template.
For example, if the template has a min duration of event set to 1 hour, then each event will only be added if
it's at least 1 hour long.

Next time when regular user need to login, they can simply enter their user id and password to see their
previously created schedules or to create a new schedule.

If we choose to be a trial user, we can do the same set of things as a regular user, except the trial user's user
information and schedule creations will not be stored once they exit the program.

If we choose to be an admin user, we can see all templates, create new default templates, and edit templates.
After we create a default template (set the type -Daily or Monthly- and use the default values for the time restrictions),
we can edit the template attributes such as min time between events, min/max time of an event. Once the restrictions are
modified, users who want to create a new schedule according to this template must follow the new restrictions.

However, admin user cannot interact with schedules, they can only edit templates.

Also, admin user and regular user can change their password or email after logging in.

We can exit this app through the welcome menu or main menu.

+ Files outside of the program
Note: The following four files do not exist at the first time running our program.
But they will be created as long as we run the program.

We have template data that store every templates.(TemplateData.ser)
We have user info to store user data.(usersInfo.ser)
We have scheduleTemp file to store data that maps a schedule id to a template id.(ScheduleData2.ser)
We also have schedule list to store data that maps a user id to a list of schedule id's.(ScheduleData.ser)
