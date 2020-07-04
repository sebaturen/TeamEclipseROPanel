package com.eclipse.panel.dbConnect;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {

    public static final String DB_CONTROLLER_NAME = "jdbc/db";

    private final DataSource dataSource;

    public Database(String jndiName) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiName);
        } catch (NamingException e) {
            // Handle error that it's not configured in JNDI.
            throw new IllegalStateException(jndiName + " is missing in JNDI!", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
