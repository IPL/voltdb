import org.voltdb.*;
import java.util.Date;
import java.text.*;

public class LoadAlert extends VoltProcedure {

  public final SQLStmt InsertNwsAlert = new SQLStmt(
      " INSERT INTO nws_event VALUES (?,?,?,?,?,?,?);");
  public final SQLStmt InsertLocalAlert = new SQLStmt(
      " INSERT INTO local_event VALUES (?,?,?);");

  public VoltTable[] run(String alert_id,
                         String alert_type,
                         String severity,
                         String description,
                         String starttime,
                         String endtime,
                         String updated,
                         String locations)
      throws VoltAbortException {

            // Insert a description of the alert
          try {
                voltQueueSQL( InsertNwsAlert, 
                        alert_id,
                        alert_type,
                        severity,
                        description,
                        toTimestamp(starttime),
                        toTimestamp(endtime),
                        toTimestamp(updated) );
          } catch (ParseException e) {
               throw new VoltAbortException();
          }
 
            // Go through the location codes and create
            // local alert entries. The location codes
            // consist of state/county code pairs 
            // separated by spaces.
          String locales[] = locations.split(" ");
          for (String locale : locales) {
            int statecode = Integer.parseInt(locale.substring(0,3));
            int countycode = Integer.parseInt(locale.substring(3,6));
            voltQueueSQL( InsertLocalAlert, 
                        statecode,
                        countycode,
                        alert_id);
           } 

          return voltExecuteSQL();

 
      }
  private long toTimestamp(String utc) throws ParseException {

          final SimpleDateFormat date_formatter = 
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
          Date asDate = date_formatter.parse(utc.substring(0,22)+"00");
          return asDate.getTime() * 1000;
          
  }
}
