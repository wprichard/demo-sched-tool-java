package oracle.nasc.esg.view.backing;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.nasc.esg.view.util.JSFUtils;

public class VMProvisioningTest {
    private String userId;
    private String vmId;
    private String vmLocation;
    private String params;

    public VMProvisioningTest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        userId = request.getParameter("userId");
        vmId = request.getParameter("vmId");
        vmLocation = request.getParameter("vmLocation");
    }
    
    public String setup() {
        JSFUtils.addFacesInformationMessage("Params: " + getParams());
        return null;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getParams() {
        params = "userId=" + userId + "&vmId=" + vmId + "&vmLocation=" + (vmLocation != null ? vmLocation.replace("&", "%26") : "");
        return params;
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

    public void setVmLocation(String vmLocation) {
        this.vmLocation = vmLocation;
    }

    public String getVmLocation() {
        return vmLocation;
    }
}
