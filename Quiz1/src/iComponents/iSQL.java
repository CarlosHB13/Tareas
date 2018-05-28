/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public final class iSQL {

    private String DATABASE_URL = "";
    private String USERNAME = "";
    private String PASSWORD = "";

    private STATUS status;

    private Connection connection;
    private Properties properties;

    public enum STATUS {
        SUCCESS,
        FAIL,
        WARN,
        FATAL,
        CONNECTED,
        UNCONNECTED,
        UNKNOWN
    }

    /**
     * Constructor de la clase SQL Crea una instancia a la base de datos.
     *
     * @param ip database host.
     * @param db database name
     * @param user db user
     * @param pass db password
     */
    public iSQL(String ip, String db, String user, String pass) {
        super();
        this.DATABASE_URL += "jdbc:mysql://" + ip + ":3306/" + db + "?jdbcCompliantTruncation=false&setUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
        this.USERNAME = user;
        this.PASSWORD = pass;
        this.status = STATUS.UNCONNECTED;

        this.connect();
    }

    /**
     *
     * @return
     */
    public STATUS getStatus() {
        return this.status;
    }

    public boolean isNumeric(Object element) {
        try {
            for (int i = 0; i < element.toString().length(); i++) {
                Integer.parseInt(String.valueOf(element.toString().charAt(i)));
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    /**
     * Crea la instancia a la base de datos, luego de cargar la libreria:
     * com.mysql.jdbc.Driver: sin esta libreria no funcionará.
     *
     * @return
     */
    public Connection connect() {
        SwingWorker sw = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        connection = DriverManager.getConnection(DATABASE_URL, getProperties());
                        status = STATUS.CONNECTED;
                    } catch (ClassNotFoundException | SQLException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                        System.exit(0);
                    }
                }
                return true;
            }
        };
        sw.execute();
        return connection;
    }

    /**
     * Retorna si una consulta (SELECT) tiene datos o bien si existe o no.
     *
     * @param rs
     * @return true si existe y-o tiene datos.
     */
    public boolean Exists(ResultSet rs) {
        if (connection == null) {
            return false;
        }
        try {
            return rs.isBeforeFirst();
        } catch (SQLException ex) {
            return false;
        }
    }

    public ArrayList<String> getColumnName(ResultSet rs) {
        try {
            ArrayList<String> cols = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                cols.add(metaData.getColumnLabel(i));
            }

            return cols;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<Integer> getColumnType(ResultSet rs) {
        try {
            ArrayList<Integer> cols = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                cols.add(metaData.getColumnType(i));
            }

            return cols;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Recibe las sentencias que no son select: UPDATE, DELETE, INSERT, DROP ..
     *
     * @param preparedQuery
     * @param objs
     * @return
     */
    public boolean exec(String preparedQuery, ArrayList<Object> objs) {
        PreparedStatement ps;
        try {
            ps = this.connection.prepareStatement(preparedQuery);

            Iterator it = objs.iterator();
            int poc = 1;
            while (it.hasNext()) {
                Object element = it.next();
                if (this.isNumeric(element)) {
                    ps.setInt(poc, Integer.parseInt(element.toString()));
                } else {
                    ps.setString(poc, element.toString());
                }
                poc++;
            }

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException | NumberFormatException ex) {
            //iAlert iA = new iAlert(null, ex.getMessage(), 2);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    public boolean exec(String preparedQuery) {
        PreparedStatement ps;
        try {
            ps = this.connection.prepareStatement(preparedQuery);

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException | NumberFormatException ex) {
            iAlert iA = new iAlert(null, ex.getMessage(), 1);
            //JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    /**
     * Sentencia ùnica para SELECT, retorna un ResultSet, que podrà ser
     * utilizado para crear tablas o manipular informaciòn.
     *
     * @param preparedQuery
     * @param objs
     * @return
     */
    public ResultSet SELECT(String preparedQuery, ArrayList<Object> objs) {
        PreparedStatement ps;
        try {
            if (connection != null) {
                ps = this.connection.prepareStatement(preparedQuery);

                Iterator it = objs.iterator();
                int poc = 1;
                while (it.hasNext()) {
                    Object element = it.next();
                    if (this.isNumeric(element)) {
                        try {
                            ps.setInt(poc, Integer.parseInt(element.toString()));
                        } catch (NumberFormatException ex) {
                            return null;
                        }
                    } else {
                        ps.setString(poc, element.toString());
                    }
                    poc++;
                }

                ResultSet rs = ps.executeQuery();
                return rs;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n tratando de reconectar");
        }
        return null;
    }

    public ResultSet SELECT(String preparedQuery) {
        PreparedStatement ps;
        try {
            ps = this.connection.prepareStatement(preparedQuery);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n tratando de reconectar");
        }
        return null;
    }

    public String DUMP(String schema,String path) {
        try {
            DatabaseMetaData dbMetaData = this.connection.getMetaData();

            StringBuffer result = new StringBuffer();
            ResultSet rs = dbMetaData.getTables(null, schema, null, null);
            if (!rs.next()) 
            {
                System.err.println("Unable to find any matching tables");
                rs.close();
                return result.toString();
            }
            
            do {
                String tableName = rs.getString("TABLE_NAME");
                String tableType = rs.getString("TABLE_TYPE");
                if ("TABLE".equalsIgnoreCase(tableType)) 
                {
                    result.append("\n\n-- Table extructure: ").append(tableName);
                    result.append("\nDROP TABLE IF EXISTS `").append(tableName).append("`;");
                    result.append("\nCREATE TABLE `").append(tableName).append("` (\n");
                    ResultSet tableMetaData = dbMetaData.getColumns(null, null, tableName, "%");
                    boolean firstLine = true;
                    while (tableMetaData.next()) {
                        if (firstLine)
                            firstLine = false;
                        else
                            result.append(",\n");
                       
                        String columnName = tableMetaData.getString("COLUMN_NAME");
                        String columnType = tableMetaData.getString("TYPE_NAME");

                        int columnSize = tableMetaData.getInt("COLUMN_SIZE");
                        String nullable = tableMetaData.getString("IS_NULLABLE");
                        String nullString = "NULL";

                        if ("NO".equalsIgnoreCase(nullable)) {
                            nullString = "NOT NULL";
                        }
                        String tmp = "";
                        if (!columnType.equalsIgnoreCase("DATE"))
                            tmp = "    `" + columnName + "` " + columnType + " (" + columnSize + ")" + " " + nullString;
                        else
                            tmp = "    `" + columnName + "` " + columnType + " " + nullString;
                            
                        result.append(tmp);
                    }
                    tableMetaData.close();

                    try {
                        ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, schema, tableName);
                        
                        String primaryKeyName = null;
                        StringBuffer primaryKeyColumns = new StringBuffer();
                        while (primaryKeys.next()) {
                            String thisKeyName = primaryKeys.getString("PK_NAME");
                            if ((thisKeyName != null && primaryKeyName == null)
                                    || (thisKeyName == null && primaryKeyName != null)
                                    || (thisKeyName != null && !thisKeyName.equals(primaryKeyName))
                                    || (primaryKeyName != null && !primaryKeyName.equals(thisKeyName))) {
                                if (primaryKeyColumns.length() > 0) {
                                    result.append(",\n    PRIMARY KEY ");
                                    if (primaryKeyName != null && !primaryKeyName.equals("PRIMARY")) {
                                        result.append(primaryKeyName);
                                    }
                                    result.append("(").append(primaryKeyColumns.toString()).append(")");
                                }
                                primaryKeyColumns = new StringBuffer();
                                primaryKeyName = thisKeyName;
                            }
                            if (primaryKeyColumns.length() > 0) {
                                primaryKeyColumns.append(", ");
                            }
                            primaryKeyColumns.append(primaryKeys.getString("COLUMN_NAME"));
                        }
                        if (primaryKeyColumns.length() > 0) {
                            result.append(",\n    PRIMARY KEY ");
                            if (primaryKeyName != null && !primaryKeyName.equals("PRIMARY")) {
                                System.out.println(primaryKeyName);
                                result.append(primaryKeyName);
                            }
                            result.append(" (").append(primaryKeyColumns.toString()).append(")");
                        }
                    } catch (SQLException e) {
                        System.err.println("Unable to get primary keys for table " + tableName + " because " + e);
                    }

                    result.append("\n);\n");

                    dumpTable(result, tableName);
                }
            } while (rs.next());
            rs.close();

            return result.toString();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * dump this particular table to the string buffer
     */
    private void dumpTable(StringBuffer result, String tableName) {
        try {
            if (tableName.equals('0'))
                return;
            
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            result.append("\n\n-- Data for ").append(tableName).append("\n");
            result.append("INSERT INTO `").append(tableName).append("` VALUES (");
            boolean firstRow = true;
            while (rs.next()) {
                if (firstRow)
                    firstRow = false;
                else
                    result.append("), (");
                for (int i = 0; i < columnCount; i++) {
                    if (i > 0) {
                        result.append(", ");
                    }
                    Object value = rs.getObject(i + 1);
                    if (value == null) {
                        result.append("NULL");
                    } else {
                        String outputValue = value.toString();
                        outputValue = outputValue.replaceAll("'", "\\'");
                        result.append("'").append(outputValue).append("'");
                    }
                }
            }
            result.append(");\n");

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Unable to dump table " + tableName + " because: " + e);
        }
    }
}
