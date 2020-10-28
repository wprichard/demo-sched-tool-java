package oracle.nasc.esg.view.backing;

import java.sql.Date;

import java.text.SimpleDateFormat;

public class GenericFn {
    public static String FormattedDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}
