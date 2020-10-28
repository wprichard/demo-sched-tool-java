package oracle.nasc.esg.view.bean;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
    import javax.faces.event.ValueChangeEvent;

    import oracle.adf.model.BindingContext;

    import oracle.binding.BindingContainer;
    import oracle.binding.AttributeBinding;


public class ComboBean {
    private String CustomerName;

    private BindingContainer bindings(){return BindingContext.getCurrent().getCurrentBindingsEntry();}
       
        public void cambiaCustomer(ValueChangeEvent vce){
            
            
           /* FacesContext fctx = FacesContext.getCurrentInstance();
            Application app = fctx.getApplication();
            ExpressionFactory expFact = app.getExpressionFactory();
            ELContext context = fctx.getELContext();
            ValueExpression valueExp =
                expFact.createValueExpression(context, "#{backing_VmProvisioning}",
                                              oracle.nasc.esg.view.backing.VmProvisioning.class);
            //El segundo parametro es el acceso al bean que se quiere acceder
            //El tercer parametro es la clase del bean y se le agrega .class
            oracle.nasc.esg.view.backing.VmProvisioning elfctx =
                (oracle.nasc.esg.view.backing.VmProvisioning)valueExp.getValue(context);
            CustomerName = elfctx.getCustomer();*/
            
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AttributeBinding att = (AttributeBinding)bindings().getControlBinding("Customerlname1");
            if(bindings().get("Customerlname")!=null){
                             att.setInputValue(bindings().get("Customerlname"));
            }else{
               
                att.setInputValue("");
            }
        }


    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }
}
