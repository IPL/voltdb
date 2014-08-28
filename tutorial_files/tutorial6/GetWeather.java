import org.voltdb.*;
import org.voltdb.client.*;
import java.util.Random;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class GetWeather {

    static long txns = 0;
    static long totaltxns = 0;

    public static void main(String[] args) throws Exception {

        // Disable Log4J log messages.
        Logger.getRootLogger().setLevel(Level.OFF);
        System.out.println("Emulating read queries of weather alerts by location.");

        /*
         * Instantiate a client and connect to the database.
         */
        org.voltdb.client.Client client;
        client = ClientFactory.createClient();
        client.createConnection("localhost");
         
         /*
         * Get a list of possible counties per state.
         * This information is used to randomize calls to the
         * database emulating users looking up alerts for
         * their state and county.
         */
        int[] states;
        states = new int[70];
        int maxstate = 0;

        ClientResponse response = client.callProcedure("@AdHoc",
             "Select state_num, max(county_num) from people " +
             "group by state_num order by state_num;");
        if (response.getStatus() != ClientResponse.SUCCESS){
            System.err.println(response.getStatusString());
            System.exit(-1);
        }

        VoltTable results = response.getResults()[0];
        for (int i = 0; i < results.getRowCount(); i++ ) {
           VoltTableRow row = results.fetchRow(i);
           states[(int) row.getLong(0)] = (int) row.getLong(1);
           maxstate = (int) row.getLong(0);
         }


         /*
         * Use random selection to pick counties to look at
         * for alerts, emulating large quantities of users 
         * fetching data.
         */

        long timelimit = 5 * 60 * 1000;  // 5 minutes
        long reportdelta = 30 * 1000;    // every 1/2 minute
        long starttime = System.currentTimeMillis();
        long currenttime = starttime;
        long lastreport = currenttime;

        while ( currenttime - starttime < timelimit) {

              // Pick a state and county
            int s = 1 + (int)(Math.random() * maxstate);
            int c = 1 + (int)(Math.random() * states[s]); 

              // Get the alerts
            client.callProcedure(new AlertCallback(),
                                 "GetAlertsByLocation",
                                 s, c, new Date().getTime());
          
            currenttime = System.currentTimeMillis();
             if (currenttime > lastreport + reportdelta) {
                DisplayInfo(currenttime-lastreport);
                lastreport = currenttime;
             }
        } 

            // one final report
        if (txns > 0 && currenttime > lastreport) 
           DisplayInfo(currenttime - lastreport);

        client.close();
    }

    public static void DisplayInfo(long delta) {
        /*
        * Show some basic transaction rate info.
        */
        System.out.printf ("\n%d Transactions in %d seconds (%d TPS)\n",
                txns, delta/1000, txns *1000 /  delta );
        totaltxns += txns;
        txns = 0;
    }

   
    static class AlertCallback implements ProcedureCallback {
        @Override
        public void clientCallback(ClientResponse response) throws Exception {
            if (response.getStatus() == ClientResponse.SUCCESS) {
                VoltTable tuples = response.getResults()[0];
                  // Could do something with the results.
                  // For now we throw them away since we are
                  // demonstrating load on the database
                tuples.resetRowPosition();
                while (tuples.advanceRow()) {
                   String id = tuples.getString(0);
                   String summary = tuples.getString(1);    
                   String type = tuples.getString(2);    
                   String severity = tuples.getString(3);    
                   long starttime = tuples.getTimestampAsLong(4);    
                   long endtime = tuples.getTimestampAsLong(5);
                }
             }
            txns++;
            if ( (txns % 50000) == 0) System.out.print(".");
        }
    }


}
