import org.voltdb.*;

public class UpdatePeople extends VoltProcedure {

  public final SQLStmt findCurrent = new SQLStmt(
      " SELECT * FROM people WHERE state_num=? AND county_num=?;");
  public final SQLStmt updateExisting = new SQLStmt(
      " UPDATE people SET population=?"
    + " WHERE state_num=? AND county_num=?;");
  public final SQLStmt addNew = new SQLStmt(
      " INSERT INTO people VALUES (?,?,?,?);");

  public VoltTable[] run(byte state_num,
                         short county_num,
                         String county,
                         long population)
      throws VoltAbortException {

          voltQueueSQL( findCurrent, state_num, county_num );
          VoltTable[] results = voltExecuteSQL();

          if (results[0].getRowCount() > 0) { // found a record
             voltQueueSQL( updateExisting, population, 
                                           state_num, 
                                           county_num );

          } else {  // no existing record
             voltQueueSQL( addNew, state_num, 
                                   county_num,
                                   county,
                                   population);

          }
          return voltExecuteSQL();

      }
}
