package Utility;

import java.sql.*;

public class DataBaseUtility
{
    private String jdbcDriver;
    private String dbUrl;
    private String user;
    private String pass;

    public DataBaseUtility(String jdbcDriver, String dbUrl, String user, String pass) {
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.user = user;
        this.pass = pass;
    }

    public Connection getConnection() {
        try {
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(dbUrl, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String update) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void executeStoredProcedure(String procedureCall, Object... params) {
        try (Connection conn = getConnection();
             CallableStatement cstmt = conn.prepareCall(procedureCall)) {
            for (int i = 0; i < params.length; i++) {
                cstmt.setObject(i + 1, params[i]);
            }
            cstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
