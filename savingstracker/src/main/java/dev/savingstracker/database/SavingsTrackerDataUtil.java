package dev.savingstracker.database;


import dev.savingstracker.SavingsTrackerMainApplication;
import dev.savingstracker.database.setup.StatementAPI;
import dev.savingstracker.model.Tracker;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
public class SavingsTrackerDataUtil {

    private AtomicReference<Tracker> tracker = new AtomicReference<>();

    private final StatementAPI statementAPI = SavingsTrackerMainApplication.getStatementAPI();

    public void transferDatabaseInfo(List<Tracker> trackerObjects) {
        checkIfEmpty();
        statementAPI.executeQuery("SELECT * FROM SAVINGS", resultSet -> {
            while (resultSet.next()) {
                tracker.set(new Tracker(resultSet.getString("SESSIONID"), resultSet.getString("NAME"), resultSet.getString("DATE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("AMOUNT"), resultSet.getInt("SAVINGSAMOUNT")));
                if (!trackerObjects.contains(tracker.get())) {
                    trackerObjects.add(tracker.get());
                }
            }
            return resultSet;
        });
    }

    public void checkIfEmpty() {
        statementAPI.executeQuery("SELECT * FROM SAVINGS", resultSet -> {
            if (!resultSet.next()) {
                System.out.println("Table was currently empty!");
            }
            return resultSet;
        });
    }

    public void addTrackerObject(Tracker trackerObject) {
        if (!isValidDate(trackerObject.getDate())) {
            throw new IllegalArgumentException("Invalid date format!");
        }
        String insert = "INSERT INTO SAVINGS (SESSIONID, NAME, DATE, DESCRIPTION, AMOUNT, SAVINGSAMOUNT) " + "VALUES (?,?,?,?,?,?)";
        statementAPI.executeUpdate(insert, ps -> {
            ps.setString(1, trackerObject.getSessionID());
            ps.setString(2, trackerObject.getName());
            ps.setString(3, trackerObject.getDate());
            ps.setString(4, trackerObject.getDescription());
            ps.setInt(5, trackerObject.getAmount());
            ps.setInt(6, trackerObject.getSavingsAmount());
        });
    }

    public void removeTrackerObject(String sessionID) {
        String delete = "DELETE FROM SAVINGS WHERE SESSIONID = ?";
        statementAPI.executeUpdate(delete, ps -> {
            ps.setString(1, sessionID);
        });
    }


    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
