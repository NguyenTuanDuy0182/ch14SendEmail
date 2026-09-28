package com.ch13emaillist.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;

public class DBUtil {

    private static final EntityManagerFactory emFactory =
            Persistence.createEntityManagerFactory("emailListPU");

    public static EntityManagerFactory getEmFactory() {
        return emFactory;
    }

    public static void closeEmFactory() {
        if (emFactory.isOpen()) {
            emFactory.close();
        }
    }

    public static void closeStatement(Statement s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void closePreparedStatement(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
