package com.epam.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class provides connection pool.
 *
 * @author Solodchenko Nikita
 */
public class ConnectionPool {
    private ConnectionPool() {
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/beautySalon");
            c = ds.getConnection();
            if (c == null) {
                throw new IllegalStateException("Cannot get connection to database");
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot get connection to database");
        }
        return c;
    }

}
