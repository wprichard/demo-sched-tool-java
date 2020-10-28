package oracle.nasc.esg.view.bean;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.jbo.Row;

import oracle.nasc.esg.view.bean.CalendarBean;


public class DeleteBean {
    public DeleteBean() {
    }

    public void dialogListener(DialogEvent dialogEvent) {
        /* This event fires when the user clicks on of the dialog buttons.
         * Return a boolean from the dialog that indicates if the delete
         * was confirmed.
         */
        
        Boolean deleteConfirmed;
        System.out.println("The dialog outcome is:"+ dialogEvent.getOutcome());
        
        //if the selected the OK button then...
        if (dialogEvent.getOutcome().equals(Outcome.ok)) {
            //indicate the delete was confirmed
            deleteConfirmed = true;
        }
        else {
            //indicate the delete was not confirmed
            deleteConfirmed = false;
        }
        
        //return the outcome to the invoker of the dialog
        AdfFacesContext afContext = AdfFacesContext.getCurrentInstance();
        afContext.getCurrentInstance().returnFromDialog(deleteConfirmed, null);

    }
}
