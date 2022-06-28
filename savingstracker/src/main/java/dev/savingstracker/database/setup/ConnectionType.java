package dev.savingstracker.database.setup;

import java.sql.Connection;

public interface ConnectionType {

    Connection getConnection();

}
