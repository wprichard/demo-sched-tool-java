package oracle.nasc.esg.view.bean;


import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.AttributeBinding;

import oracle.nasc.esg.view.backing.VmProvisioning;
public class SelectOneRadioBean {
    private boolean selectCustomerRadio = true;
    private boolean selectCustomerRadioCalendar = true;
    private boolean selectCustomerRadioCalendarEdit = true;
    private boolean selectBandera = true;

    public void cambiaRadioCustomer(ValueChangeEvent vce){
        selectCustomerRadio = !selectCustomerRadio;
        
    
       System.out.println("mensaje  "+ selectCustomerRadio);
    }


    public void cambiaRadioCustomerCalendar(ValueChangeEvent vce){
        selectCustomerRadioCalendar = !selectCustomerRadioCalendar;
        selectBandera = !selectBandera;
       System.out.println("mensaje  "+ selectCustomerRadioCalendar);
    }
    
    public void cambiaRadioCustomerCalendarEdit(ValueChangeEvent vce){
        selectCustomerRadioCalendarEdit = !selectCustomerRadioCalendarEdit;
        selectBandera = !selectBandera;
       System.out.println("mensaje  "+ selectCustomerRadioCalendarEdit);
    }
    
    public boolean isSelectCustomerRadio() {
        return selectCustomerRadio;
    }

    public void setSelectCustomerRadio(boolean selectCustomerRadio) {
        this.selectCustomerRadio = selectCustomerRadio;
    }

    public boolean isSelectCustomerRadioCalendar() {
        return selectCustomerRadioCalendar;
    }

    public void setSelectCustomerRadioCalendar(boolean selectCustomerRadioCalendar) {
        this.selectCustomerRadioCalendar = selectCustomerRadioCalendar;
    }

    public boolean isSelectCustomerRadioCalendarEdit() {
        return selectCustomerRadioCalendarEdit;
    }

    public void setSelectCustomerRadioCalendarEdit(boolean selectCustomerRadioCalendarEdit) {
        this.selectCustomerRadioCalendarEdit = selectCustomerRadioCalendarEdit;
    }

    public boolean isSelectBandera() {
        return selectBandera;
    }

    public void setSelectBandera(boolean selectBandera) {
        this.selectBandera = selectBandera;
    }
}
