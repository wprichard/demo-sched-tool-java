//proto4
package oracle.nasc.esg.view.bean;

import java.io.IOException;

import java.sql.SQLException;

import org.apache.myfaces.trinidad.util.Service;

import java.sql.Time;
import java.sql.Timestamp;

import java.text.Format;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
//import oracle.adf.share.security.SecurityContext;
//import oracle.adf.share.security.providers.jps.JpsSecurityContext;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.identitymanagement.UserProfile;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.CalendarActivityEvent;
import oracle.adf.view.rich.event.CalendarEvent;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.MouseButton;
import oracle.adf.view.rich.model.CalendarActivity;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.domain.TimestampTZ;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import oracle.nasc.esg.view.util.ADFUtils;

import oracle.nasc.esg.view.util.PLSQLUtils;

import org.apache.myfaces.trinidad.event.ReturnEvent;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class CalendarBean {
    //Private property variables
    private TimeZone _timeZone = getDefaultTimeZone(); //local time zone
    private CalendarActivity currActivity; //current activity
    private String currId; //id of activity being edited - used to find row
    private Date triggerDate; //trigger date for a create operation
    private int hour; //start hour displayed in create popup
    private int minute; //start minute displayed in create popup
    private String ampm; //am/pm displayed in create popup
    private Date startDate; //start date displayed in create popup
    private Date currDay; //today's date used for Start Date minimum
    private boolean editActivity =
        false; //indicates if an activity is being edited
    private boolean deleteActivity =
        false; //indicates if an activity is being deleted
    private boolean editEnabled =
        false; //indicates if the user is allowed to edit
    private boolean saveEnabled =
        true; //indicates if user is allowed to save a booking
    //private String timeZoneString;  //the ID of the timezone selected by the user in the picklist
    private String content;

    //Private class-scope variables
    private BindingContainer bindings;
    private String row; //binding object used for the create operation
    private RichPopup popupUI;

    //Constructor

    public CalendarBean() {
        super(); //why?
    }

    //Public Accessor methods

    public void setCurrActivity(CalendarActivity currActivity) {
        this.currActivity = currActivity;
    }

    public CalendarActivity getCurrActivity() {
        return currActivity;
    }

    public TimeZone getTimeZone() {
        //System.out.println("getting TimeZone " + _timeZone.getID());
        return _timeZone;
    }

    public void setTimeZone(TimeZone _timeZone) {
        System.out.println("setting TimeZone " + _timeZone.getID());
        this._timeZone = _timeZone;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getCurrDay() {
        //Return the current day will all time components zeroed
        SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currDay = cal.getTime();
        //System.out.println("Min date for Start Date: " + dateFormat.format(cal.getTime()));
        return currDay;
    }

    public void setEditActivity(boolean editActivity) {
        this.editActivity = editActivity;
    }

    public boolean isEditActivity() {
        return editActivity;
    }

    public void setDeleteActivity(boolean deleteActivity) {
        this.deleteActivity = deleteActivity;
    }

    public boolean isDeleteActivity() {
        return deleteActivity;
    }

    public void setEditEnabled(boolean editEnabled) {
        this.editEnabled = editEnabled;
    }

    public boolean isEditEnabled() {
        return editEnabled;
    }

    public List getTimeZoneIDs() {
        /* Return the list of timezone ids for the time zone picklist
         * obtained from the TimeZone object
         */
        String tzIDs[] = TimeZone.getAvailableIDs();
        List options;
        options = new ArrayList();
        SelectItem option;
        //Iterate through the array to build a List of SelectItems
        int i;
        for (i = 0; i < tzIDs.length; i++) {
            option = null;
            option = new SelectItem((Object)tzIDs[i]);
            options.add(option);
        }
        return options;
    }

    public void setTimeZoneString(String timeZoneID) {
        //Set the instance timezone to the specified timezone string
        TimeZone tz = null;

        if (timeZoneID != null)
            tz = TimeZone.getTimeZone(timeZoneID);
        else
            tz = getDefaultTimeZone();

        System.out.println("setting TimeZone based on TimeZoneString " +
                           timeZoneID);
        setTimeZone(tz);
    }

    public String getTimeZoneString() {
        //Return the string ID of the local default time zone
        TimeZone tz = getTimeZone();
        System.out.println("getting TimeZone based on TimeZoneString " +
                           tz.getID());
        return tz.getID();
    }

    //    public void setTimeZoneString1(String timeZoneString) {
    //        this.timeZoneString = timeZoneString;
    //        //Set the timezone object to be used for the calendar
    //        this._timeZone = TimeZone.getTimeZone(timeZoneString);
    //    }
    //
    //    public String getTimeZoneString1() {
    //        return timeZoneString;
    //    }

    public void setSaveEnabled(boolean saveEnabled) {
        this.saveEnabled = saveEnabled;
    }

    public boolean isSaveEnabled() {
        return saveEnabled;
    }

    //Public Listeners

    public void calendarListener(CalendarEvent calendarEvent) {
        //Handle the calendar event that fires when the user clicks
        //on empty space on a calendar day
        //The calendar's create facet is used to display a popup and
        //the popup renders after this event finishes.
        //Most of the controls on the popup are bound to the
        //BookingsViewInsert data control except the start time
        //controls, which are bound to properties of this bean.
        //Ignore anything other than a left-click because the calendar's
        //create facet works on the left click.

        System.out.println("CalendarBean.calendarListener: Entering");
        if (calendarEvent.getButton() == MouseButton.LEFT) {
            Format formatter;
            formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");

            //Initialize property variables linked to controls
            //Grab the date based on where the user clicked on the calendar
            triggerDate = (Date)calendarEvent.getTriggerDate().clone();
            //Update class properties that are used by the UI controls
            extractStartDateTimeVals(triggerDate);

            //Start a CreateInsert operation so a button can be used to commit or rollback
            //This must be done prior to initialzing data in order to create the rowset for the new record
            bindings = ADFUtils.getBindingContainer();
            OperationBinding ob = bindings.getOperationBinding("CreateInsert");
            ob.execute();

            //Initialize data in the new rowset
            //Set the event start datetime to the trigger date
            ControlBinding sdtCB = bindings.getControlBinding("StartDatetime");
            AttributeBinding sdtAB = (AttributeBinding)sdtCB;
            //sdtAB.setInputValue(formatter.format(triggerDate));
            //sdtAB.setInputValue(new oracle.jbo.domain.Date(new java.sql.Timestamp(triggerDate.getTime())));
            try {
                sdtAB.setInputValue(new TimestampTZ(new java.sql.Timestamp(triggerDate.getTime())));
            } catch (java.sql.SQLException ex) {
                System.out.println("CalendarBean.calendarListener: error on start datetime conversion: " +
                                   ex);
            }
            System.out.println("CalendarBean.calendarListener: Saved startDatetime= " +
                               sdtAB.getInputValue());

            //Set the end date/time based the trigger datetime plus the default duration
            //Get the default duration from the attribute binding
            ControlBinding durCB = bindings.getControlBinding("DurationHours");
            AttributeBinding durAB = (AttributeBinding)durCB;
            oracle.jbo.domain.Number jboDuration =
                (oracle.jbo.domain.Number)durAB.getInputValue();
            int intDuration = (int)jboDuration.byteValue();

            //Get attribute binding for endDatetime
            ControlBinding edtCB = bindings.getControlBinding("EndDatetime");
            AttributeBinding edtAB = (AttributeBinding)edtCB;

            //use a calendar object to calc the default end datetime
            //NOTE - this assumes the default duration is an integer value
            Calendar cal = Calendar.getInstance();
            cal.setTime(triggerDate);
            cal.setTimeZone(getTimeZone());
            cal.add(Calendar.HOUR, intDuration);
            System.out.println("CalendarBean.calendarListener: New Time= " +
                               cal.get(Calendar.HOUR) + ":" +
                               cal.get(Calendar.MINUTE));
            Date endDate = cal.getTime();
            System.out.println("CalendarBean.calendarListener: calculated end date = " +
                               endDate.toString());
            System.out.println("CalendarBean.calendarListener: model end date before update= " +
                               edtAB.getInputValue());

            //update the endDatetime attribute with a formatted date & time
            //so that the time appears in the UI
            //edtAB.setInputValue(formatter.format(endDate));
            //edtAB.setInputValue(new oracle.jbo.domain.Date(new java.sql.Timestamp(endDate.getTime())));
            try {
                edtAB.setInputValue(new TimestampTZ(new java.sql.Timestamp(endDate.getTime())));
            } catch (java.sql.SQLException ex) {
                System.out.println("CalendarBean.calendarListener: error on end datetime conversion: " +
                                   ex);
            }
            System.out.println("CalendarBean.calendarListener: model end date after update= " +
                               edtAB.getInputValue());

            //Test code to look at request header
            //        Map reqHdrValMap = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderValuesMap();  //string, string[]
            //        System.out.println("calendarListener: reqHdrValMap -----------");
            //        iterateMapOA(reqHdrValMap);
            //        Map reqHdrMap = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();  //string, string
            //        System.out.println("calendarListener: reqHdrMap -----------");
            //        iterateMapOO(reqHdrMap);
            Map sessionMap =
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); //string, obj

            //this httprequest provides the same values as the reqHdrMap above
            oracle.adf.model.servlet.HttpBindingContext httpBC =
                (oracle.adf.model.servlet.HttpBindingContext)sessionMap.get("data");
            HttpServletRequest httpRequest = null;
            httpRequest =
                    (HttpServletRequest)httpBC.get(BindingContext.HTTP_REQUEST);
            //test code to look at http request
            System.out.println("RemoteUser: " + httpRequest.getRemoteUser());
            System.out.println("Osso-User-Dn: " +
                               httpRequest.getHeader("Osso-User-Dn"));
            System.out.println("Osso-User-Guid: " +
                               httpRequest.getHeader("Osso-User-Guid"));
            System.out.println("Osso-Subscriber: " +
                               httpRequest.getHeader("Osso-Subscriber"));
            System.out.println("Osso-User-Dn: " +
                               httpRequest.getHeader("Osso-User-Dn"));
            System.out.println("Osso-Subscriber-Dn: " +
                               httpRequest.getHeader("Osso-Subscriber-Dn"));
            System.out.println("Osso-Subscriber-Guid: " +
                               httpRequest.getHeader("Osso-Subscriber-Guid"));
            System.out.println("Proxy-Remote-User: " +
                               httpRequest.getHeader("Proxy-Remote-User"));

            //Initialize the user info
            //Get user profile from the security context but it may be empty when
            //using osso. Future - look at using the OSSO identity asserter for weblogic
            ADFContext adfContext = ADFContext.getCurrent();
            UserProfile usrProfile =
                adfContext.getSecurityContext().getUserProfile();
            System.out.println("CalendarBean.calendarListener: Security Context Display Name: '" +
                               usrProfile.getDisplayName() + "'");

            String reqUser = httpRequest.getHeader("Proxy-Remote-User");
            if (reqUser == null) {
                reqUser = "UNAUTHENTICATED USER";
            }

            ControlBinding ridCB = bindings.getControlBinding("RequesterId");
            AttributeBinding ridAB = (AttributeBinding)ridCB;
            ridAB.setInputValue(reqUser);
            System.out.println("CalendarBean.calendarListener: RequesterID set to: " +
                               ridAB.getInputValue());

            //Determine if saving is disabled for an unauthenticated user
            Boolean modeTest;
            try {
                Properties prop =
                    PLSQLUtils.loadParams("oracle.nasc.esg.view.util.Application");
                modeTest = Boolean.parseBoolean((String)prop.get("TestMode"));
            } catch (IOException ex) { // Trap I/O errors
                System.out.println("CalendarBean.calendarListener: Error in reading the Application.properties file: " +
                                   ex.toString());
                modeTest = false;
            }
            if ((modeTest == false) && (reqUser == "UNAUTHENTICATED USER")) {
                saveEnabled = false;
                System.out.println("CalendarBean.calendarListener: saves are disabled");
            } else {
                saveEnabled = true;
            }

            //Note - the email address is not expected to be in the osso header so this field will
            //be blank unless the identity asserter is used or the email can be derived
            //from the header field Osso-User-Dn
            ControlBinding remCB =
                bindings.getControlBinding("RequesterEmail");
            AttributeBinding remAB = (AttributeBinding)remCB;
            remAB.setInputValue(usrProfile.getBusinessEmail());
            System.out.println("CalendarBean.calendarListener: RequesterEmailD set to: " +
                               remAB.getInputValue());

            //Initialize the CreateDate field
            Calendar sysDate = Calendar.getInstance();
            ControlBinding cdateCB = bindings.getControlBinding("CreateDate");
            AttributeBinding cdateAB = (AttributeBinding)cdateCB;
            cdateAB.setInputValue(sysDate.getTime());
            System.out.println("CalendarBean.calendarListener: Saved CreateDate= " +
                               sdtAB.getInputValue());
        }


        System.out.println("CalendarBean.calendarListener: Exiting");
    }

    public void calendarActivityListener(CalendarActivityEvent calendarActivityEvent) {
        /* This event fires when the user clicks on or hovers over an existing
         * calendar event.
         * Check to see if the current user is the save as the user that created the
         * event and if so then: enable the edit/delete/save buttons, get the
         * current activity and id and set the row in the iterator to the activity's row,
         * and execute a CreateInsert operation (to be committed or rolled-back
         * by the save or cancel buttons).
         */

        System.out.println("calendarActivityListener: Entering");
        currActivity = calendarActivityEvent.getCalendarActivity();

        //if no activity was selected in the calendar then...
        if (currActivity == null) {
            //set the current activity property to null
            setCurrActivity(null);
        } else {
            //Get the current activity and id and set the iterator row
            System.out.println("calendarActivityListener: " +
                               currActivity.getTitle() + ", " +
                               currActivity.getLocation()); //debug
            //update the current activity propery
            setCurrActivity(currActivity);
            //save the activity id for other methods
            currId = currActivity.getId();
            //set the row to match the selected activity
            setCurrentRowWithActivity();

            //Log the original create date
            bindings = ADFUtils.getBindingContainer();
            ControlBinding cdateCB = bindings.getControlBinding("CreateDate");
            AttributeBinding cdateAB = (AttributeBinding)cdateCB;
            System.out.println("calendarActivityListener: CreateDate = " +
                               cdateAB.getInputValue());

            //Do the following only for the edit facet
            if (calendarActivityEvent.getTriggerType().name() != "HOVER") {
                System.out.println("calendarActivityListener: this event was triggered by " +
                                   calendarActivityEvent.getTriggerType().name());

                //Initialize start date and start time properties used by the UI controls
                //Grab the StartDatetime from the row
                ControlBinding sdtCB =
                    bindings.getControlBinding("StartDatetime");
                AttributeBinding sdtAB = (AttributeBinding)sdtCB;
                /* This code is for when StartDateTime is stored a a Date in DB
                oracle.jbo.domain.Date startDateTime =
                    (oracle.jbo.domain.Date)sdtAB.getInputValue();
                //convert to java.util.date
                java.util.Date judStart = toJavaDate(startDateTime);
                */
                /* This code is for when StartDateTime is stored as a Timestamp
                 * with TimeZone in DB
                 */
                oracle.jbo.domain.TimestampTZ tzStart =
                    (oracle.jbo.domain.TimestampTZ)sdtAB.getInputValue();
                //convert to java.util.date
                long lngStart = tzStart.getTime();
                java.util.Date judStart = new Date(lngStart);

                //Update class properties
                extractStartDateTimeVals(judStart);

                /*Check to see if the current user is the same as the event creator
                 * and if so, enable edits
                 * AND, if the enddate is >= the current date then enable edits
                 * otherwise edits are disabled
                 */
                //Get the current user from the OSSO request header
                Map sessionMap =
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); //string, obj
                oracle.adf.model.servlet.HttpBindingContext httpBC =
                    (oracle.adf.model.servlet.HttpBindingContext)sessionMap.get("data");
                HttpServletRequest httpRequest = null;
                httpRequest =
                        (HttpServletRequest)httpBC.get(BindingContext.HTTP_REQUEST);
                String user = httpRequest.getHeader("Proxy-Remote-User");
                if (user == null) {
                    user = "UNAUTHENTICATED USER";
                }

                //Get the user that created this event
                ControlBinding ridCB =
                    bindings.getControlBinding("RequesterId");
                AttributeBinding ridAB = (AttributeBinding)ridCB;
                String requester = (String)ridAB.getInputValue();
                if (requester == null) {
                    //this handles test cases where the requester was blank
                    requester = "UNAUTHENTICATED USER";
                }
                //If the current user is the creator then...
                Boolean origCreator;
                if (requester.equals(user)) {
                    origCreator = true; //the user is the creator
                } else {
                    origCreator = false; //the user is not the creator
                }

                ControlBinding edtCB =
                    bindings.getControlBinding("EndDatetime");
                AttributeBinding edtAB = (AttributeBinding)edtCB;
                /* Code for when endDateTime is a Date in DB */
                //                oracle.jbo.domain.Date endDateTime =
                //                    (oracle.jbo.domain.Date)edtAB.getInputValue();
                /* Code for when endDateTime is a TimeStampTZ in DB */
                TimestampTZ tzEnd = (TimestampTZ)edtAB.getInputValue();
                oracle.jbo.domain.Date endDateTime =
                    new oracle.jbo.domain.Date(tzEnd.getValue());
                /*end of timestamp code*/

                oracle.jbo.domain.Date today = new oracle.jbo.domain.Date();
                today = (oracle.jbo.domain.Date)today.getCurrentDate();
                //if (endDate is not less than today) AND (the current user is the creator) then...
                if ((!(endDateTime.compareTo(today) == -1)) &&
                    (origCreator == true)) {
                    editEnabled = true;
                    /* Note - editEnabled is initialized false so it is set true
                     * only if conditions allow and it not set back to false for
                     * the popup because there is no need to
                     */
                } else {
                    editEnabled = false;
                }

                //If editing is allowed...
                if (editEnabled == true) {
                    System.out.println("calendarActivityListener: editing is enabled");
                    //Enable the Edit, Delete buttons
                    /*The buttons disabled property is tied to editEnabled so they
                    * will automatically be initialized based on the state of the
                    * property.
                    */

                    //Execute a CreateInsert operation on the data control
                    //This also indirectly enables the Save and Cancel buttons
                    //                    OperationBinding ob =
                    //                        bindings.getOperationBinding("CreateInsert");
                    //                    ob.execute();
                } else {
                    System.out.println("calendarActivityListener: editing is not enabled");
                }
            }
        }
        System.out.println("calendarActivityListener: Exiting");
    }

    public void startDate_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //This handles the event that fires when the user change the start date in
        //the Create popup.
        //Calculate the new end date and update the endDatetime attribute

        System.out.println("startDate_ValueChangeListener: entering");
        System.out.println("startDate_ValueChangeListener: Old Start Date= " +
                           (Date)valueChangeEvent.getOldValue());
        System.out.println("startDate_ValueChangeListener: New Start Date= " +
                           (Date)valueChangeEvent.getNewValue());
        oracle.jbo.domain.Number jboDuration = new oracle.jbo.domain.Number();
        jboDuration = null; //intentionally not initialized
        Integer intNull = new Integer(0);
        intNull = null;
        String strNull = new String();
        strNull = null;
        updateCreateEndDateTime((Date)valueChangeEvent.getNewValue(),
                                jboDuration, intNull, intNull, strNull);
        System.out.println("startDate_ValueChangeListener: exiting");
    }

    public void startHour_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //This handles the event that fires when the user change the start hour in
        //the Create popup.
        //Calculate the new end date and update the endDatetime attribute
        System.out.println("startHour_ValueChangeListener: entering");
        System.out.println("startHour_ValueChangeListener: Old Start Hour= " +
                           (Integer)valueChangeEvent.getOldValue());
        System.out.println("startHour_ValueChangeListener: New Start Hour= " +
                           (Integer)valueChangeEvent.getNewValue());
        System.out.println("startHour_ValueChangeListener: hour property= " +
                           hour);
        oracle.jbo.domain.Number jboDuration = new oracle.jbo.domain.Number();
        jboDuration = null; //intentionally not initialized
        Date dateStart = new Date();
        dateStart = null; //intentionally not initialized
        Integer intNull = new Integer(0);
        intNull = null; //intentionally not initialized
        String strNull = new String("");
        strNull = null;
        updateCreateEndDateTime(dateStart, jboDuration,
                                (Integer)valueChangeEvent.getNewValue(),
                                intNull, strNull);
        System.out.println("startHour_ValueChangeListener: exiting");
    }

    public void startMinute_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //This handles the event that fires when the user change the start minute in
        //the Create popup.
        //Calculate the new end date and update the endDatetime attribute
        System.out.println("startMinute_ValueChangeListener: entering");
        System.out.println("startMinute_ValueChangeListener: Old Start Minute= " +
                           (Integer)valueChangeEvent.getOldValue());
        System.out.println("startMinute_ValueChangeListener: New Start Minute= " +
                           (Integer)valueChangeEvent.getNewValue());
        System.out.println("startMinute_ValueChangeListener: Minute property= " +
                           minute);
        oracle.jbo.domain.Number jboDuration = new oracle.jbo.domain.Number();
        jboDuration = null; //intentionally not initialized
        Date dateStart = new Date();
        dateStart = null; //intentionally not initialized
        Integer intNull = new Integer(0);
        intNull = null; //intentionally not initialized
        String strNull = new String("");
        strNull = null;
        updateCreateEndDateTime(dateStart, jboDuration, intNull,
                                (Integer)valueChangeEvent.getNewValue(),
                                strNull);
        System.out.println("startMinute_ValueChangeListener: exiting");
    }

    public void startAMPM_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //This handles the event that fires when the user change the start AM/PM
        //radio button in the Create popup.
        //Calculate the new end date and update the endDatetime attribute
        System.out.println("startAMPM_ValueChangeListener: entering");
        System.out.println("startAMPM_ValueChangeListener: Old Start AM/PM= " +
                           (String)valueChangeEvent.getOldValue());
        System.out.println("startAMPM_ValueChangeListener: New Start AM/PM= " +
                           (String)valueChangeEvent.getNewValue());
        System.out.println("startAMPM_ValueChangeListener: AM/PM property= " +
                           ampm);
        oracle.jbo.domain.Number jboDuration = new oracle.jbo.domain.Number();
        jboDuration = null; //intentionally not initialized
        Date dateStart = new Date();
        dateStart = null; //intentionally not initialized
        Integer intNull = new Integer(0);
        intNull = null; //intentionally not initialized
        updateCreateEndDateTime(dateStart, jboDuration, intNull, intNull,
                                (String)valueChangeEvent.getNewValue());
        System.out.println("startAMPM_ValueChangeListener: exiting");
    }

    public void Duration_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //This handles the event that fires when the user change the duration in
        //the Create popup.
        //Calculate the new end date and update the endDatetime attribute
        System.out.println("Duration_ValueChangeListener: entering");
        System.out.println("Duration_ValueChangeListener: Old Duration= " +
                           (oracle.jbo.domain.Number)valueChangeEvent.getOldValue());
        System.out.println("Duration_ValueChangeListener: New Duration= " +
                           (oracle.jbo.domain.Number)valueChangeEvent.getNewValue());
        oracle.jbo.domain.Number jboDuration =
            (oracle.jbo.domain.Number)valueChangeEvent.getNewValue();
        Date dateStart = new Date();
        dateStart = null; //intentionally not initialized
        Integer intNull = new Integer(0);
        intNull = null; //intentionally not initialized
        String strNull = new String("");
        strNull = null;
        updateCreateEndDateTime(dateStart, jboDuration, intNull, intNull,
                                strNull);
        System.out.println("Duration_ValueChangeListener: exiting");
    }

    public void startDate_Validator(FacesContext facesContext,
                                    UIComponent uIComponent, Object object) {
        // Add event code here...
    }


    public void demosys_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //NOT USED - the method in CalendarBacking.java is used instead
        /*DemoSystem is set with Immediate=True so it can update without
         * causing a validation error on DemoScenario because it is blank when
         * it first displays. This listener must therefore force DemoSystem
         * in the model to have the new value so that DemoScenario will then
         * update
         */
        System.out.println("demosys_ValueChangeListener: entering");
        System.out.println("demosys_ValueChangeListener: DemoSystem control value old= " +
                           valueChangeEvent.getOldValue());
        System.out.println("demosys_ValueChangeListener: DemoSystem control value new= " +
                           valueChangeEvent.getNewValue());
        //Set the new value in DemoSystem
        bindings = ADFUtils.getBindingContainer();
        ControlBinding dsCB = bindings.getControlBinding("DemoSystem");
        AttributeBinding dsAB = (AttributeBinding)dsCB;
        System.out.println("demosys_ValueChangeListener: DemoSystem model value before= " +
                           dsAB.getInputValue());
        dsAB.setInputValue(valueChangeEvent.getNewValue()); //assign the int value
        System.out.println("demosys_ValueChangeListener: DemoSystem model value after= " +
                           dsAB.getInputValue());


        //        dciter.setCurrentRowIndexInRange(((Integer)valueChangeEvent.getNewValue()).intValue());
        //        sysVal = (String)currentRow.getAttribute("DemoSystem");
        //        System.out.println("new row val: " + sysVal);

        //attempting to use list binding
        //        JUCtrlListBinding listBinding = null;
        //        listBinding = (JUCtrlListBinding)bindings.get("DemoSystem");    /error
        //        ViewRowImpl selectedListRow = null;
        //        selectedListRow = (ViewRowImpl)listBinding.getSelectedValue();
        //        System.out.println("Selected Value: " + (String)selectedListRow.getAttribute("DemoSystem"));
        //        listBinding.setSelectedIndex(((Integer)valueChangeEvent.getNewValue()).intValue());
        //        selectedListRow = (ViewRowImpl)listBinding.getSelectedValue();
        //        System.out.println("Selected Value: " + (String)selectedListRow.getAttribute("DemoSystem"));

        //Set the new value in DemoScenario
        //        ControlBinding scenCB = bindings.getControlBinding("DemoScenario");
        //        AttributeBinding scenAB = (AttributeBinding)scenCB;
        //        System.out.println("DemoScenario model value before :" + scenAB.getInputValue());
        //        scenAB.setInputValue(new Integer(0));
        //        System.out.println("DemoScenario model value after :" + scenAB.getInputValue());

        //call the Render Response phase (thereby skipping validation of the input component)
        //        AdfFacesContext facesCtxt = AdfFacesContext.getCurrentInstance();
        //        UIComponent uic = new UIComponent();
        //        UIComponent demoscen = uic.findComponent("soc5");
        //        facesCtxt.addPartialTarget(soc5);
        FacesContext.getCurrentInstance().renderResponse();
        System.out.println("demosys_ValueChangeListener: exiting");
    }

    public void fireDeleteActivity() {
        /* This is the action listener for the Delete button in the Detail facet
         * popup.
         * Get a reference to the row to be deleted then do a commit on the
         * binding
         */

        System.out.println("fireDeleteActivity: entering");
        //Get the row to be deleted and delete it
        Row row = setCurrentRowWithActivity();
        row.remove();

        //Do a Commit operation on the binding to commit the row deletion
        BindingContainer bindings = ADFUtils.getBindingContainer();
        OperationBinding ob = bindings.getOperationBinding("Commit");
        Object obj = ob.execute();
        System.out.println("fireDeleteActivity: row deleted");

        bindings.refresh();

        if (obj != null)
            System.out.println("fireDeleteActivity: " +
                               obj.getClass().getName());
        System.out.println("fireDeleteActivity: exiting");
    }


    public void oscRequestActionListener(ActionEvent actionEvent) {
        // Invoke the stored procedure that initiates the OSC hosted demo request

        String pUserEmail;
        String pDemoSystem;
        String pDemoScenario;
        String pRequest_basc_desc;
        java.sql.Date pStartDate;
        java.sql.Date pEndDate;
        String pCostCenter;
        String pCustomer;
        String pBookingId;
        String pDemoType;
        Boolean status = false;
        Time pStartTime;
        Time pEndTime;
        String pDescription = "";

        try {
            //Instantiate the object to run the stored procedure
            PLSQLUtils proc = new PLSQLUtils();

            //Connect to the database
            status = proc.initConnection();
            System.out.println("CalendarBean.oscRequestActionListener: DB connection status is '" +
                               status + "'");

            if (status == true) {
                //Get a reference to the binding containter for the data control
                bindings = ADFUtils.getBindingContainer();

                //Get the start date & time
                ControlBinding ctlBinding =
                    bindings.getControlBinding("StartDatetime");
                AttributeBinding atrBinding = (AttributeBinding)ctlBinding;
                TimestampTZ startTSTZ =
                    (TimestampTZ)atrBinding.getInputValue();
                pStartDate = startTSTZ.dateValue();
                pStartTime = startTSTZ.timeValue();


                //Get the end date & time
                ctlBinding = bindings.getControlBinding("EndDatetime");
                atrBinding = (AttributeBinding)ctlBinding;
                TimestampTZ endTSTZ = (TimestampTZ)atrBinding.getInputValue();
                pEndDate = endTSTZ.dateValue();
                pEndTime = endTSTZ.timeValue();


                //Get the user
                //Note - currently RequesterEmail is not available so use RequesterId
                ctlBinding = bindings.getControlBinding("RequesterId");
                atrBinding = (AttributeBinding)ctlBinding;
                pUserEmail = (String)atrBinding.getInputValue();

                System.out.println(pUserEmail);

                //Get demo system
                ctlBinding = bindings.getControlBinding("DemoSystem");
                atrBinding = (AttributeBinding)ctlBinding;
                pDemoSystem = (String)atrBinding.getInputValue();

                System.out.println(pDemoSystem);
                //Get demo scenario
                ctlBinding = bindings.getControlBinding("DemoScenario");
                atrBinding = (AttributeBinding)ctlBinding;
                pDemoScenario = (String)atrBinding.getInputValue();


                System.out.println(pDemoScenario);

                //Get cost center
                ctlBinding = bindings.getControlBinding("CostCenter");
                atrBinding = (AttributeBinding)ctlBinding;
                pCostCenter = (String)atrBinding.getInputValue();

                System.out.println(pCostCenter);

                //Get customer
                ctlBinding = bindings.getControlBinding("Customer");
                atrBinding = (AttributeBinding)ctlBinding;
                pCustomer = (String)atrBinding.getInputValue();


                System.out.println(pDescription);
                proc.callPlsqlProc(pUserEmail, pDemoSystem, pDemoScenario,
                                   pStartDate, pEndDate, pCostCenter,
                                   pCustomer, pStartTime, pEndTime,
                                   pDescription);
                proc.closeConnection();


                System.out.println("CalendarBean.oscRequestActionListener: OSC demo request created");
            } else {
                System.out.println("CalendarBean.oscRequestActionListener: database connection " +
                                   "could not be established to create OSC demo request");
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("CalendarBean.oscRequestActionListener: date conversion error: " +
                               ex);
        } catch (Exception e) {
            System.out.println("CalendarBean.oscRequestActionListener: error: " +
                               e);
        }

    }

    //Private Utility methods    */

    private Row setCurrentRowWithActivity() {
        //Set the current row in the iterator based on the current activity

        System.out.println("setCurrentRowWithActivity: entering");
        //get the insert iterator
        DCIteratorBinding iterator =
            ADFUtils.findIterator("BookingsViewInsertIterator");
        //get a row set iterator based on the insert iterator
        RowSetIterator rsi = iterator.getRowSetIterator();
        //create a key for the current activity id
        Key key = new Key(new Object[] { currId });
        //find 1 row in iterator row set matching the currID
        Row row = rsi.findByKey(key, 1)[0];
        //assign found row as current row in the iterator
        rsi.setCurrentRow(row);

        //get and return the row from the iterator
        row = rsi.getCurrentRow();
        System.out.println("setCurrentRowWithActivity: " +
                           (String)row.getAttribute("DemoDescription")); //debug
        System.out.println("setCurrentRowWithActivity: exiting");
        return row;
    }

    private static TimeZone getDefaultTimeZone() {
        //Return the local system default time zone object
        TimeZone tz;

        AdfFacesContext context = AdfFacesContext.getCurrentInstance();
        tz = context.getTimeZone();
        //tz = TimeZone.getDefault();
        //tz = TimeZone.getTimeZone("America/New_York");
        System.out.println("getDefaultTimeZone: " + tz);
        return tz;
    }

    private void updateCreateEndDateTime(Date newStartDate,
                                         oracle.jbo.domain.Number jboDuration,
                                         Integer newHour, Integer newMinute,
                                         String newAMPM) {
        /*Calculate and update the create booking end date and time row attribute
        based on the start date and time plus the duration. Also update the
        combined startDateTime in the row in preparation for a future commit
        operation.
        Inputs:
        newStartDate - the new date passed in from the change listener or null
        jboDuration - the new duration passed in from the change listener or null
        newHour - the new start hour passed in from the change listener or null
        newMinute - the new start minute passed in from the listener or null
        newAMPM - the new AM/PM string passed in from the listener or null
        */
        java.util.Date startDateTime; //includes both start date and time
        oracle.jbo.domain.Number numDuration; //used to split interger and fractional parts of duration
        int intHour; //start hour to use to update start date
        int intMinute; //start minute to use to update start date
        int intAMPM; //offset for start hour to account for am or pm

        System.out.println("updateCreateEndDateTime: entering");
        //if a start date was not supplied then...
        if (newStartDate == null) {
            /*//Get the start date from the rowset (which includes a start time)
            ControlBinding sdtCB = bindings.getControlBinding("StartDatetime");
            AttributeBinding sdtAB = (AttributeBinding)sdtCB;
            oracle.jbo.domain.Date jboStartDate = (oracle.jbo.domain.Date)sdtAB.getInputValue();
            //convert the oracle.jbo.domain.Date to a java.util.Date for the calculation below
            java.sql.Date javaSqlDate = jboStartDate.dateValue();
            long javaMilliSeconds = javaSqlDate.getTime();
            startDateTime = new java.util.Date(javaMilliSeconds);*/

            //Get start date from the bean property
            newStartDate = startDate;
        }
        System.out.println("updateCreateEndDateTime: specified Start Date= " +
                           newStartDate);

        //if the duration was not supplied then...
        if (jboDuration == null) {
            //Set the end date/time based the trigger date time and the default duration
            //Get attribute binding for duration
            ControlBinding durCB = bindings.getControlBinding("DurationHours");
            AttributeBinding durAB = (AttributeBinding)durCB;
            jboDuration = (oracle.jbo.domain.Number)durAB.getInputValue();
        }
        //convert Number obj to an int for the calculation below
        //numDuration = (Number)jboDuration.byteValue();
        System.out.println("updateCreateEndDateTime: Specified Duration= " +
                           jboDuration.toString());

        //if the AM/PM offset was not supplied then
        if (newAMPM == null) {
            //get the hour offset from the bean property and convert it to a numeric offset
            System.out.println("updateCreateEndDateTime: Using bean property for AM/PM= " +
                               ampm);
            if (ampm.equals("AM"))
                intAMPM = 0;
            else
                intAMPM = 12;
        } else {
            //use the input parameter and convert it to a numeric offset
            System.out.println("updateCreateEndDateTime: Using listener value for AM/PM= " +
                               newAMPM);
            if (newAMPM.equals("AM"))
                intAMPM = 0;
            else
                intAMPM = 12;
        }
        System.out.println("updateCreateEndDateTime: Specified Start AM/PM offset= " +
                           intAMPM);

        //if a new hour was not supplied then
        if (newHour == null) {
            //get the hour from the bean property
            intHour = hour;
        } else {
            //get the new start hour as an integer
            intHour = newHour.intValue();
        }
        //if the input is 12
        if (intHour == 12) {
            //change the hour to zero for the correct calculation
            intHour = 0;
        }
        System.out.println("updateCreateEndDateTime: Specified Start Hour= " +
                           intHour + " " + _timeZone.getID());

        //if the new minute was not supplied then
        if (newMinute == null) {
            //get the minute from the bean property
            intMinute = minute;
        } else {
            //get the start minute as an integer
            intMinute = newMinute.intValue();
        }
        System.out.println("updateCreateEndDateTime: Specified Start Minute= " +
                           intMinute);

        //else
        //get a calendar obj for the current rowset startdatetime
        /*
            ControlBinding sdtCB = bindings.getControlBinding("StartDatetime");
            AttributeBinding sdtAB = (AttributeBinding)sdtCB;
            oracle.jbo.domain.Date jboCurrentStartDate = (oracle.jbo.domain.Date)sdtAB.getInputValue();
            java.sql.Date sqlCurrentStartDate = jboCurrentStartDate.dateValue();
            long lngMilliSec = sqlCurrentStartDate.getTime();
            java.util.Date dateCurrentStart = new java.util.Date(lngMilliSec);
            Calendar calCurrent = Calendar.getInstance();
            calCurrent.setTime(dateCurrentStart);
            */

        //get a cal obj for the input date
        /*
            java.sql.Date sqlNewDate = jboStartDate.dateValue();
            long lngNewDate = sqlNewDate.getTime();
            java.util.Date utilNewDate = new java.util.Date(lngNewDate);
            */

        //set the start time in the start date
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(newStartDate);

        //combine them to set a calendar
        //use the calendar obj to set the java.util.date
        Calendar calStart = Calendar.getInstance();
        calStart.clear();
        calStart.setTimeZone(_timeZone);
        calStart.set(calNew.get(Calendar.YEAR), calNew.get(Calendar.MONTH),
                     calNew.get(Calendar.DATE), (intHour + intAMPM),
                     intMinute);
        startDateTime = calStart.getTime();
        System.out.println("updateCreateEndDateTime: Combined start DateTime= " +
                           startDateTime);

        //Save the new combined start datetime back to the row
        ControlBinding sdtCB = bindings.getControlBinding("StartDatetime");
        AttributeBinding sdtAB = (AttributeBinding)sdtCB;
        //old code
        //sdtAB.setInputValue(new oracle.jbo.domain.Date(new java.sql.Timestamp(startDateTime.getTime())));
        //new code to handle timestamp
        try {
            sdtAB.setInputValue(new TimestampTZ(new java.sql.Timestamp(startDateTime.getTime())));
        } catch (java.sql.SQLException ex) {
            System.out.println("CalendarBean.updateCreateEndDateTime: error on start date conversion: " +
                               ex);
        }
        System.out.println("updateCreateEndDateTime: New DB startDateTime= " +
                           sdtAB.getInputValue());

        //use a calendar object to calc the end datetime
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDateTime);
        //get the integer part of the duration
        numDuration = (oracle.jbo.domain.Number)jboDuration.truncate(0);
        int intDurHr = numDuration.byteValue();
        cal.add(Calendar.HOUR, intDurHr);
        //get the fractional part of the duration
        numDuration = (oracle.jbo.domain.Number)jboDuration.mod(1);
        numDuration = numDuration.multiply(60);
        int intDurMin = (int)numDuration.byteValue();
        //intDurMin = (intDurMin/10)*60;
        cal.add(Calendar.MINUTE, intDurMin);
        System.out.println("updateCreateEndDateTime: New End Time= " +
                           cal.get(Calendar.HOUR) + ":" +
                           cal.get(Calendar.MINUTE)); //debug
        Date endDate = cal.getTime();
        System.out.println("updateCreateEndDateTime: new calculated end datetime= " +
                           endDate);


        //Get attribute binding for endDatetime
        ControlBinding edtCB = bindings.getControlBinding("EndDatetime");
        AttributeBinding edtAB = (AttributeBinding)edtCB;
        System.out.println("updateCreateEndDateTime: EndDT Before rec update= " +
                           edtAB.getInputValue());

        //set the end date/time in the rowset

        /*Option1: date type conversion approach
        //convert java.util.Date to oracle.jbo.domain.Date
        //java.util.Date utilDate = (java.util.Date)endDate;
        //java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("Option 1");
        java.sql.Date sqlDate = new java.sql.Date(endDate.getTime());
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        System.out.println("sqlDate: " + formatter.format(sqlDate));
        oracle.jbo.domain.Date ojdDate = new oracle.jbo.domain.Date(formatter.format(sqlDate));
        System.out.println("ojdDate: " + formatter.format(ojdDate));
        edtAB.setInputValue(ojdDate);*/

        /*Option2: formatter approach - might work with the right format
         * Cannot create an object of type:oracle.jbo.domain.Date from type:java.lang.String with value:2010/04/02 01:30 PM
        System.out.println("Option 2");
        Format formatter;
        //formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa"); //doesn't work
        formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aaa");   //doesn't work
        edtAB.setInputValue(formatter.format(endDate));
        */

        /*Option3: jboDateFormat
        System.out.println("Option 3");
        SimpleDateFormat jboDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        edtAB.setInputValue(new oracle.jbo.domain.Date(jboDateFormat.format(endDate)));*/

        /*Option4:*/
        //System.out.println("updateCreateEndDateTime: Option 4");
        //oracle.jbo.domain.Date dbDate;
        //java.util.Date inputDate;
        //old code
        //edtAB.setInputValue(new oracle.jbo.domain.Date(new java.sql.Timestamp(endDate.getTime())));
        //new code
        try {
            edtAB.setInputValue(new TimestampTZ(new java.sql.Timestamp(endDate.getTime())));
        } catch (java.sql.SQLException ex) {
            System.out.println("CalendarBean.updateCreateEndDateTime: error on end date conversion: " +
                               ex);
        }

        System.out.println("updateCreateEndDateTime: EndDT After rec update= " +
                           edtAB.getInputValue()); //debug
        System.out.println("updateCreateEndDateTime: exiting");
    }

    private void iterateMapOO(Map m) {
        /*This is a method for examining the contents of a map object containing
         * strings or objects.
         * Iterate through the specified map and output the key/value pairs
        */
        System.out.println("iterateMapOO: entering");
        int mapsize = m.size();
        Iterator keyValuePairs1 = m.entrySet().iterator();
        for (int i = 0; i < mapsize; i++) {
            Map.Entry entry = (Map.Entry)keyValuePairs1.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("iterateMapOO: " + key + " = " + value);
        }
        System.out.println("iterateMapOO: exiting");
    }

    private void iterateMapOA(Map m) {
        /*This is a method for examining the contents of a map object where the
         * value is an array.
         * Iterate through the specified map and output the key/value pairs
        */
        System.out.println("iterateMapOA: exiting");
        int mapsize = m.size();
        Iterator keyValuePairs1 = m.entrySet().iterator();
        for (int i = 0; i < mapsize; i++) {
            Map.Entry entry = (Map.Entry)keyValuePairs1.next();
            Object key = entry.getKey();
            String value[] = (String[])entry.getValue();
            for (int x = 0; x < value.length; x++) {
                System.out.println("iterateMapOA: " + key + " = " + value[x]);
            }
        }
        System.out.println("iterateMapOA: exiting");
    }

    private void extractStartDateTimeVals(Date dStart) {
        /*Update class properties with the date components
         * for access by the popup controls
         */

        System.out.println("extractStartDateTimeVals: entering");
        //initialize a calendar obj based on the specified datetime
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getDefault()); //use the local timezone
        //dStart is in the server's timezone
        cal.setTime(dStart);
        //System.out.println("extractStartDateTimeVals: time in default time zone= " + cal.getTime());
        cal.setTimeZone(getTimeZone()); //set the time zone used when getting the hour
        //System.out.println("extractStartDateTimeVals: time in calendar's time zone= " + cal.getTime());

        //determine the AM/PM hour
        if (cal.get(Calendar.HOUR) == 0) {
            //make 0 AM/PM -> 12 AM/PM because the hour control has range of 1-12
            hour = 12;
        } else {
            hour = cal.get(Calendar.HOUR);
        }
        //Get the minute value
        minute = cal.get(Calendar.MINUTE);

        //Determine if AM or PM
        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
            ampm = "AM";
        } else {
            ampm = "PM";
        }

        //Get the date
        /*java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        startDate = new oracle.jbo.domain.Date(sqlDate);*/
        startDate = cal.getTime();
        System.out.println("extractStartDateTimeVals: Extracted start date time= " +
                           startDate + " " + hour + ":" + minute + " " + ampm);
    }

    public oracle.jbo.domain.Date toJboDate(java.util.Date pJavaDate) {
        return new oracle.jbo.domain.Date(new Timestamp(pJavaDate.getTime()));
    }

    public java.util.Date toJavaDate(oracle.jbo.domain.Date pJboDate) {
        return new Date(pJboDate.timestampValue().getTime());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {


        BindingContainer binding =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            binding.getOperationBinding("getContent");
        content = (String)operationBinding.execute();

        return content;
    }


    public String getRow() {
        BindingContainer binding =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            binding.getOperationBinding("getRow");
        row = (String)operationBinding.execute();

        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setPopupUI(RichPopup popupUI) {
        this.popupUI = popupUI;
    }

    public RichPopup getPopupUI() {
        return popupUI;
    }


    public void dialogDeleteListener(DialogEvent dialogEvent) {
        System.out.println("dialogDeleteListener  entering: The dialog outcome is:" +
                           dialogEvent.getOutcome());

        //if the selected the OK button then...
        if (dialogEvent.getOutcome().equals(Outcome.ok)) {
            //execute the delete
            System.out.println("dialogDeleteListener: user clicked ok");
            fireDeleteActivity();
            
        } else {
            //indicate the delete was not confirmed
            System.out.println("dialogDeleteListener: user clicked cancel");
        }
        System.out.println("dialogDeleteListener: exiting");
    }


    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }


}
