package oracle.nasc.esg.view.util;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;
import java.util.ArrayList;

import java.util.Calendar;

import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.jbo.ApplicationModule;

import oracle.jdbc.pool.OracleDataSource;

import oracle.nasc.esg.view.backing.GenericFn;

import weblogic.jdbc.common.internal.RmiDataSource;

public class PLSQLUtils {
    /* To use this class, provide a properties file containing the connection 
     * information for the database containing the stored procedure. 
     * Call initConnection and check for a return status of True. Call callPLSQLProc
     * then call closeConnection.
     * SQL errors are trapped but not raised.
     */
    private Connection connection; // Database Connection Object

    public Boolean initConnection() {
   
        Boolean connectStatus = true;
        try {
            System.out.println("PLSQLUtils.initConnection: attempting DB connection for stored procedure...");
            InitialContext initContext = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            //OracleDataSource datasource = (OracleDataSource) envContext.lookup("jdbc/DemoSchedConnDS");
            RmiDataSource datasource =  (RmiDataSource) envContext.lookup("jdbc/DemoSchedConnDS");
            connection = datasource.getConnection();
            System.out.println("PLSQLUtils.initConnection: established DB connection for stored procedure");
            
        } catch (Exception ex) {
            System.out.println("PLSQLUtils.initConnection: Error getting connection to weblogic datasource: " + ex.toString());
            connectStatus = false;
        }
        return connectStatus;
    }

    /**
     * This method reads a properties file which is passed as
     * the parameter to it and load it into a java Properties
     * object and returns it.
     */
    public static Properties loadParams(String file) throws IOException {
      
      // Loads a ResourceBundle and creates Properties from it
      Properties prop = new Properties();
      ResourceBundle bundle = ResourceBundle.getBundle(file);
      Enumeration myEnum = bundle.getKeys();
      String key = null;
      while(myEnum.hasMoreElements()) {
        key = (String)myEnum.nextElement();
        prop.put(key, bundle.getObject(key));
      }
      return prop;
    }

    /**
     * This method invokes the PLSQL Stored Procedure 
     * SCHEDULE_OSC_DEMO using JDBC CallableStatement.
     */
    
    public String callCustomer(String customer) throws SQLException {
        
        String value = null;
          Statement  stmt = connection.createStatement();
          
          String Dato ="select customer_id from TBUTC_STG.tbu_customers where LOWER(customer_name)=LOWER('"+customer+ "')";
          System.out.println("Dato: " + Dato);
            ResultSet rset =
        
                        stmt.executeQuery("select customer_id from TBUTC_STG.tbu_customers where LOWER(customer_name)=LOWER('"+customer+ "')");
        
            while (rset.next()) {
                     value =rset.getString(1);
                }
            
            return value;
        }
    public void callPlsqlProc(String userEmail, String demoSystem, String demoScenario, 
                               Date startDate, Date endDate, String costCenter, 
                               String customer, Time startTime, Time endTime, String demoDesc) {

        System.out.println("PLSQLUtils.callPlsqlProc: entered...");
        CallableStatement stmt = null;
        TimeZone zone = TimeZone.getTimeZone("America/New_York");
        Calendar cal = Calendar.getInstance(zone);

        try {
            // Call PLSQL Stored Procedure
            // Prepare callable Statement to call PL/SQL Stored Procedure
            // Note that to invoke a PL/SQL Stored Function, you can still use a Callable
            // Statement which takes following form :
            // CallableStatement stmt =
            //             connection.prepareCall("begin ? = fn(?,?,..); end;");
            stmt = connection.prepareCall("begin SCHEDULE_OSC_DEMO(?,?,?,?,?,?,?,?,?,?); end;");
            /* Input parameters:
             * P_APP_USER - requestor's email, VARCHAR2
             * P_DEMO_SYSTEM - the requested demo system, VARCHAR2
             * P_DEMO_SCENARIO - the requested demo scenario, VARCHAR2
             * P_START_DATE - date demo begins, DATE
             * P_END_DATE - date demo ends, DATE
             * P_COST_CENTER - requestor's cost center, VARCHAR2
             * P_CUSTOMER - name of demo recipient, VARCHAR2
             * P_START_TIME - time reservation begins with time zone, VARCHAR2
             * P_END_TIME - time reservation ends with time zone, VARCHAR2
             * P_DESCRIPTION - user's description, VARCHAR2
             */ 
            // Bind the input parameters
            stmt.setString(1, userEmail);       // Bind 1st parameter
            stmt.setString(2, demoSystem);      // Bind 2nd parameter
            stmt.setString(3, demoScenario);    //bind 3rd parameter 
            stmt.setDate(4, startDate, cal);
            stmt.setDate(5, endDate, cal);
            stmt.setString(6, costCenter);
            stmt.setString(7, customer);
            stmt.setTime(8, startTime, cal);
            stmt.setTime(9, endTime, cal);
            stmt.setString(10, demoDesc);
            
            // Execute the callable statement
            stmt.execute();
            System.out.println("PLSQLUtils.callPlsqlProc: procedure executed");
        }
        //Check for exceptions
        catch(SQLException ex) { // Trap SQL Errors
             System.out.println("PLSQLUtils.callPlsqlProc: Error while Calling PL/SQL Procedure:" + ex.toString());
        } 
        finally {
            try {
                if(stmt != null) {
                    stmt.close(); // close the statement
                }
            } 
            catch(SQLException ex) {
                System.out.println("PLSQLUtils.callPlsqlProc: Error closing stored procedure statement: " + ex.toString());
            }
        }
    }
    
    /**
     * This method invokes the PLSQL Stored Procedure 
     * VM_PROVISIONING using JDBC CallableStatement.
     */
     /* OLD CODE: Is not used anymore
     * public void callVmProvisioning(String userId, String phone, String vmId, String vmLocation, 
                               Date startDate, Date endDate, String costCenter, String customer,
                                   String opportunityFocus, String description) {   */
    public void callVmProvisioning(int requestTypeId, String requestorEmail, String costCenter,
                                   String requestorOfficePhone, String requestorCellPhone, String hwPartner,
                                   String gcmCode, String requestDescription, int countryId,
                                   int roleId, int hardwareLocation, int oscLocation,
                                   String customerName, String customerId, String projectName,
                                   Date startDate, Date endDate, int oppFocusId,
                                   int lobId, String hwNeeds, Double shortRevenue,
                                   Double longRevenue, String salesRepEmail, String host,
                                   int appNumber){
        CallableStatement stmt = null;
        TimeZone zone = TimeZone.getTimeZone("America/New_York");
        Calendar cal = Calendar.getInstance(zone);

        try {
            // Call PLSQL Stored Procedure
            // Prepare callable Statement to call PL/SQL Stored Procedure
            // Note that to invoke a PL/SQL Stored Function, you can still use a Callable
            // Statement which takes following form :
            // CallableStatement stmt =
            //             connection.prepareCall("begin ? = fn(?,?,..); end;");
            
            /* OLD CODE: Is not used anymore.
            stmt = connection.prepareCall("begin ETC_CLOUD_RESOURCE(?,?,?,?,?,?,?,?,?,?); end;");
            */
            /* Input parameters:
             * P_APP_USER - requestor's email, VARCHAR2
             * P_USER_PHONE - requestor's phone, VARCHAR2
             * P_VM_ID - VM id, VARCHAR2
             * P_VM_LOCATION
             * P_START_DATE - date demo begins (US eastern time), DATE
             * P_END_DATE - date demo ends (US eastern time), DATE
             * P_COST_CENTER - requestor's cost center, VARCHAR2
             * P_CUSTOMER - name of demo recipient, VARCHAR2
             * P_DESCRIPTION
             */
            // Bind the input parameters(
            //   P_APP_USER IN VARCHAR2,
            //   P_USER_PHONE  IN VARCHAR2,
            //   P_VM_ID IN VARCHAR2,
            //   P_VM_LOCATION IN VARCHAR2(200),
            //   P_START_DATE IN date,
            //   P_END_DATE IN date,
            //   P_COST_CENTER IN VARCHAR2,
            //   P_CUSTOMER IN VARCHAR2,
            //   P_OPP_FOCUS IN VARCHAR2,
            //   P_DESCRIP IN VARCHAR2
            // )
            /* OLD CODE: Is not used anymore.
            stmt.setString(1, userId);       // Bind 1st parameter
            stmt.setString(2, phone);      // Bind 2nd parameter
            stmt.setString(3, vmId);    //bind 3rd parameter 
            stmt.setString(4, vmLocation);    //bind 3rd parameter 
            stmt.setDate(5, startDate, cal);
            stmt.setDate(6, endDate, cal);
            stmt.setString(7, costCenter);
            stmt.setString(8, customer);
            stmt.setString(9, opportunityFocus);
            stmt.setString(10, description);
            */
            
            stmt = connection.prepareCall("begin TBUTC_STG.osc_api.create_generic_osc_request(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
            stmt.setInt(1, requestTypeId); // Bind 1st parameter
            stmt.setString(2, requestorEmail); // Bind 2nd parameter
            stmt.setString(3, costCenter); // Bind 3rd parameter
            stmt.setString(4, requestorOfficePhone); // Bind 4th parameter
            stmt.setString(5, requestorCellPhone);
            stmt.setString(6, hwPartner);
            stmt.setString(7, gcmCode);
            stmt.setString(8, requestDescription);
            stmt.setInt(9, countryId);
            stmt.setInt(10, roleId);
            stmt.setInt(11, hardwareLocation);
            stmt.setInt(12, oscLocation);
            stmt.setString(13, customerName);
            stmt.setString(14, customerId);
            stmt.setString(15, projectName);
            stmt.setString(16, GenericFn.FormattedDate(startDate));
            stmt.setString(17, GenericFn.FormattedDate(endDate));
            stmt.setInt(18, oppFocusId);
            stmt.setInt(19, lobId);
            stmt.setString(20, hwNeeds);
            stmt.setDouble(21, shortRevenue);
            stmt.setDouble(22, longRevenue);
            stmt.setString(23, salesRepEmail);
            stmt.setString(24, host);
            stmt.setInt(25, appNumber);

            // Execute the callable statement
            stmt.execute();
        }
        //Check for exceptions
        catch(SQLException ex) { // Trap SQL Errors
             throw new RuntimeException("PLSQLUtils.callVmProvisioning: Error while Calling PL/SQL Procedure:" + ex.toString());
        } finally {
            try {
                if(stmt != null) {
                    stmt.close(); // close the statement
                }
            } catch(SQLException ex) {
                System.out.println("PLSQLUtils.callVmProvisioning: Error closing stored procedure statement: " + ex.toString());
            }
        }
    }
    
    /**
     * Method to close the database connection
     */
    public void closeConnection() {
        try {
            if(connection != null) {
              connection.close(); // Close the connection
            }
        } 
        catch(SQLException ex) { // Trap SQL Errors
            System.out.println("PLSQLUtils.closeConnection: Error While Closing Connection .." + ex.toString());
        }
    }

}

