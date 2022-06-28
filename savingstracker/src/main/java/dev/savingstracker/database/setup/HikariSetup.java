package dev.savingstracker.database.setup;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class HikariSetup implements ConnectionType {

    private HikariDataSource dataSource;

    protected HikariSetup init(SQLTypes types, DatabaseInfo authentication, int timeout, int poolSize) {
        dataSource = new HikariDataSource(getDataProperties(types, authentication, timeout, poolSize));
        return this;
    }


    public HikariConfig getDataProperties(SQLTypes types, DatabaseInfo authentication, int timeOut, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(types.getDriverName());
        config.setUsername(authentication.user());
        config.setPassword(authentication.password());
        config.setJdbcUrl(generateURL(types.getDriverURL(), authentication));
        config.setConnectionTimeout(timeOut);
        config.setMaximumPoolSize(poolSize);
        return config;
    }

    protected void disconnect() {
        if (dataSource == null) return;
        if (!dataSource.isClosed()) {
            dataSource.close();
        }
    }

    protected HikariSetup createTable(String tableOutput) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(tableOutput)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public String generateURL(String jdurl, DatabaseInfo authentication) {
        String url = jdurl.replace("{host}", authentication.host());
        url = url.replace("{port}", String.valueOf(authentication.port()));
        url = url.replace("{database}", authentication.database());
        return url;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

}
