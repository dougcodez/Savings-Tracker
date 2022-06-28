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
                tracker.set(new Tracker(resultSet.getString("NAME"), resultSet.getString("DATE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("AMOUNT")));
                if (!trackerObjects.contains(tracker.get())) {
                    trackerObjects.add(tracker.get());
                    System.out.println("Tetsing");
                }
            }
            return resultSet;
        });
    }

    public void checkIfEmpty() {
        statementAPI.executeQuery("SELECT * FROM SAVINGS", resultSet -> {
            if (!resultSet.next()) {
                System.out.println("Table was currently empty! Inserting default data...");
            }
            return resultSet;
        });
    }

    public void addTrackerObject(Tracker trackerObject) {
        String insert = "INSERT INTO SAVINGS (NAME, DATE, DESCRIPTION, AMOUNT) " + "VALUES (?,?,?,?)";
        updateQuery(insert, trackerObject);
    }

    public void removeTrackerObject(Tracker trackerObject) {
        String delete = "DELETE FROM SAVINGS WHERE NAME = " + trackerObject.getName() + " AND DATE = " + trackerObject.getDate() + " AND DESCRIPTION = " + trackerObject.getDescription() + " AND AMOUNT = " + trackerObject.getAmount();
        if (check(trackerObject.getName())) {
            updateQuery(delete, trackerObject);
        } else {
            System.out.println("No such entry in database!");
        }
    }

    private void updateQuery(String queryType, Tracker trackerObject) {
        if(!isValidDate(trackerObject.getDate())) {
            throw new IllegalArgumentException("Invalid date format!");
        }
        statementAPI.executeUpdate(queryType, ps -> {
            ps.setString(1, trackerObject.getName());
            ps.setString(2, trackerObject.getDate());
            ps.setString(3, trackerObject.getDescription());
            ps.setInt(4, trackerObject.getAmount());
        });
    }

    private boolean check(String object) {
        AtomicBoolean fetchedValue = new AtomicBoolean();
        statementAPI.executeQuery("SELECT * FROM SAVINGS WHERE NAME=?", ps ->
                ps.setString(1, object), rs -> {
            if (rs.next()) {
                fetchedValue.set(true);
            }
            return rs;
        });
        return fetchedValue.get();
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
