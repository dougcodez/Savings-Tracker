package dev.savingstracker.database.setup;

public enum SQLTypes {

    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{host}:{port}/{database}"),
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:{database}"),
    POSTGRES("org.postgresql.ds.PGSimpleDataSource", "jdbc:postgresql://{host}:{port}/{database}");

    private final String driverName;
    private final String driverURL;

    SQLTypes(String driverName, String driverURL) {
        this.driverName = driverName;
        this.driverURL = driverURL;
    }

    public static SQLTypes fromName(String name, DatabaseInfo databaseInfoRecord) {
        for (SQLTypes type : values()) {
            if (type.name().equalsIgnoreCase(name))
                return type;
        }
        return SQLITE;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverURL() {
        return driverURL;
    }
}
