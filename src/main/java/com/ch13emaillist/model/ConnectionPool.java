package com.ch13emaillist.model;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool;
    private final DataSource dataSource;

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            try {
                dataSource = (DataSource)
                        ic.lookup("java:comp/env/jdbc/murach");
            } finally {
                ic.close();
            }
        } catch (NamingException e) {
            throw new IllegalStateException(
                    "Khong tim thay DataSource jdbc/murach. "
                            + "Kiem tra cau hinh context.xml.", e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Khong lay duoc ket noi MySQL.", e);
        }
    }

    public void freeConnection(Connection c) {
        if (c == null) {
            return;
        }

        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}