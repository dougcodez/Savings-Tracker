package dev.savingstracker.database;


import dev.savingstracker.database.setup.DatabaseInfo;
import dev.savingstracker.database.setup.HikariSetup;
import dev.savingstracker.database.setup.SQLTypes;
import dev.savingstracker.database.setup.StatementAPI;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

public class SavingsTrackerDatabaseInit extends HikariSetup {

    public SavingsTrackerDatabaseInit init(StatementAPI statementAPI) {
        init(SQLTypes.MYSQL, new DatabaseInfo("localhost", 3306, "savingsdb", "user1", "password123"), 5000, 3);
        statementAPI.setConnectionType(this);
        initTables();
        return this;
    }


    private void initTables() {
        createTable("CREATE TABLE IF NOT EXISTS SAVINGS(ID INTEGER(10) NOT NULL AUTO_INCREMENT, SESSIONID VARCHAR(24), NAME VARCHAR(255), DATE VARCHAR(255), DESCRIPTION VARCHAR(255), AMOUNT INTEGER(10) NOT NULL, SAVINGSAMOUNT INTEGER(10) NOT NULL," +
                " PRIMARY KEY (ID))");
    }

    @Override
    public Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
