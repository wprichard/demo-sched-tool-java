package oracle.nasc.esg.view.backing;

import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.AttributeBinding;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;

import oracle.nasc.esg.view.util.JSFUtils;
import oracle.nasc.esg.view.util.PLSQLUtils;

import org.apache.myfaces.trinidad.component.UIXGroup;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.output.RichSeparator;

import oracle.binding.OperationBinding;

public class VmProvisioning {
    private RichForm f1;
    private RichDocument d1;
    private RichPanelGroupLayout pgl1;
    private RichPanelHeader ph1;
    private RichPanelFormLayout pfl2;
    private RichInputText userIdInputText;
    private RichInputText phoneInputText;
    private RichInputText vmLocationInputText;
    private RichPanelHeader ph2;
    private RichPanelFormLayout pfl1;
    private RichInputText vmIdInputText;
    private RichInputText costCenterInputText;
    private RichInputText descriptionInputText;
    private RichInputDate startDateInputDate;
    private RichInputDate endDateInputDate;
    private RichPanelGroupLayout pgl2;
    private RichCommandButton saveButton;
    private RichCommandButton cancel;
    private RichSelectOneChoice opportunityFocusSelect;
    private UISelectItems si1;
    private RichSpacer spacer1;
    private String userId;
    private String custom;
    private String vmId;
    private String vmLocation;
    private String phoneNumber;
    private String costCenter;
    private String customer;
    private String opportunityFocus;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean incompleteRequest;
    private HtmlInputHidden ih1;
    private HtmlInputHidden ih2;
    private RichOutputText ot2;
    private UIXGroup g1;
    private RichPanelGroupLayout pgl3;
    private RichImage i1;
    private RichSpacer s2;
    private RichSpacer s3;
    private RichOutputLabel ol1;
    private String cellPhone;
    private String officePhone;
    private RichInputText it1;
    private UIXGroup g2;
    private RichInputText it2;
    private RichSelectOneChoice soc1;

    private UISelectItems si2;
    private int roleId;
    private RichOutputText ot3;
    private RichSelectOneChoice soc2;
    private UISelectItems si3;
    private RichOutputText ot4;
    private int lobId;
    private RichSelectOneChoice soc4;
    private UISelectItems si5;
    private RichOutputText ot6;
    private RichSelectOneChoice soc5;
    private UISelectItems si6;
    private int countryId;
    private RichOutputText ot7;
    private RichSelectOneChoice soc3;
    private UISelectItems si4;
    private RichOutputText ot5;
    private int oppFocusId;
    //private RichInputComboboxListOfValues customerNameId;
    private RichOutputText ot8;
    private RichPanelGroupLayout pgl4;
    private RichSelectOneRadio sor1;
    private RichSelectItem si7;
    private RichSelectItem si8;
    private RichPanelGroupLayout pgl5;
    private String requestorEmail;
    private String requestDescription;
    private RichInputText it3;
    private String content;
    private RichInputText it4;
    private String row;
    private RichInputText customerNameId;

    private RichPanelSplitter ps1;
    private RichPanelFormLayout pfl3;

    private RichSpacer s1;
    //binding object used for the create operation


    private BindingContainer bindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public VmProvisioning() {


        HttpServletRequest request =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        userId = request.getParameter("userId");
        vmId = request.getParameter("vmId");
        vmLocation = request.getParameter("vmLocation");
        System.out.println("VmProvisioning constructor: new provisioning request for " +
                           userId);
        if (userId == null || vmId == null || userId.isEmpty() ||
            vmId.isEmpty() || vmLocation == null || vmLocation.isEmpty()) {
            incompleteRequest = true;
            /* System.out.println("Entro customername pre" + customerNameId;
            if (customerNameId.getValue().equals(null))

            System.out.println("Entro customername");
            {customerNameId.setValue("AMEX");

                }*/
            System.out.println("VmProvisioning constructor: incomplete request ");
        } else {
            vmLocation = vmLocation.replace("%26", "&");
            if (vmLocation.length() > 200) {
                vmLocation = vmLocation.substring(0, 200);
            }
        }
    }

    public String save() {
        System.out.println("Nuevo OSCrequest");

        String pCustomer;
        String pCustomerId;
        try {
            incompleteRequest = false;
            if (endDate.after(startDate)) {
                PLSQLUtils plsql = new PLSQLUtils();
                plsql.initConnection();
                /* OLD CODE: Is not used anymore.
                 * plsql.callVmProvisioning(userId, phoneNumber, vmId, vmLocation, startDate, endDate, costCenter, customer, opportunityFocus, description);
                 */

                //Get Role

                if (bindings().get("RoleId1") != null) {
                    System.out.println("Valor de RoleId: " + bindings().get("RoleId1"));
                    String aux = String.valueOf(bindings().get("RoleId1"));
                    roleId = Integer.parseInt(aux);
                } else {
                    roleId = 0;
                }

                //Get Lob

                if (bindings().get("LobId1") != null) {
                    System.out.println("Valor de LobId1: " +
                                       bindings().get("LobId1"));
                    String aux = String.valueOf(bindings().get("LobId1"));
                    lobId = Integer.parseInt(aux);
                } else {
                    lobId = 0;
                }

                //Get Country

                if (bindings().get("CountryId1") != null) {
                    System.out.println("Valor de CountryId1: " +
                                       bindings().get("CountryId1"));
                    String aux = String.valueOf(bindings().get("CountryId1"));
                    countryId = Integer.parseInt(aux);
                } else {
                    countryId = 0;
                }

                //Get Focus

                if (bindings().get("Id1") != null) {
                    System.out.println("Valor de oppFocusId: " +
                                       bindings().get("Id1"));
                    String aux = String.valueOf(bindings().get("Id1"));
                    oppFocusId = Integer.parseInt(aux);
                } else {
                    oppFocusId = 0;
                }


                //Get Customer

                System.out.println("Valor de Customer: " + customer);
                pCustomer = String.valueOf(it3.getValue());
                System.out.println("pCustomer:" + pCustomer);

                //Get CustomerId

                /*if(bindings().get("CustomerId")!=null){
                                System.out.println("Valor de CustomerId: "+bindings().get("CustomerId"));
                                pCustomerId = String.valueOf(bindings().get("CustomerId"));

                        }else{ pCustomerId = ""; }
                    */

                if (customerNameId.getValue() == null)
                    pCustomerId = "";
                else
                    pCustomerId =
                            plsql.callCustomer(customerNameId.getValue().toString());
                System.out.println("Valor de CustomerId: " + pCustomerId);
                //Get Costo

                costCenter = String.valueOf(costCenterInputText.getValue());
                System.out.println("costCenter:" + costCenter);
                //Get requestorEmail
                requestorEmail = String.valueOf(userIdInputText.getValue());
                System.out.println("requestorEmail:" + requestorEmail);

                //Get description
                requestDescription =
                        String.valueOf(descriptionInputText.getValue());
                System.out.println("descriptionInputText:" +
                                   requestDescription);


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
                if (elfctx.isSelectCustomerRadio() == true) {
                   /* System.out.println(59 + "," + requestorEmail + "," +
                                       costCenter + "," + officePhone + "," +
                                       cellPhone + "," + "hw Partner 1" + "," +
                                       "null" + "," + requestDescription +
                                       "," + countryId + "," + roleId + "," +
                                       "," + 47 + "," + 47 + "," + "null" +
                                       "," + pCustomerId + "," +
                                       "Project Name" + "," + startDate + "," +
                                       endDate + "," + oppFocusId + "," +
                                       lobId + "," + "hw needs" + "," + 2.5 +
                                       "," + 33.5 + "," + "null" + "," +
                                       "osc.oraclecorp.com" + "," + 108);*/
                   // System.out.println(" Entro true");
                    plsql.callVmProvisioning(7, requestorEmail, costCenter,
                                             officePhone, cellPhone, "Other",
                                             null, requestDescription,
                                             countryId, roleId, 47, 47, null,
                                             pCustomerId, "N/A", startDate,
                                             endDate, oppFocusId, lobId, null,
                                             0.0, 0.0, null,
                                             "osc.oraclecorp.com", 108);
                    plsql.closeConnection();

                } else {

                   
                    plsql.callVmProvisioning(7, requestorEmail, costCenter,
                                             officePhone, cellPhone, "Other",
                                             null, requestDescription,
                                             countryId, roleId, 47, 47,
                                             pCustomer, null, "N/A", startDate,
                                             endDate, oppFocusId, lobId, null,
                                             0.0, 0.0, null,
                                             "osc.oraclecorp.com", 108);
                    plsql.closeConnection();
                }

                System.out.println("VmProvisioning.save: new provisioning request submitted to OSC ");
                return "success";
            } else {
                //JSFUtils.addFacesInformationMessage("End date must be after start date");
                JSFUtils.addFacesErrorMessage("Validation Error - End Date must be after Start Date");

            }
        } catch (Exception ex) {
            JSFUtils.addFacesInformationMessage("An error has occurred while creating the OSC request: " +
                                                ex.getMessage());
            JSFUtils.addFacesInformationMessage("params: By Hardcode - test 9");
            JSFUtils.addFacesInformationMessage("costCenterGAF: " +
                                                costCenter);
            JSFUtils.addFacesInformationMessage("startDateGAF: " +
                                                GenericFn.FormattedDate(startDate) +
                                                " - endDate: " +
                                                GenericFn.FormattedDate(endDate));
            /* OLD CODE: Is not used anymore.
             * JSFUtils.addFacesInformationMessage("params: " + vmLocation + "," + phoneNumber + "," + vmId + "," + startDate + "," + endDate + "," + costCenter + "," + customer);
             */
        }
        return null;
    }




    public void setF1(RichForm f1) {
        this.f1 = f1;
    }

    public RichForm getF1() {
        return f1;
    }

    public void setD1(RichDocument d1) {
        this.d1 = d1;
    }

    public RichDocument getD1() {
        return d1;
    }

    public void setPgl1(RichPanelGroupLayout pgl1) {
        this.pgl1 = pgl1;
    }

    public RichPanelGroupLayout getPgl1() {
        return pgl1;
    }

    public void setPh1(RichPanelHeader ph1) {
        this.ph1 = ph1;
    }

    public RichPanelHeader getPh1() {
        return ph1;
    }

    public void setPfl2(RichPanelFormLayout pfl2) {
        this.pfl2 = pfl2;
    }

    public RichPanelFormLayout getPfl2() {
        return pfl2;
    }

    public void setUserIdInputText(RichInputText it3) {
        this.userIdInputText = it3;
    }

    public RichInputText getUserIdInputText() {
        return userIdInputText;
    }

    public void setPhoneInputText(RichInputText it4) {
        this.phoneInputText = it4;
    }

    public RichInputText getPhoneInputText() {
        return phoneInputText;
    }

    public void setVmLocationInputText(RichInputText it5) {
        this.vmLocationInputText = it5;
    }

    public RichInputText getVmLocationInputText() {
        return vmLocationInputText;
    }

    public void setPh2(RichPanelHeader ph2) {
        this.ph2 = ph2;
    }

    public RichPanelHeader getPh2() {
        return ph2;
    }

    public void setPfl1(RichPanelFormLayout pfl1) {
        this.pfl1 = pfl1;
    }

    public RichPanelFormLayout getPfl1() {
        return pfl1;
    }

    public void setVmIdInputText(RichInputText vmId) {
        this.vmIdInputText = vmId;
    }

    public RichInputText getVmIdInputText() {
        return vmIdInputText;
    }

    public void setCostCenterInputText(RichInputText costCenter) {
        this.costCenterInputText = costCenter;
    }

    public RichInputText getCostCenterInputText() {
        return costCenterInputText;
    }


    public void setDescriptionInputText(RichInputText description) {
        this.descriptionInputText = description;
    }

    public RichInputText getDescriptionInputText() {
        return descriptionInputText;
    }

    public void setStartDateInputDate(RichInputDate startDate) {
        this.startDateInputDate = startDate;
    }

    public RichInputDate getStartDateInputDate() {
        return startDateInputDate;
    }

    public void setEndDateInputDate(RichInputDate endDate) {
        this.endDateInputDate = endDate;
    }

    public RichInputDate getEndDateInputDate() {
        return endDateInputDate;
    }


    public void setPgl2(RichPanelGroupLayout pgl2) {
        this.pgl2 = pgl2;
    }

    public RichPanelGroupLayout getPgl2() {
        return pgl2;
    }

    public void setSaveButton(RichCommandButton saveButton) {
        this.saveButton = saveButton;
    }

    public RichCommandButton getSaveButton() {
        return saveButton;
    }

    public void setCancel(RichCommandButton cancel) {
        this.cancel = cancel;
    }

    public RichCommandButton getCancel() {
        return cancel;
    }

    public void setOpportunityFocusSelect(RichSelectOneChoice soc1) {
        this.opportunityFocusSelect = soc1;
    }

    public RichSelectOneChoice getOpportunityFocusSelect() {
        return opportunityFocusSelect;
    }

    public void setSi1(UISelectItems si1) {
        this.si1 = si1;
    }

    public UISelectItems getSi1() {
        return si1;
    }

    public void setSpacer1(RichSpacer spacer1) {
        this.spacer1 = spacer1;
    }

    public RichSpacer getSpacer1() {
        return spacer1;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setOpportunityFocus(String opportunityFocus) {
        this.opportunityFocus = opportunityFocus;
    }

    public String getOpportunityFocus() {
        return opportunityFocus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setIncompleteRequest(boolean incompleteRequest) {
        this.incompleteRequest = incompleteRequest;
    }

    public boolean isIncompleteRequest() {
        return incompleteRequest;
    }

    public void setIh1(HtmlInputHidden ih1) {
        this.ih1 = ih1;
    }

    public HtmlInputHidden getIh1() {
        return ih1;
    }

    public void setIh2(HtmlInputHidden ih2) {
        this.ih2 = ih2;
    }

    public HtmlInputHidden getIh2() {
        return ih2;
    }


    public void setOt2(RichOutputText ot2) {
        this.ot2 = ot2;
    }

    public RichOutputText getOt2() {
        return ot2;
    }

    public void setG1(UIXGroup g1) {
        this.g1 = g1;
    }

    public UIXGroup getG1() {
        return g1;
    }


    public void setPgl3(RichPanelGroupLayout pgl3) {
        this.pgl3 = pgl3;
    }

    public RichPanelGroupLayout getPgl3() {
        return pgl3;
    }


    public void setI1(RichImage i1) {
        this.i1 = i1;
    }

    public RichImage getI1() {
        return i1;
    }


    public void setS2(RichSpacer s2) {
        this.s2 = s2;
    }

    public RichSpacer getS2() {
        return s2;
    }

    public void setS3(RichSpacer s3) {
        this.s3 = s3;
    }

    public RichSpacer getS3() {
        return s3;
    }

    public void setVmLocation(String vmLocation) {
        this.vmLocation = vmLocation;
    }

    public String getVmLocation() {
        return vmLocation;
    }

    public void setOl1(RichOutputLabel ol1) {
        this.ol1 = ol1;
    }

    public RichOutputLabel getOl1() {
        return ol1;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void setG2(UIXGroup g2) {
        this.g2 = g2;
    }

    public UIXGroup getG2() {
        return g2;
    }

    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void setSoc1(RichSelectOneChoice soc1) {
        this.soc1 = soc1;
    }

    public RichSelectOneChoice getSoc1() {
        return soc1;
    }

    public void setSi2(UISelectItems si2) {
        this.si2 = si2;
    }

    public UISelectItems getSi2() {
        return si2;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    public void setOt3(RichOutputText ot3) {
        this.ot3 = ot3;
    }

    public RichOutputText getOt3() {
        return ot3;
    }

    public void setSoc2(RichSelectOneChoice soc2) {
        this.soc2 = soc2;
    }

    public RichSelectOneChoice getSoc2() {
        return soc2;
    }

    public void setSi3(UISelectItems si3) {
        this.si3 = si3;
    }

    public UISelectItems getSi3() {
        return si3;
    }

    public void setOt4(RichOutputText ot4) {
        this.ot4 = ot4;
    }

    public RichOutputText getOt4() {
        return ot4;
    }

    public int getLobId() {
        return lobId;
    }

    public void setLobId(int lobId) {
        this.lobId = lobId;
    }


    public void setSoc4(RichSelectOneChoice soc4) {
        this.soc4 = soc4;
    }

    public RichSelectOneChoice getSoc4() {
        return soc4;
    }

    public void setSi5(UISelectItems si5) {
        this.si5 = si5;
    }

    public UISelectItems getSi5() {
        return si5;
    }

    public void setOt6(RichOutputText ot6) {
        this.ot6 = ot6;
    }

    public RichOutputText getOt6() {
        return ot6;
    }

    public void setSoc5(RichSelectOneChoice soc5) {
        this.soc5 = soc5;
    }

    public RichSelectOneChoice getSoc5() {
        return soc5;
    }

    public void setSi6(UISelectItems si6) {
        this.si6 = si6;
    }

    public UISelectItems getSi6() {
        return si6;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setOt7(RichOutputText ot7) {
        this.ot7 = ot7;
    }

    public RichOutputText getOt7() {
        return ot7;
    }


    public void setSoc3(RichSelectOneChoice soc3) {
        this.soc3 = soc3;
    }

    public RichSelectOneChoice getSoc3() {
        return soc3;
    }

    public void setSi4(UISelectItems si4) {
        this.si4 = si4;
    }

    public UISelectItems getSi4() {
        return si4;
    }

    public void setOt5(RichOutputText ot5) {
        this.ot5 = ot5;
    }

    public RichOutputText getOt5() {
        return ot5;
    }

    public int getOppFocusId() {
        return oppFocusId;
    }

    public void setOppFocusId(int oppFocusId) {
        this.oppFocusId = oppFocusId;
    }


    public List ada(String string) {
        // Add event code here...
        return null;
    }


    public List da(String string) {
        // Add event code here...
        return null;
    }


    public List sd(String string) {
        // Add event code here...
        return null;
    }


    public List ds(String string) {
        // Add event code here...
        return null;
    }


    public List ad(String string) {
        // Add event code here...
        return null;
    }

    public void setCustomerNameId(RichInputText customerNameId) {
        this.customerNameId = customerNameId;
    }

    public RichInputText getCustomerNameId() {
        return customerNameId;
    }

    public void setOt8(RichOutputText ot8) {
        this.ot8 = ot8;
    }

    public RichOutputText getOt8() {
        return ot8;
    }

    public void setPgl4(RichPanelGroupLayout pgl4) {
        this.pgl4 = pgl4;
    }

    public RichPanelGroupLayout getPgl4() {
        return pgl4;
    }

    public void setSor1(RichSelectOneRadio sor1) {
        this.sor1 = sor1;
    }

    public RichSelectOneRadio getSor1() {
        return sor1;
    }

    public void setSi7(RichSelectItem si7) {
        this.si7 = si7;
    }

    public RichSelectItem getSi7() {
        return si7;
    }

    public void setSi8(RichSelectItem si8) {
        this.si8 = si8;
    }

    public RichSelectItem getSi8() {
        return si8;
    }

    public void setPgl5(RichPanelGroupLayout pgl5) {
        this.pgl5 = pgl5;
    }

    public RichPanelGroupLayout getPgl5() {
        return pgl5;
    }


    public String getRequestorEmail() {
        return requestorEmail;
    }

    public void setRequestorEmail(String requestorEmail) {
        this.requestorEmail = requestorEmail;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public void setIt3(RichInputText it3) {
        this.it3 = it3;
    }

    public RichInputText getIt3() {
        return it3;
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


    public void setIt4(RichInputText it4) {
        this.it4 = it4;
    }

    public RichInputText getIt4() {
        return it4;
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

    /* public void setIlov1(RichInputListOfValues ilov1) {
        this.ilov1 = ilov1;
    }

    public RichInputListOfValues getIlov1() {
        return ilov1;
    }*/


    public void setPs1(RichPanelSplitter ps1) {
        this.ps1 = ps1;
    }

    public RichPanelSplitter getPs1() {
        return ps1;
    }


    public void setPfl3(RichPanelFormLayout pfl3) {
        this.pfl3 = pfl3;
    }

    public RichPanelFormLayout getPfl3() {
        return pfl3;
    }


    public void setS1(RichSpacer s1) {
        this.s1 = s1;
    }

    public RichSpacer getS1() {
        return s1;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getCustom() {
        return custom;
    }

    public void newCustomer(ValueChangeEvent valueChangeEvent) {
        
        if(customerNameId.getValue()!=null)
        it3.setValue(customerNameId.getValue());
        
    }
}
