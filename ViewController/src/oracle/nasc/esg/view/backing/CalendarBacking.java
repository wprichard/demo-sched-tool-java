package oracle.nasc.esg.view.backing;

import java.sql.SQLException;
import java.sql.Time;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichNoteWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSeparator;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.TimestampTZ;

import oracle.nasc.esg.view.util.PLSQLUtils;

import org.apache.myfaces.trinidad.component.UIXGroup;


//import javax.faces.component.UIComponent;


//import oracle.adf.view.js.base.AdfPage;


public class CalendarBacking {
    private RichCalendar c1;
    private RichPopup p1;
    private RichNoteWindow nw1;
    private RichInputText it1;
    private RichInputText it2;
    private RichInputText it3;
    private RichInputText it4;
    private RichPanelGroupLayout pgl1;
    private RichInputDate id1;
    private RichInputDate id2;
    private RichPopup p2;
    private RichDialog d1;
    private RichPanelFormLayout pfl1;
    private RichPanelGroupLayout pgl2;
    private RichPanelGroupLayout pgl3;
    private RichPanelHeader ph1;
    private RichPanelHeader ph2;
    private RichInputText it5; //create
    private RichInputText it6; //create
    private RichInputText it7;
    private RichInputText it8;
    private RichInputText it9;
    private RichSelectOneChoice soc1;
    private UISelectItems si1;
    private RichInputDate id3;
    private RichInputNumberSpinbox ins2;
    private RichInputDate id4;
    private RichSeparator s1;
    private RichPanelGroupLayout pgl5;
    private RichInputNumberSpinbox ins1;
    private RichInputNumberSpinbox ins3;
    private RichSelectOneRadio sor1;
    private RichSelectItem si2;
    private RichSelectItem si3;
    private RichPopup popup1;
    private RichDialog dialog1;
    private RichPanelFormLayout pfl2;
    private RichPanelLabelAndMessage plam1;
    private RichPanelFormLayout pfl3;

    private RichCommandButton cb1; //create
    private RichCommandButton cb2; //create
    private RichPanelFormLayout panelFormLayout2;
    private RichPanelFormLayout panelFormLayout3;
    private RichSelectOneChoice selectOneChoice2;
    private UISelectItems selectItems2;
    private RichSelectOneChoice selectOneChoice3;
    private UISelectItems selectItems3;
    private RichSelectOneChoice selectOneChoice4;
    private UISelectItems selectItems4;
    private RichPanelLabelAndMessage panelLabelAndMessage1;
    private RichCommandButton cb3;
    private RichCommandButton cb4;
    private RichPanelFormLayout panelFormLayout1;
    private RichPanelGroupLayout panelGroupLayout1;
    private RichCommandButton cbSave; //edit
    private RichCommandButton cbCancel; //edit
    private RichPanelGroupLayout panelGroupLayout2;
    private RichPanelHeader panelHeader1;
    private RichPanelFormLayout pfl4;
    private RichInputText inputText1; //edit
    private RichInputText inputText2; //edit
    private RichPanelHeader panelHeader2;
    private RichPanelFormLayout pfl5;
    private RichSelectOneChoice soc4;
    private UISelectItems si8;
    private RichInputText inputText4;
    private RichInputText inputText5;
    private RichSelectOneChoice soc7;
    private UISelectItems si9;
    private RichInputDate inputDate1;
    private RichPanelLabelAndMessage plam2;
    private RichPanelGroupLayout panelGroupLayout3;
    private RichInputNumberSpinbox inputNumberSpinbox1;
    private RichInputNumberSpinbox inputNumberSpinbox2;
    private RichSelectOneRadio selectOneRadio1;
    private RichSelectItem selectItem1;
    private RichSelectItem selectItem2;
    private RichInputNumberSpinbox inputNumberSpinbox3;
    private RichInputDate inputDate2;
    private RichSeparator separator1;
    private RichCommandButton cbEdit; //edit
    private RichCommandButton cbDelete;
    private RichPopup p3;
    private RichDialog d2;
    private RichOutputText ot1;
    private RichPanelStretchLayout psl1;
    private RichSelectOneChoice soc8;
    private UISelectItems si10;
    private RichPanelFormLayout pfl6;
    private RichInputText it10;
    private RichInputText it11;
    private String costCenter;
    private String officePhone;
    private String cellPhone;
    private RichPanelGroupLayout pgl4;
    private RichSelectOneRadio sor2;
    private RichSelectItem si15;

    private RichPanelGroupLayout pgl6;
    private RichInputComboboxListOfValues customerNameId;
    private RichPanelGroupLayout pgl7;
    private RichSelectOneRadio sor3;
    private RichSelectItem si21;
    private RichSelectItem si22;
    private RichPanelGroupLayout pgl8;
    private RichInputComboboxListOfValues inputComboboxListOfValues1;
    private int roleId;
    private int lobId;
    private int countryId;
    private int oppFocusId;
    private String eventType;

    private RichSelectItem si16;

    private RichInputText it12;
    private RichInputText it13;
    private RichSelectOneChoice soc2;
    private RichSelectOneChoice soc26;
    private UISelectItems si5;
    private UISelectItems si55;
    private RichOutputText ot2;
    private RichSelectOneChoice soc9;
    private UISelectItems si11;
    private RichOutputText ot3;
    private RichSelectOneChoice soc12;
    private UISelectItems si14;
    private RichOutputText ot6;
    private RichInputText it14;
    private RichInputText it15;
    private RichSelectOneChoice soc13;
    private UISelectItems si17;
    private RichOutputText ot7;
    private RichSelectOneChoice soc14;
    private UISelectItems si18;
    private RichOutputText ot8;
    private RichSelectOneChoice soc15;
    private UISelectItems si19;
    private RichOutputText ot9;
    private RichSelectOneChoice soc11;
    private UISelectItems si13;
    private RichOutputText ot5;
    private RichSelectOneChoice soc10;
    private UISelectItems si12;
    private RichOutputText ot4;
    private RichSelectOneChoice soc16;
    private UISelectItems si20;
    private RichOutputText ot10;
    private RichSelectOneChoice soc17;
    private UISelectItems si23;
    private RichOutputText ot12;
    private RichSelectOneChoice soc5;
    private UISelectItems si4;
    private RichSelectOneChoice soc6;
    private UISelectItems si7;
    private RichSelectOneChoice soc3;
    private UISelectItems si6;
    private RichSelectOneChoice soc18;
    private UISelectItems si24;

    private UIXGroup g1;
    private RichDialog d3;
    private RichPanelFormLayout pfl7;
    private RichPanelGroupLayout pgl9;

    private RichCommandButton cb5;
    private RichCommandButton cb6;
    private RichPopup p4;
    private RichDialog d4;
    private RichOutputText ot14;
    private RichPanelGroupLayout pgl10;
    private RichPanelHeader ph3;
    private RichPanelFormLayout pfl8;
    private RichInputText it17;
    private RichPanelHeader ph4;
    private RichPanelFormLayout pfl9;
    private RichSelectOneChoice soc20;
    private UISelectItems si26;
    private RichInputText it18;
    private RichInputText it19;
    private RichSelectOneChoice soc21;
    private UISelectItems si27;
    private RichOutputText ot15;
    private RichSelectOneChoice soc22;
    private UISelectItems si28;
    private RichSelectOneChoice soc23;
    private UISelectItems si29;
    private RichOutputText ot17;
    private RichSelectOneChoice soc24;
    private UISelectItems si30;
    private RichOutputText ot18;
    private RichSelectOneChoice soc25;
    private UISelectItems si31;
    private RichPanelGroupLayout pgl11;
    private RichSelectOneRadio sor4;
    private RichSelectItem si32;
    private RichSelectItem si33;
    private RichInputText it20;
    private RichInputText it21;
    private RichSelectOneChoice soc27;
    private UISelectItems si34;
    private RichOutputText ot19;
    private RichSelectOneChoice soc28;
    private UISelectItems si35;
    private RichInputDate id5;
    private RichPanelLabelAndMessage plam3;
    private RichPanelGroupLayout pgl12;
    private RichInputNumberSpinbox ins4;
    private RichInputNumberSpinbox ins5;
    private RichSelectOneRadio sor5;
    private RichSelectItem si36;
    private RichSelectItem si37;
    private RichInputNumberSpinbox ins6;
    private RichInputDate id6;
    private RichSeparator s2;
    private RichInputComboboxListOfValues customerlnameId;
    private RichOutputText ot13;
    private RichInputComboboxListOfValues iclov1;
    private RichOutputText ot11;
    private RichInputText it16;
    private java.sql.Date startDate;
    private RichOutputText ot16;


    //edit

    private BindingContainer bindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setC1(RichCalendar c1) {
        this.c1 = c1;
    }

    public RichCalendar getC1() {
        return c1;
    }


    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }

    public void setNw1(RichNoteWindow nw1) {
        this.nw1 = nw1;
    }

    public RichNoteWindow getNw1() {
        return nw1;
    }


    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void setIt3(RichInputText it3) {
        this.it3 = it3;
    }

    public RichInputText getIt3() {
        return it3;
    }

    public void setIt4(RichInputText it4) {
        this.it4 = it4;
    }

    public RichInputText getIt4() {
        return it4;
    }

    public void setPgl1(RichPanelGroupLayout pgl1) {
        this.pgl1 = pgl1;
    }

    public RichPanelGroupLayout getPgl1() {
        return pgl1;
    }


    public void setId1(RichInputDate id1) {
        this.id1 = id1;
    }

    public RichInputDate getId1() {
        return id1;
    }

    public void setId2(RichInputDate id2) {
        this.id2 = id2;
    }

    public RichInputDate getId2() {
        return id2;
    }

    public void setP2(RichPopup p2) {
        this.p2 = p2;
    }

    public RichPopup getP2() {
        return p2;
    }

    public void setD1(RichDialog d1) {
        this.d1 = d1;
    }

    public RichDialog getD1() {
        return d1;
    }

    public void setPfl1(RichPanelFormLayout pfl1) {
        this.pfl1 = pfl1;
    }

    public RichPanelFormLayout getPfl1() {
        return pfl1;
    }

    public void setPgl2(RichPanelGroupLayout pgl2) {
        this.pgl2 = pgl2;
    }

    public RichPanelGroupLayout getPgl2() {
        return pgl2;
    }


    public void setPgl3(RichPanelGroupLayout pgl3) {
        this.pgl3 = pgl3;
    }

    public RichPanelGroupLayout getPgl3() {
        return pgl3;
    }

    public void setPh1(RichPanelHeader ph1) {
        this.ph1 = ph1;
    }

    public RichPanelHeader getPh1() {
        return ph1;
    }

    public void setPh2(RichPanelHeader ph2) {
        this.ph2 = ph2;
    }

    public RichPanelHeader getPh2() {
        return ph2;
    }

    public void setIt5(RichInputText it5) {
        this.it5 = it5;
    }

    public RichInputText getIt5() {
        return it5;
    }

    public void setIt6(RichInputText it6) {
        this.it6 = it6;
    }

    public RichInputText getIt6() {
        return it6;
    }


    public void setIt7(RichInputText it7) {
        this.it7 = it7;
    }

    public RichInputText getIt7() {
        return it7;
    }

    public void setIt8(RichInputText it8) {
        this.it8 = it8;
    }

    public RichInputText getIt8() {
        return it8;
    }


    public void setIt9(RichInputText it9) {
        this.it9 = it9;
    }

    public RichInputText getIt9() {
        return it9;
    }

    public void setSoc1(RichSelectOneChoice soc1) {
        this.soc1 = soc1;
    }

    public RichSelectOneChoice getSoc1() {
        return soc1;
    }

    public void setSi1(UISelectItems si1) {
        this.si1 = si1;
    }

    public UISelectItems getSi1() {
        return si1;
    }

    public void setId3(RichInputDate id3) {
        this.id3 = id3;
    }

    public RichInputDate getId3() {
        return id3;
    }


    public void setIns2(RichInputNumberSpinbox ins2) {
        this.ins2 = ins2;
    }

    public RichInputNumberSpinbox getIns2() {
        return ins2;
    }

    public void setId4(RichInputDate id4) {
        this.id4 = id4;
    }

    public RichInputDate getId4() {
        return id4;
    }


    public void setS1(RichSeparator s1) {
        this.s1 = s1;
    }

    public RichSeparator getS1() {
        return s1;
    }


    public void setPgl5(RichPanelGroupLayout pgl5) {
        this.pgl5 = pgl5;
    }

    public RichPanelGroupLayout getPgl5() {
        return pgl5;
    }

    public void setIns1(RichInputNumberSpinbox ins1) {
        this.ins1 = ins1;
    }

    public RichInputNumberSpinbox getIns1() {
        return ins1;
    }

    public void setIns3(RichInputNumberSpinbox ins3) {
        this.ins3 = ins3;
    }

    public RichInputNumberSpinbox getIns3() {
        return ins3;
    }

    public void setSor1(RichSelectOneRadio sor1) {
        this.sor1 = sor1;
    }

    public RichSelectOneRadio getSor1() {
        return sor1;
    }

    public void setSi2(RichSelectItem si2) {
        this.si2 = si2;
    }

    public RichSelectItem getSi2() {
        return si2;
    }

    public void setSi3(RichSelectItem si3) {
        this.si3 = si3;
    }

    public RichSelectItem getSi3() {
        return si3;
    }


    public void setPopup1(RichPopup popup1) {
        this.popup1 = popup1;
    }

    public RichPopup getPopup1() {
        return popup1;
    }

    public void setDialog1(RichDialog dialog1) {
        this.dialog1 = dialog1;
    }

    public RichDialog getDialog1() {
        return dialog1;
    }


    public void setPfl2(RichPanelFormLayout pfl2) {
        this.pfl2 = pfl2;
    }

    public RichPanelFormLayout getPfl2() {
        return pfl2;
    }


    public void setPlam1(RichPanelLabelAndMessage plam1) {
        this.plam1 = plam1;
    }

    public RichPanelLabelAndMessage getPlam1() {
        return plam1;
    }


    public void setPfl3(RichPanelFormLayout pfl3) {
        this.pfl3 = pfl3;
    }

    public RichPanelFormLayout getPfl3() {
        return pfl3;
    }


    public void setCb1(RichCommandButton cb1) {
        this.cb1 = cb1;
    }

    public RichCommandButton getCb1() {
        return cb1;
    }

    public void setCb2(RichCommandButton cb2) {
        this.cb2 = cb2;
    }

    public RichCommandButton getCb2() {
        return cb2;
    }


    public void demosys_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //setShow2(Boolean.TRUE.equals(valueChangeEvent.getNewValue()));
        System.out.println("Old DemoSystem: " +
                           valueChangeEvent.getOldValue());
        System.out.println("New DemoSystem: " +
                           valueChangeEvent.getNewValue());

        //        BindingContainer bindings = ADFUtils.getBindingContainer();
        //        ControlBinding dsCB = bindings.getControlBinding("DemoSystem");
        //        AttributeBinding dsAB = (AttributeBinding)dsCB;
        //        System.out.println("DemoSystem model value before :" + dsAB.getInputValue());
        //        dsAB.setInputValue(valueChangeEvent.getNewValue()); //assign the int value
        //        System.out.println("DemoSystem model value after :" + dsAB.getInputValue());
        soc5.setValue(valueChangeEvent.getNewValue());

        AdfFacesContext facesCtxt = AdfFacesContext.getCurrentInstance();
        //facesCtxt.addPartialTarget(soc6);
        //facesCtxt.addPartialTarget(getSoc5());
        facesCtxt.addPartialTarget(soc5);
        //facesCtxt.addPartialTarget(si7);
        System.out.println("partial targets added");
        //        System.out.println("si7 val: " + si7.getValue());
        //        UISelectItem item = (UISelectItem)si7.getValue();
        //        si7.setValue(new Integer(0));
        //        System.out.println("si7 val: " + si7.getValue());
        soc5.resetValue();
        System.out.println("soc5 reset");
        //        soc6.validate(FacesContext.getCurrentInstance());
        //call the Render Response phase (thereby skipping validation of soc5)
        FacesContext.getCurrentInstance().renderResponse();
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String cb2_action() throws SQLException {
        /* This method attempts to commit the new booking record to the database.
         */
        String pDescription;
        String pRequest_basc_desc;
        String pDemoSystem;
        String pCustomer;
        String pCustomerId;
        Time pStartTime;
        Time pEndTime;
        java.sql.Date pStartDate;
        java.sql.Date pEndDate;

        System.out.println("cb2_action: Preparing to commit...");
        //execute the commit operation on the binding
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        //if errors is not empty (i.e. there are errors)
        if (!operationBinding.getErrors().isEmpty()) {
            //The commit had errors; TODO raise an error message
            System.out.println("cb2_action: commit error");
            return null;
        } else {
            //else no commit errors; do nothing
            PLSQLUtils plsql = new PLSQLUtils();
            plsql.initConnection();


            //Get officePhone
            if (bindings().get("OfficePhone") != null) {
                //  System.out.println("Valor de CostCenter: "+bindings().get("CostCenter"));
                officePhone = String.valueOf(bindings().get("OfficePhone"));

            } else {
                officePhone = "";
            }


            //Get cellPhone
            if (bindings().get("CellPhone") != null) {
                //  System.out.println("Valor de CostCenter: "+bindings().get("CostCenter"));
                cellPhone = String.valueOf(bindings().get("CellPhone"));

            } else {
                cellPhone = "";
            }
            //Get Role
            if (bindings().get("Role1") != null) {
                //       System.out.println("Valor de RoleId: "+bindings().get("Role1"));
                String aux = String.valueOf(bindings().get("Role1"));
                roleId = Integer.parseInt(aux);
            } else {
                roleId = 0;
            }

            //Get LOB
            if (bindings().get("Lob1") != null) {
                //       System.out.println("Valor de LobId1: "+bindings().get("Lob1"));
                String aux = String.valueOf(bindings().get("Lob1"));
                lobId = Integer.parseInt(aux);
            } else {
                lobId = 0;
            }


            //Get Country
            if (bindings().get("Country1") != null) {
                //    System.out.println("Valor de CountryId1: "+bindings().get("Country1"));
                String aux = String.valueOf(bindings().get("Country1"));
                countryId = Integer.parseInt(aux);
            } else {
                countryId = 0;
            }

            //Get Focus
            if (bindings().get("Oportunityfocus1") != null) {
                //    System.out.println("Valor de oppFocusId: "+bindings().get("Oportunityfocus1"));
                String aux =
                    String.valueOf(bindings().get("Oportunityfocus1"));
                oppFocusId = Integer.parseInt(aux);
            } else {
                oppFocusId = 0;
            }
            //Get EvenType
            if (bindings().get("DemoType") != null) {
                //     System.out.println("Valor de EventType: "+bindings().get("DemoType"));
                eventType = String.valueOf(bindings().get("DemoType"));

            } else {
                eventType = "";
            }

            //Get CostCenter
            if (bindings().get("CostCenter") != null) {
                //     System.out.println("Valor de CostCenter: "+bindings().get("CostCenter"));
                costCenter = String.valueOf(bindings().get("CostCenter"));

            } else {
                costCenter = "";
            }

            //Get descripcion
            if (bindings().get("DemoDescription") != null) {
                //      System.out.println("Valor de DemoDescription: "+bindings().get("DemoDescription"));
                pDescription =
                        String.valueOf(bindings().get("DemoDescription"));

            } else {
                pDescription = "";
            }

            //Get Solution System
            if (bindings().get("DemoSystemsVO1") != null) {
                //      System.out.println("Valor de DemoSystemsVO1: "+bindings().get("DemoSystemsVO1"));
                pDemoSystem = String.valueOf(bindings().get("DemoSystemsVO1"));

            } else {
                pDemoSystem = "";
            }

            //    var vars= AdfPage.PAGE;
            //Get CustomerID
            if (bindings().get("Customerid") != null) {
                //          System.out.println("Valor de Customer: "+bindings().get("Customerid"));
                pCustomerId = String.valueOf(bindings().get("Customerid"));

            } else {
                pCustomerId = "";
            }
            //Get Customer
            if (bindings().get("Customerlname2") != null) {
                //               System.out.println("Valor de Customer: "+bindings().get("Customerlname2"));
                pCustomer = String.valueOf(bindings().get("Customerlname2"));

            } else {
                pCustomer = "";
            }

            pRequest_basc_desc =
                    "This request has been auto-generated from OSC Hosted Environment Scheduler. A\n" +
                    "request has been made for " + pDemoSystem +
                    ", Event Type: " + eventType + ", User Description: " +
                    pDescription;


            //Get the end date

            ControlBinding ctlBinding =
                bindings.getControlBinding("EndDatetime");
            AttributeBinding atrBinding = (AttributeBinding)ctlBinding;
            TimestampTZ endTSTZ = (TimestampTZ)atrBinding.getInputValue();
            pEndDate = endTSTZ.dateValue();

            System.out.println("pEndDate : " + pEndDate);


            //Get the start date & time
            ControlBinding ctlBindingstart =
                bindings.getControlBinding("StartDatetime");
            AttributeBinding atrBindingstart =
                (AttributeBinding)ctlBindingstart;
            TimestampTZ startTSTZ =
                (TimestampTZ)atrBindingstart.getInputValue();
            pStartDate = startTSTZ.dateValue();
            pStartTime = startTSTZ.timeValue();

            startDate = pStartDate;

            System.out.println("pStartDate : " + startDate);


            FacesContext fctx = FacesContext.getCurrentInstance();
            Application app = fctx.getApplication();
            ExpressionFactory expFact = app.getExpressionFactory();
            ELContext context = fctx.getELContext();
            ValueExpression valueExp =
                expFact.createValueExpression(context, "#{SetValue}",
                                              oracle.nasc.esg.view.bean.SelectOneRadioBean.class);
            //El segundo parametro es el acceso al bean que se quiere acceder
            //El tercer parametro es la clase del bean y se le agrega .class
            oracle.nasc.esg.view.bean.SelectOneRadioBean elfctx =
                (oracle.nasc.esg.view.bean.SelectOneRadioBean)valueExp.getValue(context);
            System.out.println(" isSelectCustomerRadio: " +
                               elfctx.isSelectCustomerRadio());
            if (elfctx.isSelectBandera() == true) {
                System.out.println("[CalendarBacking.java] cb2_action: Select Customer" );
                System.out.println("[CalendarBacking.java] cb2_action: plsql.callVmProvisioning BEGIN" );
                System.out.println("    "+59+", "+"unosolo@oracle.com"+", "+costCenter);
                System.out.println("    "+officePhone+", "+cellPhone+", "+"hw Partner 1");
                System.out.println("    "+"null"+", "+pRequest_basc_desc+", "+countryId);
                System.out.println("    "+roleId+", "+47+", "+47);
                System.out.println("    "+"null"+", "+pCustomerId+", "+"Project Name");
                System.out.println("    "+startDate+", "+pEndDate+", "+oppFocusId);
                System.out.println("    "+lobId+", "+"hw needs"+", "+2.5);
                System.out.println("    "+33.5+", "+"null"+", "+"osc-dev.us.oracle.com");
                System.out.println("    "+108);
                System.out.println("[CalendarBacking.java] cb2_action: plsql.callVmProvisioning END");
                
                plsql.callVmProvisioning(59, "unosolo@oracle.com", costCenter,
                                         officePhone, cellPhone, "hw Partner 1",
                                         null, pRequest_basc_desc, countryId,
                                         roleId, 47, 47,
                                         null, pCustomerId, "Project Name",
                                         startDate, pEndDate, oppFocusId,
                                         lobId, "hw needs", 2.5,
                                         33.5, null, "osc-dev.us.oracle.com",
                                         108);
                plsql.closeConnection();

            } else {
                System.out.println("[CalendarBacking.java] cb2_action: New Customer" );
                System.out.println("[CalendarBacking.java] cb2_action: plsql.callVmProvisioning BEGIN" );
                System.out.println("    "+59+", "+"unosolo@oracle.com"+", "+costCenter);
                System.out.println("    "+officePhone+", "+cellPhone+", "+"hw Partner 1");
                System.out.println("    "+"null"+", "+pRequest_basc_desc+", "+countryId);
                System.out.println("    "+roleId+", "+47+", "+47);
                System.out.println("    "+pCustomer+", "+null+", "+"Project Name");
                System.out.println("    "+startDate+", "+pEndDate+", "+oppFocusId);
                System.out.println("    "+lobId+", "+"hw needs"+", "+2.5);
                System.out.println("    "+33.5+", "+"null"+", "+"osc-dev.us.oracle.com");
                System.out.println("    "+108);
                System.out.println("[CalendarBacking.java] cb2_action: plsql.callVmProvisioning END");

                plsql.callVmProvisioning(59, "unosolo@oracle.com", costCenter,
                                         officePhone, cellPhone, "hw Partner 1",
                                         null, pRequest_basc_desc, countryId,
                                         roleId, 47, 47,
                                         pCustomer, null, "Project Name",
                                         startDate, pEndDate, oppFocusId,
                                         lobId, "hw needs", 2.5,
                                         33.5, null, "osc-dev.us.oracle.com",
                                         108);
                plsql.closeConnection();
            }

            elfctx.setSelectCustomerRadioCalendar(true);
            elfctx.setSelectCustomerRadioCalendarEdit(true);
            elfctx.setSelectBandera(true);


            System.out.println(pRequest_basc_desc);

            System.out.println("cb2_action: commit successful");
            return null;
        }
    }

    public void soc5_validator(FacesContext facesContext,
                               UIComponent uIComponent, Object object) {
        // Validate Demo Scenario
        //If component valid property is set false here, the UI will show
        //a red boc around the control and the picklist will not update.
        //This is similar to what happens if field is made mandatory and is
        //not desirable.
        //Using this method to test validation code.
        //This event only fires when there is a selection in the control and the
        //parent (Demo Systems) is changed. It does not fire if there is no
        //selection in Demo Scenarios and Demo Systems is changed.

        /* Use the javax.faces.validator.ValidatorException exception to throw the
         * appropriate exceptions and the javax.faces.application.FacesMessage
         * error message to generate the corresponding error messages.
         */
        //        System.out.println("Validating Demo Scenarios");
        //        //FacesContext fc = FacesContext.getCurrentInstance();
        //        FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dummy Save validation exception", "detail string");
        //        facesContext.addMessage(uIComponent.getClientId(facesContext), fmsg);
        //        //ValidatorException valx = new ValidatorException(fmsg);
        //        Object val = ((RichSelectOneChoice)uIComponent).getValue();
        //        if (<valuenotvalid>) {
        //            ((RichSelectOneChoice)uIComponent).setValid(false);
        //        }
        //        facesContext.getApplication().getViewHandler().restoreView(facesContext, "/DemoCalendar.jspx");
    }


    public void setPanelFormLayout2(RichPanelFormLayout panelFormLayout2) {
        this.panelFormLayout2 = panelFormLayout2;
    }

    public RichPanelFormLayout getPanelFormLayout2() {
        return panelFormLayout2;
    }


    public void setPanelFormLayout3(RichPanelFormLayout panelFormLayout3) {
        this.panelFormLayout3 = panelFormLayout3;
    }

    public RichPanelFormLayout getPanelFormLayout3() {
        return panelFormLayout3;
    }


    public void setSelectOneChoice2(RichSelectOneChoice selectOneChoice2) {
        this.selectOneChoice2 = selectOneChoice2;
    }

    public RichSelectOneChoice getSelectOneChoice2() {
        return selectOneChoice2;
    }

    public void setSelectItems2(UISelectItems selectItems2) {
        this.selectItems2 = selectItems2;
    }

    public UISelectItems getSelectItems2() {
        return selectItems2;
    }

    public void setSelectOneChoice3(RichSelectOneChoice selectOneChoice3) {
        this.selectOneChoice3 = selectOneChoice3;
    }

    public RichSelectOneChoice getSelectOneChoice3() {
        return selectOneChoice3;
    }

    public void setSelectItems3(UISelectItems selectItems3) {
        this.selectItems3 = selectItems3;
    }

    public UISelectItems getSelectItems3() {
        return selectItems3;
    }


    public void setSelectOneChoice4(RichSelectOneChoice selectOneChoice4) {
        this.selectOneChoice4 = selectOneChoice4;
    }

    public RichSelectOneChoice getSelectOneChoice4() {
        return selectOneChoice4;
    }

    public void setSelectItems4(UISelectItems selectItems4) {
        this.selectItems4 = selectItems4;
    }

    public UISelectItems getSelectItems4() {
        return selectItems4;
    }


    public void setPanelLabelAndMessage1(RichPanelLabelAndMessage panelLabelAndMessage1) {
        this.panelLabelAndMessage1 = panelLabelAndMessage1;
    }

    public RichPanelLabelAndMessage getPanelLabelAndMessage1() {
        return panelLabelAndMessage1;
    }


    public void setCbEdit(RichCommandButton cb3) {
        this.cbEdit = cbEdit;
    }

    public RichCommandButton getCbEdit() {
        return cbEdit;
    }

    public void setCbDelete(RichCommandButton cb4) {
        this.cbDelete = cbDelete;
    }

    public RichCommandButton getCbDelete() {
        return cbDelete;
    }

    public void setPanelFormLayout1(RichPanelFormLayout panelFormLayout1) {
        this.panelFormLayout1 = panelFormLayout1;
    }

    public RichPanelFormLayout getPanelFormLayout1() {
        return panelFormLayout1;
    }

    public void setPanelGroupLayout1(RichPanelGroupLayout panelGroupLayout1) {
        this.panelGroupLayout1 = panelGroupLayout1;
    }

    public RichPanelGroupLayout getPanelGroupLayout1() {
        return panelGroupLayout1;
    }

    public void setCbSave(RichCommandButton commandButton1) {
        this.cbSave = commandButton1;
    }

    public RichCommandButton getCbSave() {
        return cbSave;
    }

    public void setCbCancel(RichCommandButton commandButton2) {
        this.cbCancel = commandButton2;
    }

    public RichCommandButton getCbCancel() {
        return cbCancel;
    }

    public void setPanelGroupLayout2(RichPanelGroupLayout panelGroupLayout2) {
        this.panelGroupLayout2 = panelGroupLayout2;
    }

    public RichPanelGroupLayout getPanelGroupLayout2() {
        return panelGroupLayout2;
    }

    public void setPanelHeader1(RichPanelHeader panelHeader1) {
        this.panelHeader1 = panelHeader1;
    }

    public RichPanelHeader getPanelHeader1() {
        return panelHeader1;
    }

    public void setPfl4(RichPanelFormLayout pfl4) {
        this.pfl4 = pfl4;
    }

    public RichPanelFormLayout getPfl4() {
        return pfl4;
    }

    public void setInputText1(RichInputText inputText1) {
        this.inputText1 = inputText1;
    }

    public RichInputText getInputText1() {
        return inputText1;
    }

    public void setInputText2(RichInputText inputText2) {
        this.inputText2 = inputText2;
    }

    public RichInputText getInputText2() {
        return inputText2;
    }

    public void setPanelHeader2(RichPanelHeader panelHeader2) {
        this.panelHeader2 = panelHeader2;
    }

    public RichPanelHeader getPanelHeader2() {
        return panelHeader2;
    }

    public void setPfl5(RichPanelFormLayout pfl5) {
        this.pfl5 = pfl5;
    }

    public RichPanelFormLayout getPfl5() {
        return pfl5;
    }


    public void setSoc4(RichSelectOneChoice soc4) {
        this.soc4 = soc4;
    }

    public RichSelectOneChoice getSoc4() {
        return soc4;
    }

    public void setSi8(UISelectItems si8) {
        this.si8 = si8;
    }

    public UISelectItems getSi8() {
        return si8;
    }


    public void setInputText4(RichInputText inputText4) {
        this.inputText4 = inputText4;
    }

    public RichInputText getInputText4() {
        return inputText4;
    }

    public void setInputText5(RichInputText inputText5) {
        this.inputText5 = inputText5;
    }

    public RichInputText getInputText5() {
        return inputText5;
    }

    public void setSoc7(RichSelectOneChoice soc7) {
        this.soc7 = soc7;
    }

    public RichSelectOneChoice getSoc7() {
        return soc7;
    }

    public void setSi9(UISelectItems si9) {
        this.si9 = si9;
    }

    public UISelectItems getSi9() {
        return si9;
    }

    public void setInputDate1(RichInputDate inputDate1) {
        this.inputDate1 = inputDate1;
    }

    public RichInputDate getInputDate1() {
        return inputDate1;
    }

    public void setPlam2(RichPanelLabelAndMessage plam2) {
        this.plam2 = plam2;
    }

    public RichPanelLabelAndMessage getPlam2() {
        return plam2;
    }

    public void setPanelGroupLayout3(RichPanelGroupLayout panelGroupLayout3) {
        this.panelGroupLayout3 = panelGroupLayout3;
    }

    public RichPanelGroupLayout getPanelGroupLayout3() {
        return panelGroupLayout3;
    }

    public void setInputNumberSpinbox1(RichInputNumberSpinbox inputNumberSpinbox1) {
        this.inputNumberSpinbox1 = inputNumberSpinbox1;
    }

    public RichInputNumberSpinbox getInputNumberSpinbox1() {
        return inputNumberSpinbox1;
    }

    public void setInputNumberSpinbox2(RichInputNumberSpinbox inputNumberSpinbox2) {
        this.inputNumberSpinbox2 = inputNumberSpinbox2;
    }

    public RichInputNumberSpinbox getInputNumberSpinbox2() {
        return inputNumberSpinbox2;
    }

    public void setSelectOneRadio1(RichSelectOneRadio selectOneRadio1) {
        this.selectOneRadio1 = selectOneRadio1;
    }

    public RichSelectOneRadio getSelectOneRadio1() {
        return selectOneRadio1;
    }

    public void setSelectItem1(RichSelectItem selectItem1) {
        this.selectItem1 = selectItem1;
    }

    public RichSelectItem getSelectItem1() {
        return selectItem1;
    }

    public void setSelectItem2(RichSelectItem selectItem2) {
        this.selectItem2 = selectItem2;
    }

    public RichSelectItem getSelectItem2() {
        return selectItem2;
    }

    public void setInputNumberSpinbox3(RichInputNumberSpinbox inputNumberSpinbox3) {
        this.inputNumberSpinbox3 = inputNumberSpinbox3;
    }

    public RichInputNumberSpinbox getInputNumberSpinbox3() {
        return inputNumberSpinbox3;
    }

    public void setInputDate2(RichInputDate inputDate2) {
        this.inputDate2 = inputDate2;
    }

    public RichInputDate getInputDate2() {
        return inputDate2;
    }

    public void setSeparator1(RichSeparator separator1) {
        this.separator1 = separator1;
    }

    public RichSeparator getSeparator1() {
        return separator1;
    }

    public void setCb3(RichCommandButton cb3) {
        this.cb3 = cb3;
    }

    public RichCommandButton getCb3() {
        return cb3;
    }

    public void setCb4(RichCommandButton cb4) {
        this.cb4 = cb4;
    }

    public RichCommandButton getCb4() {
        return cb4;
    }

    public void setP3(RichPopup p3) {
        this.p3 = p3;
    }

    public RichPopup getP3() {
        return p3;
    }

    public void setD2(RichDialog d2) {
        this.d2 = d2;
    }

    public RichDialog getD2() {
        return d2;
    }

    public void setOt1(RichOutputText ot1) {
        this.ot1 = ot1;
    }

    public RichOutputText getOt1() {
        return ot1;
    }

    public void setPsl1(RichPanelStretchLayout psl1) {
        this.psl1 = psl1;
    }

    public RichPanelStretchLayout getPsl1() {
        return psl1;
    }

    public void setSoc8(RichSelectOneChoice soc8) {
        this.soc8 = soc8;
    }

    public RichSelectOneChoice getSoc8() {
        return soc8;
    }

    public void setSi10(UISelectItems si10) {
        this.si10 = si10;
    }

    public UISelectItems getSi10() {
        return si10;
    }

    public void oscRequestActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPfl6(RichPanelFormLayout pfl6) {
        this.pfl6 = pfl6;
    }

    public RichPanelFormLayout getPfl6() {
        return pfl6;
    }


    public void setIt10(RichInputText it10) {
        this.it10 = it10;
    }

    public RichInputText getIt10() {
        return it10;
    }

    public void setIt11(RichInputText it11) {
        this.it11 = it11;
    }

    public RichInputText getIt11() {
        return it11;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }


    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }


    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }


    public void setPgl4(RichPanelGroupLayout pgl4) {
        this.pgl4 = pgl4;
    }

    public RichPanelGroupLayout getPgl4() {
        return pgl4;
    }

    public void setSor2(RichSelectOneRadio sor2) {
        this.sor2 = sor2;
    }

    public RichSelectOneRadio getSor2() {
        return sor2;
    }

    public void setSi15(RichSelectItem si15) {
        this.si15 = si15;
    }

    public RichSelectItem getSi15() {
        return si15;
    }

    public void setSi16(RichSelectItem si16) {
        this.si16 = si16;
    }

    public RichSelectItem getSi16() {
        return si16;
    }

    public void setPgl6(RichPanelGroupLayout pgl6) {
        this.pgl6 = pgl6;
    }

    public RichPanelGroupLayout getPgl6() {
        return pgl6;
    }

    public void setCustomerNameId(RichInputComboboxListOfValues customerNameId) {
        this.customerNameId = customerNameId;
    }

    public RichInputComboboxListOfValues getCustomerNameId() {
        return customerNameId;
    }


    public void setPgl7(RichPanelGroupLayout pgl7) {
        this.pgl7 = pgl7;
    }

    public RichPanelGroupLayout getPgl7() {
        return pgl7;
    }

    public void setSor3(RichSelectOneRadio sor3) {
        this.sor3 = sor3;
    }

    public RichSelectOneRadio getSor3() {
        return sor3;
    }

    public void setSi21(RichSelectItem si21) {
        this.si21 = si21;
    }

    public RichSelectItem getSi21() {
        return si21;
    }

    public void setSi22(RichSelectItem si22) {
        this.si22 = si22;
    }

    public RichSelectItem getSi22() {
        return si22;
    }

    public void setPgl8(RichPanelGroupLayout pgl8) {
        this.pgl8 = pgl8;
    }

    public RichPanelGroupLayout getPgl8() {
        return pgl8;
    }


    public void setInputComboboxListOfValues1(RichInputComboboxListOfValues inputComboboxListOfValues1) {
        this.inputComboboxListOfValues1 = inputComboboxListOfValues1;
    }

    public RichInputComboboxListOfValues getInputComboboxListOfValues1() {
        return inputComboboxListOfValues1;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public List dc(String string) {
        // Add event code here...
        return null;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getLobId() {
        return lobId;
    }

    public void setLobId(int lobId) {
        this.lobId = lobId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getOppFocusId() {
        return oppFocusId;
    }

    public void setOppFocusId(int oppFocusId) {
        this.oppFocusId = oppFocusId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public void setIt12(RichInputText it12) {
        this.it12 = it12;
    }

    public RichInputText getIt12() {
        return it12;
    }

    public void setIt13(RichInputText it13) {
        this.it13 = it13;
    }

    public RichInputText getIt13() {
        return it13;
    }

    public void setSoc2(RichSelectOneChoice soc2) {
        this.soc2 = soc2;
    }

    public RichSelectOneChoice getSoc2() {
        return soc2;
    }

    public void setSi5(UISelectItems si5) {
        this.si5 = si5;
    }

    public UISelectItems getSi5() {
        return si5;
    }

    public void setSi55(UISelectItems si55) {
        this.si55 = si55;
    }

    public UISelectItems getSi55() {
        return si55;
    }

    public void setOt2(RichOutputText ot2) {
        this.ot2 = ot2;
    }

    public RichOutputText getOt2() {
        return ot2;
    }

    public void setSoc9(RichSelectOneChoice soc9) {
        this.soc9 = soc9;
    }

    public RichSelectOneChoice getSoc9() {
        return soc9;
    }

    public void setSi11(UISelectItems si11) {
        this.si11 = si11;
    }

    public UISelectItems getSi11() {
        return si11;
    }

    public void setOt3(RichOutputText ot3) {
        this.ot3 = ot3;
    }

    public RichOutputText getOt3() {
        return ot3;
    }


    public void setSoc12(RichSelectOneChoice soc12) {
        this.soc12 = soc12;
    }

    public RichSelectOneChoice getSoc12() {
        return soc12;
    }

    public void setSi14(UISelectItems si14) {
        this.si14 = si14;
    }

    public UISelectItems getSi14() {
        return si14;
    }

    public void setOt6(RichOutputText ot6) {
        this.ot6 = ot6;
    }

    public RichOutputText getOt6() {
        return ot6;
    }


    public void setSoc26(RichSelectOneChoice soc26) {
        this.soc26 = soc26;
    }

    public RichSelectOneChoice getSoc26() {
        return soc26;
    }


    public void setIt14(RichInputText it14) {
        this.it14 = it14;
    }

    public RichInputText getIt14() {
        return it14;
    }

    public void setIt15(RichInputText it15) {
        this.it15 = it15;
    }

    public RichInputText getIt15() {
        return it15;
    }

    public void setSoc13(RichSelectOneChoice soc13) {
        this.soc13 = soc13;
    }

    public RichSelectOneChoice getSoc13() {
        return soc13;
    }

    public void setSi17(UISelectItems si17) {
        this.si17 = si17;
    }

    public UISelectItems getSi17() {
        return si17;
    }

    public void setOt7(RichOutputText ot7) {
        this.ot7 = ot7;
    }

    public RichOutputText getOt7() {
        return ot7;
    }

    public void setSoc14(RichSelectOneChoice soc14) {
        this.soc14 = soc14;
    }

    public RichSelectOneChoice getSoc14() {
        return soc14;
    }

    public void setSi18(UISelectItems si18) {
        this.si18 = si18;
    }

    public UISelectItems getSi18() {
        return si18;
    }

    public void setOt8(RichOutputText ot8) {
        this.ot8 = ot8;
    }

    public RichOutputText getOt8() {
        return ot8;
    }


    public void setSoc15(RichSelectOneChoice soc15) {
        this.soc15 = soc15;
    }

    public RichSelectOneChoice getSoc15() {
        return soc15;
    }

    public void setSi19(UISelectItems si19) {
        this.si19 = si19;
    }

    public UISelectItems getSi19() {
        return si19;
    }

    public void setOt9(RichOutputText ot9) {
        this.ot9 = ot9;
    }

    public RichOutputText getOt9() {
        return ot9;
    }


    public void setSoc11(RichSelectOneChoice soc11) {
        this.soc11 = soc11;
    }

    public RichSelectOneChoice getSoc11() {
        return soc11;
    }

    public void setSi13(UISelectItems si13) {
        this.si13 = si13;
    }

    public UISelectItems getSi13() {
        return si13;
    }

    public void setOt5(RichOutputText ot5) {
        this.ot5 = ot5;
    }

    public RichOutputText getOt5() {
        return ot5;
    }

    public void setSoc10(RichSelectOneChoice soc10) {
        this.soc10 = soc10;
    }

    public RichSelectOneChoice getSoc10() {
        return soc10;
    }

    public void setSi12(UISelectItems si12) {
        this.si12 = si12;
    }

    public UISelectItems getSi12() {
        return si12;
    }

    public void setOt4(RichOutputText ot4) {
        this.ot4 = ot4;
    }

    public RichOutputText getOt4() {
        return ot4;
    }

    public void setSoc16(RichSelectOneChoice soc16) {
        this.soc16 = soc16;
    }

    public RichSelectOneChoice getSoc16() {
        return soc16;
    }

    public void setSi20(UISelectItems si20) {
        this.si20 = si20;
    }

    public UISelectItems getSi20() {
        return si20;
    }

    public void setOt10(RichOutputText ot10) {
        this.ot10 = ot10;
    }

    public RichOutputText getOt10() {
        return ot10;
    }

    public void setSoc17(RichSelectOneChoice soc17) {
        this.soc17 = soc17;
    }

    public RichSelectOneChoice getSoc17() {
        return soc17;
    }

    public void setSi23(UISelectItems si23) {
        this.si23 = si23;
    }

    public UISelectItems getSi23() {
        return si23;
    }

    public void setOt12(RichOutputText ot12) {
        this.ot12 = ot12;
    }

    public RichOutputText getOt12() {
        return ot12;
    }


    public void setSoc5(RichSelectOneChoice soc5) {
        this.soc5 = soc5;
    }

    public RichSelectOneChoice getSoc5() {
        return soc5;
    }

    public void setSi4(UISelectItems si4) {
        this.si4 = si4;
    }

    public UISelectItems getSi4() {
        return si4;
    }

    public void setSoc6(RichSelectOneChoice soc6) {
        this.soc6 = soc6;
    }

    public RichSelectOneChoice getSoc6() {
        return soc6;
    }

    public void setSi7(UISelectItems si7) {
        this.si7 = si7;
    }

    public UISelectItems getSi7() {
        return si7;
    }

    public void setSoc3(RichSelectOneChoice soc3) {
        this.soc3 = soc3;
    }

    public RichSelectOneChoice getSoc3() {
        return soc3;
    }

    public void setSi6(UISelectItems si6) {
        this.si6 = si6;
    }

    public UISelectItems getSi6() {
        return si6;
    }

    public void setSoc18(RichSelectOneChoice soc18) {
        this.soc18 = soc18;
    }

    public RichSelectOneChoice getSoc18() {
        return soc18;
    }

    public void setSi24(UISelectItems si24) {
        this.si24 = si24;
    }

    public UISelectItems getSi24() {
        return si24;
    }


    public void setG1(UIXGroup g1) {
        this.g1 = g1;
    }

    public UIXGroup getG1() {
        return g1;
    }

    public void setD3(RichDialog d3) {
        this.d3 = d3;
    }

    public RichDialog getD3() {
        return d3;
    }

    public void setPfl7(RichPanelFormLayout pfl7) {
        this.pfl7 = pfl7;
    }

    public RichPanelFormLayout getPfl7() {
        return pfl7;
    }

    public void setPgl9(RichPanelGroupLayout pgl9) {
        this.pgl9 = pgl9;
    }

    public RichPanelGroupLayout getPgl9() {
        return pgl9;
    }


    public void setCb5(RichCommandButton cb5) {
        this.cb5 = cb5;
    }

    public RichCommandButton getCb5() {
        return cb5;
    }

    public void setCb6(RichCommandButton cb6) {
        this.cb6 = cb6;
    }

    public RichCommandButton getCb6() {
        return cb6;
    }

    public void setP4(RichPopup p4) {
        this.p4 = p4;
    }

    public RichPopup getP4() {
        return p4;
    }

    public void setD4(RichDialog d4) {
        this.d4 = d4;
    }

    public RichDialog getD4() {
        return d4;
    }

    public void setOt14(RichOutputText ot14) {
        this.ot14 = ot14;
    }

    public RichOutputText getOt14() {
        return ot14;
    }

    public void setPgl10(RichPanelGroupLayout pgl10) {
        this.pgl10 = pgl10;
    }

    public RichPanelGroupLayout getPgl10() {
        return pgl10;
    }

    public void setPh3(RichPanelHeader ph3) {
        this.ph3 = ph3;
    }

    public RichPanelHeader getPh3() {
        return ph3;
    }

    public void setPfl8(RichPanelFormLayout pfl8) {
        this.pfl8 = pfl8;
    }

    public RichPanelFormLayout getPfl8() {
        return pfl8;
    }


    public void setIt17(RichInputText it17) {
        this.it17 = it17;
    }

    public RichInputText getIt17() {
        return it17;
    }

    public void setPh4(RichPanelHeader ph4) {
        this.ph4 = ph4;
    }

    public RichPanelHeader getPh4() {
        return ph4;
    }

    public void setPfl9(RichPanelFormLayout pfl9) {
        this.pfl9 = pfl9;
    }

    public RichPanelFormLayout getPfl9() {
        return pfl9;
    }


    public void setSoc20(RichSelectOneChoice soc20) {
        this.soc20 = soc20;
    }

    public RichSelectOneChoice getSoc20() {
        return soc20;
    }

    public void setSi26(UISelectItems si26) {
        this.si26 = si26;
    }

    public UISelectItems getSi26() {
        return si26;
    }

    public void setIt18(RichInputText it18) {
        this.it18 = it18;
    }

    public RichInputText getIt18() {
        return it18;
    }

    public void setIt19(RichInputText it19) {
        this.it19 = it19;
    }

    public RichInputText getIt19() {
        return it19;
    }

    public void setSoc21(RichSelectOneChoice soc21) {
        this.soc21 = soc21;
    }

    public RichSelectOneChoice getSoc21() {
        return soc21;
    }

    public void setSi27(UISelectItems si27) {
        this.si27 = si27;
    }

    public UISelectItems getSi27() {
        return si27;
    }

    public void setOt15(RichOutputText ot15) {
        this.ot15 = ot15;
    }

    public RichOutputText getOt15() {
        return ot15;
    }

    public void setSoc22(RichSelectOneChoice soc22) {
        this.soc22 = soc22;
    }

    public RichSelectOneChoice getSoc22() {
        return soc22;
    }

    public void setSi28(UISelectItems si28) {
        this.si28 = si28;
    }

    public UISelectItems getSi28() {
        return si28;
    }


    public void setSoc23(RichSelectOneChoice soc23) {
        this.soc23 = soc23;
    }

    public RichSelectOneChoice getSoc23() {
        return soc23;
    }

    public void setSi29(UISelectItems si29) {
        this.si29 = si29;
    }

    public UISelectItems getSi29() {
        return si29;
    }

    public void setOt17(RichOutputText ot17) {
        this.ot17 = ot17;
    }

    public RichOutputText getOt17() {
        return ot17;
    }

    public void setSoc24(RichSelectOneChoice soc24) {
        this.soc24 = soc24;
    }

    public RichSelectOneChoice getSoc24() {
        return soc24;
    }

    public void setSi30(UISelectItems si30) {
        this.si30 = si30;
    }

    public UISelectItems getSi30() {
        return si30;
    }

    public void setOt18(RichOutputText ot18) {
        this.ot18 = ot18;
    }

    public RichOutputText getOt18() {
        return ot18;
    }

    public void setSoc25(RichSelectOneChoice soc25) {
        this.soc25 = soc25;
    }

    public RichSelectOneChoice getSoc25() {
        return soc25;
    }

    public void setSi31(UISelectItems si31) {
        this.si31 = si31;
    }

    public UISelectItems getSi31() {
        return si31;
    }

    public void setPgl11(RichPanelGroupLayout pgl11) {
        this.pgl11 = pgl11;
    }

    public RichPanelGroupLayout getPgl11() {
        return pgl11;
    }

    public void setSor4(RichSelectOneRadio sor4) {
        this.sor4 = sor4;
    }

    public RichSelectOneRadio getSor4() {
        return sor4;
    }

    public void setSi32(RichSelectItem si32) {
        this.si32 = si32;
    }

    public RichSelectItem getSi32() {
        return si32;
    }

    public void setSi33(RichSelectItem si33) {
        this.si33 = si33;
    }

    public RichSelectItem getSi33() {
        return si33;
    }

    public void setIt20(RichInputText it20) {
        this.it20 = it20;
    }

    public RichInputText getIt20() {
        return it20;
    }

    public void setIt21(RichInputText it21) {
        this.it21 = it21;
    }

    public RichInputText getIt21() {
        return it21;
    }

    public void setSoc27(RichSelectOneChoice soc27) {
        this.soc27 = soc27;
    }

    public RichSelectOneChoice getSoc27() {
        return soc27;
    }

    public void setSi34(UISelectItems si34) {
        this.si34 = si34;
    }

    public UISelectItems getSi34() {
        return si34;
    }

    public void setOt19(RichOutputText ot19) {
        this.ot19 = ot19;
    }

    public RichOutputText getOt19() {
        return ot19;
    }

    public void setSoc28(RichSelectOneChoice soc28) {
        this.soc28 = soc28;
    }

    public RichSelectOneChoice getSoc28() {
        return soc28;
    }

    public void setSi35(UISelectItems si35) {
        this.si35 = si35;
    }

    public UISelectItems getSi35() {
        return si35;
    }

    public void setId5(RichInputDate id5) {
        this.id5 = id5;
    }

    public RichInputDate getId5() {
        return id5;
    }

    public void setPlam3(RichPanelLabelAndMessage plam3) {
        this.plam3 = plam3;
    }

    public RichPanelLabelAndMessage getPlam3() {
        return plam3;
    }

    public void setPgl12(RichPanelGroupLayout pgl12) {
        this.pgl12 = pgl12;
    }

    public RichPanelGroupLayout getPgl12() {
        return pgl12;
    }

    public void setIns4(RichInputNumberSpinbox ins4) {
        this.ins4 = ins4;
    }

    public RichInputNumberSpinbox getIns4() {
        return ins4;
    }

    public void setIns5(RichInputNumberSpinbox ins5) {
        this.ins5 = ins5;
    }

    public RichInputNumberSpinbox getIns5() {
        return ins5;
    }

    public void setSor5(RichSelectOneRadio sor5) {
        this.sor5 = sor5;
    }

    public RichSelectOneRadio getSor5() {
        return sor5;
    }

    public void setSi36(RichSelectItem si36) {
        this.si36 = si36;
    }

    public RichSelectItem getSi36() {
        return si36;
    }

    public void setSi37(RichSelectItem si37) {
        this.si37 = si37;
    }

    public RichSelectItem getSi37() {
        return si37;
    }

    public void setIns6(RichInputNumberSpinbox ins6) {
        this.ins6 = ins6;
    }

    public RichInputNumberSpinbox getIns6() {
        return ins6;
    }

    public void setId6(RichInputDate id6) {
        this.id6 = id6;
    }

    public RichInputDate getId6() {
        return id6;
    }

    public void setS2(RichSeparator s2) {
        this.s2 = s2;
    }

    public RichSeparator getS2() {
        return s2;
    }


    public void setCustomerlnameId(RichInputComboboxListOfValues customerlnameId) {
        this.customerlnameId = customerlnameId;
    }

    public RichInputComboboxListOfValues getCustomerlnameId() {
        return customerlnameId;
    }

    public void setOt13(RichOutputText ot13) {
        this.ot13 = ot13;
    }

    public RichOutputText getOt13() {
        return ot13;
    }

    public void setIclov1(RichInputComboboxListOfValues iclov1) {
        this.iclov1 = iclov1;
    }

    public RichInputComboboxListOfValues getIclov1() {
        return iclov1;
    }

    public void setOt11(RichOutputText ot11) {
        this.ot11 = ot11;
    }

    public RichOutputText getOt11() {
        return ot11;
    }


    public void setIt16(RichInputText it16) {
        this.it16 = it16;
    }

    public RichInputText getIt16() {
        return it16;
    }


    public void setOt16(RichOutputText ot16) {
        this.ot16 = ot16;
    }

    public RichOutputText getOt16() {
        return ot16;
    }


    public void onDeleteActivity(DialogEvent dialogEvent) {
        System.out.println("OnDeleteActivity: " + dialogEvent.getOutcome().toString() );
        Outcome outcome = dialogEvent.getOutcome();

        if (outcome == outcome.ok) {
            System.out.println("Delete Activity");
            
            FacesContext fctx = FacesContext.getCurrentInstance();
            Application app = fctx.getApplication();
            ExpressionFactory expFact = app.getExpressionFactory();
            ELContext context = fctx.getELContext();
            ValueExpression valueExp =
                expFact.createValueExpression(context, "#{Calendar}",
                                              oracle.nasc.esg.view.bean.CalendarBean.class);

            oracle.nasc.esg.view.bean.CalendarBean calBean =
                (oracle.nasc.esg.view.bean.CalendarBean)valueExp.getValue(context);
            
            if (calBean != null ) {
                System.out.println("Activity Date: " + calBean.getTimeZoneString());
                
                calBean.fireDeleteActivity();
            } else {
                System.out.println("Calendar Bean is null");
            }
                
            
        } else {
            System.out.println("Cancel - Return to Calendar ");
        }
    }
}
